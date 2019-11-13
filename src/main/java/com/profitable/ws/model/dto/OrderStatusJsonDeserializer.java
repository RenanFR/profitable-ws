package com.profitable.ws.model.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.profitable.ws.model.entity.OrderStatus;

public class OrderStatusJsonDeserializer extends StdDeserializer<OrderStatus> {
	
	private static final long serialVersionUID = 1L;

	public OrderStatusJsonDeserializer() {
		this(null);
	}
	
	public OrderStatusJsonDeserializer(Class<?> clss) {
		super(clss);
	}

	@Override
	public OrderStatus deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return OrderStatus.valueOf(json.getText().toUpperCase());
	}
	
}
