package com.gkbhitech.drishti.model;

public class Status implements JsonDataMarker {
	
	private int status_code;
	private String status_desc;
	
	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}

}
