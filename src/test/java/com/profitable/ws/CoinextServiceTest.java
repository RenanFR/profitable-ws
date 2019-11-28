package com.profitable.ws;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.service.impl.CoinextService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinextServiceTest {
	
	@Autowired
	private CoinextService service;
	
	@Test
	public void test() {
		assertNotNull(service.getBalance());
//		assertNotNull(service.getCurrencyQuote(CurrencyType.BRL, CurrencyType.BTC, new BigDecimal(0)));
	}

}
