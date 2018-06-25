package io.pivotal.analytics;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import io.pivotal.analytics.entity.Trade;
import io.pivotal.analytics.service.AnalyticsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
@TestPropertySource(properties = { "elasticsearch.clustername=elasticsearch",
		"elasticsearch.host=10.193.152.238",
		"elasticsearch.port=32231" })
public class AnalyticsServiceTest {

	@Autowired
	AnalyticsService service;

	@Test
	public void testFindByTitle() {

		List<Trade> bySymbol = service.getTrades("PVTL");
		assertThat(bySymbol.size(), greaterThan(0));
	}

}
