package com.profitable.ws.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.service.ExchangeService;

@Service
@Qualifier("biscoint")
public class BiscointService implements ExchangeService {
	
	@Value("${biscoint.api.url}")
	private String apiUrl;	
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		String uri = UriComponentsBuilder
				.fromHttpUrl(apiUrl.concat("/ticker"))
				.queryParam("base", ticker)
				.queryParam("quote", baseCoin)
				.queryParam("amount", amountToBuy)
				.toUriString();
		HttpEntity<?> httpRequest = new HttpEntity<>(new HttpHeaders());
		ResponseEntity<AssetTicker> tickerBiscoint = restTemplate.exchange(uri, HttpMethod.GET, httpRequest, AssetTicker.class);
		return tickerBiscoint.getBody();
	}

	@Override
	public List<Symbol> symbols() {
		// TODO Auto-generated method stub
		return null;
	}

}
