package com.atguigu.springcloud.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisLock implements Lock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate redisTemplate;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁中断状态
     **/
    private volatile boolean interruped = true;

    /**
     * 持有锁的最长时间
     **/
    private final int expireTime = 300;
    /**
     * 获取不到锁的休眠时间
     **/
    private final long sleepTime = 100;
    /**
     * 超时时间
     **/
    private long expireTimeOut = 0;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    @Override
    public void lock() {
        if (redisTemplate == null)
            throw new NullPointerException("jedis is null");
        if (lockKey == null)
            throw new NullPointerException("key is null");
        while (true) {
            if (!interruped)
                throw new RuntimeException("线程被中断");
            boolean b = this.setNX(lockKey, lockKey);
            // id = false 表示加锁失败
            if (!b)
                try {
                    Thread.currentThread().sleep(this.sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            else {
                // 设置锁过期时间
                expireTimeOut = System.currentTimeMillis() / 1000 + expireTime;
                redisTemplate.expire(this.lockKey, expireTimeOut, TimeUnit.SECONDS);
                break;
            }
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.interruped = false;
    }

    @Override
    public boolean tryLock() {

        if (redisTemplate == null)
            throw new NullPointerException("jedis is null");
        if (lockKey == null)
            throw new NullPointerException("key is null");
        if (!interruped)
            throw new RuntimeException("线程被中断");
        boolean b = this.setNX(lockKey, lockKey);
        // id = false 表示加锁失败
        if (!b)
            return false;
        else {
            // 设置锁过期时间
            expireTimeOut = System.currentTimeMillis() / 1000 + expireTime;
            redisTemplate.expire(this.lockKey, expireTimeOut,TimeUnit.SECONDS);
            return true;
        }

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (redisTemplate == null)
            throw new NullPointerException("jedis is null");
        if (lockKey == null)
            throw new NullPointerException("key is null");
        if (time == 0)
            return false;
        long now = System.currentTimeMillis();
        long timeOutAt = now + calcSeconds(time, unit);
        while (true) {
            if (!interruped)
                throw new InterruptedException("线程被中断");
            Boolean id = this.setNX(this.lockKey, this.lockKey);
            // id = false 表示加锁失败
            if (!id) {
                // 获取锁超时
                if (System.currentTimeMillis() > timeOutAt)
                    return false;
                try {
                    // 休眠一段时间，继续获取锁
                    Thread.currentThread().sleep(this.sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //获取锁成功，设置锁过期时间
                expireTimeOut = System.currentTimeMillis() / 1000 + expireTime;
                redisTemplate.expire(this.lockKey, expireTimeOut, TimeUnit.SECONDS);
                return true;
            }
        }
    }

    @Override
    public void unlock() {
        try {
            //当前时间小于过期时间,则锁未超时,删除锁定
            if (System.currentTimeMillis() / 1000 < expireTimeOut)
                redisTemplate.delete(this.lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("不支持当前的操作");
    }


    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * 时间转换成毫秒
     * @param time
     * @param unit
     * @return
     */
    private long calcSeconds(long time, TimeUnit unit) {
        if (unit == TimeUnit.DAYS)
            return time * 24 * 60 * 60 * 1000;
        else if (unit == TimeUnit.HOURS)
            return time * 60 * 60 * 1000;
        else if (unit == TimeUnit.MINUTES)
            return time * 60 * 1000;
        else
            return time * 1000;
    }
}
