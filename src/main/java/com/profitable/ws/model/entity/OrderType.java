package com.profitable.ws.model.entity;

public enum OrderType {
	
	BUY,
	SELL;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
