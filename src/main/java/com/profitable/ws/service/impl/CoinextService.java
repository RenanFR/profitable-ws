package com.profitable.ws.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.CoinextToken;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;

public class CoinextService implements ExchangeAccountService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${coinext.api.url}")
	private String apiUrl;	
	
	@Value("${coinext.api.email}")
	private String apiEmail;
	
	@Value("${coinext.api.password}")
	private String apiPassword;	
	
	private String authToken;
	
	private String authenticate() {
		CoinextToken coinextToken = restTemplate.getForObject(String.format("%s/%s:%s@%s/%s", "https://", apiEmail, apiPassword, apiUrl, "/authenticate"), CoinextToken.class);
		authToken = coinextToken.getToken();
		return authToken;
	}

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<CurrencyType, BigDecimal> getBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, CurrencyType currency,
			OrderType orderType, Integer pageSize, Integer currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order createOrder(CurrencyType currency, OrderType orderType, OrderSubtype orderSubtype, BigDecimal amount,
			BigDecimal unitPrice, BigDecimal requestPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer cancelOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CriptoDeposit> criptoDeposits(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Withdraw> criptoWithdrawals(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
