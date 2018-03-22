package io.pivotal.quotes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlphaAdvantageQuoteList {
	
	@JsonProperty(value="Stock Quotes")
	private List<AlphaAdvantageQuote> quote;

}