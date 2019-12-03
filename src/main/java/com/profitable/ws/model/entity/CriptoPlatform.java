package com.profitable.ws.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CriptoPlatform {
	
	private Long id;
	
	private String name;
	
	private String symbol;
	
	private String slug;
	
	@JsonProperty("token_address")
	private String tokenAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

}
