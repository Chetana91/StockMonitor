package com.chetana.stockapp.standalone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.chetana.stockapp.common.Constants;
import com.chetana.stockapp.database.ConnectionUtil;

public class FetchStock {

	public static void main(String args[]) {

		Timer timer = new Timer();
		timer.schedule( new TimerTask() {
			@Override
			public void run(){

				String[] symbols = null;
				List<StockResult> dataToInsert = new ArrayList<StockResult>();

				Connection conn = ConnectionUtil.getConnection();
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(Constants.LIST_COMPANY_NAMES);

					ResultSet rs = ps.executeQuery();

					List<String> symbolList = new ArrayList<String>();
					while (rs.next()) {
						symbolList.add(rs.getString("SYMBOL"));
					}

					symbols = symbolList.toArray(new String[symbolList.size()]);

				} catch (SQLException e1) {

				}


				ExecutorService executor = Executors.newFixedThreadPool(25);
				//create a list to hold the Future object associated with Callable
				List<Future<StockResult>> list = new ArrayList<Future<StockResult>>();

				for(String s : symbols){
					//submit Callable tasks to be executed by thread pool
					Future<StockResult> future = executor.submit(new StockCallable(s));
					list.add(future);
				}
				for(Future<StockResult> fut : list){
					try {
						StockResult result = fut.get();
						dataToInsert.add(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//shut down the executor service now
				executor.shutdown();

				// Insert values into DB
				try {
					ps = conn.prepareStatement(Constants.BATCH_INSERT_STOCK_QUOTE);
					conn.setAutoCommit(false);
					
					for (StockResult value : dataToInsert) {
						
						ps.setString(1, value.getSymbol());
						double ask = 0;
						if (value.getAsk() != null && !value.getAsk().equalsIgnoreCase("null")) {
							ask = Double.parseDouble(value.getAsk());
						}
						ps.setDouble(2, ask);
						double bid = 0;
						if (value.getBid() != null && !value.getBid().equalsIgnoreCase("null")) {
							bid = Double.parseDouble(value.getBid());
						}
						ps.setDouble(3, bid);
						ps.addBatch();
					}
					
					ps.executeBatch();
					conn.commit();
				}
				catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} , 0, 600000);

	}
}
