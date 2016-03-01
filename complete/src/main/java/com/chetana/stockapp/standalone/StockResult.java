package com.chetana.stockapp.standalone;

public class StockResult {
	
	private String symbol;
	private String ask;
	private String bid;

	public StockResult(String symbol, String ask, String bid) {
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getAsk() {
		return ask;
	}
	public void setAsk(String ask) {
		this.ask = ask;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
}
