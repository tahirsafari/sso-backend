package com.ssobackend.config.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ssobackend.config.datasource.TenantSchemaContext;

@Configuration
@PropertySource("classpath:connection-pool.properties")
public class StaticFieldInjectionConfiguration {
	@Autowired
    private org.springframework.core.env.Environment env;
	
	@PostConstruct
    private void init() {
		String defaultSchemaName = env.getProperty("datasource.globalSchemaName");
        TenantSchemaContext.setDefaultTenantSchema(defaultSchemaName);
    }
}
