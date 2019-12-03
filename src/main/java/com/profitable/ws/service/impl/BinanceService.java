package com.profitable.ws.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.BinanceCoinsInfo;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;

@Service("binance")
public class BinanceService implements ExchangeAccountService {
	
	@Value("${binance.api.endpoint}")
	private String apiUrl;
	
	@Value("${binance.api.key}")
	private String apiKey;
	
	@Value("${binance.api.secret}")
	private String apiSecret;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	private static final String API_KEY_HEADER = "X-MBX-APIKEY";
	
	private static final String SIGNED_PARAM = "signature";
	
	private static final String TIMESTAMP_PARAM = "timestamp";
	
	private static final String ENCODE_ALGORITHM = "HmacSHA256";
	
	private Mac encoder;
	
	private String signature;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private HttpEntity<Object> requestParameters;
	
	@PostConstruct
	public void setRequestConfigurations() throws NoSuchAlgorithmException, InvalidKeyException {
		encoder = Mac.getInstance(ENCODE_ALGORITHM);
		encoder.init(new SecretKeySpec(apiSecret.getBytes(), ENCODE_ALGORITHM));
		headers.add(API_KEY_HEADER, apiKey);
		requestParameters = new HttpEntity<Object>(headers);
	}

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<CurrencyType, BigDecimal> getBalance() {
		long timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(apiUrl.concat("/sapi/v1/capital/config/getall"))
				.queryParam(TIMESTAMP_PARAM, timestamp);
		String totalParams = uriBuilder.build().getQuery();
		signature = new String(Hex.encodeHex(encoder.doFinal(totalParams.getBytes())));
		String uri = uriBuilder
				.queryParam(SIGNED_PARAM, signature)
				.toUriString();
		ResponseEntity<List<BinanceCoinsInfo>> coinsBinance = restTemplate.exchange(uri, HttpMethod.GET, requestParameters, new ParameterizedTypeReference<List<BinanceCoinsInfo>>() {});
		Function<BinanceCoinsInfo, CurrencyType> coinKeyMapper = (var coin) -> {
			try {
				return CurrencyType.valueOf(coin.getCoin()); 
			} catch (IllegalArgumentException exception) {
				return CurrencyType.UNKNOWN;
			}
		};
		Function<BinanceCoinsInfo, BigDecimal> coinValueMapper = (var coin) -> coin.getFree();
		BinaryOperator<BigDecimal> mergeFunction = (var b, var bb) -> b.add(bb);  
		return coinsBinance.getBody().stream().collect(Collectors.toMap(coinKeyMapper, coinValueMapper, mergeFunction));
	}

	@Override
	public List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, CurrencyType currency,
			OrderType orderType, Integer pageSize, Integer currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order createOrder(CurrencyType currency, OrderType orderType, OrderSubtype orderSubtype, BigDecimal amount,
			BigDecimal unitPrice, BigDecimal requestPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer cancelOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CriptoDeposit> criptoDeposits(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Withdraw> criptoWithdrawals(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CurrencyType> symbols() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> symbols = new ArrayList<>();
		String response = restTemplate.getForObject(apiUrl.concat("/api/v3/exchangeInfo"), String.class);
		try {
			objectMapper.readTree(response).get("symbols").elements().forEachRemaining(symbol -> symbols.add(symbol.get("baseAsset").asText()));
			symbols.stream().distinct().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
