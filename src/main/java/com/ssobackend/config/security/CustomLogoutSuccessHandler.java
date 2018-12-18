package com.ssobackend.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ssobackend.util.ApiJsonResponse;


/**
 * Spring Security logout handler
 */
@Component
public class CustomLogoutSuccessHandler
        extends AbstractAuthenticationTargetUrlRequestHandler
        implements LogoutSuccessHandler {


    private static final String BEARER_AUTHENTICATION = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "authorization";
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
    	
    	ApiJsonResponse apiJsonResponse;
    	
        String tokenHeader = request.getHeader(HEADER_AUTHORIZATION);
        boolean loggedOut = false;
        
        if (tokenHeader != null && tokenHeader.startsWith(BEARER_AUTHENTICATION)) {
        	String token = ((tokenHeader.split(" ")[1]).replaceAll("\\s+",""));
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
            
            if (oAuth2AccessToken != null) {
            	
            	OAuth2Authentication auth = tokenStore.readAuthentication(oAuth2AccessToken);
            	
            	String clientId = null, username = null;
            	
            	if(auth != null){
            		clientId = auth.getOAuth2Request().getClientId();
            		username = auth.getName();
            	}
            	
            	logger.info("User with username: "+username+" and application client "+clientId+" has been logged out.");
                tokenStore.removeAccessToken(oAuth2AccessToken);
                
                apiJsonResponse = new ApiJsonResponse(true, "User with username: "+username+" has been logged out.");
                Gson gson = new Gson();
                String gsonResponse = gson.toJson(apiJsonResponse);
                
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(gsonResponse);
                response.setStatus(HttpServletResponse.SC_OK);
                loggedOut = true;
            }
        }
        
        if(!loggedOut){
        	apiJsonResponse = new ApiJsonResponse(false, "Invalid Bearer -Either the Bearer token is invalid or the session for the posted Bearer token has already been expired.");
            Gson gson = new Gson();
            String gsonResponse = gson.toJson(apiJsonResponse);
            
        	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(gsonResponse);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
