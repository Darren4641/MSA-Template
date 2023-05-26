package com.example.springapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class SpringApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayApplication.class, args);
    }

}
