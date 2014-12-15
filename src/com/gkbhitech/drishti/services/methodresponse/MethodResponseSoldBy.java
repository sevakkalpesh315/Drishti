package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Executive;


public class MethodResponseSoldBy {

	private int responseCode;
	private String responseMessage;
	private Executive[] dataArray;
	
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
	public Executive[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(Executive[] dataArray) {
		this.dataArray = dataArray;
	}
}
