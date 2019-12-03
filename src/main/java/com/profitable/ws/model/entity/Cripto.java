package com.profitable.ws.model.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cripto extends Asset {
	
	private Long id;
	
	private String name;
	
	private String symbol;
	
	private String slug;
	
	@JsonProperty("is_active")
	private boolean isActive;
	
	@JsonProperty("first_historical_data")
	private LocalDateTime firstHistoricalData;
	
	@JsonProperty("last_historical_data")
	private LocalDateTime lastHistoricalData;
	
	private CriptoPlatform platform;

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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getFirstHistoricalData() {
		return firstHistoricalData;
	}

	public void setFirstHistoricalData(LocalDateTime firstHistoricalData) {
		this.firstHistoricalData = firstHistoricalData;
	}

	public LocalDateTime getLastHistoricalData() {
		return lastHistoricalData;
	}

	public void setLastHistoricalData(LocalDateTime lastHistoricalData) {
		this.lastHistoricalData = lastHistoricalData;
	}

	public CriptoPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(CriptoPlatform platform) {
		this.platform = platform;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cripto other = (Cripto) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
}
