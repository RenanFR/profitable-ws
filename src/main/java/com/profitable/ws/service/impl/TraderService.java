package com.profitable.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.profitable.ws.model.entity.Trader;
import com.profitable.ws.repositories.TraderRepository;

@Service
public class TraderService implements UserDetailsService {
	
	@Autowired
	private TraderRepository repository;
	
	public Trader create(Trader trader) {
		return repository.save(trader);
	}
	
	public List<Trader> all() {
		return repository.findAll();
	}
	
	public Optional<Trader> query(Long id) {
		return repository.findById(id);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Trader> traderUser = repository.findByEmail(username);
		return traderUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
}
