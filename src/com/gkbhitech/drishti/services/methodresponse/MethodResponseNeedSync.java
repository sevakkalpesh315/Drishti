package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.NeedSync;

public class MethodResponseNeedSync {

	private int responseCode;
	private String responseMessage;
	private NeedSync data;
	
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
	public NeedSync getData() {
		return data;
	}
	public void setData(NeedSync data) {
		this.data = data;
	}
	
	
}
