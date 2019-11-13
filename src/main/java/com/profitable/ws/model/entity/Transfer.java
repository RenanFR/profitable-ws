package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {
	
	private TransferType type;
	
	private LocalDateTime execution;
	
	private FundStorage from;
	
	private FundStorage to;
	
	private Asset asset;
	
	private BigDecimal value;
	
	private BigDecimal cost;
	
}
