package com.liao.edu.service.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liao
 * @since 2019/12/24 15:46
 */


@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class EduMicroServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduMicroServiceFeignApplication.class, args);
    }
}
