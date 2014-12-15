package com.gkbhitech.drishti.model;

public class Masters implements JsonDataMarker {
	private String auth_token;
	private String username;
	private int user_type;
	private int r_plant;
	private String customer_no;
	private String full_name;

	/*
	 * private Lens[] lenses; private LensCoat[] lensCoats; private Coating[]
	 * coatings; private ProductBrand[] productBrands; private Status[] status;
	 * private Service[] services; private Plant[] plants;
	 */

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getR_plant() {
		return r_plant;
	}

	public void setR_plant(int r_plant) {
		this.r_plant = r_plant;
	}

	public String getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	/*
	 * public Lens[] getLenses() { return lenses; }
	 * 
	 * public void setLenses(Lens[] lenses) { this.lenses = lenses; }
	 * 
	 * public LensCoat[] getLensCoats() { return lensCoats; }
	 * 
	 * public void setLensCoats(LensCoat[] lensCoats) { this.lensCoats =
	 * lensCoats; }
	 * 
	 * public Coating[] getCoatings() { return coatings; }
	 * 
	 * public void setCoatings(Coating[] coatings) { this.coatings = coatings; }
	 * 
	 * public ProductBrand[] getProductBrands() { return productBrands; }
	 * 
	 * public void setProductBrands(ProductBrand[] productBrands) {
	 * this.productBrands = productBrands; }
	 * 
	 * public Status[] getStatus() { return status; }
	 * 
	 * public void setStatus(Status[] status) { this.status = status; }
	 * 
	 * public Service[] getServices() { return services; }
	 * 
	 * public void setServices(Service[] services) { this.services = services; }
	 * 
	 * public Plant[] getPlants() { return plants; }
	 * 
	 * public void setPlants(Plant[] plants) { this.plants = plants; }
	 */
}
