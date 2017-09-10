package com.heiha.demo.springboot.websocket.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Project: <b>spring-boot-websocket-demo</b>
 * Date: <b>2017-09-10 23:01</b>
 * Author: <b>heiha</b>
 */
@Data
@Builder
public class DemoResult {
    private String message;
}
