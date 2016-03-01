package com.chetana.stockapp.model;

/**
 * Created by vedantam on 2/28/16.
 */
public class CompanyDeleteResult {

    String message; //Stores SUCCESS or FAILURE
    String reason;  //Stores the reason for failure

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
