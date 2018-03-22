package io.pivotal.accounts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Tests for the Accounts Application.
 * @author David Ferreira Pinto
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountsApplication.class)
@WebAppConfiguration
public class AccountsApplicationTest {
	/**
	 * test loading of spring context.
	 */
	@Test
	public void contextLoads() {
	}
}
