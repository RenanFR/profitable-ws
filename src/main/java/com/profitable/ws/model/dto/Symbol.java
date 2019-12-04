package com.profitable.ws.model.dto;

import java.util.List;

import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.OrderSubtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Symbol {
	
	private CurrencyType baseAsset;
	
	private CurrencyType quoteAsset;
	
	private List<OrderSubtype> orderTypes;
	
	private boolean icebergAllowed;
	
	private boolean ocoAllowed;
	
	private boolean isSpotTradingAllowed;
	
	private boolean isMarginTradingAllowed;
	
	private int quotePrecision;
	
	private int baseAssetPrecision;
	
	private String status;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseAsset == null) ? 0 : baseAsset.hashCode());
		result = prime * result + ((quoteAsset == null) ? 0 : quoteAsset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (baseAsset != other.baseAsset)
			return false;
		if (quoteAsset != other.quoteAsset)
			return false;
		return true;
	}
	
}
