package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.CustomerDetailForVisit;
import com.gkbhitech.drishti.model.CustomerVisit;

public class MethodResponseCustomerDetailForVisit {

	private int responseCode;
	private String responseMessage;
	private CustomerVisit data;

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
	public CustomerVisit getData() {
		return data;
	}
	public void setData(CustomerVisit data) {
		this.data = data;
	}
}

