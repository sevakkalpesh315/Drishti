package com.gkbhitech.drishti.model;

public class Customer implements JsonDataMarker{
	private int r_plant;
	private String cust_code;
	private String cust_name;
	private String address1;
	private String address2;
	private String city;
	private String state_code;
	private String country_code;
	private String phone;
	private String mobile;
	private String email;
	private String fax;
	private Double longitude;
	private Double latitude;
	private int active;
	private String contact_person;
	private String cust_type_biforcate;
	private String customer_incharge;
	
	public String getCust_type_biforcate() {
		return cust_type_biforcate;
	}
	public void setCust_type_biforcate(String cust_type_biforcate) {
		this.cust_type_biforcate = cust_type_biforcate;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	private String pin;

	
	public int getR_plant() {
		return r_plant;
	}
	public void setR_plant(int r_plant) {
		this.r_plant = r_plant;
	}
	public String getCust_code() {
		return cust_code;
	}
	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getCustomer_incharge() {
		return customer_incharge;
	}
	public void setCustomer_incharge(String customer_incharge) {
		this.customer_incharge = customer_incharge;
	}
}
