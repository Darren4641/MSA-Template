package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/")
    public String getInfo() {
        return "User-Service";
    }

    @GetMapping("/welcome")
    public String doUser() {
        return "User-doIt";
    }

}
