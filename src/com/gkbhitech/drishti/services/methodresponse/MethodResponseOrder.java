package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Order;

public class MethodResponseOrder {
	
	private int responseCode;
	private String responseMessage;
	private Order data;
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
	public Order getData() {
		return data;
	}
	public void setData(Order data) {
		this.data = data;
	}
}
