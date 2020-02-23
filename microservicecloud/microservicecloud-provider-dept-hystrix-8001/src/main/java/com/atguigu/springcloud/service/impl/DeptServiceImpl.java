package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.DeptMapper;
import com.atguigu.springcloud.entities.DeptModel;
import com.atguigu.springcloud.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张斌文
 * @since 2019-12-19
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptModel> implements DeptService {
    @Autowired
    private DeptMapper dao ;

    @Override
    public boolean add(DeptModel dept) {
        return dao.addDept(dept);
    }

    @Override
    public DeptModel get(Long id) {
        return dao.findById(id);
    }

    @Override
    //@Cacheable(value = "usercache",keyGenerator = "wiselyKeyGenerator")
    public List<DeptModel> findAll() {
        System.out.println("执行了吗？");
        return dao.findAll();
    }
}
