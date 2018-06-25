package io.pivotal.web.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Integer orderid;
    private String userid;
    private Integer accountid;
    private String symbol;
    private Date completiondate;
    private BigDecimal price;
    private BigDecimal orderfee;

    public BigDecimal getOrderfee() {
        return orderfee;
    }

    public void setOrderfee(BigDecimal orderfee) {
        this.orderfee = orderfee;
    }

    private Integer quantity;
    private String currency;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getCompletiondate() {
        return completiondate;
    }

    public void setCompletiondate(Date completiondate) {
        this.completiondate = completiondate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getOrderid() {

        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", orderid=" + orderid +
                ", userid='" + userid + '\'' +
                ", accountid=" + accountid +
                ", symbol='" + symbol + '\'' +
                ", completiondate=" + completiondate +
                ", price=" + price +
                ", orderfee=" + orderfee +
                ", quantity=" + quantity +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Trade() {
    }

    public Trade(String id, Integer orderid, String userid, Integer accountid, String symbol, Date completiondate, BigDecimal price, BigDecimal orderfee, Integer quantity, String currency) {
        this.id = id;
        this.orderid = orderid;
        this.userid = userid;
        this.accountid = accountid;
        this.symbol = symbol;
        this.completiondate = completiondate;
        this.price = price;
        this.orderfee = orderfee;
        this.quantity = quantity;
        this.currency = currency;
    }
}
