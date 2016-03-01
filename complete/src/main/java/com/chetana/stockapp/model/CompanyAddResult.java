package com.chetana.stockapp.model;

public class CompanyAddResult {
	
	private String message;  // Stores SUCCESS or FAILURE
	private String reason;  // Reason of failure
	private String[] suggestions;  // Suggestions of company IDs if FAILURE has occured

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(String[] suggestions) {
		this.suggestions = suggestions;
	}
	
}
