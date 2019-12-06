package com.profitable.ws.model.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderFee {
	
	private boolean passive;
	
	private boolean executed;
	
	private boolean sell;
	
	private boolean buy;
	
	private BigDecimal value;
	
}
