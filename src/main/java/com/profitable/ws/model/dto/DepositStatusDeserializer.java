package com.profitable.ws.model.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.profitable.ws.model.entity.DepositStatus;

public class DepositStatusDeserializer extends StdDeserializer<DepositStatus> {

	private static final long serialVersionUID = 1L;
	
	public DepositStatusDeserializer() {
		this(null);
	}
	
	protected DepositStatusDeserializer(Class<?> clss) {
		super(clss);
	}

	@Override
	public DepositStatus deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return DepositStatus.valueOf(json.getText().toUpperCase());
	}

}
