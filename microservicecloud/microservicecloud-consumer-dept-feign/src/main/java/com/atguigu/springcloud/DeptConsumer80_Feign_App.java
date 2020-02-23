package com.atguigu.springcloud;

import com.atguigu.springcloud.myrule.MySelfRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: DeptConsumer80_App
 * Description:
 * date: 2020/1/1 19:22
 *
 * @author zhangbinwen
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
@EnableFeignClients(basePackages= {"com.atguigu.springcloud"})
@ComponentScan("com.atguigu.springcloud")
public class DeptConsumer80_Feign_App {
    public static void main(String[] args)
    {
        SpringApplication.run(DeptConsumer80_Feign_App.class, args);
        log.info("Start Success");
    }

}
