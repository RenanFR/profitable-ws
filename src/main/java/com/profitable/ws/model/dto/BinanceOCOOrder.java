package com.profitable.ws.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceOCOOrder {
	
	private int orderListId;
	
	private String contingencyType;
	
	private String listStatusType;
	
	private String listOrderStatus;
	
	private String listClientOrderId;
	
	private Long transactionTime;
	
	private String symbol;
	
	private List<BinanceOrder> orders;
	
	private List<BinanceOrder> orderReports;

}
