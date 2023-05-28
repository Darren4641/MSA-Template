package com.example.springuserservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class UserController {

    Environment env;

    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/do")
    public String doIt() {
        return "test";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header, HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort());
        return String.format("This is a message from First Service on PORT $s",
                env.getProperty("local.server.port"));
    }
}
