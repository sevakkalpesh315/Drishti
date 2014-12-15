package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.PplpResult;

public class MethodResponseActivatePPLP {

	private int responseCode;
	private String responseMessage;
	private PplpResult data;
	
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
	public PplpResult getData() {
		return data;
	}
	public void setData(PplpResult data) {
		this.data = data;
	}
	
	
}
