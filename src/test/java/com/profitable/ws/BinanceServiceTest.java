package com.profitable.ws;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
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
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.impl.BinanceService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BinanceServiceTest {
	
	@Autowired
	private BinanceService service;
	
	@Ignore
	public void shouldGetWallet() {
		Map<CurrencyType, BigDecimal> wallet = service.getBalance();
		assertNotNull(wallet);
	}
	
	@Ignore
	public void exchangeSymbols() {
		List<Symbol> symbols = service.symbols();
		assertNotNull(symbols);
		Symbol symbol = Symbol.builder().baseAsset(CurrencyType.ETH).quoteAsset(CurrencyType.BTC).build();
		assertTrue(symbols.contains(symbol));
		assertTrue(symbols.stream().filter(s -> s.equals(symbol)).findAny().get().isOcoAllowed());
	}
	
	@Ignore
	public void quote() {
		AssetTicker currencyQuote = service.getCurrencyQuote(CurrencyType.TUSD, CurrencyType.BTC, new BigDecimal(0));
		assertNotNull(currencyQuote);
	}
	
	@Ignore
	public void getWithdrawals() {
		List<Withdraw> withdrawals = service.criptoWithdrawals(CurrencyType.BTC, 0, 0, DepositStatus.CONFIRMED, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 3));
		assertNotNull(withdrawals);
		assertTrue(withdrawals.isEmpty());
	}
	
	@Test
	public void getDeposits() {
		List<CriptoDeposit> deposits = service.criptoDeposits(CurrencyType.BTC, 0, 0, DepositStatus.CONFIRMED, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 3));
		assertNotNull(deposits);
		assertFalse(deposits.isEmpty());
	}
	
}
