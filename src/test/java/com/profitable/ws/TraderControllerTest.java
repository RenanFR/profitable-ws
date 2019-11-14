package com.profitable.ws;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.service.impl.BitcoinTradeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TraderControllerTest {
	
	@LocalServerPort
	private int serverPort;
	
	@MockBean
	private BitcoinTradeService exchangeService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void balanceWithoutAuthorization() {
		given(exchangeService.getBalance()).willReturn(Map.of(CurrencyType.BCH, new BigDecimal(0.00000039)));
		Map response = this.restTemplate.getForObject("http://localhost:" + serverPort + "/traders/1/wallet/balance", Map.class);
		assertTrue(response.get("error").equals("unauthorized"));
	}

}
