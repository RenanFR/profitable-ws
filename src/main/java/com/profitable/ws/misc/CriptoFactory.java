package com.profitable.ws.misc;

import java.math.BigDecimal;

import com.profitable.ws.model.entity.Bitcoin;
import com.profitable.ws.model.entity.Cripto;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.TradingType;

public class CriptoFactory extends AssetAbstractFactory<Cripto> {

	@Override
	public Cripto build(BigDecimal quote, CurrencyType currency, TradingType type) {
		Bitcoin bitcoin = new Bitcoin();
		bitcoin.setQuote(quote);
		bitcoin.setCurrencyQuote(currency);
		bitcoin.setType(type);
		return bitcoin;
	}

}
