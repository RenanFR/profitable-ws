package com.profitable.ws.service;

import java.math.BigDecimal;
import java.util.List;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.CurrencyType;

public interface ExchangeService {
	
	AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy);
	
	List<Symbol> symbols();
	
}
