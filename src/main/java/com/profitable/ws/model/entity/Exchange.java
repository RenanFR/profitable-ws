package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class Exchange {
	
	private String name;
	
	private String ownerGroup;
	
	private ExchangeType type;
	
	private List<OrderFee> orderFees;
	
	private BigDecimal withdrawFee;
	
}
