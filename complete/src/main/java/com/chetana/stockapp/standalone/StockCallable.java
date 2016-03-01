package com.chetana.stockapp.standalone;

import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

import com.chetana.stockapp.common.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class StockCallable implements Callable<StockResult> {

	private String symbol;
	
	public StockCallable(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public StockResult call() throws Exception {
		
		String url = String.format(Constants.FETCH_LATEST_STOCK_QUOTE, symbol);
		RestTemplate restTemplate = new RestTemplate();
		String jsonString = restTemplate.getForObject(url, String.class);
		
		Gson gson = new Gson();
    	JsonElement element = gson.fromJson (jsonString, JsonElement.class);
    	JsonObject jsonObj = element.getAsJsonObject();
    	
    	JsonObject results = jsonObj.get("query").getAsJsonObject()
    						 .get("results").getAsJsonObject()
    						 .get("quote").getAsJsonObject();
    	
    	String ask = null, bid = null;
    	if (results.get("Ask") != null) {
    		 ask = results.get("Ask").toString();
    		 ask = ask.replace("\"", "");
    	}
    	if (results.get("Bid") != null) {
    	     bid = results.get("Bid").toString();
    	     bid = bid.replace("\"", "");
    	}
		
		return new StockResult(symbol, ask, bid);
	}

}
