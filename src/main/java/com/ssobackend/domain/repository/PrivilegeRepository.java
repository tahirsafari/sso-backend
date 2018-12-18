package com.ssobackend.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.ssobackend.domain.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, QueryDslPredicateExecutor<Privilege> {
	Privilege findByName(String name);
	Privilege findByUrl(String url);
	List<Privilege> findOneByName(String name);
}
