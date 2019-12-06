package com.profitable.ws.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;

public interface ExchangeAccountService extends ExchangeService {

	Map<CurrencyType, BigDecimal> getBalance();
	
	List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, Symbol symbol, OrderType orderType, Integer pageSize, Integer currentPage);
	
	Order createOrder(Symbol symbol, OrderType orderType, OrderSubtype orderSubtype, BigDecimal amount, BigDecimal unitPrice, BigDecimal requestPrice, BigDecimal stopPrice, BigDecimal stopLimitPrice);
	
	Integer cancelOrder(Symbol symbol, String orderId);
	
	List<CriptoDeposit> criptoDeposits(CurrencyType coin, Integer pageSize, Integer currentPage, DepositStatus status, LocalDate startDate, LocalDate endDate);
	
	List<Withdraw> criptoWithdrawals(CurrencyType coin, Integer pageSize, Integer currentPage, DepositStatus status, LocalDate startDate, LocalDate endDate);
	
}
