package com.atguigu.springcloud.util;

import org.springframework.context.ConfigurableApplicationContext;

/**
* Description:
* @author: zhangbinwen
* @date: 2019/12/20 10:59
* @param:
* @return:
*/
public class WebfluxContext {

    private static WebfluxContext instance;


    private ConfigurableApplicationContext applicationContext;

    private WebfluxContext() {

    }

    private WebfluxContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static WebfluxContext newInstance(ConfigurableApplicationContext applicationContext) {
        if (null == instance) {
            synchronized (SpringBeanUtils.class) {
                if (null == instance) {
                    instance = new WebfluxContext(applicationContext);
                }
            }
        }
        return instance;
    }

    public static WebfluxContext getInstance() throws NullPointerException {
        if (null != instance) {
            return instance;
        } else {
            return null;
        }
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
