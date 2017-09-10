package com.heiha.demo.springboot.websocket.web;

import com.heiha.demo.springboot.websocket.vo.DemoResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: <b>spring-boot-websocket-demo</b>
 * Date: <b>2017-09-10 11:53</b>
 * Author: <b>heiha</b>
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/message")
    public DemoResult message() {
        return DemoResult.builder().message("Hello Message").build();
    }

    @GetMapping("/add")
    public Long add(@RequestParam String params) {
        String[] paramsArray = params.split(",");
        Long result = 0L;
        for (String s : paramsArray) {
            result += Long.parseLong(s);
        }
        return result;
    }

}
