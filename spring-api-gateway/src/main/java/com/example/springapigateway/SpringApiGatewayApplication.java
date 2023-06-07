package com.example.springapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class SpringApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayApplication.class, args);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        return security.csrf().disable().build();
    }

    @Bean
    public InMemoryHttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
