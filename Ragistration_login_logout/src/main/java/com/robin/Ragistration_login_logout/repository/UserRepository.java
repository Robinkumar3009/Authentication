package com.robin.Ragistration_login_logout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robin.Ragistration_login_logout.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String email);

}
