package com.profitable.ws.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceWithdraw {

	private String address;
	
	private LocalDateTime applyTime;
	
	private String coin;
	
	private String id;
	
	private String network;
	
	private String txId;
	
	private BigDecimal amount;
	
	private int status;
	
}
