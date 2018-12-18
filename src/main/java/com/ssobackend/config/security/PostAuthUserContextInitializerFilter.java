package com.ssobackend.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssobackend.domain.model.User;
import com.ssobackend.domain.service.UserDetailsServiceImpl;

public class PostAuthUserContextInitializerFilter extends OncePerRequestFilter {
	private UserDetailsServiceImpl userService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PostAuthUserContextInitializerFilter(UserDetailsServiceImpl userService){
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(SecurityContextHolder.getContext().getAuthentication() != null && 
				SecurityContextHolder.getContext().getAuthentication() instanceof org.springframework.security.oauth2.provider.OAuth2Authentication){
			
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.findByUsername(userName);
			
			RequestContext.setCurrentUser(user);
			
			logger.info("User Context has been Set.");
		}
		
		filterChain.doFilter(request, response);
	}
}
