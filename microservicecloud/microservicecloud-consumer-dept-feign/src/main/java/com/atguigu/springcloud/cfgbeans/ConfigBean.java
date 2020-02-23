package com.atguigu.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName: ConfigBean
 * Description:
 * date: 2020/1/1 17:38
 *
 * @author zhangbinwen
 * @since JDK 1.8
 */
@Configuration
public class ConfigBean {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate()
    {

        return new RestTemplate();
    }
    //自己内部算法
    public IRule myRule(){
        //return new RoundRobinRule();//轮询
        //return new RandomRule();//随机
        return new RetryRule();//先轮询再可用
    }
}
