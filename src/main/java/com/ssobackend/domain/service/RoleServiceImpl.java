package com.ssobackend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssobackend.domain.model.QRole;
import com.querydsl.core.types.Predicate;
import com.ssobackend.domain.model.Permission;
import com.ssobackend.domain.model.Role;
import com.ssobackend.domain.repository.PermissionRepository;
import com.ssobackend.domain.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public List<Role> findAllRoles(){
		return roleRepository.findAll();
	}

	@Override
	public boolean updateRole(Role auth) {
		roleRepository.save(auth);
		return true;
	}

	@Override
	public boolean isRoleDuplicate(String roleName) {
		QRole role = QRole.role;
		Predicate where = role.authority.equalsIgnoreCase(roleName);
		
		return roleRepository.exists(where);
	}

	@Override
	public Role findRoleById(Long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public void createRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public boolean isPermissionValid(String permissionId) {
		return permissionRepository.exists(permissionId);
	}

	@Override
	public Permission findPermissionById(String permissionId) {
		return permissionRepository.findOne(permissionId);
	}

	@Override
	public List<Permission> findAllPermissions() {
		return permissionRepository.findAll();
	}
}
