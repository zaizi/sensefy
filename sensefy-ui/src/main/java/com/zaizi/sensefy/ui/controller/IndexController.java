package com.zaizi.sensefy.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	// Match everything without a suffix (so not a static resource)
	@RequestMapping(value = "/{[path:[^\\.]*}")
	public String redirect() {
		// Forward to home page so that route is preserved.
		return "forward:/";
	}

	// @RequestMapping(value="/logout",method=RequestMethod.POST)
	// public String logout(HttpSecurity http) throws Exception {
	// http.logout();
	//
	// SecurityContextHolder.clearContext();
	// return "forward:/login";
	// }

}
