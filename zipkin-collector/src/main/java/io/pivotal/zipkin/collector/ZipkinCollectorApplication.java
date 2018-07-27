package io.pivotal.zipkin.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
@EnableDiscoveryClient
public class ZipkinCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinCollectorApplication.class, args);
	}
}
