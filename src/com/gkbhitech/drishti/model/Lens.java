package com.gkbhitech.drishti.model;

public class Lens implements JsonDataMarker {

	private String lens_code;
	private String description;
	private int lens_size;
	private int product_brand;
	private int matrix;
	private int active_id;
	private String material;
	private float sphmin;
	private float sphmax;
	private float cylmin;
	private float cylmax;
	private float addmin;
	private float addmax;
	private String lens_design;
	private String lens_index_oe;
	private String vision_type;
	private String display_name;
	private String ltyp;

	// private int active_on_mobile;

	/*
	 * private int bom_type; private String lens_type; private Long id;
	 * 
	 * private Double cylmin; private Double cylmax; private Double addmin;
	 * private Double addmax; private String sap_size; private String eye_type;
	 * private String sloc; private String miss_group; private String
	 * lens_design_code; private String ltyp; private Integer lmattype; private
	 * Double lind; private Integer lmatid; private String pcs_code; private
	 * String line_1; private String line_2; private String lens_index; private
	 * int automation; private String lens_index_rfid; private String
	 * exim_hs_code; private String lens_properties;
	 */

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

	public int getMatrix() {
		return matrix;
	}

	public void setMatrix(int matrix) {
		this.matrix = matrix;
	}

	public int getLens_size() {
		return lens_size;
	}

	public void setLens_size(int lens_size) {
		this.lens_size = lens_size;
	}

	public int getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(int product_brand) {
		this.product_brand = product_brand;
	}

	public int getActive_id() {
		return active_id;
	}

	public void setActive_id(int active_id) {
		this.active_id = active_id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public String getLens_index_oe() {
		return lens_index_oe;
	}

	public void setLens_index_oe(String lens_index_oe) {
		this.lens_index_oe = lens_index_oe;
	}

	/*
	 * public int getBom_type() { return bom_type; } public void setBom_type(int
	 * bom_type) { this.bom_type = bom_type; } public String getLens_type() {
	 * return lens_type; } public void setLens_type(String lens_type) {
	 * this.lens_type = lens_type; } public Long getId() { return id; } public
	 * void setId(Long id) { this.id = id; }
	 * 
	 * public Double getCylmin() { return cylmin; } public void setCylmin(Double
	 * cylmin) { this.cylmin = cylmin; } public Double getCylmax() { return
	 * cylmax; } public void setCylmax(Double cylmax) { this.cylmax = cylmax; }
	 * public Double getAddmin() { return addmin; } public void setAddmin(Double
	 * addmin) { this.addmin = addmin; } public Double getAddmax() { return
	 * addmax; } public void setAddmax(Double addmax) { this.addmax = addmax; }
	 * public String getSap_size() { return sap_size; } public void
	 * setSap_size(String sap_size) { this.sap_size = sap_size; } public String
	 * getEye_type() { return eye_type; } public void setEye_type(String
	 * eye_type) { this.eye_type = eye_type; } public String getSloc() { return
	 * sloc; } public void setSloc(String sloc) { this.sloc = sloc; } public
	 * String getMiss_group() { return miss_group; } public void
	 * setMiss_group(String miss_group) { this.miss_group = miss_group; } public
	 * String getLens_design_code() { return lens_design_code; } public void
	 * setLens_design_code(String lens_design_code) { this.lens_design_code =
	 * lens_design_code; } public String getLtyp() { return ltyp; } public void
	 * setLtyp(String ltyp) { this.ltyp = ltyp; } public Integer getLmattype() {
	 * return lmattype; } public void setLmattype(Integer lmattype) {
	 * this.lmattype = lmattype; } public Double getLind() { return lind; }
	 * public void setLind(Double lind) { this.lind = lind; } public Integer
	 * getLmatid() { return lmatid; } public void setLmatid(Integer lmatid) {
	 * this.lmatid = lmatid; } public String getPcs_code() { return pcs_code; }
	 * public void setPcs_code(String pcs_code) { this.pcs_code = pcs_code; }
	 * public String getLine_1() { return line_1; } public void setLine_1(String
	 * line_1) { this.line_1 = line_1; } public String getLine_2() { return
	 * line_2; } public void setLine_2(String line_2) { this.line_2 = line_2; }
	 * public String getLens_index() { return lens_index; } public void
	 * setLens_index(String lens_index) { this.lens_index = lens_index; } public
	 * int getAutomation() { return automation; } public void setAutomation(int
	 * automation) { this.automation = automation; } public String
	 * getLens_index_rfid() { return lens_index_rfid; } public void
	 * setLens_index_rfid(String lens_index_rfid) { this.lens_index_rfid =
	 * lens_index_rfid; } public String getExim_hs_code() { return exim_hs_code;
	 * } public void setExim_hs_code(String exim_hs_code) { this.exim_hs_code =
	 * exim_hs_code; } public String getLens_properties() { return
	 * lens_properties; } public void setLens_properties(String lens_properties)
	 * { this.lens_properties = lens_properties; }
	 */
	public float getCylmin() {
		return cylmin;
	}

	public void setCylmin(float cylmin) {
		this.cylmin = cylmin;
	}

	public float getCylmax() {
		return cylmax;
	}

	public void setCylmax(float cylmax) {
		this.cylmax = cylmax;
	}

	public float getAddmin() {
		return addmin;
	}

	public void setAddmin(float addmin) {
		this.addmin = addmin;
	}

	public float getAddmax() {
		return addmax;
	}

	public void setAddmax(float addmax) {
		this.addmax = addmax;
	}

	public String getLens_design() {
		return lens_design;
	}

	public void setLens_design(String lens_design) {
		this.lens_design = lens_design;
	}

	public String getVision_type() {
		return vision_type;
	}

	public void setVision_type(String vision_type) {
		this.vision_type = vision_type;
	}
	
	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getLtyp() {
		return ltyp;
	}

	public void setLtyp(String ltyp) {
		this.ltyp = ltyp;
	}

}
