package com.ssobackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.ssobackend.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>, QueryDslPredicateExecutor<Role>{
	Role findByAuthority(String authority);
}
