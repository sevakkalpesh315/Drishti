package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.LensSpecification;

public class MethodResponseLensSpecification {

	private int responseCode;
	private String responseMessage;
	private LensSpecification data;
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
	public LensSpecification getData() {
		return data;
	}
	public void setData(LensSpecification data) {
		this.data = data;
	}
}
