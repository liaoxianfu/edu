package com.liao.edu.video.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liao
 * @since 2019/12/23 18:23
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class VideoFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoFeignApplication.class,args);
    }

}
