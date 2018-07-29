package io.pivotal.analytics.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;


@ConfigurationProperties("es-sink")
@Validated
public class EsSinkProperties {

	/**
	 * Elasticsearch cluster name.
	 */
	private String clusterName = "elasticsearch";

	/**
	 * Elasticsearch host name.
	 */
	private String host = "localhost";

	/**
	 * Elasticsearch native port.
	 */
	private String port = "9300";

	@NotBlank
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	@NotBlank
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@NotBlank
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
