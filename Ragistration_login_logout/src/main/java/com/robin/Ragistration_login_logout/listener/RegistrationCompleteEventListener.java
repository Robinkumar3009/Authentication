package com.robin.Ragistration_login_logout.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.robin.Ragistration_login_logout.entity.Users;
import com.robin.Ragistration_login_logout.event.RagistrationCompleteEvent;
import com.robin.Ragistration_login_logout.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RagistrationCompleteEvent> {

	@Autowired
	UserService userService;
	@Override
	public void onApplicationEvent(RagistrationCompleteEvent event) {
//		Create the Varification Token for the User with Link
		
		Users users= event.getUsers();
		String token=UUID.randomUUID().toString();
		userService.saveVerificationTokenForUser(token,users);
//		Send mail to user
		String url=event.getApplicationUrl()+"/verifyRegistration?token="+token;
		
		log.info("click the link to verify your account:{}",url);
	}

}
