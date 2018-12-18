package com.ssobackend.domain.service;

import java.util.List;

import com.ssobackend.domain.model.Privilege;

public interface PrivilegeService {
	Privilege findPrivilegeByName(String name);
	Privilege findPrivilegeByUrl(String url);
	List<Privilege> findAll();
}
