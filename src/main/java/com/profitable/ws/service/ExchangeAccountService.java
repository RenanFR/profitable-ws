package com.profitable.ws.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;

public interface ExchangeAccountService extends ExchangeService {

	Map<CurrencyType, BigDecimal> getBalance();
	
	List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, CurrencyType currency, OrderType orderType, Integer pageSize, Integer currentPage);
	
	Order createOrder(CurrencyType currency, OrderType orderType, OrderSubtype orderSubtype, BigDecimal amount, BigDecimal unitPrice, BigDecimal requestPrice);
	
	Integer cancelOrder(String orderId);
	
}
