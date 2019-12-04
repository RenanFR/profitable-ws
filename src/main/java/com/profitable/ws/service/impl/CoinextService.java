package com.profitable.ws.service.impl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.dto.CoinextRequest;
import com.profitable.ws.model.dto.CoinextToken;
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.dto.UserCoinext;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderSubtype;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Trader;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;

@Service("coinext")
public class CoinextService implements ExchangeAccountService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${coinext.api.url}")
	private String apiUrl;	
	
	@Value("${coinext.websocket.url}")
	private String websocketUrl;	
	
	@Value("${coinext.api.email}")
	private String apiEmail;
	
	@Value("${coinext.api.password}")
	private String apiPassword;	
	
//	@Autowired
//	private StompSession stompSession;
	
	private String authToken;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private HttpEntity<Object> requestParameters;
	
	@PostConstruct
	public void setHeadersAndConnect() {
//		headers.add("Connection", "Upgrade");
//		headers.add("Upgrade", "websocket");
//		String handShake = restTemplate.getForObject(websocketUrl, String.class, headers);
		UserCoinext user = UserCoinext.builder().UserName(apiEmail).Password(apiPassword).build();
//		stompSession.send(websocketUrl.concat("/WebAuthenticateUser"), user);
		requestParameters = new HttpEntity<Object>(headers);
	}	
	
	private String authenticate() {
		CoinextToken coinextToken = restTemplate.getForObject(String.format("%s/%s:%s@%s/%s", "https://", apiEmail, apiPassword, apiUrl, "/authenticate"), CoinextToken.class);
		authToken = coinextToken.getToken();
		return authToken;
	}

	@Override
	public AssetTicker getCurrencyQuote(CurrencyType baseCoin, CurrencyType ticker, BigDecimal amountToBuy) {
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		CoinextRequest request = CoinextRequest
				.builder()
				.OMSId(1)
				.InstrumentId(1)
				.Interval(60)
				.IncludeLastCount(100)
				.build();
		requestParameters = new HttpEntity<Object>(request);
		String url = String.format("%s/%s", "https://".concat(apiUrl), "SubscribeTicker");
		ResponseEntity<Object> instrument = restTemplate.exchange(url, HttpMethod.POST, requestParameters, Object.class);
		return null;
	}

	@Override
	public Map<CurrencyType, BigDecimal> getBalance() {
		CoinextRequest request = CoinextRequest
				.builder()
				.OMSId(1)
				.AccountId(40024)
				.Depth(100)
				.build();
		requestParameters = new HttpEntity<Object>(request);
//		stompSession.subscribe("GetAccountTransactions", new StompFrameHandler() {
//			
//			@Override
//			public void handleFrame(StompHeaders headers, Object payload) {
//				System.out.println(payload);
//			}
//			
//			@Override
//			public Type getPayloadType(StompHeaders headers) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
		String url = String.format("%s/%s", "https://".concat(apiUrl), "GetAccountTransactions");
		ResponseEntity<String> transactions = restTemplate.exchange(url, HttpMethod.POST, requestParameters, String.class);
		return null;
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
	public List<Symbol> symbols() {
		// TODO Auto-generated method stub
		return null;
	}

}
