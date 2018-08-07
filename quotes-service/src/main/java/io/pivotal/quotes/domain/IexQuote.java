package io.pivotal.quotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IexQuote {

    private String symbol;
    private String companyName;
    private BigDecimal open;
    private BigDecimal close;
    private Long closeTime;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal latestPrice;
    private Long latestUpdate;
    private String latestSource;
    private BigDecimal change;
    private BigDecimal changePercent;
    private BigDecimal avgTotalVolume;
    private BigDecimal marketCap;

}
