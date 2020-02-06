package com.liao.edu.ffmpeg.video.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author liao
 * @since 2020/2/5 13:35
 */
@Slf4j
@Configuration
public class MinioClientAutoConfig {

    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.access-key}")
    String accessKey;
    @Value("${minio.secret-key}")
    String secretKey;


    @Bean
    public MinioClient getMinioClient() {
        try {
            if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(secretKey)) {
                throw new RuntimeException("配置MinioClient出错");
            }
            log.info("初始化MinioClient");
            return new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException | InvalidPortException e) {
            throw new RuntimeException("初始化Minio失败");
        }
    }
}
