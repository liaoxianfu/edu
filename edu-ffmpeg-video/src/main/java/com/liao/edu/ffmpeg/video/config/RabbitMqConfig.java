package com.liao.edu.ffmpeg.video.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liao
 * @since 2020/2/4 19:42
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter setMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
