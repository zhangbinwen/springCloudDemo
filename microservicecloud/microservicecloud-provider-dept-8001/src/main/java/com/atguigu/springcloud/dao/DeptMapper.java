package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.DeptModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张斌文
 * @since 2019-12-19
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptModel> {
    public boolean addDept(DeptModel dept);

    public DeptModel findById(Long id);

    public List<DeptModel> findAll();

}
