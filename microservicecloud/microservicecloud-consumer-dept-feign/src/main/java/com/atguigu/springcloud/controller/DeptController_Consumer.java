package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.DeptModel;
import com.atguigu.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * ClassName: DeptController_Consumer
 * Description:
 * date: 2020/1/1 19:01
 *
 * @author zhangbinwen
 * @since JDK 1.8
 */
@RestController
public class DeptController_Consumer
{
    @Autowired
    private DeptClientService service;

    @RequestMapping(value = "/consumer/dept/get/{id}")
    public DeptModel get(@PathVariable("id") Long id)
    {
        return this.service.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list")
    public List<DeptModel> list()
    {
        System.out.println("feign进来得");
        return this.service.list();
    }

    @RequestMapping(value = "/consumer/dept/add")
    public Object add(DeptModel dept)
    {
        return this.service.add(dept);
    }
}
