package com.chetana.stockapp.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.chetana.stockapp.common.Constants;

public class ConnectionUtil {

	public static Connection getConnection() {

		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Constants.HOST, Constants.USER, Constants.PASSWORD);
			
			return conn; 
		}
		catch(Exception ex) {
			System.out.println("Error connecting to database");
			ex.printStackTrace();
			return null;
		}
	}
}
