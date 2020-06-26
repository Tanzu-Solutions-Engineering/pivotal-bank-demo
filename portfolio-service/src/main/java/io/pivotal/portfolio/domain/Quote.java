package io.pivotal.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a point in time price and information for a company's stock.
 *
 * @author David Ferreira Pinto
 *
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	/*
     * {
        "Name":"Apple Inc",
        "Symbol":"AAPL",
        "LastPrice":524.49,
        "Change":15.6,
        "ChangePercent":3.06549549018453,
        "Timestamp":"Wed Oct 23 13:39:19 UTC-06:00 2013",
        "MSDate": 41570.568969907,
        "MarketCap":476497591530,
        "Volume":397562,
        "ChangeYTD":532.1729,
        "ChangePercentYTD":-1.44368493773359,
        "High":52499,
        "Low":519.175,
        "Open":519.175
        "Currency":"USD"
    }
     */
	@JsonProperty("Symbol")
	private String symbol;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Open")
	private BigDecimal open;
	@JsonProperty("High")
	private BigDecimal high;
	@JsonProperty("Low")
	private BigDecimal low;

	@JsonProperty("Change")
	private BigDecimal change;
	@JsonProperty("ChangePercent")
	private Float changePercent;
	@JsonProperty("LastPrice")
	private BigDecimal lastPrice;

	@JsonProperty("Timestamp")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="EEE MMM dd HH:mm:ss zzzXXX yyyy", locale="ENGLISH")
	private Date timestamp;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Volume")
	private Integer volume;


	@JsonProperty("MarketCap")
	private Float marketCap;

	@JsonProperty("ChangeYTD")
	private Float changeYTD;
	@JsonProperty("Currency")
	private String currency;



}