package com.profitable.ws.model.entity;

public enum OrderSubtype {
	
	LIMIT,
	LIMIT_MAKER,
	STOP_LOSS,
	STOP_LOSS_LIMIT,
	TAKE_PROFIT_LIMIT,
	TAKE_PROFIT,
	LIMITED,
	MARKET;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
}
