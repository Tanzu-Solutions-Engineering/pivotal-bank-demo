package io.pivotal.quotes.service;

import io.pivotal.quotes.QuotesApplication;
import io.pivotal.quotes.configuration.TestConfiguration;
import io.pivotal.quotes.domain.CompanyInfo;
import io.pivotal.quotes.domain.Quote;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the QuoteService.
 *
 * @author David Ferreira Pinto
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuotesApplication.class)
public class QuoteServiceTest {
    MockMvc mockMvc;

    @InjectMocks
    @Autowired
    QuoteService service;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();

    }

    /**
     * Tests retrieving a quote from the external service.
     *
     * @throws Exception
     */
    @Test
    public void getQuote() throws Exception {
        Quote quote = service.getQuote(TestConfiguration.QUOTE_SYMBOL);
        assertEquals(TestConfiguration.QUOTE_SYMBOL, quote.getSymbol());
        assertEquals(TestConfiguration.QUOTE_NAME, quote.getName());
    }

    /**
     * Tests retrieving a quote with an unknown/null symbol from the external service.
     *
     * @throws Exception
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getNullQuote() throws Exception {
        Quote quote = service.getQuote(TestConfiguration.NULL_QUOTE_SYMBOL);
        assertEquals(quote.getStatus(), "FAILED");
    }

    /**
     * tests retrieving company information from external service.
     *
     * @throws Exception
     */
    @Test
    public void getCompanyInfo() throws Exception {
        List<CompanyInfo> comps = service.getCompanyInfo(TestConfiguration.QUOTE_SYMBOL);
        assertFalse(comps.isEmpty());
        boolean pass = false;
        for (CompanyInfo info : comps) {
            if (info.getSymbol().equals(TestConfiguration.QUOTE_SYMBOL)) {
                pass = true;
            }
        }
        assertTrue(pass);
    }

    /**
     * tests retrieving company information from external service with unkown company.
     *
     * @throws Exception
     */
    @Test
    public void getNullCompanyInfo() throws Exception {
        List<CompanyInfo> comps = service.getCompanyInfo(TestConfiguration.NULL_QUOTE_SYMBOL);
        assertTrue(comps.isEmpty());
    }

    /**
     * test yahoo service with multiple quotes
     *
     * @throws Exception
     */
    @Test
    public void getQuotes() throws Exception {
        List<Quote> quotes = service.getQuotes(TestConfiguration.QUOTE_SYMBOLS);
        assertNotNull("should have 2 quotes", quotes);
        assertEquals("should have 2 quotes", quotes.size(), 2);
    }

    @Test
    public void getQuotesOneQuote() throws Exception {
        List<Quote> quotes = service.getQuotes(TestConfiguration.QUOTE_SYMBOL);
        assertNotNull("should have 1 quotes", quotes);
        assertEquals("should have 1 quotes", quotes.size(), 1);
    }
}

