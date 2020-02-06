package com.liao.edu.ffmpeg.video.config;

import com.liao.edu.ffmpeg.video.utils.FileStorage;
import com.liao.edu.ffmpeg.video.utils.impl.MinioFileStorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置FileStorage的实现类 默认使用Minio实现分布式存储
 * 如果需要换用其他的存储系统 可以实现FileStorage接口
 *
 * @author liao
 * @since 2020/2/5 15:01
 */
@Configuration
public class FileStorageAutoConfig {

    @Bean
    public FileStorage setFileStorage() {
        return new MinioFileStorageImpl();
    }

}
