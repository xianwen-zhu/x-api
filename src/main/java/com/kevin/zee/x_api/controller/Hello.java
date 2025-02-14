package com.kevin.zee.x_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Hello {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!11111";
    }
}