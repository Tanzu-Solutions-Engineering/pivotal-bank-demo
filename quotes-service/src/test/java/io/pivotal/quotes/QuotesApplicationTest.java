package io.pivotal.quotes;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for the Quotes Application.
 * @author David Ferreira Pinto
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuotesApplication.class)
public class QuotesApplicationTest {
	
	/**
	 * test loading of spring context.
	 */
	@Test
	public void contextLoads() {
	}
}
