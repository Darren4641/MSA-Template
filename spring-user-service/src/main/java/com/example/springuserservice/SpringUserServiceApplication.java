package com.example.springuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringUserServiceApplication.class, args);
    }

}
