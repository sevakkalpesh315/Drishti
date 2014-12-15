package com.gkbhitech.drishti.model;

public class User {

	private String userName;
	private String password;
	private String accessToken;
	private int r_plant;
	private String customer_no;
	private double userPlantsRightsLastSyncTime;
	private double customerLastSyncTime;
	private String full_name;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public double getUserPlantsRightsLastSyncTime() {
		return userPlantsRightsLastSyncTime;
	}
	public void setUserPlantsRightsLastSyncTime(double userPlantsRightsLastSyncTime) {
		this.userPlantsRightsLastSyncTime = userPlantsRightsLastSyncTime;
	}
	public double getCustomerLastSyncTime() {
		return customerLastSyncTime;
	}
	public void setCustomerLastSyncTime(double customerLastSyncTime) {
		this.customerLastSyncTime = customerLastSyncTime;
	}
	public int getR_plant() {
		return r_plant;
	}
	public void setR_plant(int r_plant) {
		this.r_plant = r_plant;
	}
	public String getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
}
