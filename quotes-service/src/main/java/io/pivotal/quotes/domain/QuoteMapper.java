/**
 *
 */
package io.pivotal.quotes.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Singleton Class to map between quote objects as returned by different public quotes URLS
 *
 * @author Sufyaan Kazi
 */
public class QuoteMapper {
	public static QuoteMapper INSTANCE = new QuoteMapper();

	private QuoteMapper(){
		super();
	}

	public Quote mapFromAlphaAdvantageQuote(AlphaAdvantageQuote aaQuote){
		if(aaQuote == null){
			return null;
		}

		Quote mappedQuote = new Quote();
		mappedQuote.setLastPrice(aaQuote.getPrice());
		mappedQuote.setStatus("SUCCESS");
		mappedQuote.setSymbol(aaQuote.getSymbol());
		mappedQuote.setTimestamp(aaQuote.getTimestamp());
		if(!aaQuote.getVolume().equals("--")) {
			mappedQuote.setVolume(Integer.parseInt(aaQuote.getVolume()));
		} else {
			mappedQuote.setVolume(0);
		}

		return mappedQuote;
	}

}
