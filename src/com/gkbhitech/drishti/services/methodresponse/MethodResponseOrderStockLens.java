package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.order.OrderNo;

public class MethodResponseOrderStockLens {

	private int responseCode;
	private String responseMessage;
	private OrderNo data;
	
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
	public OrderNo getData() {
		return data;
	}
	public void setData(OrderNo data) {
		this.data = data;
	}
}