package io.pivotal.quotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaAdvantageResponse {

	@JsonProperty(value="Stock Quotes")
	private List<AlphaAdvantageQuote> quotes;

	@JsonProperty(value="Meta Data")
	private Map<String, String> metadata;

}