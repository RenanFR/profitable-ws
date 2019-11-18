package com.profitable.ws.model.entity;

import lombok.Getter;
import lombok.Setter;

public enum CurrencyType {
	BRL("real"),
	BTC("bitcoin"),
	ETH("ethereum"),
	LTC("litecoin"),
	BCH("bitcoincash"),
	XRP("ripple");
	
	@Getter
	@Setter
	private String coinName;
	
	private CurrencyType(String coinName) {
		this.coinName = coinName;
	}
	
}
