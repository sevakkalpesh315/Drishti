package com.gkbhitech.drishti.model;

public class PostCustomerReceiptResponse implements JsonDataMarker {
	private String docNumber;
	private String year;
	private String error;

	public PostCustomerReceiptResponse() {
	}

	public PostCustomerReceiptResponse(String docNumber, String year,
			String error) {
		this.docNumber = docNumber;
		this.year = year;
		this.error = error;
	}
	public String getDocNumber() {
		return this.docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
