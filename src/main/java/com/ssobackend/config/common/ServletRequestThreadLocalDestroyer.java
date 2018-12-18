package com.ssobackend.config.common;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssobackend.config.datasource.TenantSchemaContext;
import com.ssobackend.config.security.RequestContext;

public class ServletRequestThreadLocalDestroyer implements ServletRequestListener {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		RequestContext.clear();
		TenantSchemaContext.clear();
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
	}

}
