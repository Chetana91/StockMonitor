package com.chetana.stockapp.common;

public final class Constants {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";

    //Run the scheduler at htis rate to fetch current stock values
    public static final int REFRESH_RATE = 30*1000;
	
	//DATABASE CONSTANTS
	public static final String HOST = "jdbc:mysql://52.32.212.110:3306/stocks";
	public static final String USER = "root";
	public static final String PASSWORD = "goodbadugly";
	
	//Fetching latest stock price
	public static final String FETCH_LATEST_STOCK_QUOTE = "https://query.yahooapis.com/v1/public/yql?q=select Ask,Bid from yahoo.finance.quotes where"
			+ " symbol = \"%s\"&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=";
	
	//Lookup company name
	public static final String COMPANY_NAME_LOOKUP_URL = "http://d.yimg.com/aq/autoc?query=%s&region=US&lang=en-US";

	//Lookup stock price
    public static final String STOCK_PRICE_LOOKUP_URL = "";

    //TABLE COLUMNS;
    public static final String SYMBOL = "SYMBOL";
    public static final String NAME = "NAME";
    public static final String STOCK_TIME = "STOCK_TIME";
    public static final String ASK = "ASK";
    public static final String BID = "BID";

	
	//SQL Queries
	public static final String ADD_COMPANY = "INSERT INTO COMPANY (SYMBOL, NAME) VALUES (?, ?)";
	public static final String DELETE_COMPANY = "DELETE FROM COMPANY WHERE SYMBOL = ?";

	public static final String LIST_COMPANY_NAMES = "SELECT SYMBOL FROM COMPANY";
	public static final String BATCH_INSERT_STOCK_QUOTE = "INSERT INTO QUOTES (SYMBOL, ASK, BID) values (?, ?, ?)";

    public static final String LIST_COMPANY = "select c.NAME, c.SYMBOL, Q.STOCK_TIME, Q.ASK, Q.BID from COMPANY c JOIN (\n" +
                                                "SELECT q.*\n" +
                                                "FROM QUOTES q\n" +
                                                "INNER JOIN\n" +
                                                "    (SELECT SYMBOL,ASK, BID, MAX(STOCK_TIME) as MAX_STOCK_TIME\n" +
                                                "    FROM QUOTES\n" +
                                                "    GROUP BY SYMBOL) groupedq\n" +
                                                "ON q.SYMBOL = groupedq.SYMBOL \n" +
                                                "AND q.STOCK_TIME = groupedq.MAX_STOCK_TIME\n" +
                                                ") Q on c.SYMBOL = Q.SYMBOL";

    public static final String QUOTE_HISTORY = "SELECT c.name, q.SYMBOL, q.STOCK_TIME, q.ASK, q.BID\n" +
                                                "FROM stocks.QUOTES q join stocks.COMPANY c\n" +
                                                "on c.SYMBOL = q.SYMBOL\n" +
                                                "where q.SYMBOL = ?\n" +
                                                "order by q.STOCK_TIME asc;";

}
