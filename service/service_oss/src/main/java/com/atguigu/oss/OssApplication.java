package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jiang zhongxun
 * @create 2022-11-16 22:27
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)     // 此属性值表示默认不加载数据库配置
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient      // nacos 注册
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
