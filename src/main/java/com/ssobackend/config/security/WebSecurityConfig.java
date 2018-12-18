package com.ssobackend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	public void configure(WebSecurity registry){
		registry
			.ignoring()
			.antMatchers("/docs/**")
			.antMatchers("/actuator/**")
			.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", 
	    			"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}
}
