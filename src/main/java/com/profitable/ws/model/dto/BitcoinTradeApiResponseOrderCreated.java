package com.profitable.ws.model.dto;

import com.profitable.ws.model.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitcoinTradeApiResponseOrderCreated {
	
	private String message;
	
	private Order data;
	
}
