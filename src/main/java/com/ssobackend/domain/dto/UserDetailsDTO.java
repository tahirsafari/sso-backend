package com.ssobackend.domain.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class UserDetailsDTO {
	private Long id;
	private String username;
	private Date createdOn;
	private String createdBy;
	
	private boolean enabled;
	private boolean accountLocked;
	private boolean accountExpired;
	private boolean credentialsExpired;

	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserRoleDTO.class)
	private List<UserRoleDTO> roles;
	
	public UserDetailsDTO(){
		
	}
	public UserDetailsDTO(Long id, String username,
			Date createdOn, String createdBy, boolean enabled, boolean accountLocked, boolean accountExpired,
			boolean credentialsExpired) {

		this.id = id;
		this.username = username;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.enabled = enabled;
		this.accountLocked = accountLocked;
		this.accountExpired = accountExpired;
		this.credentialsExpired = credentialsExpired;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date timeStamp) {
		this.createdOn = timeStamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<UserRoleDTO> getRoles() {
		if (this.roles == null)
			this.roles = new ArrayList<UserRoleDTO>();
		return this.roles;
	}

	public void setRoles(List<UserRoleDTO> rolesDtos) {
		this.roles = rolesDtos;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	
	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
}
