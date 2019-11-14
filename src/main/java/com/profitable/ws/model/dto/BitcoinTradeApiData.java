package com.profitable.ws.model.dto;

import java.util.List;

import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.Withdraw;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitcoinTradeApiData {
	
	private BitcoinTradeApiPagination pagination;
	
	private List<Order> orders;
	
	private List<CriptoDeposit> deposits;
	
	private List<Withdraw> withdrawals;
	
}
