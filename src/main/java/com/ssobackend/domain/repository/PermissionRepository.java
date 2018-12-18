package com.ssobackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssobackend.domain.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
