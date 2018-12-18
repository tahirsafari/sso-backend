package com.ssobackend.config.datasource;

public class TenantSchemaContext {
	public static String DEFAULT_TENANT_SCHEMA = "";
	
	public static void setDefaultTenantSchema(String defaultTenantSchema) {
		TenantSchemaContext.DEFAULT_TENANT_SCHEMA = defaultTenantSchema;
	}
	
	private static ThreadLocal<String> currentTenantSchema = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return DEFAULT_TENANT_SCHEMA;
		}
	};

	public static void setCurrentTenantSchema(String tenant) {
		currentTenantSchema.set(tenant);
	}

	public static String getCurrentTenantSchema() {
		return currentTenantSchema.get();
	}
	
	public static void clear() {
		currentTenantSchema.remove();
	}
}
