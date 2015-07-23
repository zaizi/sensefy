package com.zaizi.controller;

import java.util.UUID;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zaizi.dto.Hello;

@RestController
public class ServiceController extends WebMvcAutoConfigurationAdapter {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Hello home() {
		
		return new Hello(UUID.randomUUID().toString(), "Hello World");
		
		
	}

}
