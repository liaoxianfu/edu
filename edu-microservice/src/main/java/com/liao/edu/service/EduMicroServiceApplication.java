package com.liao.edu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @author liao
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.liao.edu.service", "com.liao.edu.common"})
public class EduMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduMicroServiceApplication.class, args);
    }
}
