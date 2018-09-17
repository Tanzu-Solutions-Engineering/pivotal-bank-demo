package io.pivotal.analytics;

import io.pivotal.analytics.Application;
import io.pivotal.analytics.entity.Trade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import io.pivotal.analytics.repository.TradeRepository;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class TradeServiceTest {

    @Autowired
    private TradeRepository repository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(Trade.class);
        esTemplate.createIndex(Trade.class);
        esTemplate.putMapping(Trade.class);
        esTemplate.refresh(Trade.class);
    }

    @After
    public void tearDown() throws Exception {
        esTemplate.deleteIndex(Trade.class);
    }

    @Test
    public void testSave() {

        Trade trade = new Trade();
        trade.setOrderid(1);
        trade.setPrice(BigDecimal.valueOf(31.21));
        trade.setSymbol("MS");
        Trade testTrade = repository.save(trade);

        assertNotNull(testTrade.getId());
        assertThat(testTrade.getOrderid(), equalTo(trade.getOrderid()));
        assertThat(testTrade.getSymbol(), equalTo(trade.getSymbol()));
        assertThat(testTrade.getPrice(), equalTo(trade.getPrice()));

    }

}
