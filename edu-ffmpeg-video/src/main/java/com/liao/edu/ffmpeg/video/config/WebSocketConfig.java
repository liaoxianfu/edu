package com.liao.edu.ffmpeg.video.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author liao
 * @since 2020/2/6 11:32
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter getServerEndpoint() {
        return new ServerEndpointExporter();
    }
}
