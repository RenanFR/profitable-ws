package com.profitable.ws.model.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.profitable.ws.model.entity.OrderType;

public class OrderTypeJsonDeserializer extends StdDeserializer<OrderType> {

	private static final long serialVersionUID = 1L;
	
	public OrderTypeJsonDeserializer() {
		this(null);
	}
	
	protected OrderTypeJsonDeserializer(Class<?> clss) {
		super(clss);
	}

	@Override
	public OrderType deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return OrderType.valueOf(json.getText().toUpperCase());
	}

}
