package com.profitable.ws.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitcoinTradeApiResponse {
	
	private String message;
	
	private BitcoinTradeApiData data;
	
}
