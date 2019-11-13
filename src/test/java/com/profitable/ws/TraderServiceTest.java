package com.profitable.ws;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.entity.Trader;
import com.profitable.ws.repositories.TraderRepository;
import com.profitable.ws.service.impl.TraderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraderServiceTest {
	
	@Autowired
	private TraderService service;
	
	@MockBean
	private TraderRepository repository;
	
	@Test
	public void mustCreate() {
		Trader trader = Trader
				.builder()
				.firstName("Renan")
				.lastName("Rodrigues")
				.build();
		given(repository.save(trader)).willReturn(trader);
		assertNotNull(service.create(trader));
	}
	

	
}
