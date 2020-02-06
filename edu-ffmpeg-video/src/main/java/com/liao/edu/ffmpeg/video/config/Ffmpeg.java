package com.liao.edu.ffmpeg.video.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liao
 * @since 2020/2/4 22:19
 */
@Component
@PropertySource(value = "classpath:application.yml")
@ConfigurationProperties(prefix = "ffmpeg")
@Data
@ToString
public class Ffmpeg {
    private String uploadPath;
    private String exePath;
    private String exchange;
    private String routingKey;
    private String[] queues;
}
