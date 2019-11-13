package com.profitable.ws.resource;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitable.ws.model.dto.AssetTicker;
import com.profitable.ws.model.entity.CurrencyType;
import com.profitable.ws.model.entity.ExchangeName;
import com.profitable.ws.model.entity.TradingType;
import com.profitable.ws.service.ExchangeService;
import com.profitable.ws.service.impl.BiscointService;
import com.profitable.ws.service.impl.BitcoinTradeService;
import com.profitable.ws.service.impl.MercadoBitcoinService;

@RestController
@RequestMapping("assets")
public class AssetResource {
	
	@Autowired
	private List<ExchangeService> servicesExchange;
	
	//FIXME Filter service implementation by exchange
	@GetMapping("cripto/quote/{ticker}")
	public ResponseEntity<AssetTicker> quote(TradingType assetType, @PathVariable("ticker") CurrencyType ticker, @RequestParam(value = "exchange", required = false) ExchangeName exchange) {
		ExchangeService exchangeToCheck;
		if (exchange == null) {
			exchange = ExchangeName.BISCOINT;
		}
		switch (exchange) {
		case BITCOIN_TRADE:
			exchangeToCheck = servicesExchange.stream().filter(serv -> serv instanceof BitcoinTradeService).findFirst().get();
			break;
		case MERCADO_BITCOIN:
			exchangeToCheck = servicesExchange.stream().filter(serv -> serv instanceof MercadoBitcoinService).findFirst().get();
			break;
		default:
			exchangeToCheck = servicesExchange.stream().filter(serv -> serv instanceof BiscointService).findFirst().get();
		}
		var currencyQuote = exchangeToCheck.getCurrencyQuote(CurrencyType.BRL, ticker, new BigDecimal(1000));
		return ResponseEntity.ok(currencyQuote);
	}

}
