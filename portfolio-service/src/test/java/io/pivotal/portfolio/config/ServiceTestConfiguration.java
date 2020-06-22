package io.pivotal.portfolio.config;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.pivotal.portfolio.domain.Holding;
import io.pivotal.portfolio.domain.Order;
import io.pivotal.portfolio.domain.OrderType;
import io.pivotal.portfolio.domain.Portfolio;
import io.pivotal.portfolio.domain.Quote;
import io.pivotal.portfolio.domain.Transaction;

public class ServiceTestConfiguration {
	
	public static final Integer ACCOUNT_ID = 500;
	public static final String USER_ID = "davpin";
	public static final String SYMBOL = "EMC";
	public static final Integer QUANTITY = 1000;
	public static final BigDecimal PRICE = new BigDecimal(10.00);
	public static final BigDecimal FEE = new BigDecimal(1.00);
	public static final Date COMPLETION_DATE = new Date(1329759342904l);
	
	public static final String QUOTE_NAME = "EMC Corp";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzzXXX yyyy");
	public static final String QUOTE_DATE_STRING = "Wed May 6 00:00:00 UTC-04:00 2015";
	public static final BigDecimal QUOTE_LAST_PRICE = new BigDecimal(26.135);
	public static final BigDecimal QUOTE_CHANGE = new BigDecimal(0.00500000000000256);
	public static final Float QUOTE_CHANGE_PERCENT = 0.0191350937619692f;
	public static final Float QUOTE_MSDATE = 42130f;
	
	public static final String COMPANY_EXCHANGE = "NASDAQ";
	
	public static List<Order> orders() {
		List<Order> orders = new ArrayList<>();
		orders.add(order());
		return orders;
	}
	
	public static Order order() {
		Order order1 = new Order();
		order1.setUserId(USER_ID);
		order1.setAccountId(ACCOUNT_ID);
		order1.setCompletionDate(COMPLETION_DATE);
		order1.setOrderFee(FEE);
		order1.setOrderType(OrderType.BUY);
		order1.setPrice(PRICE);
		order1.setQuantity(QUANTITY);
		order1.setSymbol(SYMBOL);
		order1.setCurrency("USD");
		return order1;
	}
	
	public static Order order2() {
		Order order1 = new Order();
		order1.setOrderId(1);
		order1.setAccountId(ACCOUNT_ID);
		order1.setUserId(USER_ID);
		order1.setCompletionDate(COMPLETION_DATE);
		order1.setOrderFee(FEE);
		order1.setOrderType(OrderType.BUY);
		order1.setPrice(PRICE);
		order1.setQuantity(QUANTITY);
		order1.setSymbol(SYMBOL);
		return order1;
	}
	public static Order sellOrder() {
		Order order1 = new Order();
		order1.setOrderId(1);
		order1.setUserId(USER_ID);
		order1.setAccountId(ACCOUNT_ID);
		order1.setCompletionDate(COMPLETION_DATE);
		order1.setOrderFee(FEE);
		order1.setOrderType(OrderType.SELL);
		order1.setPrice(PRICE);
		order1.setQuantity(QUANTITY);
		order1.setSymbol(SYMBOL);
		return order1;
	}
	
	public static Quote quote() {
		Quote quote = new Quote();
		quote.setName("EMC Corp");
		quote.setSymbol(SYMBOL);
		quote.setLastPrice(QUOTE_LAST_PRICE);
		quote.setChange(QUOTE_CHANGE);
		quote.setChangePercent(QUOTE_CHANGE_PERCENT);
		try {
			quote.setTimestamp(dateFormat.parse(QUOTE_DATE_STRING));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		quote.setMarketCap(50755764235.00f);
		quote.setVolume(15159291);
		quote.setChangeYTD(29.74f);
		quote.setChangePercentYTD(-12.1217215870881f);
		quote.setHigh(new BigDecimal(0.0));
		quote.setLow(new BigDecimal(0.0));
		quote.setOpen(new BigDecimal(26.52));
		quote.setStatus("SUCCESS");
		return quote;
	}
	
	public static Portfolio portfolio() {
		Holding holding = new Holding();
		holding.setId(1);
		holding.setQuantity(QUANTITY);
		holding.setPurchaseValue(PRICE);
		holding.setCurrentValue(QUOTE_LAST_PRICE);
		holding.addOrder(order2());
		holding.setSymbol(SYMBOL);
		Portfolio folio = new Portfolio();
		folio.setUserName(USER_ID);
		folio.addHolding(holding);
		folio.refreshTotalValue();
		return folio;
	}
	
	public static Transaction transaction() {
		Transaction tx = new Transaction();
		tx.setAccountId(ACCOUNT_ID);
		tx.setAmount(PRICE);
		tx.setCurrency("USD");
		
		return tx;
	}

}
