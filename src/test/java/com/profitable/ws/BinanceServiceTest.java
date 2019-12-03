package com.profitable.ws;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.service.impl.BinanceService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BinanceServiceTest {
	
	@Autowired
	private BinanceService service;
	
	@Test
	public void shouldGetWallet() {
		Map<CurrencyType, BigDecimal> wallet = service.getBalance();
		assertNotNull(wallet);
	}
	
//	@Test
	public void exchangeSymbols() {
		assertNotNull(service.symbols());
	}
}
