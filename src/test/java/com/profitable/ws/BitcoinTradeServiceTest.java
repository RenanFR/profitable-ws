package com.profitable.ws;

import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.service.impl.BitcoinTradeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinTradeServiceTest {
	
	@Autowired
	private BitcoinTradeService service;
	
	@Test
	public void shouldGetQuote() {
		AssetTicker currencyQuote = service.getCurrencyQuote(CurrencyType.BRL, CurrencyType.BTC, new BigDecimal(0));
		assertNotEquals(new BigDecimal(0), currencyQuote.getLast());
	}

}
