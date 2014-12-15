package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.RegisterDevice;

public class MethodResponseRegisterDevice {

	private int responseCode;
	private String responseMessage;
	private RegisterDevice data;
	
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
	public RegisterDevice getData() {
		return data;
	}
	public void setData(RegisterDevice data) {
		this.data = data;
	}
}
