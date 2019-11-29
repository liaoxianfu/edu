package com.liao.edu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author liao
 */
@SpringBootApplication(scanBasePackages = {"com.liao.edu.service", "com.liao.edu.common"})
public class EduMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduMicroServiceApplication.class, args);
    }
}
