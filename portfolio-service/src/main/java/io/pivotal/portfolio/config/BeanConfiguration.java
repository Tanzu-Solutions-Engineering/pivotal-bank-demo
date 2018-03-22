package io.pivotal.portfolio.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
	@LoadBalanced
    @Bean
    public RestTemplate loadBalanced() {
        return new RestTemplate();
    }
}
