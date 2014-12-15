package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class LensCoat implements JsonDataMarker {
	
	private String lens_code;
	private String coating_code;
	private BigDecimal id;
	private int active;
	
	public String getLens_code() {
		return lens_code;
	}
	public void setLens_code(String lens_code) {
		this.lens_code = lens_code;
	}
	public String getCoating_code() {
		return coating_code;
	}
	public void setCoating_code(String coating_code) {
		this.coating_code = coating_code;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}

}
