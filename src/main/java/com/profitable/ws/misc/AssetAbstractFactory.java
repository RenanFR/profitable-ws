package com.profitable.ws.misc;

import java.math.BigDecimal;

import com.profitable.ws.model.entity.Asset;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.TradingType;

public abstract class AssetAbstractFactory <A extends Asset> {
	
	public abstract A build(BigDecimal quote, CurrencyType currency, TradingType type);
}
