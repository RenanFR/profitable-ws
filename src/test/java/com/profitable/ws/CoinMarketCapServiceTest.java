package com.profitable.ws;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.entity.Cripto;
import com.profitable.ws.service.impl.CoinMarketCapService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinMarketCapServiceTest {
	
	@Autowired
	private CoinMarketCapService service;
	
	@Test
	public void shouldGetCurrencies() {
		List<Cripto> currencies = service.cryptoCurrencies();
		assertTrue(!currencies.isEmpty());
		String all = currencies
			.stream()
			.distinct()
			.map((var c) -> c.getSymbol() + "(" + "\"" + c.getSlug() + "\"" + ")")
			.sorted(Comparator.comparing(s -> s))
			.filter((String c) -> !Character.isDigit(c.charAt(0)))
			.collect(Collectors.joining(",\n"));
		System.out.println(all);
		assertTrue(currencies.stream().filter(c -> c.getName().equals("Bitcoin")).findAny().isPresent());
	}
}
