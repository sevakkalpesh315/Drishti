package com.gkbhitech.drishti.model;

public class NeedSync implements JsonDataMarker {
	private boolean coating;
	private boolean inhouseBanks;
	private boolean productBrands;
	private boolean customers;
	private boolean lenses;
	private boolean lensCoats;
	private boolean plants;
	private boolean services;
	private boolean statusCodes;
	private boolean mobileVideo;
	private boolean userPlantsRights;
	private boolean custRepVisit;
	
	public boolean isCoating() {
		return coating;
	}
	public void setCoating(boolean coating) {
		this.coating = coating;
	}
	public boolean isInhouseBanks() {
		return inhouseBanks;
	}
	public void setInhouseBanks(boolean inhouseBanks) {
		this.inhouseBanks = inhouseBanks;
	}
	public boolean isProductBrands() {
		return productBrands;
	}
	public void setProductBrands(boolean productBrands) {
		this.productBrands = productBrands;
	}
	public boolean isCustomers() {
		return customers;
	}
	public void setCustomers(boolean customers) {
		this.customers = customers;
	}
	public boolean isLenses() {
		return lenses;
	}
	public void setLenses(boolean lenses) {
		this.lenses = lenses;
	}
	public boolean isLensCoats() {
		return lensCoats;
	}
	public void setLensCoats(boolean lensCoats) {
		this.lensCoats = lensCoats;
	}
	public boolean isPlants() {
		return plants;
	}
	public void setPlants(boolean plants) {
		this.plants = plants;
	}
	public boolean isServices() {
		return services;
	}
	public void setServices(boolean services) {
		this.services = services;
	}
	public boolean isStatusCodes() {
		return statusCodes;
	}
	public void setStatusCodes(boolean statusCodes) {
		this.statusCodes = statusCodes;
	}
	public boolean isMobileVideo() {
		return mobileVideo;
	}
	public void setMobileVideo(boolean mobileVideo) {
		this.mobileVideo = mobileVideo;
	}
	public boolean isUserPlantsRights() {
		return userPlantsRights;
	}
	public void setUserPlantsRights(boolean userPlantsRights) {
		this.userPlantsRights = userPlantsRights;
	}
	public boolean isCustRepVisit() {
		return custRepVisit;
	}
	public void setCustRepVisit(boolean custRepVisit) {
		this.custRepVisit = custRepVisit;
	}
}
