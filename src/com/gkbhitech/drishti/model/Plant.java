package com.gkbhitech.drishti.model;

public class Plant implements JsonDataMarker{
	
	private Integer r_plant;
	private String plant_desc;
	private Integer company_code;
	private String main_area;
	private String address1;
	private String address2;
	private String city;
	private String state_code;
	private String country_code;
	private String phone;
	private String fax;
	private String manager;
	private String gmanager;
	private Double customerLastSyncTime;
	
	public Integer getR_plant() {
		return r_plant;
	}
	public void setR_plant(Integer r_plant) {
		this.r_plant = r_plant;
	}
	public Integer getCompany_code() {
		return company_code;
	}
	public void setCompany_code(Integer company_code) {
		this.company_code = company_code;
	}
	public String getMain_area() {
		return main_area;
	}
	public void setMain_area(String main_area) {
		this.main_area = main_area;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getGmanager() {
		return gmanager;
	}
	public void setGmanager(String gmanager) {
		this.gmanager = gmanager;
	}
	public String getPlant_desc() {
		return plant_desc;
	}
	public void setPlant_desc(String plant_desc) {
		this.plant_desc = plant_desc;
	}
	public Double getCustomerLastSyncTime() {
		return customerLastSyncTime;
	}
	public void setCustomerLastSyncTime(Double customerLastSyncTime) {
		this.customerLastSyncTime = customerLastSyncTime;
	}
}
