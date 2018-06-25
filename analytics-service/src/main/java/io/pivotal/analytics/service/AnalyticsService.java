package io.pivotal.analytics.service;

import io.pivotal.analytics.entity.Trade;
import io.pivotal.analytics.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
public class AnalyticsService {
	private static final Logger logger = LoggerFactory
			.getLogger(AnalyticsService.class);

	@Autowired
	TradeRepository repository;

	@Value("${pivotal.accountsService.name}")
	protected String accountsService;

	public List<Trade> getTrades(String symbol) {

		logger.debug("Getting trades for symbol: " + symbol);
		List<Trade> trades = repository.findBySymbolOrderByCompletiondateDesc(symbol, new PageRequest(0, 20)).getContent();
		return trades;

	}

}
