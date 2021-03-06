package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.CalculatedBaseCurve;

public class MethodResponseCalBaseCurve {

	private int responseCode;
	private String responseMessage;
	private CalculatedBaseCurve data;

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
	public CalculatedBaseCurve getData() {
		return data;
	}
	public void setData(CalculatedBaseCurve data) {
		this.data = data;
	}
}
