package com.example.springuserservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
public class UserController {

    @GetMapping("/do")
    public String doIt() {
        return "test";
    }
}
