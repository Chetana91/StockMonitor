package com.chetana.stockapp.database;

import com.chetana.stockapp.common.Constants;
import com.chetana.stockapp.model.CompanyBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.ResultSet;

public class CompanyDao {

	public boolean addCompany(String companySymbol, String companyName) throws SQLException {
		
		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement ps = conn.prepareStatement(Constants.ADD_COMPANY);
		ps.setString(1, companySymbol);
		ps.setString(2, companyName);
		
		int result = ps.executeUpdate();
		
		return (result == 1) ? true : false;
	}

    public boolean deleteCompany(String companySymbol) throws SQLException {

        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Constants.DELETE_COMPANY);
        ps.setString(1, companySymbol);

        int result = ps.executeUpdate();

        return (result == 1) ? true : false;
    }

	public ArrayList<CompanyBean> listCompanies() throws SQLException{
		Connection conn = ConnectionUtil.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(Constants.LIST_COMPANY);

		ArrayList<CompanyBean> companyList = new ArrayList<CompanyBean>();
		while(rs.next()) {
			CompanyBean company = new CompanyBean(rs.getString(Constants.NAME), rs.getString(Constants.SYMBOL));
			company.setTimestamp(rs.getTimestamp(Constants.STOCK_TIME));
			company.setAsk(rs.getDouble(Constants.ASK));
			company.setBid(rs.getDouble(Constants.BID));
			companyList.add(company);
		}
		return companyList;
	}

	public ArrayList<CompanyBean> getHistory(String companySymbol) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Constants.QUOTE_HISTORY);
        ps.setString(1, companySymbol);

		ResultSet rs = ps.executeQuery();

		ArrayList<CompanyBean> companyList = new ArrayList<CompanyBean>();

		while(rs.next()) {
			CompanyBean company = new CompanyBean(rs.getString(Constants.NAME), rs.getString(Constants.SYMBOL));
			company.setTimestamp(rs.getTimestamp(Constants.STOCK_TIME));
			company.setAsk(rs.getDouble(Constants.ASK));
			company.setBid(rs.getDouble(Constants.BID));
			companyList.add(company);
		}
		return companyList;
	}
	
}
