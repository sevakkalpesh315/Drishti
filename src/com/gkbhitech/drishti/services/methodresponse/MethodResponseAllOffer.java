package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.Offer;

public class MethodResponseAllOffer {

	private int responseCode;
	private String responseMessage;
	private Offer[] dataArray;
	
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
	public Offer[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(Offer[] dataArray) {
		this.dataArray = dataArray;
	}
}
