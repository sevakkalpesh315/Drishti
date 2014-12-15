package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Quote;

public class MethodResponseQuote {

	private int responseCode;
	private String responseMessage;
	private Quote data;
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
	public Quote getData() {
		return data;
	}
	public void setData(Quote data) {
		this.data = data;
	}
}
