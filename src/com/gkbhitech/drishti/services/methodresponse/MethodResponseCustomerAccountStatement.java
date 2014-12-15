package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.CustomerAccountStatement;

public class MethodResponseCustomerAccountStatement {

	private int responseCode;
	private String responseMessage;
	private CustomerAccountStatement[] dataArray;
	private Long fromDate;
	private Long toDate;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public CustomerAccountStatement[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(CustomerAccountStatement[] dataArray) {
		this.dataArray = dataArray;
	}
	public Long getFromDate() {
		return fromDate;
	}
	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}
	public Long getToDate() {
		return toDate;
	}
	public void setToDate(Long toDate) {
		this.toDate = toDate;
	}
}
