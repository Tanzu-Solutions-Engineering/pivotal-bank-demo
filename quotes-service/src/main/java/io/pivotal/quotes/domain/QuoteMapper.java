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
        mappedQuote.setHigh(iexQuote.getWeek52High());
        mappedQuote.setLow(iexQuote.getWeek52Low());
        mappedQuote.setChange(iexQuote.getChange());
        mappedQuote.setChangePercent(iexQuote.getChangePercent().floatValue());
        mappedQuote.setMarketCap(iexQuote.getMarketCap().floatValue());
        if ("Close".equalsIgnoreCase(iexQuote.getLatestSource())) {
            mappedQuote.setLastPrice(iexQuote.getClose());

        } else {
            mappedQuote.setLastPrice(iexQuote.getLatestPrice());
            mappedQuote.setTimestamp(new Date(iexQuote.getLatestUpdate()));
        }
        mappedQuote.setStatus("SUCCESS");
        mappedQuote.setVolume((int) Math.round(iexQuote.getAvgTotalVolume().doubleValue()));

        mappedQuote.setMarketCap(iexQuote.getMarketCap().floatValue());
        mappedQuote.setChangeYTD(iexQuote.getYtdChange().floatValue());
        mappedQuote.setCurrency("USD");
        return mappedQuote;
    }
}
