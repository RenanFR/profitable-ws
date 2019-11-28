package com.profitable.ws.config;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class ProfitableStompSessionHandler implements StompSessionHandler {

	@Override
	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

}
