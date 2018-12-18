package com.ssobackend.domain.service;

import java.util.List;

import com.ssobackend.domain.model.Permission;
import com.ssobackend.domain.model.Role;

public interface RoleService {
	List<Role> findAllRoles();
	boolean updateRole(Role auth);
	boolean isRoleDuplicate(String roleName);
	Role findRoleById(Long id);
	void createRole(Role role);
	boolean isPermissionValid(String permissionId);
	Permission findPermissionById(String permissionId);
	List<Permission> findAllPermissions();
}
