package com.gkbhitech.drishti.model;

public class Coating implements JsonDataMarker {

	private String coating_code;
	private String coating_desc;
	private Integer route_id;
	private String coating_desc2;

	public String getCoating_code() {
		return coating_code;
	}

	public void setCoating_code(String coating_code) {
		this.coating_code = coating_code;
	}

	public String getCoating_desc() {
		return coating_desc;
	}

	public void setCoating_desc(String coating_desc) {
		this.coating_desc = coating_desc;
	}

	public Integer getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Integer route_id) {
		this.route_id = route_id;
	}

	public String getCoating_desc2() {
		return coating_desc2;
	}

	public void setCoating_desc2(String coating_desc2) {
		this.coating_desc2 = coating_desc2;
	}

}
