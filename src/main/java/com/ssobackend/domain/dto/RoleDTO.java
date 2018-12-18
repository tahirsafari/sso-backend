package com.ssobackend.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
	private Long id;
    private String name;
	private String description;
	private Boolean enabled;
	
    private List<PermissionDTO> permissions;

    public RoleDTO() {

		this.permissions = new ArrayList<>();
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}
}
