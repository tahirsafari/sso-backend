package com.ssobackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.ssobackend.domain.model.FailedAuthenticationLog;

public interface FailedAuthenticationLogRepository 
			extends JpaRepository<FailedAuthenticationLog, Long>,
					QueryDslPredicateExecutor<FailedAuthenticationLog>{

}
