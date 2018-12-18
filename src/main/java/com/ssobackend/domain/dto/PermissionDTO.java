package com.ssobackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionDTO {
	private String id;
	private String description;
	
	
	@JsonProperty("name")
	public String getAuthority() {
		return id;
	}
	
	public void setAuthority(String authority){
		this.id = authority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
