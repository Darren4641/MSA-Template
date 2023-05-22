package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    @RequestMapping("/")
    public String getInfo() {
        return "Post-Service";
    }

    @GetMapping("/welcome")
    public String doUser() {
        return "Post-doIt";
    }
}
