package io.pivotal.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

//	@Bean
//	public RouteLocator customRouteLocator(ThrottleWebFilterFactory throttle) {
//		return Routes.locator()
//				.route("test")
//				.uri("http://httpbin.org:80")
//				.predicate(host("**.abc.org").and(path("/image/png")))
//				.addResponseHeader("X-TestHeader", "foobar")
//				.and()
//				.route("test2")
//				.uri("http://httpbin.org:80")
//				.predicate(path("/image/webp"))
//				.add(addResponseHeader("X-AnotherHeader", "baz"))
//				.and()
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
