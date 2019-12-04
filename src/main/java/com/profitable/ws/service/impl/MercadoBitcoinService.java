package com.profitable.ws.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.service.ExchangeService;

@Service
@Qualifier("mercadobitcoin")
public class MercadoBitcoinService implements ExchangeService {
	
	@Value("${mercadobitcoin.api.url}")
	private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		ResponseEntity<AssetTicker> assetTicker = restTemplate.exchange(String.format("%s/%s/%s", apiUrl, ticker, "/ticker/"), HttpMethod.GET, null, AssetTicker.class);
		return assetTicker.getBody();
	}

	@Override
	public List<Symbol> symbols() {
		// TODO Auto-generated method stub
		return null;
	}

}
