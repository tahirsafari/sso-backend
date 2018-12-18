package com.ssobackend.config.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.ssobackend.domain.model.Role;
import com.ssobackend.domain.model.User;
import com.ssobackend.domain.service.UserDetailsServiceImpl;

public class OAuth2TokenEnhancer implements TokenEnhancer {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Value("${backendApp.user.passwordExpiresInDays}")
	private int passwordExpiresInDays;
//	@Autowired
//	private CalendarService calendarService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userDetailsService.findByUsername(authentication.getName());
		
		final Map<String, Object> additionalInfo = new HashMap<>();
		
		//Tenant tenant = RequestContext.getCurrentTenant();
		
//		if (tenant != null) {
//			additionalInfo.put("companyName", tenant.getName());
//		}
		
		List<String> authorities = new ArrayList<String>();
		List<String> permissions = new ArrayList<>();
		
		for (Role role : user.getAuthorities()) {
			authorities.add(role.getAuthority());
			permissions.addAll(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}

//		additionalInfo.put("name", user.getRefEmployee().getFullName());
//		additionalInfo.put("email", user.getRefEmployee().getEmail());
		additionalInfo.put("roles", authorities);
		additionalInfo.put("permissions", permissions);
		additionalInfo.put("username", user.getUsername());
		
//		LocalDate fromDate;
//		LocalDate toDate = LocalDate.now();
//
//		if (user.getPasswordChangedDate() == null) {
//			fromDate = calendarService.convertFromDateToLocalDate(user.getCreatedDate());
//		} else {
//			fromDate = calendarService.convertFromDateToLocalDate(user.getPasswordChangedDate());
//		}

//		long diff = java.time.temporal.ChronoUnit.DAYS.between(toDate, fromDate.plusDays(passwordExpiresInDays));
//		additionalInfo.put("passwordExpiryDays", diff);
//		
//		if(user.getRefEmployee().getDesignation() != null)
//			additionalInfo.put("designation", user.getRefEmployee().getDesignation().getName());
//		if(user.getRefEmployee().getDepartment() != null)
//			additionalInfo.put("department", user.getRefEmployee().getDepartment().getName());
//		
//		if(user.getRefEmployee() != null && user.getRefEmployee().getDistribution() != null){
//			Map<String, Object> distMap = new HashMap<>();
//			distMap.put("id", user.getRefEmployee().getDistribution().getId());
//			distMap.put("name", user.getRefEmployee().getDistribution().getName());
//			
//			additionalInfo.put("distribution", distMap);
//		}
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		
		return accessToken;
	}
}
