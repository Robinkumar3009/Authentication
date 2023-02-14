package com.robin.Ragistration_login_logout.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.robin.Ragistration_login_logout.entity.PasswordResetToken;
import com.robin.Ragistration_login_logout.entity.Users;
import com.robin.Ragistration_login_logout.entity.VerificationToken;
import com.robin.Ragistration_login_logout.model.UserModel;
import com.robin.Ragistration_login_logout.repository.PasswordResetRepository;
import com.robin.Ragistration_login_logout.repository.UserRepository;
import com.robin.Ragistration_login_logout.repository.VerificationTokenRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	PasswordResetRepository passwordResetRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Users registerUser(UserModel userModel) {
		Users user=new Users();
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setEmail(userModel.getEmail());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		user.setRole("Normal");
		userRepository.save(user);
		return user;
	}

	@Override
	public void saveVerificationTokenForUser(String token, Users users) {
		// TODO Auto-generated method stub
		VerificationToken varificationToken=new VerificationToken( token, users);
		verificationTokenRepository.save(varificationToken);
		
	}

	@Override
	public String validateVerificationToken(String token) {
		// TODO Auto-generated method stub
		
		VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
		
		if(verificationToken==null)
		{
			return "invalid";
		}
		
			Users user=verificationToken.getUser();
			Calendar cal=Calendar.getInstance();
			
			if(verificationToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0)
			{
				verificationTokenRepository.delete(verificationToken);
				return "expired";
			}
			
			user.setEnabled(true);
			userRepository.save(user);
		
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		// TODO Auto-generated method stub
		
		VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepository.save(verificationToken);
		
		
		return verificationToken;
	}

	@Override
	public Users findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(Users user, String token) {
		// TODO Auto-generated method stub
		 PasswordResetToken passwordResetToken=new PasswordResetToken(token,user);
		 
		 passwordResetRepository.save(passwordResetToken);
		 
		 
		
	}

	@Override
	public String validatePasswordResetToken(String token) {
		// TODO Auto-generated method stub
		
PasswordResetToken passwordResetToken=passwordResetRepository.findByToken(token);
		
		if(passwordResetToken==null)
		{
			return "invalid";
		}
		
			Users user=passwordResetToken.getUser();
			Calendar cal=Calendar.getInstance();
			
			if(passwordResetToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0)
			{
				passwordResetRepository.delete(passwordResetToken);
				return "expired";
			}
			
			
		
		return "valid";
		
		
	}

	@Override
	public Optional<Users> getUserByPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(passwordResetRepository.findByToken(token).getUser());
	}

	@Override
	public void changePassword(Users users, String newPassword) {
		// TODO Auto-generated method stub
		
		users.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(users);
		
	}

	@Override
	public boolean checkIfValidPassword(Users user, String oldPassword) {
		// TODO Auto-generated method stub
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

}
