package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Inventory;

public class MethodResponseInventory{

	
	private int responseCode;
	private String responseMessage;
	private Inventory data;
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
	public Inventory getData() {
		return data;
	}
	public void setData(Inventory data) {
		this.data = data;
	}
}
