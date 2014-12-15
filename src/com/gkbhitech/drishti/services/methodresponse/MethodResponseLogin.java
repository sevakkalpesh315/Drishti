package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Masters;

public class MethodResponseLogin {

	private int responseCode;
	private String responseMessage;
	private Masters data;
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
	public Masters getData() {
		return data;
	}
	public void setData(Masters data) {
		this.data = data;
	}
}
