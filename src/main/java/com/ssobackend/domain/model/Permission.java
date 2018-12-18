package com.ssobackend.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name="permission")
public class Permission implements GrantedAuthority {
	private static final long serialVersionUID = 6198923344523640526L;
	
	private String id;
	private String description;
	
	public Permission(){}
	
	public Permission(String name, String description){
		this.id = name;
		this.description = description;
	}
	
	@Id
	@Override
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
