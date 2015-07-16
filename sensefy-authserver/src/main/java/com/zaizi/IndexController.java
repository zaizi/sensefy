package com.zaizi;

import java.security.Principal;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Component
@Controller
public class IndexController extends WebMvcAutoConfigurationAdapter {

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		//registry.addViewController("/oauth/confirm_access").setViewName("authorize");
		
	}

	
}
