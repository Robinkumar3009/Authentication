package com.robin.Ragistration_login_logout.model;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	private String firstName;
	private String lastName;
	private String email;
	@Column(length=60)
	private String password;
	private String role;
	private boolean enabled=false;
}
