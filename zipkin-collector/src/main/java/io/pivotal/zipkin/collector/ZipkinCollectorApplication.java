package io.pivotal.zipkin.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
@EnableDiscoveryClient
public class ZipkinCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinCollectorApplication.class, args);
	}
}
