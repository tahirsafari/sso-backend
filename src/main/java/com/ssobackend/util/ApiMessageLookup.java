package com.ssobackend.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApiMessageLookup {
	@Autowired
	MessageSource messageSource;	
	
	public String getMessage(String code, Object[] args){
		return messageSource.getMessage(code, args, Locale.ENGLISH);
	}
	
	public String getMessage(String code){
		return messageSource.getMessage(code, null, Locale.ENGLISH);
	}
}
