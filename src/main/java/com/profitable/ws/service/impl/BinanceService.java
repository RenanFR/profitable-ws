package com.profitable.ws.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
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
import com.profitable.ws.model.dto.BinanceDeposit;
import com.profitable.ws.model.dto.BinanceWithdraw;
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.Cripto;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;

import lombok.extern.slf4j.Slf4j;

@Service("binance")
@Slf4j
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
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@PostConstruct
	public void setRequestConfigurations() throws NoSuchAlgorithmException, InvalidKeyException {
		encoder = Mac.getInstance(ENCODE_ALGORITHM);
		encoder.init(new SecretKeySpec(apiSecret.getBytes(), ENCODE_ALGORITHM));
		headers.add(API_KEY_HEADER, apiKey);
		requestParameters = new HttpEntity<Object>(headers);
	}

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		Symbol symbol = Symbol
			.builder()
			.baseAsset(baseCoin)
			.quoteAsset(ticker)
			.build();
		String url = UriComponentsBuilder
			.fromHttpUrl(apiUrl.concat("/api/v3/ticker/price"))
			.queryParam("symbol", symbol.getBaseAsset().toString().concat(symbol.getQuoteAsset().toString()))
			.build()
			.toString();
		ResponseEntity<String> json = restTemplate.exchange(url, HttpMethod.GET, requestParameters, String.class);
		return AssetTicker
				.builder()
				.last(new BigDecimal(json.getBody().substring(json.getBody().toString().lastIndexOf(":") + 1, json.getBody().toString().lastIndexOf("}") - 2)))
				.build();
	}

	@Override
	public Map<CurrencyType, BigDecimal> getBalance() {
		long timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(apiUrl.concat("/sapi/v1/capital/config/getall"))
				.queryParam(TIMESTAMP_PARAM, timestamp);
		String totalParams = uriBuilder.build().getQuery();
		fillSignature(totalParams);
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
	
	private void fillSignature(String totalParams) {
		signature = new String(Hex.encodeHex(encoder.doFinal(totalParams.getBytes())));
	}

	@Override
	public List<Order> orders(OrderStatus status, LocalDate startDate, LocalDate endDate, CurrencyType currency,
			OrderType orderType, Integer pageSize, Integer currentPage) {
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
		String service = "/sapi/v1/capital/deposit/hisrec";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(apiUrl.concat(service))
				.queryParam("coin", coin.toString())
				.queryParam("timestamp", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.queryParam("status", status == DepositStatus.PENDING? 0 : status == DepositStatus.CONFIRMATION_PENDING? 6 : 1)
				.queryParam("startTime", startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.queryParam("endTime", endDate.atTime(0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());			
		fillSignature(uriBuilder.build().getQuery());
		String url = uriBuilder.queryParam(SIGNED_PARAM, signature).build().toString();
		ResponseEntity<List<BinanceDeposit>> deposits = restTemplate.exchange(url, HttpMethod.GET, requestParameters, new ParameterizedTypeReference<List<BinanceDeposit>>() {});
		return deposits
				.getBody()
				.stream()
				.map(d -> {
					CriptoDeposit deposit = new CriptoDeposit();
					Cripto cripto = new Cripto();
					deposit.setAmount(d.getAmount());
					deposit.setHash(d.getTxId());
					deposit.setCreateDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getInsertTime()), TimeZone.getDefault().toZoneId()));
					deposit.setStatus(d.getStatus() == 0? DepositStatus.PENDING : d.getStatus() == 6? DepositStatus.CONFIRMATION_PENDING : DepositStatus.CONFIRMED);
					cripto.setSymbol(d.getCoin());
					deposit.setAsset(cripto);
					return deposit;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<Withdraw> criptoWithdrawals(CurrencyType coin, Integer pageSize, Integer currentPage,
			DepositStatus status, LocalDate startDate, LocalDate endDate) {
		String serviceAddress = "/sapi/v1/capital/withdraw/history";
		UriComponentsBuilder uri = UriComponentsBuilder
				.fromHttpUrl(apiUrl.concat(serviceAddress))
				.queryParam("coin", coin.getCoinName())
				.queryParam("timestamp", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.queryParam("status", status == DepositStatus.PENDING? 0 : status == DepositStatus.CONFIRMATION_PENDING? 2 : status == DepositStatus.CONFIRMED? 6 : 1)
				.queryParam("startTime", startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.queryParam("endTime", endDate.atTime(0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		fillSignature(uri.build().getQuery());
		ResponseEntity<List<BinanceWithdraw>> withdraws = restTemplate.exchange(uri.queryParam(SIGNED_PARAM, signature).build().toString(), HttpMethod.GET, requestParameters, new ParameterizedTypeReference<List<BinanceWithdraw>>() {});
		return 	withdraws
				.getBody()
				.stream()
				.map(w -> {
					Withdraw withdraw = Withdraw
						.builder()
						.code(w.getId())
						.destinationAddress(w.getAddress())
						.amount(w.getAmount())
						.status(w.getStatus() == 0 || w.getStatus() == 4? 
								DepositStatus.PENDING : (w.getStatus() == 1 || w.getStatus() == 3 || w.getStatus() == 5)? 
										DepositStatus.CANCELED : w.getStatus() == 2? DepositStatus.CONFIRMATION_PENDING : DepositStatus.CONFIRMED)
						.build();
					return withdraw;
				})
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<Symbol> symbols() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Symbol> symbols = new ArrayList<>();
		String response = restTemplate.getForObject(apiUrl.concat("/api/v3/exchangeInfo"), String.class);
		try {
			objectMapper.readTree(response).get("symbols").elements()
				.forEachRemaining(symbol -> {
					List<OrderSubtype> orderTypes = new ArrayList<>();
					CurrencyType baseAsset;
					CurrencyType quoteAsset;
					symbol.get("orderTypes").elements().forEachRemaining(type -> {
						orderTypes.add(OrderSubtype.valueOf(type.asText()));
					});
					try {
						baseAsset = CurrencyType.valueOf(symbol.get("baseAsset").asText());
					} catch (IllegalArgumentException exception) {
						log.error(exception.getMessage());
						baseAsset = CurrencyType.UNKNOWN;
					}
					try {
						quoteAsset = CurrencyType.valueOf(symbol.get("quoteAsset").asText());
					} catch (IllegalArgumentException exception) {
						log.error(exception.getMessage());
						quoteAsset = CurrencyType.UNKNOWN;						
					}
					Symbol s = Symbol
							.builder()
							.status(symbol.get("status").asText())
							.baseAsset(baseAsset)
							.baseAssetPrecision(symbol.get("baseAssetPrecision").asInt())
							.quoteAsset(quoteAsset)
							.quotePrecision(symbol.get("quotePrecision").asInt())
							.orderTypes(orderTypes)
							.icebergAllowed(symbol.get("icebergAllowed").asBoolean())
							.ocoAllowed(symbol.get("ocoAllowed").asBoolean())
							.isSpotTradingAllowed(symbol.get("isSpotTradingAllowed").asBoolean())
							.isMarginTradingAllowed(symbol.get("isMarginTradingAllowed").asBoolean())
							.build();
					symbols.add(s);
				});
			symbols.stream().distinct().forEach(System.out::println);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return symbols;
	}

}
