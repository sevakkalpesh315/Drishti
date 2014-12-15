package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.OrderHistory;

public class MethodResponseOrderHistory {

	private int responseCode;
	private String responseMessage;
	private OrderHistory data;
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
	public OrderHistory getData() {
		return data;
	}
	public void setData(OrderHistory data) {
		this.data = data;
	}
}
