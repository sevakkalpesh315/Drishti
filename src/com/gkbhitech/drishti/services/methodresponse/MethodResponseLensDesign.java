package com.gkbhitech.drishti.services.methodresponse;

import com.gkbhitech.drishti.model.LensDesign;

public class MethodResponseLensDesign {

	private int responseCode;
	private String responseMessage;
	private LensDesign[] dataArray;
	private double sys_time;
	
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
	public LensDesign[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(LensDesign[] dataArray) {
		this.dataArray = dataArray;
	}
	public double getSys_time() {
		return sys_time;
	}
	public void setSys_time(double sys_time) {
		this.sys_time = sys_time;
	}
}
