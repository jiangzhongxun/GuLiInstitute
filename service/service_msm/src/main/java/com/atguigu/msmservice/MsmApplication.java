package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiang zhongxun
 * @create 2022-11-26 13:33
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)     //取消数据源自动配置
@ComponentScan({"com.atguigu"})
@EnableDiscoveryClient      // nacos 注册
public class MsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }

}
