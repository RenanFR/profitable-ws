package com.profitable.ws.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinextToken {
	
	private Boolean Authenticated;
	
	private Integer UserId;
	
	private String Token;
	
	private Integer AccountId;
	
	private Integer OMSId;

}
