package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.DeptModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张斌文
 * @since 2019-12-19
 */
public interface DeptService extends IService<DeptModel> {
    public boolean add(DeptModel dept);
    public DeptModel  get(Long id);
    public List<DeptModel> findAll();

}
