package io.pivotal.portfolio;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortfolioApplication.class)
public class PortfolioApplicationTest {
	/**
	 * test loading of spring context.
	 */
	@Test
	public void contextLoads() {
	}
}
