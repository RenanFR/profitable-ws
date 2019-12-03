package com.profitable.ws.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.profitable.ws.model.dto.CoinMarketCapPayload;
import com.profitable.ws.model.entity.Cripto;

@Service("coinMarketCap")
public class CoinMarketCapService {
	
	enum CryptoListingStatus {
		ACTIVE,
		INACTIVE,
		UNTRACKED
	}

	private static final String SECURITY_HEADER = "X-CMC_PRO_API_KEY";
	
	@Value("${coinmarketcap.api.key}")
	private String apiKey;
	
	@Value("${coinmarketcap.api.endpoint}")
	private String apiEndpoint;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private HttpEntity<Object> request;
	
	@PostConstruct
	public void prepareRequest() {
		headers.add("Accept", "application/json");
		headers.add("Accept-Encoding", "deflate, gzip");
		headers.add(SECURITY_HEADER, apiKey);
		request = new HttpEntity<Object>(headers);
	}
	
	public List<Cripto> cryptoCurrencies() {
		String endpoint = UriComponentsBuilder
			.fromHttpUrl(apiEndpoint.concat("/cryptocurrency/map"))
			.queryParam("listing_status", CryptoListingStatus.ACTIVE.toString().toLowerCase())
			.toUriString();
		ResponseEntity<CoinMarketCapPayload<Cripto>> currencies = restTemplate.exchange(endpoint, HttpMethod.GET, request, new ParameterizedTypeReference<CoinMarketCapPayload<Cripto>>() {});
		return currencies
				.getBody()
				.getData();
	}
	
}
