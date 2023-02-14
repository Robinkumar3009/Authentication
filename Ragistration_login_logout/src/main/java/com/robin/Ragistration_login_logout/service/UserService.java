package com.robin.Ragistration_login_logout.service;


import java.util.Optional;

import com.robin.Ragistration_login_logout.entity.Users;
import com.robin.Ragistration_login_logout.entity.VerificationToken;
import com.robin.Ragistration_login_logout.model.UserModel;

public interface UserService {
	
	public Users registerUser(UserModel userModel);

	public void saveVerificationTokenForUser(String token, Users users);

	public String validateVerificationToken(String token);

	public VerificationToken generateNewVerificationToken(String oldToken);

	public Users findUserByEmail(String email);

	public void createPasswordResetTokenForUser(Users user, String token);

	public String validatePasswordResetToken(String token);

	public Optional<Users> getUserByPasswordResetToken(String token);

	public void changePassword(Users users, String newPassword);

	public boolean checkIfValidPassword(Users user, String oldPassword);

}
