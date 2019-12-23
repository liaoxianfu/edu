package com.liao.edu.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liao
 * @since 2019/12/21 14:53
 */
@EnableEurekaClient
@SpringBootApplication
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}
