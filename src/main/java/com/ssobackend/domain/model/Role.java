package com.ssobackend.domain.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String authority;
	private String description;
	private boolean enabled;
	
	private List<Permission> permissions;
	
	public Role() {}

	public Role(String name, String description) {
		this.authority = name;
		this.description = description;
		this.enabled = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@NotNull
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String name) {
		this.authority = name;
	}
	
	@NotNull
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission", 
				joinColumns = @JoinColumn(name = "role_id"),
				inverseJoinColumns = @JoinColumn(name = "permission_id"),
				uniqueConstraints={@UniqueConstraint(
									columnNames={"role_id", "permission_id"})}
				)
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}