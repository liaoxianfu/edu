package com.liao.edu.ffmpeg.video;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author liao
 * @since 2020/2/6 16:29
 */
@EnableRabbit
@SpringBootApplication
public class FfmpegVideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FfmpegVideoApplication.class);
    }
}
