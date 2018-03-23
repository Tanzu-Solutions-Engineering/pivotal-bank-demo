package io.pivotal.user;

import io.pivotal.user.UserApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for the Accounts Application.
 * @author David Ferreira Pinto
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class AccountsApplicationTest {
	/**
	 * test loading of spring context.
	 */
	@Test
	public void contextLoads() {
	}
}
