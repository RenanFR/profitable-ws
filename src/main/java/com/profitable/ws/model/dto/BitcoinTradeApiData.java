package com.profitable.ws.model.dto;

import java.util.List;

import com.profitable.ws.model.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitcoinTradeApiData {
	
	private BitcoinTradeApiPagination pagination;
	
	private List<Order> orders;
	
}
