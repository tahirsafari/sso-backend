package com.ssobackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssobackend.domain.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
}
