package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.DeptModel;
import com.atguigu.springcloud.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张斌文
 * @since 2019-12-19
 */
@RestController
@Slf4j
public class DeptController {
    @Autowired
    private DeptService service;
    @Autowired
    private Registration registration; // 服务注册
    @Autowired
    private DiscoveryClient client;// 服务发现
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value="/dept/add",method=RequestMethod.POST)
    public boolean add(@RequestBody DeptModel dept)
    {
        return service.add(dept);
    }

    @RequestMapping(value="/dept/get/{id}",method= RequestMethod.GET)
    public DeptModel get(@PathVariable("id") Long id)
    {
        return service.get(id);
    }

    @RequestMapping(value="/dept/list",method=RequestMethod.GET)
    public List<DeptModel> list()
    {
        return service.findAll();
    }

    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery()
    {
        List<String> list = client.getServices();
        System.out.println("**********" + list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance element : srvList) {
            log.info(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        ServiceInstance instance = serviceInstance();
        String result = "host="+instance.getHost()+","+"port="+instance.getPort()+","+"url=" + instance.getUri()  + ", "
                + "service_id:" + instance.getServiceId();
        log.info(result);
        return "From Service-A , " + result;
    }
    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = client.getInstances(registration.getServiceId());
        if (list != null && list.size() > 0) {
            for(ServiceInstance itm : list){
                if(itm.getPort() == 8001)
                    return itm;
            }
        }
        return null;
    }
}
