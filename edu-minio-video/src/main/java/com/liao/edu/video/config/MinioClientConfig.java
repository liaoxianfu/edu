package com.liao.edu.video.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;

/**
 * @author liao
 * @since 2019/12/21 16:08
 */
@Configuration
public class MinioClientConfig {
    @Value("${minio.oss.endpoint}")
    private String endpoint;
    @Value("${minio.oss.accessKey}")
    private String accessKey;
    @Value("${minio.oss.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public MinioClient createMinioClient() {
        try {
            if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeySecret)) {
                throw new RuntimeException("配置MinioClient出错");
            }
            return new MinioClient(endpoint, accessKey, accessKeySecret);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化Minio出错");
        }
    }

}
