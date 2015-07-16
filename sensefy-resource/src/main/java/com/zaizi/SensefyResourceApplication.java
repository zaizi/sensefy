package com.zaizi;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.zaizi.dto.Hello;


@SpringBootApplication
@RestController
@EnableAutoConfiguration
@EnableResourceServer
public class SensefyResourceApplication extends WebMvcAutoConfigurationAdapter {

	
	
    public static void main(String[] args) {
        SpringApplication.run(SensefyResourceApplication.class, args);
    }
    
    @RequestMapping("/")
	@JsonView(Hello.class)
	public @ResponseBody Hello home() {
		return new Hello(UUID.randomUUID().toString(), "Hello World");
	}

    
}
