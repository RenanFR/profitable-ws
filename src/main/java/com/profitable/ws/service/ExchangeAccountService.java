package com.profitable.ws.service;

import java.math.BigDecimal;
import java.util.Map;

import com.profitable.ws.model.entity.CurrencyType;

public interface ExchangeAccountService extends ExchangeService {

	Map<CurrencyType, BigDecimal> getBalance();
	
}
