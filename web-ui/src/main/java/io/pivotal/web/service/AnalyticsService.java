package io.pivotal.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.web.domain.Quote;
import io.pivotal.web.domain.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@RefreshScope
public class AnalyticsService {
	private static final Logger logger = LoggerFactory
			.getLogger(AnalyticsService.class);

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@Value("${pivotal.downstream-protocol:http}")
	protected String downstreamProtocol;

	@Value("${pivotal.analyticsService.name}")
	private String analyticsService;
	
	@HystrixCommand(fallbackMethod = "getAnalyticsFallback")
	public List<Trade> getTrades(String symbol) {
		logger.debug("Fetching trades: " + symbol);
		Trade[] tradesArr = restTemplate.getForObject(downstreamProtocol + "://" + analyticsService + "/analytics/trades/{symbol}", Trade[].class, symbol);
		List<Trade> trades = Arrays.asList(tradesArr);
		logger.debug("Found " + trades.size() + " trades");
		return trades;
	}
	
	private List<Trade> getAnalyticsFallback(String symbol) {
		logger.warn("Falling back due to error.");

		return new ArrayList<Trade>();
	}

}
