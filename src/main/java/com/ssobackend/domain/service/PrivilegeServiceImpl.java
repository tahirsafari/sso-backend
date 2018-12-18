package com.ssobackend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssobackend.domain.model.Privilege;
import com.ssobackend.domain.repository.PrivilegeRepository;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	
	@Autowired
	private PrivilegeRepository privilegeRepo;
	
	@Override
	public Privilege findPrivilegeByName(String name) {
		return privilegeRepo.findByName(name);
	}

	@Override
	public Privilege findPrivilegeByUrl(String url) {
		return privilegeRepo.findByUrl(url);
	}

	@Override
	public List<Privilege> findAll() {
		return privilegeRepo.findAll();
	}
	
}
