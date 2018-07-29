package io.pivotal.analytics;

import io.pivotal.analytics.entity.Trade;
import io.pivotal.analytics.repository.TradeRepository;
import io.pivotal.analytics.service.AnalyticsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
public class AnalyticsServiceTest {

	@Autowired
	AnalyticsService service;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Autowired
	private TradeRepository repository;

	@Before
	public void before() {
		esTemplate.deleteIndex(Trade.class);
		esTemplate.createIndex(Trade.class);
		esTemplate.putMapping(Trade.class);
		esTemplate.refresh(Trade.class);

        Trade trade = new Trade();
        trade.setUserid("nfoles");
        trade.setSymbol("PVTL");
        trade.setQuantity(100);
        trade.setPrice(BigDecimal.valueOf(24.78));
        trade.setCompletiondate(new Date());
        repository.save(trade);

	}

	@After
	public void teardown() {
		esTemplate.deleteIndex(Trade.class);
	}

	@Test
	public void testFindByTitle() {

		List<Trade> bySymbol = service.getTrades("PVTL");
		assertThat(bySymbol.size(), greaterThan(0));
	}

}
