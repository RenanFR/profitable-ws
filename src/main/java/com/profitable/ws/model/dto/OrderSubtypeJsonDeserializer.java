package com.profitable.ws.model.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.profitable.ws.model.entity.OrderSubtype;

public class OrderSubtypeJsonDeserializer extends StdDeserializer<OrderSubtype> {
	
	public OrderSubtypeJsonDeserializer() {
		this(null);
	}

	protected OrderSubtypeJsonDeserializer(Class<?> clss) {
		super(clss);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public OrderSubtype deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return OrderSubtype.valueOf(json.getText().toUpperCase());
	}
	
}
