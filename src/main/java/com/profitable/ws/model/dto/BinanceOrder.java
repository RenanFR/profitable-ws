package com.profitable.ws.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinanceOrder {
	
	private String symbol;
	
	private int orderId;
	
	private int orderListId;
	
	private String clientOrderId;
	
	private BigDecimal price;
	
	private BigDecimal origQty;
	
	private BigDecimal executedQty;
	
	private BigDecimal cummulativeQuoteQty;
	
	private String status;
	
	private String timeInForce;
	
	private String type;
	
	private String side;
	
	private BigDecimal stopPrice;
	
	private BigDecimal icebergQty;
	
	private Long time;
	
	private Long updateTime;
	
	private boolean isWorking;
	
	private BigDecimal origQuoteOrderQty;

}
