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
	
	/**
	 * Maps between a Yahoo Quote to Markit Quote
	 * 
	 * @param YahooQuote
	 * @return new quote
	 * @author David Ferreira Pinto
	 */
	public Quote mapFromYahooQuote(YahooQuote yQuote, Date created){
		if(yQuote == null){
			return null;
		}
		
		
		Quote mappedQuote = new Quote();
		if (yQuote.getChange() == null ) {
			mappedQuote.setChange(BigDecimal.ZERO);
		} else {
			mappedQuote.setChange(yQuote.getChange());
		}
		if (yQuote.getPercentChange() != null) {
			mappedQuote.setChangePercent(Float.parseFloat(yQuote.getPercentChange().replace("%", "")));
		}
		if (yQuote.getPercentChangeFromTwoHundreddayMovingAverage() != null) {
			mappedQuote.setChangePercentYTD(Float.parseFloat(yQuote.getPercentChangeFromTwoHundreddayMovingAverage().replace("%", "")));
		}

		if (yQuote.getChangeFromTwoHundreddayMovingAverage() != null) {
			mappedQuote.setChangeYTD(yQuote.getChangeFromTwoHundreddayMovingAverage().floatValue());
		} else {
			mappedQuote.setChangeYTD(0f);
		}
		mappedQuote.setHigh(yQuote.getDaysHigh());
		mappedQuote.setLastPrice(yQuote.getLastTradePriceOnly());
		mappedQuote.setLow(yQuote.getDaysLow());
		//mappedQuote.setMarketCap(yQuote.getMarketCapitalization());
		mappedQuote.setmSDate(null);
		mappedQuote.setName(yQuote.getName());
		mappedQuote.setOpen(yQuote.getOpen());
		mappedQuote.setStatus("SUCCESS");
		mappedQuote.setSymbol(yQuote.getSymbol());
		mappedQuote.setTimestamp(created);
		mappedQuote.setVolume(yQuote.getVolume());
		mappedQuote.setCurrency(yQuote.getCurrency());
		
		return mappedQuote;
	}
}
