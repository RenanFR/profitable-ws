package com.profitable.ws.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitable.ws.model.entity.CriptoDeposit;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.DepositStatus;
import com.profitable.ws.model.entity.EntityType;
import com.profitable.ws.model.entity.Order;
import com.profitable.ws.model.entity.OrderStatus;
import com.profitable.ws.model.entity.OrderType;
import com.profitable.ws.model.entity.Trader;
import com.profitable.ws.model.entity.Withdraw;
import com.profitable.ws.service.ExchangeAccountService;
import com.profitable.ws.service.impl.TraderService;

@RestController
@RequestMapping("traders")
public class TraderResource implements GenericController<Trader> {
	
	@Autowired
	private TraderService service;
	
	@Autowired
	private ExchangeAccountService exchangeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TraderResource.class);
	
	@Override
	@GetMapping
	public ResponseEntity<List<Trader>> list() {
		List<Trader> traders = service.all();
		traders.forEach(t -> t.add(addSelfLink(this, t)));
		return ResponseEntity.ok(traders);
	}
	
	@Override
	@PostMapping
	public ResponseEntity<Trader> save(@RequestBody Trader trader) {
		LOGGER.info("Creating user " + trader);
		Trader createdTrader = service.create(trader);
		createdTrader.add(addSelfLink(this, trader));
		return ResponseEntity.ok(trader);
	}

	@Override
	@GetMapping("{id}")
	public ResponseEntity<Trader> searchById(@PathVariable("id") Long id) {
		Optional<Trader> optTrader = service.query(id);
		if (optTrader.isPresent()) {
			Trader trader = optTrader.get();
			trader.add(addListLink(this, EntityType.TRADER));
			return ResponseEntity.ok(trader);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("{id}/wallet/balance")
	public ResponseEntity<Map<CurrencyType, BigDecimal>> walletBalance(@PathVariable("id") Long traderId) {
		return ResponseEntity.ok(exchangeService.getBalance());
	}
	
	@GetMapping("{id}/orders")
	public ResponseEntity<List<Order>> orders(@PathVariable("id") Long traderId, @RequestParam("status") OrderStatus status, 
			@RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate,
			@RequestParam("pair") String pair, @RequestParam("type") OrderType orderType,
			@RequestParam(value = "page_size", required = false) Integer pageSize, @RequestParam(value = "current_page", required = false) Integer currentPage) {
		return ResponseEntity.ok(exchangeService.orders(status, startDate, endDate, CurrencyType.valueOf(pair.substring(3)), orderType, pageSize, currentPage));
	}
	
	@GetMapping("{id}/deposits/{coin}")
	public ResponseEntity<List<CriptoDeposit>> deposits(@PathVariable("id") Long traderId, @PathVariable("coin") CurrencyType coin, @RequestParam("status") DepositStatus status, 
			@RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate,
			@RequestParam(value = "page_size", required = false) Integer pageSize, @RequestParam(value = "current_page", required = false) Integer currentPage) {
		return ResponseEntity.ok(exchangeService.criptoDeposits(coin, pageSize, currentPage, status, startDate, endDate));
	}
	
	@GetMapping("{id}/withdrawals/{coin}")
	public ResponseEntity<List<Withdraw>> withdrawals(@PathVariable("id") Long traderId, @PathVariable("coin") CurrencyType coin, @RequestParam("status") DepositStatus status, 
			@RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate,
			@RequestParam(value = "page_size", required = false) Integer pageSize, @RequestParam(value = "current_page", required = false) Integer currentPage) {
		return ResponseEntity.ok(exchangeService.criptoWithdrawals(coin, pageSize, currentPage, status, startDate, endDate));
	}
	
}
