package io.pivotal.quotes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlphaAdvantageQuote {
	/*
{
    "Meta Data": {
        "1. Information": "Batch Stock Market Quotes",
        "2. Notes": "IEX Real-Time Price provided for free by IEX (https://iextrading.com/developer/).",
        "3. Time Zone": "US/Eastern"
    },
    "Stock Quotes": [
        {
            "1. symbol": "MSFT",
            "2. price": "90.3900",
            "3. volume": "26236380",
            "4. timestamp": "2018-03-22 15:27:20"
        },
        {
            "1. symbol": "FB",
            "2. price": "166.3200",
            "3. volume": "66033155",
            "4. timestamp": "2018-03-22 15:27:20"
        },
        {
            "1. symbol": "AAPL",
            "2. price": "170.7200",
            "3. volume": "32402881",
            "4. timestamp": "2018-03-22 15:27:21"
        }
    ]
}
	 */


	@JsonProperty("1. symbol")
	private String symbol;

	@JsonProperty("2. price")
	private BigDecimal price;

	@JsonProperty("3. volume")
	private String volume;

	@JsonProperty("4. timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "ENGLISH")
	private Date timestamp;

}