package com.profitable.ws.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetTicker {
	
	private BigDecimal high;
	
	private BigDecimal low;
	
	private BigDecimal vol;
	
	private BigDecimal last;
	
	private BigDecimal buy;
	
	private String base;
	
	private String quote;
	
	private BigDecimal ask;
	
	private BigDecimal askQuoteAmountRef;
	
	private BigDecimal askBaseAmountRef;
	
	private BigDecimal bid;
	
	private BigDecimal bidQuoteAmountRef;
	
	private BigDecimal bidBaseAmountRef;
	
	private LocalDateTime timestamp;	
	
	private BigDecimal sell;
	
	private LocalDateTime date;
	
	private Long transactions;
	
}
