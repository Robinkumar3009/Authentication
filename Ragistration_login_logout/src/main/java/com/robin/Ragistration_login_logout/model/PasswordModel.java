package com.robin.Ragistration_login_logout.model;

import lombok.Data;

@Data
public class PasswordModel {
	
	private String email;
	
	private String oldPassword;
	private String newPassword;

}
