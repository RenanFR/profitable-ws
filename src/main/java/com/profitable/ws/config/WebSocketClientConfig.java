package com.profitable.ws.config;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebSocketClientConfig {
	
	@PostConstruct
	public void connect() {
		String binanceWs = "wss://stream.binance.com:9443";
		String echoWs = "ws://echo.websocket.org";
		WebSocketClient client = new StandardWebSocketClient();
		try {
			WebSocketSession session = client.doHandshake(new TextWebSocketHandler() {
				
				@Override
				protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
					log.info("New message arrived " + message.getPayload());
				}
				
				@Override
				public void afterConnectionEstablished(WebSocketSession session) throws Exception {
					log.info("Successfully connected");
				}
				
			}, new WebSocketHttpHeaders(), URI.create(echoWs)).get();
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
				try {
					session.sendMessage(new TextMessage("Test message from myself"));
				} catch (Exception exception) {
					log.error(exception.getMessage());
				}
			}, 1, 10, TimeUnit.SECONDS);
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}
	

}
