package com.ssobackend.config.security;

import com.ssobackend.domain.model.User;

public class RequestContext {
	
	
	private static ThreadLocal<User> currentUser = new ThreadLocal<User>(){
		@Override
		protected User initialValue(){
			return new User();
		}
	};
	
	public static void clear() {
		currentUser.remove();
	}
//
//	public static Tenant getCurrentTenant() {
//		return currentTenant.get();
//	}
//
//	public static void setCurrentTenant(Tenant client) {
//		currentTenant.set(client);
//	}

	public static User getCurrentUser() {
		return currentUser.get();
	}

	public static void setCurrentUser(User user) {
		currentUser.set(user);
	}
}