package com.profitable.ws.model.entity;

public enum OrderSubtype {
	
	LIMITED,
	MARKET;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
}
