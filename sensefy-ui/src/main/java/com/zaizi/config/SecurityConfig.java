package com.zaizi.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER").and().withUser("fahiz")
				.password("pass").roles("USER");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/box/**").hasRole("USER").anyRequest().permitAll()
				.and().logout().logoutSuccessUrl("/login").permitAll().and().formLogin()
				.loginProcessingUrl("/login").loginPage("/login").failureUrl("/login?authentication_error=true")
				.permitAll();
	}

}
