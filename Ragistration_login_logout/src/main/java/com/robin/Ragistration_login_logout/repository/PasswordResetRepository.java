package com.robin.Ragistration_login_logout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robin.Ragistration_login_logout.entity.PasswordResetToken;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

}
