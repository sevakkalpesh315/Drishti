package com.gkbhitech.drishti.model;

public class CustomerDetailForVisit implements JsonDataMarker{

	private String category_desc;
	private int priority; 
	
	public String getCategory_desc() {
		return category_desc;
	}
	public void setCategory_desc(String category_desc) {
		this.category_desc = category_desc;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
