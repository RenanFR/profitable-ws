package com.profitable.ws;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.profitable.ws.model.entity.Trader;
import com.profitable.ws.repositories.TraderRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TraderRepositoryTest implements CRUDTestSuite {
	
	@Autowired
	private TraderRepository repository;
	
	@Override
	@Test
	@Rollback(false)
	public void shouldCreateNew() {
		Trader trader = repository.save(Trader
				.builder()
				.firstName("Nicky")
				.lastName("Romero")
				.build());
		assertNotNull(trader);
	}
	
	@Override
	@Test
	public void shouldListAll() {
		assertFalse(repository.findAll().isEmpty());
	}
	
	@Override
	@Test
	public void shouldFindSpecific() {
		assertTrue(repository.findById(1L).isPresent());
	}

}
