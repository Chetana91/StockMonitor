package com.chetana.stockapp.model;

import java.sql.Timestamp;

/**
 * Created by vedantam on 2/28/16.
 */
public class CompanyBean {
    String symbol;
    String name;
    Timestamp timestamp;
    double ask;
    double bid;

    public CompanyBean(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public CompanyBean(String name, String symbol, Timestamp timestamp, double ask, double bid) {
        this.name = name;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.ask = ask;
        this.bid = bid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }
}
