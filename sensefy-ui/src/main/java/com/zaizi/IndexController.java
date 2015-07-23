package com.zaizi;

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

	
	/*protected static class Resource extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/service/**").permitAll();
		}
	} 
	
	@RequestMapping(value = "/login")
	public String se(Model model,OAuth2Authentication auth) {
		if( SecurityContextHolder.getContext().getAuthentication() != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			
			model.addAttribute("user",auth.getOAuth2Request().getClientId());
			model.addAttribute("principl",auth.getPrincipal());
			
			return "search";
			
		}
		
		return "forward:/";
		
	}*/
	
}
