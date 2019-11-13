package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class Trading {
	
	private TradingType type;
	
	private boolean closed;
	
	private boolean profit;
	
	private boolean loss;
	
	private BigDecimal value;
	
	private CurrencyType currencyType;
	
	private Trader trader;
	
	private BigDecimal currentResult;

	private BigDecimal totalFee;
	
	private BigDecimal finalResult;
	
	private Asset asset;
	
	private BigDecimal lot;
	
	private List<Order> orders;
	
	private List<Transfer> transfers;
	
}
