package com.profitable.ws.model.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asset {
	
	protected BigDecimal quote;
	
	protected CurrencyType currencyQuote;
	
	protected TradingType type;
	
}
