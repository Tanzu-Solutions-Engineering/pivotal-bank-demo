/**
 *
 */
package io.pivotal.quotes.domain;

import java.util.Date;

/**
 * Singleton Class to map between quote objects as returned by different public quotes URLS
 *
 * @author Sufyaan Kazi
 */
public class QuoteMapper {
    public static QuoteMapper INSTANCE = new QuoteMapper();

    private QuoteMapper() {
        super();
    }

    public Quote mapFromIexQuote(IexQuote iexQuote) {
        if (iexQuote == null) {
            return null;
        }

        Quote mappedQuote = new Quote();
        mappedQuote.setSymbol(iexQuote.getSymbol());
        mappedQuote.setName(iexQuote.getCompanyName());
        mappedQuote.setOpen(iexQuote.getOpen());
        mappedQuote.setHigh(iexQuote.getHigh());
        mappedQuote.setLow(iexQuote.getLow());
        mappedQuote.setChange(iexQuote.getChange());
        mappedQuote.setChangePercent(iexQuote.getChangePercent().floatValue());
        mappedQuote.setMarketCap(iexQuote.getMarketCap().floatValue());
        if ("Previous close".equals(iexQuote.getLatestSource())) {
            mappedQuote.setLastPrice(iexQuote.getClose());
            mappedQuote.setTimestamp(new Date(iexQuote.getCloseTime()));
        } else {
            mappedQuote.setLastPrice(iexQuote.getLatestPrice());
            mappedQuote.setTimestamp(new Date(iexQuote.getLatestUpdate()));
        }
        mappedQuote.setStatus("SUCCESS");
        mappedQuote.setVolume((int) Math.round(iexQuote.getAvgTotalVolume().doubleValue()));

        return mappedQuote;
    }
}
