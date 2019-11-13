package com.profitable.ws.service;

import java.math.BigDecimal;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.entity.CurrencyType;

public interface ExchangeService {
	
	AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy);
	
}
