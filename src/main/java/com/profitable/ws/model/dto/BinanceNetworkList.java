package com.profitable.ws.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceNetworkList {
	
	private String addressRegex;
	
	private String coin;
	
	private String depositDesc;
	
	private boolean depositEnable;
	
	private boolean isDefault;
	
	private String memoRegex;
	
	private String name;
	
	private String network;
	
	private boolean resetAddressStatus;
	
	private String specialTips;
	
	private String withdrawDesc;
	
	private boolean withdrawEnable;
	
	private BigDecimal withdrawFee;
	
	private BigDecimal withdrawMin;

}
