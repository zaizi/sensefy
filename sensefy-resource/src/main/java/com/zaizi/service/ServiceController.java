package com.zaizi.service;

import java.util.UUID;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.zaizi.dto.Hello;

@RestController
@EnableOAuth2Resource
@EnableAutoConfiguration
@EnableResourceServer
public class ServiceController extends WebMvcAutoConfigurationAdapter {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@JsonView(Hello.class)
	public @ResponseBody Hello home() {
		return new Hello(UUID.randomUUID().toString(), "Hello World");
	}

}
