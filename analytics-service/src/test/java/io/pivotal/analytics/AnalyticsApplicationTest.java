package io.pivotal.analytics;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
@TestPropertySource(properties = { "elasticsearch.clustername=elasticsearch",
		"elasticsearch.host=10.193.152.238",
		"elasticsearch.port=32231" })
public class AnalyticsApplicationTest {

	@Test
	public void contextLoads() {
	}
}