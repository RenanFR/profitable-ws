package com.profitable.ws;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderType;
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
	
	@Test
	public void shouldGetTraderOrders() {
		List<Order> orders = service.orders(OrderStatus.EXECUTED_COMPLETELY, LocalDate.of(2019, 06, 26), LocalDate.of(2019, 10, 21), CurrencyType.BTC, OrderType.SELL, 100, 1);
		assertTrue(orders.size() == 5);
	}

}
