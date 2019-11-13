package com.profitable.ws.model.entity;

import lombok.Data;

@Data
public class AuthInfoDTO {
	
	private String email;
	
	private String password;
	
	private String authCode;

}
