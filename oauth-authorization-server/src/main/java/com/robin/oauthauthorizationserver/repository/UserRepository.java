package com.robin.oauthauthorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robin.oauthauthorizationserver.entity.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String email);

}
