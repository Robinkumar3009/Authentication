package com.robin.Ragistration_login_logout.event;

import org.springframework.context.ApplicationEvent;

import com.robin.Ragistration_login_logout.entity.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RagistrationCompleteEvent extends ApplicationEvent {

	Users users;
	String applicationUrl;
	public RagistrationCompleteEvent(Users users,String applicationUrl) {
		super(users);
		// TODO Auto-generated constructor stub
		this.users=users;
		this.applicationUrl=applicationUrl;
	}

}
