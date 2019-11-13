package com.profitable.ws.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
import com.profitable.ws.model.dto.BitcoinTradeApiData;
import com.profitable.ws.model.dto.BitcoinTradeApiResponse;
import com.profitable.ws.model.dto.BitcoinTradeApiResponseOrders;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.service.ExchangeAccountService;

@Service
@Qualifier("bitcointrade")
public class BitcoinTradeService implements ExchangeAccountService {
	
	@Value("${bitcointrade.api.token}")
	private String apiKey;
	
	@Value("${bitcointrade.api.url}")
	private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private HttpEntity<Object> requestParameters;
	
	@PostConstruct
	public void setHeaders() {
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "ApiToken ".concat(apiKey));
		requestParameters = new HttpEntity<Object>(headers);
	}
	
	@Override
	public Map<CurrencyType, BigDecimal> getBalance() {
		ResponseEntity<BitcoinTradeApiResponse> walletBalance = restTemplate
				.exchange(apiUrl.concat("/wallets/balance"), HttpMethod.GET, requestParameters, BitcoinTradeApiResponse.class);
		var wallet = new HashMap<CurrencyType, BigDecimal>();
		for (Map<String, String> currencyPair : walletBalance.getBody().getData()) {
			BigDecimal availableAmount = new BigDecimal(currencyPair.get("available_amount")).add(new BigDecimal(currencyPair.get("locked_amount")));
			wallet.put(CurrencyType.valueOf(currencyPair.get("currency_code")), availableAmount);
		}
		return wallet;
	}

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		String uri = UriComponentsBuilder.fromHttpUrl(apiUrl.concat("/market/summary")).queryParam("pair", baseCoin.toString() + ticker.toString()).build().toUriString();
		BitcoinTradeApiResponse response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiResponse.class).getBody();
		AssetTicker assetTicker = AssetTicker
			.builder()
			.last(new BigDecimal(response.getData()[0].get("last_transaction_unit_price")))
			.high(new BigDecimal(response.getData()[0].get("max_price")))
			.low(new BigDecimal(response.getData()[0].get("min_price")))
			.vol(new BigDecimal(response.getData()[0].get("volume_24h")))
			.quote(response.getData()[0].get("pair").substring(0, 3))
			.base(response.getData()[0].get("pair").substring(3))
			.transactions(Long.valueOf(response.getData()[0].get("transactions_number")))
			.build();
		return assetTicker;
	}

	@Override
	public List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, CurrencyType currency,
			OrderType orderType, Integer pageSize, Integer currentPage) {
		String uri = UriComponentsBuilder
			.fromHttpUrl(apiUrl.concat("/market/user_orders/list"))
			.queryParam("status", status.toString().toLowerCase())
			.queryParam("start_date", startDate)
			.queryParam("end_date", endDate)
			.queryParam("pair", "BRL" + currency.toString())
			.queryParam("type", orderType.toString().toLowerCase())
			.queryParam("page_size", pageSize)
			.queryParam("current_page", currentPage)
			.build()
			.toUriString();
		ResponseEntity<BitcoinTradeApiResponseOrders> response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiResponseOrders.class);
		return response.getBody().getData().getOrders();
	}

}
