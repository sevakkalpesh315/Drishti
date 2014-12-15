package com.gkbhitech.drishti.model;

public class Offer implements JsonDataMarker{

	private String cust_code;
	private String description;
	private Integer discount;
	private long id;
	private String product_brand;
	private String lens_code;
	private int r_plant;
	private String coat;
	private String discount_type;
	
	public String getCust_code() {
		return cust_code;
	}
	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public String getLens_code() {
		return lens_code;
	}
	public void setLens_code(String lens_code) {
		this.lens_code = lens_code;
	}
	public int getR_plant() {
		return r_plant;
	}
	public void setR_plant(int r_plant) {
		this.r_plant = r_plant;
	}
	public String getCoat() {
		return coat;
	}
	public void setCoat(String coat) {
		this.coat = coat;
	}
	public String getDiscount_type() {
		return discount_type;
	}
	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
	}
}
