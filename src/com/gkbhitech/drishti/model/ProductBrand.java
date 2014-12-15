package com.gkbhitech.drishti.model;

public class ProductBrand implements JsonDataMarker {
	
	private String _id;
	private int brand_code;
	private String brand_desc;
	private String sap_brand_code;
	private Integer price_group;
	private int active;
	private int active_on_mobile;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(int brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrand_desc() {
		return brand_desc;
	}

	public void setBrand_desc(String brand_desc) {
		this.brand_desc = brand_desc;
	}

	public String getSap_brand_code() {
		return sap_brand_code;
	}

	public void setSap_brand_code(String sap_brand_code) {
		this.sap_brand_code = sap_brand_code;
	}

	public Integer getPrice_group() {
		return price_group;
	}

	public void setPrice_group(Integer price_group) {
		this.price_group = price_group;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	public int getActive_on_mobile() {
		return active_on_mobile;
	}
	public void setActive_on_mobile(int active_on_mobile) {
		this.active_on_mobile = active_on_mobile;
	}
}
