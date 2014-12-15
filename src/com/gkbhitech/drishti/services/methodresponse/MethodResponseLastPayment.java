package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.LastPayment;

public class MethodResponseLastPayment {

	private int responseCode;
	private String responseMessage;
	private LastPayment data;

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
	public LastPayment getData() {
		return data;
	}
	public void setData(LastPayment data) {
		this.data = data;
	}
}
