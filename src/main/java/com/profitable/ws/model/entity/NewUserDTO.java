package com.profitable.ws.model.entity;

import lombok.Data;

@Data
public class NewUserDTO {
	
	private String userMail;
	
	private String userName;
	
	private String userLastName;
	
	private String password;
	
	private String passwordConfirmation;

}
