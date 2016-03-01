package com.chetana.stockapp.model;

import java.util.ArrayList;

/**
 * Created by vedantam on 2/28/16.
 */
public class CompanyListResult {
    ArrayList<CompanyBean> companyList = new ArrayList<CompanyBean>();
    int count;
    String message;
    String reason;

    public ArrayList<CompanyBean> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(ArrayList<CompanyBean> companyList) {
        this.companyList = companyList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
