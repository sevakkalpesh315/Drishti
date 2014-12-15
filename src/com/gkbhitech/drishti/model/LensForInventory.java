package com.gkbhitech.drishti.model;

public class LensForInventory {

	private String lens_code;
	private String description;
	private String coating_code;
	private String coating_desc;
	private float sphmax;
	private float sphmin;
	private float cylmax;
	private float cylmin;
	private float addmax;
	private float addmin;
	
	public String getLens_code() {
		return lens_code;
	}
	public void setLens_code(String lens_code) {
		this.lens_code = lens_code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCoating_desc() {
		return coating_desc;
	}
	public void setCoating_desc(String coating_desc) {
		this.coating_desc = coating_desc;
	}
	public float getSphmax() {
		return sphmax;
	}
	public void setSphmax(float sphmax) {
		this.sphmax = sphmax;
	}
	public float getSphmin() {
		return sphmin;
	}
	public void setSphmin(float sphmin) {
		this.sphmin = sphmin;
	}
	public String getCoating_code() {
		return coating_code;
	}
	public void setCoating_code(String coating_code) {
		this.coating_code = coating_code;
	}
	public float getCylmax() {
		return cylmax;
	}
	public void setCylmax(float cylmax) {
		this.cylmax = cylmax;
	}
	public float getCylmin() {
		return cylmin;
	}
	public void setCylmin(float cylmin) {
		this.cylmin = cylmin;
	}
	public float getAddmax() {
		return addmax;
	}
	public void setAddmax(float addmax) {
		this.addmax = addmax;
	}
	public float getAddmin() {
		return addmin;
	}
	public void setAddmin(float addmin) {
		this.addmin = addmin;
	}
}
