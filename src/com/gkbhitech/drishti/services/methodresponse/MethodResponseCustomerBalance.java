package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.CustomerBalance;

public class MethodResponseCustomerBalance {

	private int responseCode;
	private String responseMessage;
	private CustomerBalance data;
	
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
	public CustomerBalance getData() {
		return data;
	}
	public void setData(CustomerBalance data) {
		this.data = data;
	}
}
