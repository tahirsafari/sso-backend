package com.ssobackend.domain.dto;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class CreateUserDTO {
	private String username;
	private long employeeId;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserRoleDTO.class)
	private Set<UserRoleDTO> roles;

	public CreateUserDTO() {

	}
	
	public CreateUserDTO(String username, long employeeId) {
		this.username = username;
		this.setEmployeeId(employeeId);
	}
	
	@NotBlank(message="user.createUser.emptyUsername")
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public Set<UserRoleDTO> getRoles() {
		if (this.roles == null)
			this.roles = new HashSet<UserRoleDTO>();
		return this.roles;
	}

	public void setRoles(Set<UserRoleDTO> authorities) {
		this.roles = authorities;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
}
