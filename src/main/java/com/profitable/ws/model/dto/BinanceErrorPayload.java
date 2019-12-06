package com.profitable.ws.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceErrorPayload {
	
	private int code;
	
	private String msg;

}
