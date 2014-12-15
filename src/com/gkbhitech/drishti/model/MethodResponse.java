package com.gkbhitech.drishti.model;

public class MethodResponse {
private String responseCode;
private String responseMessage;
private JsonDataMarker data;
private JsonDataMarker[]dataArray;
public JsonDataMarker[] getDataArray() {
	return dataArray;
}
public void setDataArray(JsonDataMarker[] dataArray) {
	this.dataArray = dataArray;
}
public String getResponseCode() {
	return responseCode;
}
public void setResponseCode(String responseCode) {
	this.responseCode = responseCode;
}
public String getResponseMessage() {
	return responseMessage;
}
public void setResponseMessage(String responseMessage) {
	this.responseMessage = responseMessage;
}
public JsonDataMarker getData() {
	return data;
}
public void setData(JsonDataMarker data) {
	this.data = data;
}
}
