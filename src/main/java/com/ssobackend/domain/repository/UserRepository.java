package com.ssobackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.ssobackend.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
	User findByUsername(String username);
}