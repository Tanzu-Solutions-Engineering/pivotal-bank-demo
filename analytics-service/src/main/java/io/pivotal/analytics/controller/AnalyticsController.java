package io.pivotal.analytics.controller;

import io.pivotal.analytics.entity.Trade;
import io.pivotal.analytics.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyticsController {

	private static final Logger logger = LoggerFactory
			.getLogger(AnalyticsController.class);

	@Autowired
	private AnalyticsService service;

	@RequestMapping(value = "/analytics/trades/{symbol}", method = RequestMethod.GET)
	public ResponseEntity<List<Trade>> getTrades(@PathVariable("symbol") final String symbol) {

		logger.debug("AnalyticsController: Retrieving trades for symbol:" + symbol);
		List<Trade> trades = service.getTrades(symbol);
		logger.debug("AnalyticsController: Retrieved trades for symbol:" + symbol);
		return new ResponseEntity<List<Trade>>(trades, getNoCacheHeaders(), HttpStatus.OK);

	}
	
	private HttpHeaders getNoCacheHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Cache-Control", "no-cache");
		return responseHeaders;
	}

}
