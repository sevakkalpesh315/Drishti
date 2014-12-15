package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Order;

public class MethodResponseOrders {
	
	private int responseCode;
	private String responseMessage;
	private Order[] dataArray;
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
	public Order[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(Order[] dataArray) {
		this.dataArray = dataArray;
	}
}
