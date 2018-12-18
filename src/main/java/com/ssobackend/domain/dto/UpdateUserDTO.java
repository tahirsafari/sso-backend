package com.ssobackend.domain.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class UpdateUserDTO {
	private long id;
	private boolean enabled;
	private boolean accountLocked;
	private boolean credentialsExpired;
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserRoleDTO.class)
	private Set<UserRoleDTO> roles;
	
	public UpdateUserDTO(){
		
	}
	public UpdateUserDTO(Long id, boolean enabled, boolean accountNonLocked, boolean credentialsNonExpired) {
		this.id = id;
		this.enabled = enabled;
		this.accountLocked = accountNonLocked;
		this.credentialsExpired = credentialsNonExpired;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	public Set<UserRoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRoleDTO> roles) {
		this.roles = roles;
	}
}
