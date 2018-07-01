package io.pivotal.analytics;

import io.pivotal.analytics.Application;
import io.pivotal.analytics.entity.Trade;
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

//    @Test
//    public void testFindOne() {
//
//        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//        bookService.save(book);
//
//        Book testBook = bookService.findOne(book.getId());
//
//        assertNotNull(testBook.getId());
//        assertEquals(testBook.getTitle(), book.getTitle());
//        assertEquals(testBook.getAuthor(), book.getAuthor());
//        assertEquals(testBook.getReleaseDate(), book.getReleaseDate());
//
//    }
//
//    @Test
//    public void testFindByTitle() {
//
//        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//        bookService.save(book);
//
//        List<Book> byTitle = bookService.findByTitle(book.getTitle());
//        assertThat(byTitle.size(), is(1));
//    }
//
//    @Test
//    public void testFindByAuthor() {
//
//        List<Book> bookList = new ArrayList<>();
//
//        bookList.add(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
//        bookList.add(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
//        bookList.add(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
//        bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Rambabu Posa", "01-APR-2017"));
//        bookList.add(new Book("1008", "Spring Boot + MongoDB", "Mkyong", "25-FEB-2017"));
//
//        for (Book book : bookList) {
//            bookService.save(book);
//        }
//
//        Page<Book> byAuthor = bookService.findByAuthor("Rambabu Posa", new PageRequest(0, 10));
//        assertThat(byAuthor.getTotalElements(), is(4L));
//
//        Page<Book> byAuthor2 = bookService.findByAuthor("Mkyong", new PageRequest(0, 10));
//        assertThat(byAuthor2.getTotalElements(), is(1L));
//
//    }
//
//    @Test
//    public void testDelete() {
//
//        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//        bookService.save(book);
//        bookService.delete(book);
//        Book testBook = bookService.findOne(book.getId());
//        assertNull(testBook);
//    }

}
