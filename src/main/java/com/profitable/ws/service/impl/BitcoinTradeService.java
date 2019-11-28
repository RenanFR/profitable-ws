package com.profitable.ws.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.BitcoinTradeApiResponse;
import com.profitable.ws.model.dto.BitcoinTradeApiResponseOrderCreated;
import com.profitable.ws.model.dto.BitcoinTradeApiSimpleResponse;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;

@Service("bitcointrade")
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
		ResponseEntity<BitcoinTradeApiSimpleResponse> walletBalance = restTemplate
				.exchange(apiUrl.concat("/wallets/balance"), HttpMethod.GET, requestParameters, BitcoinTradeApiSimpleResponse.class);
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
		BitcoinTradeApiSimpleResponse response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiSimpleResponse.class).getBody();
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
			.queryParam("page_size", 100)
			.queryParam("current_page", 1)
			.build()
			.toUriString();
		ResponseEntity<BitcoinTradeApiResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiResponse.class);
		return response.getBody().getData().getOrders();
	}
	
	@Override
	public List<CriptoDeposit> criptoDeposits(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		String uri = UriComponentsBuilder
			.fromHttpUrl(String.format("%s/%s/%s", apiUrl, coin.getCoinName(), "deposits"))
			.queryParam("page_size", 100)
			.queryParam("current_page", 1)
			.queryParam("status", status.toString().toLowerCase())
			.queryParam("start_date", startDate)
			.queryParam("end_date", endDate)
			.toUriString();
		ResponseEntity<BitcoinTradeApiResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiResponse.class);
		return response.getBody().getData().getDeposits();
	}

	@Override
	public List<Withdraw> criptoWithdrawals(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		String uri = UriComponentsBuilder
				.fromHttpUrl(String.format("%s/%s/%s", apiUrl, coin.getCoinName(), "withdraw"))
				.queryParam("page_size", 100)
				.queryParam("current_page", 1)
				.queryParam("status", status.toString().toLowerCase())
				.queryParam("start_date", startDate)
				.queryParam("end_date", endDate)
				.toUriString();
		ResponseEntity<BitcoinTradeApiResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, BitcoinTradeApiResponse.class);
		return response.getBody().getData().getWithdrawals();
	}

	@Override
	public Order createOrder(CurrencyType currency, OrderType orderType, OrderSubtype orderSubtype, BigDecimal amount,
			BigDecimal unitPrice, BigDecimal requestPrice) {
		HttpEntity<Order> orderData = new HttpEntity<Order>(Order.builder()
				.pair("BRL".concat(currency.name()))
				.amount(amount)
				.type(orderType)
				.subtype(orderSubtype)
				.unitPrice(unitPrice)
				.requestPrice(requestPrice)
				.build());
		String url = UriComponentsBuilder
			.fromHttpUrl(apiUrl.concat("/market/create_order"))
			.toUriString();
		Order response = restTemplate.exchange(url, HttpMethod.POST, orderData, BitcoinTradeApiResponseOrderCreated.class).getBody().getData();
		return response;
	}

	@Override
	public Integer cancelOrder(String orderId) {
		String url = UriComponentsBuilder
				.fromHttpUrl(String.format("%s/%s/%s", apiUrl, "/market/user_orders/", orderId))
				.toUriString();
		ResponseEntity<Integer> statusCode = restTemplate.exchange(url, HttpMethod.DELETE, requestParameters, Integer.class);
		return statusCode.getBody();
	}

}
