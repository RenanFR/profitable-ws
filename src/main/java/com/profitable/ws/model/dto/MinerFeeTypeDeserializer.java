package com.profitable.ws.model.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.profitable.ws.model.entity.MinerFeeType;

public class MinerFeeTypeDeserializer extends StdDeserializer<MinerFeeType> {

	private static final long serialVersionUID = 1L;
	
	public MinerFeeTypeDeserializer() {
		this(null);
	}
	
	protected MinerFeeTypeDeserializer(Class<?> clss) {
		super(clss);
	}

	@Override
	public MinerFeeType deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return MinerFeeType.valueOf(json.getText().toUpperCase());
	}

}
