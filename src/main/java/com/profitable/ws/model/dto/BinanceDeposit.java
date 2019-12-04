package com.profitable.ws.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceDeposit {
	
	private String address;
	
	private String addressTag;
	
	private BigDecimal amount;
	
	private String coin;
	
	private Long insertTime;
	
	private String network;
	
	private int status;
	
	private String txId;

}
