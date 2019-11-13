package com.profitable.ws.model.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitcoinTradeApiResponse {
	
	private String message;
	
	private Map<String, String>[] data;
	
}
