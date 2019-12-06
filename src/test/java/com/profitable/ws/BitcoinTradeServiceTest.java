package com.profitable.ws;

import static org.junit.Assert.assertEquals;
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
import com.profitable.ws.model.dto.Symbol;
import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Withdraw;
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
		List<Order> orders = service.orders(OrderStatus.EXECUTED_COMPLETELY, LocalDate.of(2019, 06, 26), LocalDate.of(2019, 10, 21), Symbol.builder().baseAsset(CurrencyType.BRL).quoteAsset(CurrencyType.BTC).build(), OrderType.SELL, 100, 1);
		assertTrue(orders.size() == 5);
	}
	
	
	@Test
	public void shouldGetDeposits() {
		List<CriptoDeposit> deposits = service.criptoDeposits(CurrencyType.BTC, 100, 1, DepositStatus.CONFIRMED, LocalDate.of(2019, 06, 06), LocalDate.of(2019, 9, 03));
		assertTrue(deposits.size() == 4);
	}
	
	@Test
	public void shouldGetWithdrawals() {
		List<Withdraw> withdrawals = service.criptoWithdrawals(CurrencyType.BTC, 100, 1, DepositStatus.CONFIRMED, LocalDate.of(2019, 05, 24), LocalDate.of(2019, 10, 22));
		assertTrue(withdrawals.size() == 12);
	}
	
	@Test
	public void shouldGetBalance() {
		assertEquals(Double.valueOf(0.00000039), service.getBalance().get(CurrencyType.BCH).doubleValue(), 0);
	}
	

}
