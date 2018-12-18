package com.ssobackend.config.security;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssobackend.config.datasource.TenantSchemaContext;
import com.ssobackend.domain.model.User;
import com.ssobackend.domain.service.UserDetailsServiceImpl;
import com.ssobackend.exception.InvalidTenantException;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Value("${backendApp.user.maxAllowedFailedAttempts}")
	private int maxAllowedAttempts;
	@Value("${backendApp.user.passwordExpiresInDays}")
	private int passwordExpiresInDays;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
//	@Autowired
//	private TenantService tenantService;
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		//=========== Set the current Tenant Context for Authentication of user=======//
		setTenantContextForAuthentication();
		
		//========== Start Authentication ===========================================//
		BCryptPasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		User customUser = userDetailsService.findByUsername(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			log.info("username not found");
			throw new BadCredentialsException("Username not found.");
		}

		else if (StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Provide passsword.");
		}
		
		else if (!passwordEnocder.matches(password, user.getPassword())) {
			log.info("Wrong password provided for username: "+username);
			int failedAttempts = userDetailsService.updateFailedLoginAttempts(customUser.getId());
			
			if (failedAttempts % maxAllowedAttempts == 0) {
				userDetailsService.lockUserAccount(customUser.getId());
			}
			throw new BadCredentialsException("Wrong password.");
		}

		else if (!user.isAccountNonExpired()) {
			log.info("Account has been expired for username: "+username);
			throw new AccountExpiredException("Account has been expired.");
		}

		else if (!user.isEnabled()) {
			log.info("Account has been disabled for username: "+username);
			throw new DisabledException("User has been disabled.");
		}

		else if (!user.isAccountNonLocked()) {
			log.info("Account has been locked for username: "+username);
			throw new LockedException("Account has been locked.");
		}

		else if (!user.isCredentialsNonExpired()) {
			log.info("Account has been locked for username: "+username);
			throw new CredentialsExpiredException("Password has Expired.");
		}
		
		userDetailsService.resetFailedLoginAttempts(customUser.getId());
		log.info("User with username: "+username+" has been authenticated");
		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	}
	
	private void setTenantContextForAuthentication(){
		String tenantId = request.getHeader("tenantId");
		if (StringUtils.isBlank(tenantId)) {
			throw new InvalidTenantException("Specify tenantId in request headers.");
		}
		
//		Tenant tenant = tenantService.getTenantByTenantIdentifier(tenantId);
//		if (tenant == null) {
//			throw new InvalidTenantException("Invalid tenantId specified.");
//		}
//		TenantSchemaContext.setCurrentTenantSchema(tenant.getTenantSchemaName());
//		RequestContext.setCurrentTenant(tenant);
	}
}
