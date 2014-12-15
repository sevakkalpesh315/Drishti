package com.gkbhitech.drishti.model;

public class Service implements JsonDataMarker {
	
	private String service_code;
	private String service_desc;
	private Double rate;
	private int active;
	private String lens_index;
	private String ltyp;

	public String getLtyp() {
		return ltyp;
	}

	public void setLtyp(String ltyp) {
		this.ltyp = ltyp;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getService_desc() {
		return service_desc;
	}

	public void setService_desc(String service_desc) {
		this.service_desc = service_desc;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getLens_index() {
		return lens_index;
	}

	public void setLens_index(String lens_index) {
		this.lens_index = lens_index;
	}

}
