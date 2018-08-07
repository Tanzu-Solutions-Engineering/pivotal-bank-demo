package io.pivotal.quotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IexBatchQuote extends HashMap<String, HashMap<String, IexQuote>> {

}
