package com.profitable.ws.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceCoinsInfo {
	
	private String coin;
	
	private boolean depositAllEnable;
	
	private BigDecimal free;
	
	private BigDecimal freeze;
	
	private BigDecimal ipoable;
	
	private BigDecimal ipoing;
	
	private boolean isLegalMoney;
	
	private BigDecimal locked;
	
	private String name;
	
	private BigDecimal storage;
	
	private boolean trading;
	
	private boolean withdrawAllEnable;
	
	private BigDecimal withdrawing;
	
	private BinanceNetworkList[] networkList;

}
