package com.gkbhitech.drishti.model;

public class Video implements JsonDataMarker{

	private int id;
	private String video_description;
	private String url;
	private int active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideo_description() {
		return video_description;
	}
	public void setVideo_description(String video_description) {
		this.video_description = video_description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	
}
