package com.atguigu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ClassName: EurekaServer7003_App
 * Description:
 * date: 2020/2/19 20:44
 *
 * @author zhangbinwen
 * @since JDK 1.8
 */

@SpringBootApplication
@EnableEurekaServer//EurekaServer服务器端启动类,接受其它微服务注册进来
@Slf4j
public class EurekaServer7003_App
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaServer7003_App.class, args);
        log.info("Start Success");
    }
}
