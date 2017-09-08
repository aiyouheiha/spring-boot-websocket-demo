package com.heiha.demo.springboot.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <br>
 * <b>Project:</b> spring-boot-websocket-demo<br>
 * <b>Date:</b> 2017/9/8 15:10<br>
 * <b>Author:</b> heiha<br>
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
