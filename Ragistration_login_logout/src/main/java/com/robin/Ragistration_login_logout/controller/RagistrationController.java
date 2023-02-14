package com.robin.Ragistration_login_logout.controller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robin.Ragistration_login_logout.entity.Users;
import com.robin.Ragistration_login_logout.entity.VerificationToken;
import com.robin.Ragistration_login_logout.event.RagistrationCompleteEvent;
import com.robin.Ragistration_login_logout.model.PasswordModel;
import com.robin.Ragistration_login_logout.model.UserModel;
import com.robin.Ragistration_login_logout.repository.UserRepository;
import com.robin.Ragistration_login_logout.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RagistrationController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository repo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@PostMapping("/register")
	public String register(@RequestBody UserModel userModel, final HttpServletRequest request) {
		Users user = userService.registerUser(userModel);
		applicationEventPublisher.publishEvent(new RagistrationCompleteEvent(user, applicationUrl(request)));
		return "Success";

	}

	@GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam("token") String token) {
		String result = userService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("valid")) {
			return "User Verified Successfully";
		}
		return "Bad User";

	}

	@GetMapping("/resendVerificationToken")
	public String resendVerificationToken(@RequestParam("oldToken") String oldToken, HttpServletRequest request) {
		VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
		Users user = verificationToken.getUser();

		resendVerificationTokenMail(user, applicationUrl(request), verificationToken);

		return "Verification Link Sent";

	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody PasswordModel passwordModel,HttpServletRequest request)
	{
		Users user =userService.findUserByEmail(passwordModel.getEmail());
		
		String url="";
		if(user!=null)
		{
			String token=UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user,token);
			url=passwordResetTokenMail(user,applicationUrl(request),token);
			
		}
		return url;
	}
	
	@PostMapping("/savePassword")
	public String savePassword(@RequestParam("token") String token,@RequestBody PasswordModel passwordModel)
	{
		String result=userService.validatePasswordResetToken(token);
		
		if(!result.equalsIgnoreCase("valid"))
		{
			return "Invalid Token";
		}
		
		Optional<Users> user =userService.getUserByPasswordResetToken(token);
		if(user.isPresent())
		{
			userService.changePassword(user.get(),passwordModel.getNewPassword());
			return "Password reset Successfully";
		}
		else
		{
			return "Invalid Token";
		}
			
	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestBody PasswordModel passwordModel)
	{
		Users user=userService.findUserByEmail(passwordModel.getEmail());
		
		if(!userService.checkIfValidPassword(user,passwordModel.getOldPassword()))
		{
			return "Invalid Old Password";
		}
		
		userService.changePassword(user, passwordModel.getNewPassword());
		return "Password change successfully..";
		
	}
	
	
	
	private String passwordResetTokenMail(Users user, String applicationUrl, String token) {
		// TODO Auto-generated method stub
		
		String url = applicationUrl + "/savePassword?token=" + token;

		log.info("click the link to reset your password:{}", url);
		return url;
	}

	private void resendVerificationTokenMail(Users user, String applicationUrl, VerificationToken verificationToken) {
		// TODO Auto-generated method stub
		String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

		log.info("click the link to verify your account:{}", url);
	}

	private String applicationUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@GetMapping("/api/hello")
	public String hello() {
		return "hello";
	}

}
