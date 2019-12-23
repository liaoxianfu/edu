package com.liao.edu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liao
 * create at 2019/12/4 18:49
 */

@SpringBootApplication(scanBasePackages = {"com.liao.edu.common", "com.liao.edu.oss"}
        , exclude = DataSourceAutoConfiguration.class // 解除自动配置datasource
)
// 开启eureka客户端
@EnableEurekaClient
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
