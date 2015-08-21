package com.zaizi.sensefy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot initializer
 * @author mfahiz
 * @since 2.0
 */
@SpringBootApplication
@EnableOAuth2Resource
@EnableAutoConfiguration
public class SensefyResourceApplication{

    public static void main(String[] args) {
        SpringApplication.run(SensefyResourceApplication.class, args);
    }
    
}
