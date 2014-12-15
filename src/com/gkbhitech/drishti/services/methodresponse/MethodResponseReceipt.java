package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.PostCustomerReceiptResponse;

public class MethodResponseReceipt {

	private int responseCode;
	private String responseMessage;
	private PostCustomerReceiptResponse data;
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
	public PostCustomerReceiptResponse getData() {
		return data;
	}
	public void setData(PostCustomerReceiptResponse data) {
		this.data = data;
	}
}
