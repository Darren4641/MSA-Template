package com.example.springpostservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringPostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPostServiceApplication.class, args);
    }

}
