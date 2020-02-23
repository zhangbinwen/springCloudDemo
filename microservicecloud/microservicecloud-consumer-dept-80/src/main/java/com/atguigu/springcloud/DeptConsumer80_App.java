package com.atguigu.springcloud;

import com.atguigu.springcloud.myrule.MySelfRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

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
//在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration= MySelfRule.class)
public class DeptConsumer80_App {
    public static void main(String[] args)
    {
        SpringApplication.run(DeptConsumer80_App.class, args);
        log.info("Start Success");
    }

}
