package com.profitable.ws.model.dto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.profitable.ws.model.entity.Cripto;

public class CoinMarketCapPayloadDeserializer<T> extends StdDeserializer<CoinMarketCapPayload<T>> {
	
	private ObjectMapper mapper;
	
	public CoinMarketCapPayloadDeserializer() {
		this(null);
	}

	public CoinMarketCapPayloadDeserializer(Class<?> vc) {
		super(vc);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public CoinMarketCapPayload<T> deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		mapper = (ObjectMapper) json.getCodec();
		ObjectNode node = (ObjectNode) mapper.readTree(json);
		Iterator<Entry<String, JsonNode>> fields = node.fields();
		CoinMarketCapPayload.Status status = new CoinMarketCapPayload.Status();
		while (fields.hasNext()) {
			Entry<String, JsonNode> field = fields.next();
			String fieldKey = field.getKey();
			JsonNode fieldBody = field.getValue();
			if (fieldKey.equals("status")) {
				status.setTimestamp(LocalDateTime.parse(fieldBody.get("timestamp").asText(), DateTimeFormatter.ISO_DATE_TIME));
				status.setErrorCode(((long)fieldBody.get("error_code").asInt()));
				status.setErrorMessage(fieldBody.get("error_message").asText());
				status.setElapsed(fieldBody.get("elapsed").asInt());
				status.setCreditCount(fieldBody.get("credit_count").asInt());
				
			}
			if (fieldKey.equals("data")) {
				CoinMarketCapPayload<Cripto> payload = new CoinMarketCapPayload<>();
				payload.setStatus(status);
				List<Cripto> criptos = Arrays.asList(mapper.readValue(fieldBody.toString(), Cripto[ ].class));
				payload.setData(criptos);
				return (CoinMarketCapPayload<T>) payload;
			}
		}
		return null;
	}

}
