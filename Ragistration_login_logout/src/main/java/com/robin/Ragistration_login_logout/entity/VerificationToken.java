package com.robin.Ragistration_login_logout.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
	
	
	// Expiration is 10 min
	final static int EXPIRATION_TIME=10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Date expirationTime;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id",nullable = false,foreignKey = @ForeignKey(name="FK_USER_VARIFY_tOKEN"))
	private Users user;
	public VerificationToken(String token, Users user) {
		super();
		this.token = token;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		this.user = user;
	}
	
	public VerificationToken(String token)
	{
		super();
		this.token=token;
		this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
	}
	private Date calculateExpirationDate(int expirationTime) {
		// TODO Auto-generated method stub
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}
	

}
