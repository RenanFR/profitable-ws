package com.profitable.ws.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;

@Configuration
public class BeansConfig {
	
	@Value("${binance.api.key}")
	private String binanceKey;
	
	@Value("${binance.api.secret}")
	private String binanceSecret;	
	
	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
		return new RestTemplate(factory);
	}
	
	@Bean
	public BinanceApiWebSocketClient binanceWSClient() {
		BinanceApiWebSocketClient webSocketClient = BinanceApiClientFactory.newInstance(binanceKey, binanceSecret).newWebSocketClient();
		return webSocketClient;
	}
	
	@Bean
	public BinanceApiRestClient binanceRestClient() {
		BinanceApiRestClient restClient = BinanceApiClientFactory.newInstance(binanceKey, binanceSecret).newRestClient();
		return restClient;
	}
	
	@Bean
	public WebSocketClient webSocketClient() {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.connect("wss://api.coinext.com.br/WSGateway", new ProfitableStompSessionHandler());
		return webSocketClient;
	}

}
