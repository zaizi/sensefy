package com.zaizi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;


@SpringBootApplication
@EnableOAuth2Resource
public class SensefyResourceApplication{

    public static void main(String[] args) {
        SpringApplication.run(SensefyResourceApplication.class, args);
    }
    
}
