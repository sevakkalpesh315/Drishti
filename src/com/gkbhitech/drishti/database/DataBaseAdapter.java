package com.gkbhitech.drishti.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.CustRepVisit;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.InhouseBank;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.LensCoat;
import com.gkbhitech.drishti.model.LensDesign;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.Service;
import com.gkbhitech.drishti.model.Status;
import com.gkbhitech.drishti.model.User;
import com.gkbhitech.drishti.model.UserPlantsRights;
import com.gkbhitech.drishti.model.Video;

public class DataBaseAdapter {

	private static final String tag = "DataBaseAdapter";
	private Context context;
	private static final String DATABASE_NAME = "gkbhitech";
	private static final int DATABASE_VERSION = 1;
	private static SQLiteDatabase db;
	private static DataBaseHelper dataBaseHelper;

	// ---------------------------- Comppile stmt ------------------------
	private static SQLiteStatement insertLogin;
	private static SQLiteStatement insertLens;
	private static SQLiteStatement insertLensCoat;
	private static SQLiteStatement insertCoating;
	private static SQLiteStatement insertPlant;
	private static SQLiteStatement insertProductBrand;
	private static SQLiteStatement insertStatus;
	private static SQLiteStatement insertService;
	private static SQLiteStatement insertCustomer;
	private static SQLiteStatement insertInhouseBank;
	private static SQLiteStatement insertVideo;
	private static SQLiteStatement insertUserPlantsRights;
	private static SQLiteStatement insertCustRepVisit;

	/*
	 * private static SQLiteStatement updateLogin; private static
	 * SQLiteStatement updateLens; private static SQLiteStatement
	 * updateLensCoat; private static SQLiteStatement updateCoating; private
	 * static SQLiteStatement updatePlant; private static SQLiteStatement
	 * updateProductBrand; private static SQLiteStatement updateStatus; private
	 * static SQLiteStatement updateService; private static SQLiteStatement
	 * updateCustomer; private static SQLiteStatement updateInhouseBank;
	 */

	// ---------------------------- Table ---------------------------------
	private static final String LOGIN = "login";
	private static final String LENS = "lens";
	private static final String LENS_COAT = "lenscoat";
	private static final String COATING = "coating";
	private static final String PLANT = "plant";
	private static final String PRODUCT_BRAND = "productbrand";
	private static final String STATUS = "status";
	private static final String SERVICE = "service";
	private static final String CUSTOMER = "customer";
	private static final String INHOUSE_BANK = "inhousebank";
	private static final String VIDEO = "video";
	private static final String LENS_DESIGN = "lensdesign";
	private static final String USER_PLANTS_RIGHTS	= "userplantsrights";
	private static final String CUST_REP_VISIT = "custrepvisit";
	
	private static final String _ID = "_id";

	private static final int INDEX_ID = 1; // change index of all table column

	//-----------------------------Colomns of Login table-----------------
	private static final String LOGIN_USERNAME = "username";
	private static final String LOGIN_PASSWORD = "password";
	private static final String LOGIN_R_PLANT = "r_plant";
	private static final String LOGIN_CUSTOMER_NO = "customer_no";
	private static final String LOGIN_LAST_USERPLANTSRIGHTS_SYNC_TIME = "last_user_plants_rights_sync_time";
	private static final String LOGIN_LAST_CUSTOMER_SYNC_TIME = "last_customer_sync_time";
	private static final String LOGIN_FULL_NAME = "full_name";
	
	private static final int INDEX_LOGIN_USERNAME = 1;
	private static final int INDEX_LOGIN_PASSWORD = 2;
	private static final int INDEX_LOGIN_R_PLANT = 3;
	private static final int INDEX_LOGIN_CUSTOMER_NO = 4;
	private static final int INDEX_LOGIN_LAST_USERPLANTSRIGHTS_SYNC_TIME = 5;
	private static final int INDEX_LOGIN_LAST_CUSTOMER_SYNC_TIME = 6;
	private static final int INDEX_LOGIN_FULL_NAME = 7;

	// -----------------------------Colomns of Lens table-----------------
	private static final String LENS_LENS_CODE = "lens_code";
	private static final String LENS_DESCRIPTION = "description";
	private static final String LENS_LENS_SIZE = "lens_size";
	private static final String LENS_PRODUCT_BRAND = "product_brand";
	private static final String LENS_ACTIVE_ID = "active_id";
	private static final String LENS_SPHMAX = "sphmax";
	private static final String LENS_SPHMIN = "sphmin";
	private static final String LENS_LENS_DESIGN = "lens_design";
	private static final String LENS_LENS_INDEX_OE = "lens_index_oe";
	private static final String LENS_VISION_TYPE = "vision_type";
	private static final String LENS_CYLMAX = "cylmax";
	private static final String LENS_CYLMIN = "cylmin";
	private static final String LENS_ADDMAX = "addmax";
	private static final String LENS_ADDMIN = "addmin";
	private static final String LENS_DISPLAY_NEME = "display_name";
	private static final String LENS_LTYP = "ltyp";


	private static final int INDEX_LENS_LENS_CODE = 1;
	private static final int INDEX_LENS_DESCRIPTION = 2;
	private static final int INDEX_LENS_LENS_SIZE = 3;
	private static final int INDEX_LENS_PRODUCT_BRAND = 4;
	private static final int INDEX_LENS_ACTIVE_ID = 5;
	private static final int INDEX_LENS_SPHMAX = 6;
	private static final int INDEX_LENS_SPHMIN = 7;
	private static final int INDEX_LENS_LENS_DESIGN = 8;
	private static final int INDEX_LENS_LENS_INDEX_OE = 9;
	private static final int INDEX_LENS_VISION_TYPE = 10;
	private static final int INDEX_LENS_CYLMAX = 11;
	private static final int INDEX_LENS_CYLMIN = 12;
	private static final int INDEX_LENS_ADDMAX = 13;
	private static final int INDEX_LENS_ADDMIN = 14;
	private static final int INDEX_LENS_DISPLAY_NAME = 15;
	private static final int INDEX_LENS_LTYP = 16;


	// -----------------------------Colomns of LensCoat table-----------------
	private static final String LENS_COAT_ACTIVE = "active";
	private static final String LENS_COAT_COATING_CODE = "coating_code";
	private static final String LENS_COAT_ID = "id";
	private static final String LENS_COAT_LENS_CODE = "lens_code";

	private static final int INDEX_LENS_COAT_ACTIVE = 1;
	private static final int INDEX_LENS_COAT_COATING_CODE = 2;
	private static final int INDEX_LENS_COAT_ID = 3;
	private static final int INDEX_LENS_COAT_LENS_CODE = 4;

	// -----------------------------Colomns of Coating table-----------------
	private static final String COATING_COATING_CODE = "coating_code";
	private static final String COATING_COATING_DESC = "coating_desc";
	private static final String COATING_COATING_DESC2 = "coating_desc2";
	private static final String COATING_ROUTE_ID = "route_id";

	private static final int INDEX_COATING_COATING_CODE = 1;
	private static final int INDEX_COATING_COATING_DESC = 2;
	private static final int INDEX_COATING_COATING_DESC2 = 3;
	private static final int INDEX_COATING_ROUTE_ID = 4;

	// -----------------------------Colomns of Plant table-----------------
	private static final String PLANT_ADDRESS1 = "address1";
	private static final String PLANT_ADDRESS2 = "address2";
	private static final String PLANT_CITY = "city";
	private static final String PLANT_COMPANY_CODE = "company_code";
	private static final String PLANT_COUNTRY_CODE = "country_code";
	private static final String PLANT_FAX = "fax";
	private static final String PLANT_GMANAGER = "gmanager";
	private static final String PLANT_MAIN_AREA = "main_area";
	private static final String PLANT_MANAGER = "manager";
	private static final String PLANT_PHONE = "phone";
	private static final String PLANT_PLANT_DESC = "plant_desc";
	private static final String PLANT_R_PLANT = "r_plant";
	private static final String PLANT_STATE_CODE = "state_code";
	private static final String PLANT_LAST_CUSTOMER_SYNC_TIME = "last_customer_sync_time";
	
	private static final int INDEX_PLANT_ADDRESS1 = 1;
	private static final int INDEX_PLANT_ADDRESS2 = 2;
	private static final int INDEX_PLANT_CITY = 3;
	private static final int INDEX_PLANT_COMPANY_CODE = 4;
	private static final int INDEX_PLANT_COUNTRY_CODE = 5;
	private static final int INDEX_PLANT_FAX = 6;
	private static final int INDEX_PLANT_GMANAGER = 7;
	private static final int INDEX_PLANT_MAIN_AREA = 8;
	private static final int INDEX_PLANT_MANAGER = 9;
	private static final int INDEX_PLANT_PHONE = 10;
	private static final int INDEX_PLANT_PLANT_DESC = 11;
	private static final int INDEX_PLANT_R_PLANT = 12;
	private static final int INDEX_PLANT_STATE_CODE = 13;
	private static final int INDEX_PLANT_LAST_CUSTOMER_SYNC_TIME = 14;

	// -----------------------------Colomns of Product brand
	// table-----------------
	private static final String PRODUCT_BRAND_BRAND_CODE = "brand_code";
	private static final String PRODUCT_BRAND_BRAND_DESC = "brand_desc";
	private static final String PRODUCT_BRAND_SAP_BRAND_CODE = "sap_brand_code";
	private static final String PRODUCT_BRAND_PRICE_GROUP = "price_group";
	private static final String PRODUCT_BRAND_ACTIVE = "active";
	private static final String PRODUCT_BRAND_ACTIVE_ON_MOBILE = "active_on_mobile";

	private static final int INDEX_PRODUCT_BRAND_BRAND_CODE = 1;
	private static final int INDEX_PRODUCT_BRAND_BRAND_DESC = 2;
	private static final int INDEX_PRODUCT_BRAND_SAP_BRAND_CODE = 3;
	private static final int INDEX_PRODUCT_BRAND_PRICE_GROUP = 4;
	private static final int INDEX_PRODUCT_BRAND_ACTIVE_ON_MOBILE = 5;
	private static final int INDEX_PRODUCT_BRAND_ACTIVE = 6;

	// -----------------------------Colomns of status table-----------------
	private static final String STATUS_STATUS_CODE = "status_code";
	private static final String STATUS_STATUS_DESC = "status_desc";

	private static final int INDEX_STATUS_STATUS_CODE = 1;
	private static final int INDEX_STATUS_STATUS_DESC = 2;

	// -----------------------------Colomns of service table-----------------
	private static final String SERVICE_ACTIVE = "active";
	private static final String SERVICE_LENS_INDEX = "lens_index";
	private static final String SERVICE_RATE = "rate";
	private static final String SERVICE_SERVICE_CODE = "service_code";
	private static final String SERVICE_SERVICE_DESC = "service_desc";
	private static final String SERVICE_LTYP = "ltyp";

	private static final int INDEX_SERVICE_ACTIVE = 1;
	private static final int INDEX_SERVICE_LENS_INDEX = 2;
	private static final int INDEX_SERVICE_RATE = 3;
	private static final int INDEX_SERVICE_SERVICE_CODE = 4;
	private static final int INDEX_SERVICE_SERVICE_DESC = 5;
	private static final int INDEX_SERVICE_LTYP = 6;

	// -----------------------------Colomns of customer table-----------------
	private static final String CUSTOMER_ADDRESS1 = "address1";
	private static final String CUSTOMER_ADDRESS2 = "address2";
	private static final String CUSTOMER_CITY = "city";
	private static final String CUSTOMER_COUNTRY_CODE = "country_code";
	private static final String CUSTOMER_CUST_CODE = "cust_code";
	private static final String CUSTOMER_CUST_NAME = "cust_name";
	private static final String CUSTOMER_EMAIL = "email";
	private static final String CUSTOMER_FAX = "fax";
	private static final String CUSTOMER_MOBILE = "mobile";
	private static final String CUSTOMER_PHONE = "phone";
	private static final String CUSTOMER_R_PLANT = "r_plant";
	private static final String CUSTOMER_STATE_CODE = "state_code";
	private static final String CUSTOMER_LONGITUDE = "longitude";
	private static final String CUSTOMER_LATITUDE = "latitude";
	private static final String CUSTOMER_ACTIVE = "active";
	private static final String CUSTOMER_CONTACT_PERSON = "contact_person";
	private static final String CUSTOMER_PIN = "pin";
	private static final String CUSTOMER_CUST_TYPE_BIFORCATE = "cust_type_biforcate";
	private static final String CUSTOMER_CUSTOMER_INCHARGE = "customer_incharge";


	private static final int INDEX_CUSTOMER_ADDRESS1 = 1;
	private static final int INDEX_CUSTOMER_ADDRESS2 = 2;
	private static final int INDEX_CUSTOMER_CITY = 3;
	private static final int INDEX_CUSTOMER_COUNTRY_CODE = 4;
	private static final int INDEX_CUSTOMER_CUST_CODE = 5;
	private static final int INDEX_CUSTOMER_CUST_NAME = 6;
	private static final int INDEX_CUSTOMER_EMAIL = 7;
	private static final int INDEX_CUSTOMER_FAX = 8;
	private static final int INDEX_CUSTOMER_MOBILE = 9;
	private static final int INDEX_CUSTOMER_PHONE = 10;
	private static final int INDEX_CUSTOMER_R_PLANT = 11;
	private static final int INDEX_CUSTOMER_STATE_CODE = 12;
	private static final int INDEX_CUSTOMER_ACTIVE = 13;
	private static final int INDEX_CUSTOMER_LATITUDE = 14;
	private static final int INDEX_CUSTOMER_LONGITUDE = 15;
	private static final int INDEX_CUSTOMER_CONTACT_PERSON = 16;
	private static final int INDEX_CUSTOMER_PIN = 17;
	private static final int INDEX_CUSTOMER_CUST_TYPE_BIFORCATE = 18;
	private static final int INDEX_CUSTOMER_CUSTOMER_INCHARGE = 19;


	// -----------------------------Colomns of bank table-----------------
	private static final String INHOUSE_BANK_ID = "id";
	private static final String INHOUSE_BANK_SAP_GL = "sap_gl";
	private static final String INHOUSE_BANK_PROFIT_CENTER = "profit_center";
	private static final String INHOUSE_BANK_BANK_DESC = "bank_desc";

	private static final int INDEX_INHOUSE_BANK_ID = 1;
	private static final int INDEX_INHOUSE_BANK_SAP_GL = 2;
	private static final int INDEX_INHOUSE_BANK_PROFIT_CENTER = 3;
	private static final int INDEX_INHOUSE_BANK_BANKDESC = 4;

	// -----------------------------Colomns of video-----------------
	private static final String VIDEO_ID = "id";
	private static final String VIDEO_VIDEO_DESCRIPTION = "video_description";
	private static final String VIDEO_URL = "url";
	private static final String VIDEO_ACTIVE = "active";

	private static final int INDEX_VIDEO_ID = 1;
	private static final int INDEX_VIDEO_VIDEO_DESCRIPTION = 2;
	private static final int INDEX_VIDEO_URL = 3;
	private static final int INDEX_VIDEO_ACTIVE = 4;

	// -----------------------------Colomns of Lens Design table-----------------//
	private static final String LENS_DESIGN_DESIGN = "design";
	private static final String LENS_DESIGN_DESIGN_DESC = "design_desc";

	private static final int INDEX_LENS_DESIGN_DESIGN = 1;
	private static final int INDEX_LENS_DESIGN_DESIGN_DESC = 2;
	
	// ----------------------------- Colomns of user plants rights -----------------
	
	private static final String USER_PLANTS_RIGHTS_ID = "id";
	private static final String USER_PLANTS_RIGHTS_USERNAME = "username";
	private static final String USER_PLANTS_RIGHTS_R_PLANT = "r_plant";
	
	private static final int INDEX_USER_PLANTS_RIGHTS_ID = 1;
	private static final int INDEX_USER_PLANTS_RIGHTS_USERNAME = 2;
	private static final int INDEX_USER_PLANTS_RIGHTS_R_PLANT = 3;
	
	// ----------------------------- Colomns of cust rep visit -----------------
	
	private static final String CUST_REP_VISIT_ID = "id";
	private static final String CUST_REP_VISIT_PURPOSE = "purpose";
	
	private static final int INDEX_CUST_REP_VISIT_ID = 1;
	private static final int INDEX_CUST_REP_VISIT_PURPOSE = 2;

	private static String query;

	private static Cursor cursor;

	public static List<Lens> lenses = new ArrayList<Lens>();

	public DataBaseAdapter(Context context) {
		this.context = context;
	}

	public void open() {
		try {
			dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null,
					DATABASE_VERSION);
			db = dataBaseHelper.getWritableDatabase();
			if (Constant.log)
				Log.i(tag, "database opened successfully");
		} catch (Exception e) {
			if (Constant.log)
				Log.i(tag, "error during database open");
			e.printStackTrace();
		}
	}

	public void close() {
		if (db.isOpen()) {
			try {
				db.close();
				if (Constant.log)
					Log.i(tag, "database closed successfully");
			} catch (Exception e) {
				if (Constant.log)
					Log.i(tag, "error during database close");
			}
		}
	}

	public Boolean isDbOpen() {
		return db.isOpen();
	}

	public void compileStatement() {
		// if(Constant.log) Log.i(tag,
		// "........compile stmt method..............");
		// sqLiteStatement.executeInsert();
	}

	/*public void insertUser(User user) {
		// insertLogin =
		// db.compileStatement("insert into "+LOGIN+"("+LOGIN_USERNAME+","+LOGIN_PASSWORD+") values(?,?)");
		// insertLogin.bindString(1, user.getUserName());
		// insertLogin.bindString(2, user.getPassword());
		// insertLogin.executeInsert();

		// if(Constant.log) Log.i(".....", ".........done.........");
		ContentValues cvForLogin = convertUserToCV(user);
		db.insert(LOGIN, null, cvForLogin);
	}*/
	public void insertUser(User user){
		insertLogin = db.compileStatement("insert into "+LOGIN+"("+LOGIN_USERNAME+","+LOGIN_PASSWORD+","+LOGIN_R_PLANT+","+
				LOGIN_CUSTOMER_NO+","+LOGIN_LAST_USERPLANTSRIGHTS_SYNC_TIME+","+LOGIN_LAST_CUSTOMER_SYNC_TIME+","+
				LOGIN_FULL_NAME+") values(?,?,?,?,?,?,?)");
		db.beginTransaction();
		try {
			//insertLens.bindString(INDEX_ID, lens.get_id());
			insertLogin.bindString(INDEX_LOGIN_USERNAME, user.getUserName());
			insertLogin.bindString(INDEX_LOGIN_PASSWORD, user.getPassword());
			insertLogin.bindLong(INDEX_LOGIN_R_PLANT, user.getR_plant());
			String customer_no = user.getCustomer_no();
			if(customer_no == null){
				insertLogin.bindNull(INDEX_LOGIN_CUSTOMER_NO);
			}else{
				insertLogin.bindString(INDEX_LOGIN_CUSTOMER_NO,user.getCustomer_no());
			}
			insertLogin.bindDouble(INDEX_LOGIN_LAST_USERPLANTSRIGHTS_SYNC_TIME, user.getUserPlantsRightsLastSyncTime());
			insertLogin.bindDouble(INDEX_LOGIN_CUSTOMER_NO, user.getCustomerLastSyncTime());
			insertLogin.bindString(INDEX_LOGIN_FULL_NAME, user.getFull_name());
			
			try{
				insertLogin.executeInsert();
			}catch (Exception e) {
				updateUser(user);
			}
			insertLogin.clearBindings();
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}
	
	public void updateUser(User user){
		try{
			if(Constant.log) Log.i(tag, "Update start ...");
			db.update(LOGIN, convertUserToCV(user), LOGIN_USERNAME+" = '"+user.getUserName()+"'", null);
			if(Constant.log) Log.i(tag, "Update completed ...");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//if(Constant.log) Log.i(tag, "Updated successfully");
	}

	public void beginTransaction() {
		db.beginTransaction();
	}

	public void commitTransaction() {
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	public void insertLens(Lens... lenses){
		insertLens = db.compileStatement("insert into "+LENS+"("+LENS_LENS_CODE+","+LENS_DESCRIPTION+","+LENS_LENS_SIZE+","+LENS_PRODUCT_BRAND+","+LENS_ACTIVE_ID+","+LENS_SPHMAX+","+
		LENS_SPHMIN+","+LENS_LENS_DESIGN+","+LENS_LENS_INDEX_OE+","+LENS_VISION_TYPE+","+
		LENS_CYLMAX+","+LENS_CYLMIN+","+LENS_ADDMAX+","+LENS_ADDMIN+","+LENS_DISPLAY_NEME+","+LENS_LTYP+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		db.beginTransaction();
		try {
		String lensDesign = null, lensIndexOe = null, visionType = null, displayName = null, ltyp = null;
		for(Lens lens : lenses){
		//insertLens.bindString(INDEX_ID, lens.get_id());
		insertLens.bindString(INDEX_LENS_LENS_CODE, lens.getLens_code());
		insertLens.bindString(INDEX_LENS_DESCRIPTION, lens.getDescription());
		insertLens.bindLong(INDEX_LENS_LENS_SIZE, lens.getLens_size());
		insertLens.bindDouble(INDEX_LENS_SPHMAX, lens.getSphmax());
		insertLens.bindDouble(INDEX_LENS_SPHMIN, lens.getSphmin());
		insertLens.bindLong(INDEX_LENS_PRODUCT_BRAND, lens.getProduct_brand());
		insertLens.bindLong(INDEX_LENS_ACTIVE_ID, lens.getActive_id());
		lensDesign = lens.getLens_design();
		//if(Constant.log) Log.i(tag, "lensDesign : "+lensDesign);
		if(lensDesign == null){
		insertLens.bindNull(INDEX_LENS_LENS_DESIGN);
		}else{
		insertLens.bindString(INDEX_LENS_LENS_DESIGN, lensDesign);
		}
		lensIndexOe = lens.getLens_index_oe();
		//if(Constant.log) Log.i(tag, "lensIndexOe : "+lensIndexOe);
		if(lensIndexOe == null){
		insertLens.bindNull(INDEX_LENS_LENS_INDEX_OE);
		}else{
		insertLens.bindString(INDEX_LENS_LENS_INDEX_OE, lensIndexOe);
		}
		visionType = lens.getVision_type();
		//if(Constant.log) Log.i(tag, "visionType : "+visionType);
		if(visionType == null){
		insertLens.bindNull(INDEX_LENS_VISION_TYPE);
		}else{
		insertLens.bindString(INDEX_LENS_VISION_TYPE,visionType);
		}
		insertLens.bindDouble(INDEX_LENS_CYLMAX, lens.getCylmax());
		insertLens.bindDouble(INDEX_LENS_CYLMIN, lens.getCylmin());
		insertLens.bindDouble(INDEX_LENS_ADDMAX, lens.getAddmax());
		insertLens.bindDouble(INDEX_LENS_ADDMIN, lens.getAddmin());
		displayName = lens.getDisplay_name();
		if(displayName == null){
		insertLens.bindNull(INDEX_LENS_DISPLAY_NAME);
		}else{
		insertLens.bindString(INDEX_LENS_DISPLAY_NAME, displayName);
		}
		ltyp = lens.getLtyp();
		if(ltyp == null){
		insertLens.bindNull(INDEX_LENS_LTYP);
		}else{
		insertLens.bindString(INDEX_LENS_LTYP,ltyp);
		}
		try{
		insertLens.executeInsert();
		}catch (Exception e) {
		e.printStackTrace();
		updateLens(lens);
		}
		insertLens.clearBindings();
		//lens = null;
		}
		db.setTransactionSuccessful();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}finally{
		db.endTransaction();
		}
		}


	private void updateLens(Lens lens) {
		try {
			if (Constant.log)
				Log.i(tag, "Update start ...");
			db.update(LENS, convertLensToCV(lens), LENS_LENS_CODE + " = "
					+ lens.getLens_code(), null);
			if (Constant.log)
				Log.i(tag, "Update completed ...");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// if(Constant.log) Log.i(tag, "Updated successfully");
	}

	public void insertLensCoat(LensCoat... lensCoats) {
		insertLensCoat = db.compileStatement("insert into " + LENS_COAT + "("
				+ LENS_COAT_ACTIVE + "," + LENS_COAT_COATING_CODE + ","
				+ LENS_COAT_ID + "," + LENS_COAT_LENS_CODE
				+ ") values(?,?,?,?)");
		db.beginTransaction();
		try {
			for (LensCoat lensCoat : lensCoats) {
				// insertLens.bindString(INDEX_ID, lens.get_id());
				insertLensCoat.bindLong(INDEX_LENS_COAT_ACTIVE,
						lensCoat.getActive());
				insertLensCoat.bindString(INDEX_LENS_COAT_COATING_CODE,
						lensCoat.getCoating_code());
				insertLensCoat.bindString(INDEX_LENS_COAT_ID, lensCoat.getId()
						.toString());
				insertLensCoat.bindString(INDEX_LENS_COAT_LENS_CODE,
						lensCoat.getLens_code());

				try {
					insertLensCoat.executeInsert();
				} catch (Exception e) {
					updateLensCoat(lensCoat);
				}
				insertLensCoat.clearBindings();

				// lensCoat = null;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateLensCoat(LensCoat lensCoat) {
		try {
			db.update(LENS_COAT, convertLensCoatToCV(lensCoat), LENS_COAT_ID
					+ " = " + lensCoat.getId(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void insertLensCoat(LensCoat[] lensCoats){ String sql =
	 * "insert into "
	 * +LENS_COAT+"("+LENS_COAT_ACTIVE+","+LENS_COAT_COATING_CODE+","
	 * +LENS_COAT_ID+","+LENS_COAT_LENS_CODE+") values(?,?,?,?)";
	 * db.beginTransaction(); try { for(LensCoat lensCoat : lensCoats){
	 * db.insert(LENS_COAT, null, createContentValuesLensCoat(lensCoat)); }
	 * db.setTransactionSuccessful(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }finally{
	 * db.endTransaction(); } }
	 * 
	 * private ContentValues createContentValuesLensCoat(LensCoat lensCoat) {
	 * ContentValues values = new ContentValues(); values.put(LENS_COAT_ACTIVE,
	 * lensCoat.getActive()); values.put(LENS_COAT_COATING_CODE,
	 * lensCoat.getCoating_code()); values.put(LENS_COAT_ID,
	 * lensCoat.getId().toString()); values.put(LENS_COAT_LENS_CODE,
	 * lensCoat.getLens_code());
	 * 
	 * return values; }
	 */

	public void insertCoating(Coating... coatings) {
		insertCoating = db.compileStatement("insert into " + COATING + "("
				+ COATING_COATING_CODE + "," + COATING_COATING_DESC + ","
				+ COATING_COATING_DESC2 + "," + COATING_ROUTE_ID
				+ ") values(?,?,?,?)");
		db.beginTransaction();
		try {
			for (Coating coating : coatings) {
				// insertLens.bindString(INDEX_ID, lens.get_id());
				insertCoating.bindString(INDEX_COATING_COATING_CODE,
						coating.getCoating_code());
				insertCoating.bindString(INDEX_COATING_COATING_DESC,
						coating.getCoating_desc());
				if (coating.getCoating_desc2() == null) {
					insertCoating.bindNull(INDEX_COATING_COATING_DESC2);
				} else {
					insertCoating.bindString(INDEX_COATING_COATING_DESC2,
							coating.getCoating_desc2());
				}
				insertCoating.bindString(INDEX_COATING_ROUTE_ID, coating
						.getRoute_id().toString());

				try {
					insertCoating.executeInsert();
				} catch (Exception e) {
					updateCoating(coating);
				}
				insertCoating.clearBindings();

				// coating = null;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateCoating(Coating coating) {
		try {
			db.update(COATING, convertCoatingToCV(coating),
					COATING_COATING_CODE + " = " + coating.getCoating_code(),
					null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertPlant(Plant... plants) {
		insertPlant = db.compileStatement("insert into " + PLANT + "("
				+ PLANT_ADDRESS1 + "," + PLANT_ADDRESS2 + "," + PLANT_CITY
				+ "," + PLANT_COMPANY_CODE + "," + PLANT_COUNTRY_CODE + ","
				+ PLANT_FAX + "," + PLANT_GMANAGER + "," + PLANT_MAIN_AREA
				+ "," + PLANT_MANAGER + "," + PLANT_PHONE + ","
				+ PLANT_PLANT_DESC + "," + PLANT_R_PLANT + ","
				+ PLANT_STATE_CODE + ","+ PLANT_LAST_CUSTOMER_SYNC_TIME + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		db.beginTransaction();
		try {
			String address1 = "";
			String address2 = "";
			String city = "";
			String fax = "";
			String gManager = "";
			String manager = "";
			String phone = "";
			String stateCode = "";
			Integer companyCode = 0;
			String countryCode = "";
			String mainArea = "";
			Double customerLastSyncTime = null;
			for (Plant plant : plants) {

				// insertLens.bindString(INDEX_ID, lens.get_id());
				address1 = plant.getAddress1();
				if (address1 != null) {
					insertPlant.bindString(INDEX_PLANT_ADDRESS1, address1);
				} else {
					insertPlant.bindNull(INDEX_PLANT_ADDRESS1);
				}

				address2 = plant.getAddress2();
				if (address2 != null) {
					insertPlant.bindString(INDEX_PLANT_ADDRESS2, address2);
				} else {
					insertPlant.bindNull(INDEX_PLANT_ADDRESS2);
				}

				city = plant.getCity();
				if (city != null) {
					insertPlant.bindString(INDEX_PLANT_CITY, city);
				} else {
					insertPlant.bindNull(INDEX_PLANT_CITY);
				}

				insertPlant.bindLong(INDEX_PLANT_COMPANY_CODE,
						plant.getCompany_code());

				insertPlant.bindString(INDEX_PLANT_COUNTRY_CODE,
						plant.getCountry_code());

				fax = plant.getFax();
				if (fax != null) {
					insertPlant.bindString(INDEX_PLANT_FAX, fax);
				} else {
					insertPlant.bindNull(INDEX_PLANT_FAX);
				}

				gManager = plant.getGmanager();
				if (gManager != null) {
					insertPlant.bindString(INDEX_PLANT_GMANAGER, gManager);
				} else {
					insertPlant.bindNull(INDEX_PLANT_GMANAGER);
				}

				mainArea = plant.getMain_area();
				if (mainArea != null) {
					insertPlant.bindString(INDEX_PLANT_MAIN_AREA, mainArea);
				} else {
					insertPlant.bindNull(INDEX_PLANT_MAIN_AREA);
				}

				manager = plant.getManager();
				if (manager != null) {
					insertPlant.bindString(INDEX_PLANT_MANAGER, manager);
				} else {
					insertPlant.bindNull(INDEX_PLANT_MANAGER);
				}

				phone = plant.getPhone();
				if (phone != null) {
					insertPlant.bindString(INDEX_PLANT_PHONE, phone);
				} else {
					insertPlant.bindNull(INDEX_PLANT_PHONE);
				}

				insertPlant.bindString(INDEX_PLANT_PLANT_DESC,
						plant.getPlant_desc());
				insertPlant.bindLong(INDEX_PLANT_R_PLANT, plant.getR_plant());

				stateCode = plant.getState_code();
				if (stateCode != null) {
					insertPlant.bindString(INDEX_PLANT_STATE_CODE, stateCode);
				} else {
					insertPlant.bindNull(INDEX_PLANT_STATE_CODE);
				}
				
				customerLastSyncTime = plant.getCustomerLastSyncTime();
				if(customerLastSyncTime == null){
					insertPlant.bindNull(INDEX_PLANT_LAST_CUSTOMER_SYNC_TIME);
				}else{
					insertPlant.bindDouble(INDEX_PLANT_LAST_CUSTOMER_SYNC_TIME, customerLastSyncTime);
				}

				try {
					insertPlant.executeInsert();
				} catch (Exception e) {
					updatePlant(plant);
				}
				insertPlant.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	public void updatePlant(Plant plant) {
		try {
			db.update(PLANT, convertPlantToCV(plant), PLANT_R_PLANT + " = "
					+ plant.getR_plant(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProductBrand(ProductBrand... productBrands) {
		insertProductBrand = db.compileStatement("insert into " + PRODUCT_BRAND
				+ "(" + PRODUCT_BRAND_BRAND_CODE + ","
				+ PRODUCT_BRAND_BRAND_DESC + "," + PRODUCT_BRAND_SAP_BRAND_CODE
				+ "," + PRODUCT_BRAND_PRICE_GROUP + ","
				+ PRODUCT_BRAND_ACTIVE_ON_MOBILE + "," + PRODUCT_BRAND_ACTIVE
				+ ") values(?,?,?,?,?,?)");
		db.beginTransaction();
		try {
			for (ProductBrand productBrand : productBrands) {
				// insertLens.bindString(INDEX_ID, lens.get_id());
				insertProductBrand.bindLong(INDEX_PRODUCT_BRAND_BRAND_CODE,
						productBrand.getBrand_code());
				insertProductBrand.bindString(INDEX_PRODUCT_BRAND_BRAND_DESC,
						productBrand.getBrand_desc());
				insertProductBrand.bindString(
						INDEX_PRODUCT_BRAND_SAP_BRAND_CODE,
						productBrand.getSap_brand_code());
				if (productBrand.getPrice_group() != null) {
					insertProductBrand.bindLong(
							INDEX_PRODUCT_BRAND_PRICE_GROUP,
							productBrand.getPrice_group());
				} else {
					insertProductBrand
							.bindNull(INDEX_PRODUCT_BRAND_PRICE_GROUP);
				}
				insertProductBrand.bindLong(INDEX_PRODUCT_BRAND_ACTIVE,
						productBrand.getActive());
				insertProductBrand.bindLong(
						INDEX_PRODUCT_BRAND_ACTIVE_ON_MOBILE,
						productBrand.getActive_on_mobile());

				try {
					insertProductBrand.executeInsert();
				} catch (Exception e) {
					updateProductBrand(productBrand);
				}
				insertProductBrand.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateProductBrand(ProductBrand productBrand) {
		try {
			db.update(
					PRODUCT_BRAND,
					convertProductBrandToCV(productBrand),
					PRODUCT_BRAND_BRAND_CODE + " = "
							+ productBrand.getBrand_code(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertStatus(Status... statuses) {
		insertStatus = db.compileStatement("insert into " + STATUS + "("
				+ STATUS_STATUS_CODE + "," + STATUS_STATUS_DESC
				+ ") values(?,?)");
		db.beginTransaction();
		try {
			for (Status status : statuses) {
				// insertLens.bindString(INDEX_ID, lens.get_id());
				insertStatus.bindLong(INDEX_STATUS_STATUS_CODE,
						status.getStatus_code());
				insertStatus.bindString(INDEX_STATUS_STATUS_DESC,
						status.getStatus_desc());

				try {
					insertStatus.executeInsert();
				} catch (Exception e) {
					updateStatus(status);
				}
				insertStatus.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateStatus(Status status) {
		try {
			db.update(STATUS, convertStatusToCV(status), STATUS_STATUS_CODE
					+ " = " + status.getStatus_code(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertService(Service... services){
		insertService = db.compileStatement("insert into "+SERVICE+"("+SERVICE_ACTIVE+","+SERVICE_LENS_INDEX+","+SERVICE_RATE+","
		+SERVICE_SERVICE_CODE+","+SERVICE_SERVICE_DESC+","+SERVICE_LTYP+") values(?,?,?,?,?,?)");
		db.beginTransaction();
		try {
		String lens_index = "";
		Double rate = 0.0;
		String ltyp = null;
		for(Service service : services){
		//insertLens.bindString(INDEX_ID, lens.get_id());
		insertService.bindLong(INDEX_SERVICE_ACTIVE, service.getActive());
		lens_index = service.getLens_index();
		if(lens_index != null){
		insertService.bindString(INDEX_SERVICE_LENS_INDEX, lens_index);
		}else{
		insertService.bindNull(INDEX_SERVICE_LENS_INDEX);
		}
		rate = service.getRate();
		if(rate != null){
		insertService.bindDouble(INDEX_SERVICE_RATE, rate);
		}else{
		insertService.bindNull(INDEX_SERVICE_RATE);
		}
		insertService.bindString(INDEX_SERVICE_SERVICE_CODE, service.getService_code());
		insertService.bindString(INDEX_SERVICE_SERVICE_DESC, service.getService_desc());
		ltyp = service.getLtyp();
		if(ltyp == null){
		insertService.bindNull(INDEX_SERVICE_LTYP);
		}else{
		insertService.bindString(INDEX_SERVICE_LTYP, ltyp);
		}
		try{
		insertService.executeInsert();
		}catch (Exception e) {
		updateService(service);
		}
		insertService.clearBindings();
		}
		db.setTransactionSuccessful();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}finally{
		db.endTransaction();
		}
		}


	private void updateService(Service service) {
		try {
			db.update(SERVICE, convertServiceToCV(service),
					SERVICE_SERVICE_CODE + " = " + service.getService_code(),
					null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertCustomer(Customer... customers){
		insertCustomer = db.compileStatement("insert into "+CUSTOMER+"("+CUSTOMER_ADDRESS1+","+
				CUSTOMER_ADDRESS2+","+CUSTOMER_CITY+","+CUSTOMER_COUNTRY_CODE+","+CUSTOMER_CUST_CODE+","+
				CUSTOMER_CUST_NAME+","+CUSTOMER_EMAIL+","+CUSTOMER_FAX+","+CUSTOMER_MOBILE+","+
				CUSTOMER_PHONE+","+CUSTOMER_R_PLANT+","+CUSTOMER_STATE_CODE+","+CUSTOMER_ACTIVE+","+
				CUSTOMER_LATITUDE+","+CUSTOMER_LONGITUDE+","+CUSTOMER_CONTACT_PERSON+","+CUSTOMER_PIN+","+
				CUSTOMER_CUST_TYPE_BIFORCATE+","+CUSTOMER_CUSTOMER_INCHARGE+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		db.beginTransaction();
		
		try {
			String address1 = "";
			String address2 = "";
			String city = "";
			String countryCode = "";
			String email = "";
			String fax = "";
			String mobile = "";
			String phone = "";
			String stateCode = "";
			Double longitude = null;
			Double latitude = null;
			String contactPerson = "";
			String pin = "";
			String custTypeBiforcate = "";
			String customerIncharge = "";
			
			for(Customer customer : customers){
				//insertLens.bindString(INDEX_ID, lens.get_id());
				address1 = customer.getAddress1();
				if(address1 != null){
					insertCustomer.bindString(INDEX_CUSTOMER_ADDRESS1, address1);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_ADDRESS1);
				}
				address2 = customer.getAddress2();
				if(address2 != null){
					insertCustomer.bindString(INDEX_CUSTOMER_ADDRESS2, address2);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_ADDRESS2);
				}
				city = customer.getCity();
				if(city != null){
					insertCustomer.bindString(INDEX_CUSTOMER_CITY, city);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_CITY);
				}
				countryCode = customer.getCountry_code();
				if(countryCode != null){
					insertCustomer.bindString(INDEX_CUSTOMER_COUNTRY_CODE, countryCode);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_COUNTRY_CODE);
				}
				insertCustomer.bindString(INDEX_CUSTOMER_CUST_CODE, customer.getCust_code());
				insertCustomer.bindString(INDEX_CUSTOMER_CUST_NAME, customer.getCust_name());
				email = customer.getEmail();
				if(email != null){
					insertCustomer.bindString(INDEX_CUSTOMER_EMAIL, email);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_EMAIL);
				}
				fax = customer.getFax();
				if(fax != null){
					insertCustomer.bindString(INDEX_CUSTOMER_FAX, fax);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_FAX);
				}
				mobile = customer.getMobile();
				if(mobile != null){
					insertCustomer.bindString(INDEX_CUSTOMER_MOBILE, mobile);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_MOBILE);
				}
				phone = customer.getPhone();
				if(phone != null){
					insertCustomer.bindString(INDEX_CUSTOMER_PHONE, phone);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_PHONE);
				}
				insertCustomer.bindLong(INDEX_CUSTOMER_R_PLANT, customer.getR_plant());
				stateCode = customer.getState_code();
				if(stateCode != null){
					insertCustomer.bindString(INDEX_CUSTOMER_STATE_CODE, stateCode);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_STATE_CODE);
				}
				insertCustomer.bindLong(INDEX_CUSTOMER_ACTIVE, customer.getActive());
				latitude = customer.getLatitude();
				if(latitude != null){
					insertCustomer.bindDouble(INDEX_CUSTOMER_LATITUDE, latitude);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_LATITUDE);
				}
				longitude = customer.getLongitude();
				if(longitude != null){
					insertCustomer.bindDouble(INDEX_CUSTOMER_LONGITUDE, longitude);
				}else{
					insertCustomer.bindNull(INDEX_CUSTOMER_LONGITUDE);
				}
				contactPerson = customer.getContact_person();
				if(contactPerson == null){
					insertCustomer.bindNull(INDEX_CUSTOMER_CONTACT_PERSON);
				}else{
					insertCustomer.bindString(INDEX_CUSTOMER_CONTACT_PERSON, contactPerson);
				}
				pin = customer.getPin();
				if(pin == null){
					insertCustomer.bindNull(INDEX_CUSTOMER_PIN);
				}else{
					insertCustomer.bindString(INDEX_CUSTOMER_PIN, pin);
				}
				custTypeBiforcate = customer.getCust_type_biforcate();
				if(custTypeBiforcate == null){
					insertCustomer.bindNull(INDEX_CUSTOMER_CUST_TYPE_BIFORCATE);
				}else{
					insertCustomer.bindString(INDEX_CUSTOMER_CUST_TYPE_BIFORCATE,custTypeBiforcate);
				}
				customerIncharge = customer.getCustomer_incharge();
				if(customerIncharge == null){
					insertCustomer.bindNull(INDEX_CUSTOMER_CUSTOMER_INCHARGE);
				}else{
					insertCustomer.bindString(INDEX_CUSTOMER_CUSTOMER_INCHARGE, customerIncharge);
				}
				
				try{
					insertCustomer.executeInsert();
				}catch (Exception e) {
					updateCustomer(customer);
				}
				insertCustomer.clearBindings();
				
				//customer = null;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}


	private void updateCustomer(Customer customer) {
		try {
			db.update(CUSTOMER, convertCustomerToCV(customer), CUSTOMER_R_PLANT
					+ " = " + customer.getR_plant() + " and "
					+ CUSTOMER_CUST_CODE + " = '" + customer.getCust_code()+"'",
					null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertInhouseBank(InhouseBank... inhouseBanks) {
		insertInhouseBank = db.compileStatement("insert into " + INHOUSE_BANK
				+ "(" + INHOUSE_BANK_ID + "," + INHOUSE_BANK_SAP_GL + ","
				+ INHOUSE_BANK_PROFIT_CENTER + "," + INHOUSE_BANK_BANK_DESC
				+ ") values(?,?,?,?)");
		db.beginTransaction();
		try {
			for (InhouseBank inhouseBank : inhouseBanks) {
				insertInhouseBank.bindLong(INDEX_INHOUSE_BANK_ID,
						inhouseBank.getId());
				insertInhouseBank.bindString(INDEX_INHOUSE_BANK_SAP_GL,
						inhouseBank.getSap_gl());
				insertInhouseBank.bindLong(INDEX_INHOUSE_BANK_PROFIT_CENTER,
						inhouseBank.getProfit_center());
				insertInhouseBank.bindString(INDEX_INHOUSE_BANK_BANKDESC,
						inhouseBank.getBank_desc());

				try {
					insertInhouseBank.executeInsert();
				} catch (Exception e) {
					updateInhouseBank(inhouseBank);
				}
				insertInhouseBank.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateInhouseBank(InhouseBank inhouseBank) {
		try {
			db.update(INHOUSE_BANK, convertInhouseBankToCV(inhouseBank),
					INHOUSE_BANK_ID + " = " + inhouseBank.getId(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertVideos(Video... vidoes) {
		insertVideo = db.compileStatement("insert into " + VIDEO + "("
				+ VIDEO_ID + "," + VIDEO_VIDEO_DESCRIPTION + "," + VIDEO_URL
				+ "," + VIDEO_ACTIVE + ") values(?,?,?,?)");
		db.beginTransaction();
		try {
			for (Video video : vidoes) {
				insertVideo.bindLong(INDEX_VIDEO_ID, video.getId());
				insertVideo.bindString(INDEX_VIDEO_VIDEO_DESCRIPTION,
						video.getVideo_description());
				insertVideo.bindString(INDEX_VIDEO_URL, video.getUrl());
				insertVideo.bindLong(INDEX_VIDEO_ACTIVE, video.getActive());

				try {
					insertVideo.executeInsert();
				} catch (Exception e) {
					updateVideo(video);
				}
				insertVideo.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateVideo(Video video) {
		try {
			db.update(VIDEO, convertVideoToCV(video),
					VIDEO_ID + " = " + video.getId(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertLensDesign(LensDesign... lensDesigns){
		
		for(LensDesign lensDesign : lensDesigns){
			ContentValues cvForLensDesign = convertLensDesignToCV(lensDesign);
			db.insert(LENS_DESIGN, null, cvForLensDesign);
		}
	}
	
	public void insertUserPlantsRights(UserPlantsRights... userPlantsRights){
		insertUserPlantsRights = db.compileStatement("insert into "+USER_PLANTS_RIGHTS+"("+USER_PLANTS_RIGHTS_ID+
				","+USER_PLANTS_RIGHTS_USERNAME+","+USER_PLANTS_RIGHTS_R_PLANT+") values(?,?,?)");
		db.beginTransaction();
		try {
			for(UserPlantsRights userPlantsRight : userPlantsRights){
				insertUserPlantsRights.bindLong(INDEX_USER_PLANTS_RIGHTS_ID, userPlantsRight.getId());
				insertUserPlantsRights.bindString(INDEX_USER_PLANTS_RIGHTS_USERNAME, userPlantsRight.getUsername());
				insertUserPlantsRights.bindLong(INDEX_USER_PLANTS_RIGHTS_R_PLANT, userPlantsRight.getR_plant());
				
				try{
					insertUserPlantsRights.executeInsert();
				}catch (Exception e) {
					updateUserPlantsRights(userPlantsRight);
				}
				insertUserPlantsRights.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}
	private void updateUserPlantsRights(UserPlantsRights userPlantsRight){
		try{
			db.update(USER_PLANTS_RIGHTS, convertUserPlantsRightsToCV(userPlantsRight), USER_PLANTS_RIGHTS_ID+" = "+userPlantsRight.getId(), null);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertCustRepVisit(CustRepVisit... custRepVisits) {
		insertCustRepVisit = db.compileStatement("insert into " + CUST_REP_VISIT + "("
				+ CUST_REP_VISIT_ID + "," + CUST_REP_VISIT_PURPOSE + ") values(?,?)");
		db.beginTransaction();
		try {
			for (CustRepVisit custRepVisit : custRepVisits) {
				insertCustRepVisit.bindLong(INDEX_CUST_REP_VISIT_ID, custRepVisit.getId());
				insertCustRepVisit.bindString(INDEX_CUST_REP_VISIT_PURPOSE,custRepVisit.getPurpose());

				try {
					insertCustRepVisit.executeInsert();
				} catch (Exception e) {
					updateCustRepVisit(custRepVisit);
				}
				insertCustRepVisit.clearBindings();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	private void updateCustRepVisit(CustRepVisit custRepVisit) {
		try {
			db.update(CUST_REP_VISIT, convertCustRepVisitToCV(custRepVisit),CUST_REP_VISIT_ID + " = " + custRepVisit.getId(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ---------------------------- get data methods
	 * --------------------------------------------------
	 */

	public List<ProductBrand> getProductBrandForComputeQuote(String displayName) {

		query = "SELECT DISTINCT pb.brand_code, pb.brand_desc FROM "
				+ PRODUCT_BRAND + " pb " + "JOIN " + LENS
				+ " l on pb.brand_code=l.product_brand WHERE "
				+ "l.active_id=1 and pb.active_on_mobile = 1 ";

		if (displayName != null) {
			query += "and display_name = '" + displayName + "' ";
		}
		query += "ORDER BY pb.brand_desc";

		if (Constant.log)
			Log.i(tag, "query : " + query);

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "product brand cursor size : " + cursor.getCount());
			return convertCursorToProductBrands(cursor);
		}
		cursor.close();

		return null;
	}

	public List<LensDesign> getLensDesigns(String display_name, int brandCode) {

		query = "select distinct(design_desc),design from " + LENS_DESIGN
				+ ", " + LENS
				+ " where lens.vision_type = lensdesign.design and "
				+ "product_brand = " + brandCode;

		if (display_name != null) {
			query += " and display_name = '" + display_name + "'";
		}
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "lens design cursor length : " + cursor.getCount());
			return convertCursorToLensDesign(cursor);
		}
		cursor.close();

		return null;
	}
	
public List<LensDesign> getLensDesigns(int brandCode){
		
		query = "select distinct(design_desc),design from "+LENS_DESIGN+", "+LENS+" where lens.vision_type = lensdesign.design and product_brand = "+brandCode;
		
		if(Constant.log) Log.i(tag, "query : "+query);
		
		cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			if(Constant.log) Log.i(tag, "lens design cursor length : "+cursor.getCount());
			return convertCursorToLensDesign(cursor);
		}
		cursor.close();
		
		return null;
	}


	public List<Lens> getProduct() {

		query = "select distinct display_name from lens where display_name is not null and vision_type != 'FF'";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "product cursor size : " + cursor.getCount());
			return convertCursorToLens(cursor);
		}
		cursor.close();

		return null;
	}
	
public List<Lens> getCorridor(String vision_type, int productBrand, String displayName, String indexOe){
		
		query = "select distinct lens_design from lens where vision_type = '"+vision_type+"' and product_brand = "+productBrand+
				" and lens_design is not null";
		
		if(indexOe != null){
			query += " and lens_index_oe = '"+indexOe+"'";
		}
		if(displayName != null){
			query += " and display_name = '"+displayName+"' ";
		}
		
		if(Constant.log) Log.i(tag, "query : "+query);
		
		cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			return convertCursorToLens(cursor);
		}
		cursor.close();
		
		return null;
	}


	public ProductBrand getProductBranByCode(String brandCode) {

		query = "SELECT DISTINCT pb.brand_code, pb.brand_desc FROM "
				+ PRODUCT_BRAND
				+ " pb "
				+ "JOIN "
				+ LENS
				+ " l on pb.brand_code=l.product_brand WHERE substr(l.lens_code,1,1)='S' and "
				+ "l.active_id=1 and pb.active_on_mobile=1 and pb.brand_code = '"
				+ brandCode + "'";

		/* and pb.active_on_mobile=1 */

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "product brand cursor size : " + cursor.getCount());
			return convertCursorToProductBrands(cursor).get(0);
		}
		cursor.close();

		return null;
	}

	/*public List<String> getLensIndexForRxOrder(int brandCode, String design) {

		query = "SELECT DISTINCT lens_index_oe FROM lens WHERE lens_index_oe != '' AND (lens_code like 'F%' or lens_code like 'P%') "
				+ "and product_brand = "
				+ brandCode
				+ " and vision_type = '"
				+ design + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "Lens index cursor length : " + cursor.getCount());
			List<String> indexs = new ArrayList<String>();
			for (Lens lens : convertCursorToLens(cursor)) {
				indexs.add(lens.getLens_index_oe());
			}
			return indexs;
		}
		cursor.close();

		return null;
	}*/
	
public List<String> getLensIndexForRxOrder(String display_name, int brandCode, String design){
		
		query = "SELECT DISTINCT lens_index_oe FROM lens WHERE lens_index_oe != '' AND (lens_code like 'F%' or lens_code like 'P%') " +
				"and product_brand = "+brandCode+" and vision_type = '"+design+"'";
		
		if(display_name != null){
			query += " and display_name = '"+display_name+"'";
		}
		
		if(Constant.log) Log.i(tag, "query : "+query);
		
		Cursor cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			if(Constant.log) Log.i(tag, "Lens index cursor length : "+cursor.getCount());
			List<String> indexs = new ArrayList<String>();
			for(Lens lens : convertCursorToLens(cursor)){
				indexs.add(lens.getLens_index_oe());
			}
			return indexs;
		}
		cursor.close();
		
		return null;
	}

public List<String> getLensIndexForOrderTracking(String materialType, int brandCode, String design){
	
	query = "SELECT DISTINCT lens_index_oe FROM lens WHERE lens_index_oe != '' and product_brand = "+brandCode;
	
	if(materialType.equals("rx")){
		query += " and (lens_code like 'F%' or lens_code like 'P%')";
	}else if (materialType.equals("stock")) {
		query += " and lens_code like 'S%'";
	}
	
	if(design != null && !design.equals("")){
		query += " and vision_type = '"+design+"'";
	}
	
	if(Constant.log) Log.i(tag, "query : "+query);
	
	Cursor cursor = db.rawQuery(query, null);
	if(cursor != null && cursor.getCount() > 0){
		if(Constant.log) Log.i(tag, "Lens index cursor length : "+cursor.getCount());
		List<String> indexs = new ArrayList<String>();
		for(Lens lens : convertCursorToLens(cursor)){
			indexs.add(lens.getLens_index_oe());
		}
		return indexs;
	}
	cursor.close();
	
	return null;
}



	public LensForInventory getLensAndCoatingByCode(String lensCode,
			String coatingCode, String orderType) {

		/*
		 * query =
		 * "SELECT lens_code, description FROM "+LENS+" WHERE product_brand = "
		 * +brandCode
		 * +" AND substr(lens_code,1,1)='S' AND active_id = 1 ORDER BY description"
		 * ;
		 */

		query = "SELECT l.lens_code, description, sphmax, sphmin,cylmax,cylmin,addmax," +
				"addmin, c.coating_code, coating_desc FROM "
				+ LENS
				+ " l , "
				+ LENS_COAT
				+ " b, "
				+ COATING
				+ " c "
				+ "WHERE l.lens_code=b.lens_code and b.coating_code=c.coating_code and  l.lens_code = '"
				+ lensCode
				+ "' and  c.coating_code = '"
				+ coatingCode
				+ "' AND substr(l.lens_code,1,1)='"+orderType+"' AND active_id = 1";
		
		if(Constant.log) Log.i(tag, "query : "+query);

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "lens and coating cursor size : "+cursor.getCount());
			return convertCursorToLensForInventory(cursor).get(0);
		}
		cursor.close();

		return null;
	}

	public List<ProductBrand> getProductBrand() {

		query = "SELECT DISTINCT pb.brand_code, pb.brand_desc FROM "
				+ PRODUCT_BRAND
				+ " pb "
				+ "JOIN "
				+ LENS
				+ " l on pb.brand_code=l.product_brand WHERE substr(l.lens_code,1,1)='S' and "
				+ "l.active_id=1 and pb.active_on_mobile=1 ORDER BY pb.brand_desc";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "product brand cursor size : " + cursor.getCount());
			return convertCursorToProductBrands(cursor);
		}
		cursor.close();

		return null;
	}

	public List<ProductBrand> getProductBrand(String materialType) {

		query = "SELECT DISTINCT pb.brand_code, pb.brand_desc FROM "
				+ PRODUCT_BRAND
				+ " pb "
				+ "JOIN "
				+ LENS
				+ " l on pb.brand_code=l.product_brand WHERE substr(l.lens_code,1,1) in ("
				+ materialType
				+ ") and "
				+ "l.active_id=1 and pb.active_on_mobile=1 ORDER BY pb.brand_desc";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "product brand cursor size : " + cursor.getCount());
			return convertCursorToProductBrands(cursor);
		}
		cursor.close();

		return null;
	}

	public List<LensForInventory> getLens(int brandCode) {

		/*
		 * query =
		 * "SELECT lens_code, description FROM "+LENS+" WHERE product_brand = "
		 * +brandCode
		 * +" AND substr(lens_code,1,1)='S' AND active_id = 1 ORDER BY description"
		 * ;
		 */

		query = "SELECT l.lens_code, description, sphmax, sphmin,cylmax,cylmin,addmax,addmin, c.coating_code, coating_desc FROM "
				+ LENS
				+ " l , "
				+ LENS_COAT
				+ " b, "
				+ COATING
				+ " c "
				+ "WHERE l.lens_code=b.lens_code and b.coating_code=c.coating_code and  product_brand = "
				+ brandCode
				+ " AND substr(l.lens_code,1,1)='S' AND active_id = 1 and b.active = 1 ORDER BY description";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToLensForInventory(cursor);
		}
		cursor.close();

		return null;
	}

	public List<Lens> getLens(int brandCode, String materialType) {

		query = "SELECT lens_code, description FROM " + LENS
				+ " WHERE product_brand = " + brandCode
				+ " AND substr(lens_code,1,1) in (" + materialType
				+ ") AND active_id=1 ORDER BY description";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToLens(cursor);
		}
		cursor.close();

		return null;
	}
	
	public List<Lens> getLens(String displayName, int brandCode, String visionType, String indexOe, String lensDesign){
		
		query = "SELECT lens_code, sphmax, sphmin, cylmax, cylmin, addmax, addmin, description FROM "+LENS+" WHERE active_id = 1 and (lens_code like 'F%' or lens_code like 'P%') " +
				"and product_brand = "+brandCode+" and vision_type = '"+visionType+"'";
		if(indexOe != null){
			query += " and lens_index_oe = '"+indexOe+"'";
		}
		if(displayName != null){
			query += " and display_name = '"+displayName+"'";
		}
		if(lensDesign != null){
			query += " and lens_design = '"+lensDesign+"'";
		}
		
		cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			return convertCursorToLens(cursor);
		}
		cursor.close();
		
		return null;
	}
	
	public List<Service> getFitting(String lensCode){
		
		query = "select service_code, substr(service_desc,16, length(service_desc)) as service_desc from "+SERVICE+", "+LENS+"  where lens.ltyp = service.ltyp and active = 1 and " +
				"lens_code = '"+lensCode+"' and service_code like 'F0%'";
		
		/*query = "select service_code, service_desc from "+SERVICE+", "+LENS+"  where lens.ltyp = service.ltyp and active = 1 and " +
				"lens_code = '"+lensCode+"' and service_code like 'F0%'";*/
		
		if(Constant.log) Log.i(tag, "query : "+query);
		
		Cursor cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			if(Constant.log) Log.i(tag, "fitting cursor length : "+cursor.getCount());
			return convertCursorToServices(cursor);
		}
		cursor.close();
		
		return null;
	}



	/*public List<String> getLensIndexForRxOrder() {

		query = "SELECT DISTINCT lens_index_oe FROM lens WHERE lens_index_oe != '' AND (lens_code like 'F%' or lens_code like 'P%')";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (Constant.log)
				Log.i(tag, "Lens index cursor length : " + cursor.getCount());
			List<String> indexs = new ArrayList<String>();
			for (Lens lens : convertCursorToLens(cursor)) {
				indexs.add(lens.getLens_index_oe());
			}
			return indexs;
		}
		cursor.close();

		return null;
	}*/

	public List<Lens> getLens(int brandCode, String visionType, String indexOe) {

		query = "SELECT lens_code, description FROM "
				+ LENS
				+ " WHERE active_id = 1 and (lens_code like 'F%' or lens_code like 'P%') "
				+ "and product_brand = " + brandCode + " and vision_type = '"
				+ visionType + "'";
		if (indexOe != null) {
			query += " and lens_index_oe = '" + indexOe + "'";
		}

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToLens(cursor);
		}
		cursor.close();

		return null;
	}

	public List<Coating> getCoatingForComputeQuote(String lensCode) {

		query = "select c.coating_code, t.coating_desc, t.route_id from "
				+ LENS
				+ " as l inner join "
				+ LENS_COAT
				+ " as c on l.lens_code = c.lens_code inner join "
				+ COATING
				+ " as t on t.coating_code = c.coating_code where l.lens_code = '"
				+ lensCode + "' and (active=1)";

		if (Constant.log)Log.i(tag, query);
		
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCoating(cursor);
		}
		cursor.close();

		return null;
	}

	public List<Coating> getCoatingForInventory(String lensCode) {

		query = "SELECT c.coating_code, c.coating_desc FROM " + COATING
				+ " c JOIN " + LENS_COAT
				+ " lc ON c.coating_code=lc.coating_code"
				+ " WHERE lc.lens_code = '" + lensCode
				+ "' AND lc.active=1 ORDER BY coating_desc";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCoating(cursor);
		}
		cursor.close();

		return null;
	}

	public List<Plant> getPlants(String userName) {

		/*query = "SELECT * FROM " + PLANT;*/
		query ="select * from "+PLANT+" p,"+USER_PLANTS_RIGHTS+" u where u.r_plant=p.r_plant and u.username = '"+userName+"'";

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToPlants(cursor);
		}
		cursor.close();
		return null;
	}
	
	public int getPlantsCount(String userName) {

		/*query = "SELECT * FROM " + PLANT;*/
		query ="select count(*) from "+PLANT+" p,"+USER_PLANTS_RIGHTS+" u where u.r_plant=p.r_plant and u.username = '"+userName+"'";

		cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		int noOfPlants = cursor.getInt(0);
		cursor.close();
		
		return noOfPlants;
	}
	
	public List<Plant> getAllPlants() {

		/*query = "SELECT * FROM " + PLANT;*/
		query ="select * from "+PLANT;

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToPlants(cursor);
		}
		cursor.close();
		return null;
	}
	
	public Plant getPlant(int plant) {

		query = "SELECT * FROM " + PLANT + " where " + PLANT_R_PLANT + " = "
				+ plant;

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToPlants(cursor).get(0);
		}
		cursor.close();
		return null;
	}

	public int getNoOfCustomer(int plant) {

		query = "SELECT COUNT(*) FROM " + CUSTOMER + " WHERE "
				+ CUSTOMER_R_PLANT + " = " + plant;

		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			int noOfCustomer = cursor.getInt(0);
			cursor.close();
			return noOfCustomer;
		}
		cursor.close();
		return 0;
	}

	public List<Customer> getCustomer(int userType, String userName, int plant) {
		
		if(userType == 3){
			query = "SELECT " + CUSTOMER_CUST_CODE + ", " + CUSTOMER_CITY + ",  "
					+ CUSTOMER_CUST_NAME + "," + CUSTOMER_LATITUDE + ","
					+ CUSTOMER_LONGITUDE + " FROM "+CUSTOMER+" c where c."+CUSTOMER_R_PLANT+" = "+plant;
		}else{
		
			query = "SELECT " + CUSTOMER_CUST_CODE + ", " + CUSTOMER_CITY + ",  "
					+ CUSTOMER_CUST_NAME + "," + CUSTOMER_LATITUDE + ","
					+ CUSTOMER_LONGITUDE + " FROM "+LOGIN+" u join "+CUSTOMER+" c on u."+LOGIN_FULL_NAME+" = c."+
					CUSTOMER_CUSTOMER_INCHARGE+" where c."+CUSTOMER_R_PLANT+" = "+plant+" and "+LOGIN_USERNAME+" = '"+userName+"'";
		}
		
		
		/*query = "SELECT " + CUSTOMER_CUST_CODE + ", " + CUSTOMER_CITY + ",  "
				+ CUSTOMER_CUST_NAME + "," + CUSTOMER_LATITUDE + ","
				+ CUSTOMER_LONGITUDE + " FROM " + CUSTOMER + " where "
				+ CUSTOMER_R_PLANT + " = " + plant;*/

		if (Constant.log)Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}
	
	public List<Customer> getCustomerForActivatePPLP(int userType, String userName, int plant){
		
		
		if(userType == 3){
			query = "SELECT "+CUSTOMER_CUST_CODE+","+CUSTOMER_CUST_NAME+","+CUSTOMER_LATITUDE+","+
					CUSTOMER_LONGITUDE+" FROM "+CUSTOMER+" c where c."+CUSTOMER_R_PLANT+" = "+plant+" and "+
					CUSTOMER_ACTIVE+" in(4,8) order by "+CUSTOMER_CUST_NAME;
		}else{
			query = "SELECT "+CUSTOMER_CUST_CODE+","+CUSTOMER_CUST_NAME+","+CUSTOMER_LATITUDE+","+
					CUSTOMER_LONGITUDE+" FROM "+LOGIN+" u join "+CUSTOMER+" c on u."+LOGIN_FULL_NAME+" = c."+
					CUSTOMER_CUSTOMER_INCHARGE+" where c."+CUSTOMER_R_PLANT+" = "+plant+" and "+LOGIN_USERNAME+" = '"+userName+"' and "+
					CUSTOMER_ACTIVE+" in(4,8) order by "+CUSTOMER_CUST_NAME;
		}
		
		/*//users u join customers c on u.full_name = c.customer_incharge
*/		
		/*query = "SELECT "+CUSTOMER_CUST_CODE+","+CUSTOMER_CUST_NAME+","+CUSTOMER_LATITUDE+","+
				CUSTOMER_LONGITUDE+" FROM "+CUSTOMER+" where "+CUSTOMER_R_PLANT+" = "+plant+" and "+
				CUSTOMER_ACTIVE+" in(4,8) order by "+CUSTOMER_CUST_NAME;*/
		
		if(Constant.log) Log.i(tag, query);
		
		
		cursor = db.rawQuery(query, null);
		
		if(Constant.log) Log.i(tag, "selected customer count : "+cursor.getCount());
		if(cursor != null && cursor.getCount() > 0){
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}
	
	
	public List<Customer> getCustomerForSalesBooster(int userType, String userName, int plant){
		
		if(userType == 3){
			query = "SELECT "+CUSTOMER_CUST_CODE+","+CUSTOMER_CUST_NAME+","+CUSTOMER_LATITUDE+","+
					CUSTOMER_LONGITUDE+" FROM "+CUSTOMER+" c where c."+CUSTOMER_R_PLANT+" = "+plant+" and "+
					CUSTOMER_ACTIVE+" in(1,4,8) and "+
					CUSTOMER_CUST_TYPE_BIFORCATE+" = 'GRASSROOT' order by "+CUSTOMER_CUST_NAME;
		}else{
			query = "SELECT "+CUSTOMER_CUST_CODE+","+CUSTOMER_CUST_NAME+","+CUSTOMER_LATITUDE+","+
					CUSTOMER_LONGITUDE+" FROM "+LOGIN+" u join "+CUSTOMER+" c on u."+LOGIN_FULL_NAME+" = c."+
					CUSTOMER_CUSTOMER_INCHARGE+" where c."+CUSTOMER_R_PLANT+" = "+plant+" and "+LOGIN_USERNAME+" = '"+userName+"' and "+
					CUSTOMER_ACTIVE+" in(1,4,8) and "+
					CUSTOMER_CUST_TYPE_BIFORCATE+" = 'GRASSROOT' order by "+CUSTOMER_CUST_NAME;
		}
		
		if(Constant.log) Log.i(tag, query);
		
		
		cursor = db.rawQuery(query, null);
		
		if(Constant.log) Log.i(tag, "selected customer count : "+cursor.getCount());
		if(cursor != null && cursor.getCount() > 0){
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}


	/*public Customer getCustomerByCustCode(int plant, String customerCode) {

		query = "SELECT * FROM " + CUSTOMER + " where " + CUSTOMER_CUST_CODE
				+ " = '" + customerCode + "' and " + CUSTOMER_R_PLANT + " = "
				+ plant;

		if (Constant.log)
			Log.i(tag, query);

		Cursor cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomer(cursor).get(0);
		}
		cursor.close();
		return null;
	}*/
	
	public Customer getCustomerByCustCode(String customerCode){
		
		query = "SELECT * FROM "+CUSTOMER+" where "+CUSTOMER_CUST_CODE+" = '"+customerCode+"'";
		
		if(Constant.log) Log.i(tag, query);
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(Constant.log) Log.i(tag, "selected customer count : "+cursor.getCount());
		if(cursor != null && cursor.getCount() > 0){
			return convertCursorToCustomer(cursor).get(0);
		}
		cursor.close();
		return null;
	}


	

	public List<Customer> getCustomerWithCity(int plant) {

		query = "SELECT " + CUSTOMER_CUST_CODE + "," + CUSTOMER_CUST_NAME + ","
				+ CUSTOMER_CITY + " FROM " + CUSTOMER + " where "
				+ CUSTOMER_R_PLANT + " = " + plant;

		if (Constant.log)
			Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}

	public List<Customer> getCustomerName(int C1002D0001) {

		query = "SELECT " + CUSTOMER_CITY + ", " + CUSTOMER_LATITUDE + ","
				+ CUSTOMER_LONGITUDE + " FROM " + CUSTOMER + " where "
				+ CUSTOMER_CUST_CODE + " = " + C1002D0001;

		if (Constant.log)
			Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomerName(cursor);
		}
		cursor.close();
		return null;
	}

	public List<Customer> getCustomerCity(int plant) {

		query = "SELECT DISTINCT " + CUSTOMER_CITY + " FROM " + CUSTOMER;

		if (Constant.log)
			Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomerCity(cursor);
		}
		cursor.close();
		return null;
	}

	public List<Customer> getCustomerForReceipt(int userType, String userName, int plant) {

		
		if(userType == 3){
			query = "SELECT " + CUSTOMER_CUST_CODE + "," + CUSTOMER_CUST_NAME + ","
					+ CUSTOMER_LATITUDE + "," + CUSTOMER_LONGITUDE +" FROM "+CUSTOMER
					+" c where c."+ CUSTOMER_R_PLANT + " = " + plant+" and " + CUSTOMER_ACTIVE + " = 1";
		}else{
			query = "SELECT " + CUSTOMER_CUST_CODE + "," + CUSTOMER_CUST_NAME + ","
					+ CUSTOMER_LATITUDE + "," + CUSTOMER_LONGITUDE +" FROM "+LOGIN+" u join "+CUSTOMER
					+" c on u."+LOGIN_FULL_NAME+" = c."+CUSTOMER_CUSTOMER_INCHARGE+" where c."+ CUSTOMER_R_PLANT + " = " + plant+
					" and "+LOGIN_USERNAME+" = '"+userName+ "' and " + CUSTOMER_ACTIVE + " = 1";
		}
		
		if (Constant.log)
			Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}

	public List<Customer> getCustomerForBalance(int plant) {

		query = "SELECT " + CUSTOMER_CUST_CODE + "," + CUSTOMER_CUST_NAME + ","
				+ CUSTOMER_LATITUDE + "," + CUSTOMER_LONGITUDE + " FROM "
				+ CUSTOMER + " where " + CUSTOMER_R_PLANT + " = " + plant
				+ " and " + CUSTOMER_ACTIVE + " = 4 ORDER BY "
				+ CUSTOMER_CUST_CODE;

		if (Constant.log)
			Log.i(tag, query);

		cursor = db.rawQuery(query, null);

		if (Constant.log)
			Log.i(tag, "selected customer count : " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			return convertCursorToCustomer(cursor);
		}
		cursor.close();
		return null;
	}

	public List<Status> getStatus() {

		query = "SELECT * FROM " + STATUS;
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToStatus(cursor);
		}
		cursor.close();

		return null;
	}

	public List<InhouseBank> getInhouseBank(int r_plant) {

		query = "SELECT " + INHOUSE_BANK_BANK_DESC + "," + INHOUSE_BANK_SAP_GL
				+ " FROM " + INHOUSE_BANK + " where "
				+ INHOUSE_BANK_PROFIT_CENTER + " = " + r_plant + " or "
				+ INHOUSE_BANK_PROFIT_CENTER + " = " + 0;
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToInhouseBank(cursor);
		}
		cursor.close();

		return null;
	}

	public Status getStatusByStatusCode(int statusCode) {

		query = "SELECT * FROM " + STATUS + " where " + STATUS_STATUS_CODE
				+ " = " + statusCode;
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToStatus(cursor).get(0);
		}
		cursor.close();

		return null;
	}

	public List<Lens> getLensesForOrderStockLens() {

		query = "SELECT * FROM " + LENS + " where " + LENS_LENS_CODE
				+ " like 'S%'";
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToLens(cursor);
		}
		cursor.close();

		return null;
	}

	public Lens getLensByLensCode(String lensCode) {

		query = "SELECT * FROM " + LENS + " where " + LENS_LENS_CODE + " = '"
				+ lensCode + "'";
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToLens(cursor).get(0);
		}
		cursor.close();

		return null;
	}

	public Coating getCoatingByCoatingCode(String coatingCode) {

		query = "SELECT * FROM " + COATING + " where " + COATING_COATING_CODE
				+ " = '" + coatingCode + "'";
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToCoating(cursor).get(0);
		}
		cursor.close();

		return null;
	}

	public List<Video> getVideoList() {

		query = "SELECT * FROM " + VIDEO;
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			// if(Constant.log) Log.i(tag,
			// "status cursor length : "+cursor.getCount());
			return convertCursorToVideo(cursor);
		}
		cursor.close();

		return null;
	}

	public User validateUser(String userName, String password) {

		query = "SELECT * FROM " + LOGIN + " where " + LOGIN_USERNAME + " = '"
				+ userName + "' and " + LOGIN_PASSWORD + " = '" + password
				+ "'";
		cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			return convertCursorToUser(cursor);
		}
		cursor.close();

		return null;
	}
	
	public User getUser(String userName){
		
		query = "SELECT * FROM "+LOGIN+" where "+LOGIN_USERNAME+" = '"+userName+"'";
		cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			cursor.moveToFirst();
			return convertCursorToUser(cursor);
		}
		cursor.close();
		
		return null;
	}
	
	public List<CustRepVisit> getCustRepVisit(){
		query = "select * from "+CUST_REP_VISIT;
		cursor = db.rawQuery(query, null);
		if(cursor != null && cursor.getCount() > 0){
			cursor.moveToFirst();
			return convertCursorToCustRepVisit(cursor);
		}
		cursor.close();
		return null;
	}

	public void setLatLong(int plant, String custCode, double latitude,
			double longitude) {

		// query =
		// "update "+CUSTOMER+" set "+CUSTOMER_LATITUDE+" = "+latitude+","+CUSTOMER_LONGITUDE+" = "+longitude+" where "+CUSTOMER_R_PLANT+"="+plant+" and "+CUSTOMER_CUST_CODE+" = '"+custCode+"'";
		// if(Constant.log) Log.i(tag, query);
		// db.execSQL(query);
		db.update(
				CUSTOMER,
				createCVforCustomerLatLong(plant, custCode, latitude, longitude),
				CUSTOMER_R_PLANT + "=" + plant + " and " + CUSTOMER_CUST_CODE
						+ " = '" + custCode + "'", null);

		if (Constant.log)
			Log.i(tag, "customer lat long updated successfully");
	}

	private ContentValues createCVforCustomerLatLong(int plant,
			String custCode, double latitude, double longitude) {
		ContentValues cv = new ContentValues();

		cv.put(CUSTOMER_R_PLANT, plant);
		cv.put(CUSTOMER_CUST_CODE, custCode);
		cv.put(CUSTOMER_LATITUDE, latitude);
		cv.put(CUSTOMER_LONGITUDE, longitude);

		return cv;
	}

	/*
	 * ---------------- convert cursor to _______
	 * ------------------------------------
	 */

	private User convertCursorToUser(Cursor cursor) {

		User user = new User();
		user.setUserName(cursor.getString(cursor.getColumnIndex(LOGIN_USERNAME)));
		user.setPassword(cursor.getString(cursor.getColumnIndex(LOGIN_PASSWORD)));

		cursor.close();

		return user;
	}

	private List<LensDesign> convertCursorToLensDesign(Cursor cursor) {

		List<LensDesign> lensDesigns = new ArrayList<LensDesign>();
		cursor.moveToFirst();
		LensDesign lensDesign;
		int columnIndex = -1;
		while (!cursor.isAfterLast()) {
			lensDesign = new LensDesign();
			columnIndex = cursor.getColumnIndex(LENS_DESIGN_DESIGN);
			if (columnIndex != -1)
				lensDesign.setDesign(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_DESIGN_DESIGN_DESC);
			if (columnIndex != -1)
				lensDesign.setDesign_desc(cursor.getString(columnIndex));

			lensDesigns.add(lensDesign);
			cursor.moveToNext();
		}
		cursor.close();
		return lensDesigns;
	}

	private List<ProductBrand> convertCursorToProductBrands(Cursor cursor) {

		List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
		cursor.moveToFirst();
		ProductBrand productBrand;
		while (!cursor.isAfterLast()) {
			productBrand = new ProductBrand();
			productBrand.setBrand_code(cursor.getInt(cursor
					.getColumnIndex(PRODUCT_BRAND_BRAND_CODE)));
			productBrand.setBrand_desc(cursor.getString(cursor
					.getColumnIndex(PRODUCT_BRAND_BRAND_DESC)));
			// productBrand.setSap_brand_code(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND_SAP_BRAND_CODE)));
			// productBrand.setPrice_group(cursor.getInt(cursor.getColumnIndex(PRODUCT_BRAND_PRICE_GROUP)));
			// productBrand.setActive(cursor.getInt(cursor.getColumnIndex(PRODUCT_BRAND_ACTIVE)));

			productBrands.add(productBrand);
			cursor.moveToNext();
		}
		cursor.close();

		return productBrands;
	}

	private List<Lens> convertCursorToLens(Cursor cursor) {

		lenses = new ArrayList<Lens>();
		cursor.moveToFirst();
		Lens lens;
		int columnIndex = 0;
		while (!cursor.isAfterLast()) {
			lens = new Lens();
			columnIndex = cursor.getColumnIndex(LENS_LENS_CODE);
			if (columnIndex != -1)
				lens.setLens_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_DESCRIPTION);
			if (columnIndex != -1)
				lens.setDescription(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_LENS_SIZE);
			if (columnIndex != -1)
				lens.setLens_size(cursor.getInt(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_PRODUCT_BRAND);
			if (columnIndex != -1)
				lens.setProduct_brand(cursor.getInt(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_ACTIVE_ID);
			if (columnIndex != -1)
				lens.setActive_id(cursor.getInt(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_SPHMAX);
			if (columnIndex != -1)
				lens.setSphmax(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_SPHMIN);
			if (columnIndex != -1)
				lens.setSphmin(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_LENS_DESIGN);
			if (columnIndex != -1)
				lens.setLens_design(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_LENS_INDEX_OE);
			if (columnIndex != -1)
				lens.setLens_index_oe(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_VISION_TYPE);
			if (columnIndex != -1)
				lens.setVision_type(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_CYLMAX);
			if (columnIndex != -1)
				lens.setCylmax(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_CYLMIN);
			if (columnIndex != -1)
				lens.setCylmin(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_ADDMAX);
			if (columnIndex != -1)
				lens.setAddmax(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_ADDMIN);
			if (columnIndex != -1)
				lens.setAddmin(cursor.getFloat(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_DISPLAY_NEME);
			if(columnIndex != -1)lens.setDisplay_name(cursor.getString(columnIndex));


			lenses.add(lens);
			cursor.moveToNext();
		}
		cursor.close();

		return lenses;
	}
	
	private List<Service> convertCursorToServices(Cursor cursor){
		
		List<Service> services = new ArrayList<Service>();
		cursor.moveToFirst();
		Service service;
		int columnIndex = -1;
		while(!cursor.isAfterLast()){
			service = new Service();
			columnIndex = cursor.getColumnIndex(SERVICE_SERVICE_CODE);
			if(columnIndex != -1)service.setService_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(SERVICE_SERVICE_DESC);
			if(columnIndex != -1)service.setService_desc(cursor.getString(columnIndex));
			
			services.add(service);
			cursor.moveToNext();
		}
		cursor.close();
		return services;
	}


	private List<LensForInventory> convertCursorToLensForInventory(Cursor cursor) {

		// List<Lens> lenses = new ArrayList<Lens>();
		List<LensForInventory> lensForInventories = new ArrayList<LensForInventory>();
		cursor.moveToFirst();
		LensForInventory lensForInventory;
		int columnIndex = -1;
		while (!cursor.isAfterLast()) {
			lensForInventory = new LensForInventory();
			columnIndex = cursor.getColumnIndex(LENS_LENS_CODE);
			if(columnIndex != -1)lensForInventory.setLens_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_DESCRIPTION);
			if(columnIndex != -1)lensForInventory.setDescription(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(LENS_SPHMAX);
			if(columnIndex != -1)lensForInventory.setSphmax(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(LENS_SPHMIN);
			if(columnIndex != -1)lensForInventory.setSphmin(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(LENS_CYLMAX);
			if(columnIndex != -1)lensForInventory.setCylmax(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(LENS_CYLMIN);
			if(columnIndex != -1)lensForInventory.setCylmin(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(LENS_ADDMAX);
			if(columnIndex != -1)lensForInventory.setAddmax(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(LENS_ADDMIN);
			if(columnIndex != -1)lensForInventory.setAddmin(new Float(cursor.getDouble(columnIndex)));
			columnIndex = cursor.getColumnIndex(COATING_COATING_CODE);
			if(columnIndex != -1)lensForInventory.setCoating_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(COATING_COATING_DESC);
			if(columnIndex != -1)lensForInventory.setCoating_desc(cursor.getString(columnIndex));

			lensForInventories.add(lensForInventory);
			cursor.moveToNext();
		}
		cursor.close();

		return lensForInventories;
	}

	private List<Coating> convertCursorToCoating(Cursor cursor){
		
		List<Coating> coatings = new ArrayList<Coating>();
		cursor.moveToFirst();
		Coating coating;
		int columnIndex = 0;
		while(!cursor.isAfterLast()){
			coating = new Coating();
			columnIndex = cursor.getColumnIndex(COATING_COATING_CODE);
			if(columnIndex != -1)coating.setCoating_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(COATING_COATING_DESC);
			if(columnIndex != -1)coating.setCoating_desc(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(COATING_ROUTE_ID);
			if(columnIndex != -1)coating.setRoute_id(cursor.getInt(columnIndex));
			
			coatings.add(coating);
			cursor.moveToNext();
		}
		cursor.close();
		
		return coatings;
	}


	private List<Plant> convertCursorToPlants(Cursor cursor) {

		List<Plant> plants = new ArrayList<Plant>();
		cursor.moveToFirst();
		Plant plant;
		while (!cursor.isAfterLast()) {
			plant = new Plant();
			plant.setAddress1(cursor.getString(cursor
					.getColumnIndex(PLANT_ADDRESS1)));
			plant.setAddress2(cursor.getString(cursor
					.getColumnIndex(PLANT_ADDRESS2)));
			plant.setCity(cursor.getString(cursor.getColumnIndex(PLANT_CITY)));
			plant.setCompany_code(cursor.getInt(cursor
					.getColumnIndex(PLANT_COMPANY_CODE)));
			plant.setCountry_code(cursor.getString(cursor
					.getColumnIndex(PLANT_COUNTRY_CODE)));
			plant.setFax(cursor.getString(cursor.getColumnIndex(PLANT_FAX)));
			plant.setGmanager(cursor.getString(cursor
					.getColumnIndex(PLANT_GMANAGER)));
			plant.setMain_area(cursor.getString(cursor
					.getColumnIndex(PLANT_MAIN_AREA)));
			plant.setManager(cursor.getString(cursor
					.getColumnIndex(PLANT_MANAGER)));
			plant.setPhone(cursor.getString(cursor.getColumnIndex(PLANT_PHONE)));
			plant.setPlant_desc(cursor.getString(cursor
					.getColumnIndex(PLANT_PLANT_DESC)));
			plant.setR_plant(cursor.getInt(cursor.getColumnIndex(PLANT_R_PLANT)));
			plant.setState_code(cursor.getString(cursor
					.getColumnIndex(PLANT_STATE_CODE)));
			plant.setCustomerLastSyncTime(cursor.getDouble(cursor.getColumnIndex(PLANT_LAST_CUSTOMER_SYNC_TIME)));

			plants.add(plant);
			cursor.moveToNext();
		}
		cursor.close();

		return plants;
	}

	/*private List<Customer> convertCursorToCustomer(Cursor cursor) {

		List<Customer> customers = new ArrayList<Customer>();
		cursor.moveToFirst();
		Customer customer;
		int columnIndex = -1;
		while (!cursor.isAfterLast()) {
			customer = new Customer();
			columnIndex = cursor.getColumnIndex(CUSTOMER_CUST_CODE);
			if (columnIndex != -1)
				customer.setCust_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CUST_NAME);
			if (columnIndex != -1)
				customer.setCust_name(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_LATITUDE);
			if (columnIndex != -1)
				customer.setLatitude(cursor.getDouble(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_LONGITUDE);
			if (columnIndex != -1)
				customer.setLongitude(cursor.getDouble(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CITY);
			if (columnIndex != -1)
				customer.setCity(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_ACTIVE);
			if (columnIndex != -1)customer.setActive(cursor.getInt(columnIndex));

			customers.add(customer);
			cursor.moveToNext();
		}
		cursor.close();
		return customers;
	}*/
	
private List<Customer> convertCursorToCustomer(Cursor cursor){
		
		List<Customer> customers = new ArrayList<Customer>();
		cursor.moveToFirst();
		Customer customer;
		int columnIndex = -1;
		while(!cursor.isAfterLast()){
			customer = new Customer();
			columnIndex = cursor.getColumnIndex(CUSTOMER_CUST_CODE);
			if(columnIndex != -1)customer.setCust_code(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CUST_NAME);
			if(columnIndex != -1)customer.setCust_name(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_LATITUDE);
			if(columnIndex != -1)customer.setLatitude(cursor.getDouble(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_LONGITUDE);
			if(columnIndex != -1)customer.setLongitude(cursor.getDouble(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CITY);
			if(columnIndex != -1)customer.setCity(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_MOBILE);
			if(columnIndex != -1)customer.setMobile(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CONTACT_PERSON);
			if(columnIndex != -1)customer.setContact_person(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_EMAIL);
			if(columnIndex != -1)customer.setEmail(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_PIN);
			if(columnIndex != -1)customer.setPin(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_CUST_TYPE_BIFORCATE);
			if(columnIndex != -1)customer.setCust_type_biforcate(cursor.getString(columnIndex));
			columnIndex = cursor.getColumnIndex(CUSTOMER_ACTIVE);
			if(columnIndex != -1)customer.setActive(cursor.getInt(columnIndex));
			
			customers.add(customer);
			cursor.moveToNext();
		}
		cursor.close();
		return customers;
	}

	private List<Customer> convertCursorToCustomerName(Cursor cursor) {

		List<Customer> customers = new ArrayList<Customer>();
		cursor.moveToFirst();
		Customer customer;
		while (!cursor.isAfterLast()) {
			customer = new Customer();
			customer.setCust_code(cursor.getString(cursor
					.getColumnIndex(CUSTOMER_CUST_CODE)));
			customer.setCity(cursor.getString(cursor
					.getColumnIndex(CUSTOMER_CITY)));
			customer.setLatitude(cursor.getDouble(cursor
					.getColumnIndex(CUSTOMER_LATITUDE)));
			customer.setLongitude(cursor.getDouble(cursor
					.getColumnIndex(CUSTOMER_LONGITUDE)));

			customers.add(customer);
			cursor.moveToNext();
		}
		cursor.close();
		return customers;
	}

	private List<Customer> convertCursorToCustomerCity(Cursor cursor) {

		List<Customer> customers = new ArrayList<Customer>();
		cursor.moveToFirst();
		Customer customer;
		while (!cursor.isAfterLast()) {
			customer = new Customer();
			customer.setCity(cursor.getString(cursor
					.getColumnIndex(CUSTOMER_CITY)));
			Log.i(tag, "city : " + customer.getCity());
			customers.add(customer);
			cursor.moveToNext();
		}
		cursor.close();
		return customers;
	}

	private List<Status> convertCursorToStatus(Cursor cursor) {

		List<Status> statuses = new ArrayList<Status>();
		cursor.moveToFirst();
		Status status;
		while (!cursor.isAfterLast()) {
			status = new Status();
			status.setStatus_code(cursor.getInt(cursor
					.getColumnIndex(STATUS_STATUS_CODE)));
			status.setStatus_desc(cursor.getString(cursor
					.getColumnIndex(STATUS_STATUS_DESC)));

			statuses.add(status);
			cursor.moveToNext();
		}
		cursor.close();
		// if(Constant.log) Log.i(tag, "Status length : "+statuses.size());
		return statuses;
	}

	private List<InhouseBank> convertCursorToInhouseBank(Cursor cursor) {

		List<InhouseBank> inhouseBanks = new ArrayList<InhouseBank>();
		cursor.moveToFirst();
		InhouseBank inhouseBank;
		while (!cursor.isAfterLast()) {
			inhouseBank = new InhouseBank();
			inhouseBank.setSap_gl(cursor.getString(cursor
					.getColumnIndex(INHOUSE_BANK_SAP_GL)));
			// inhouseBank.setProfit_center(cursor.getInt(cursor.getColumnIndex(INHOUSE_BANK_PROFIT_CENTER)));
			inhouseBank.setBank_desc(cursor.getString(cursor
					.getColumnIndex(INHOUSE_BANK_BANK_DESC)));

			inhouseBanks.add(inhouseBank);
			cursor.moveToNext();
		}
		cursor.close();
		// if(Constant.log) Log.i(tag, "Status length : "+statuses.size());
		return inhouseBanks;
	}

	/*
	 * private List<Lens> convertCursorToLens(Cursor cursor){
	 * 
	 * List<Lens> lenses = new ArrayList<Lens>(); cursor.moveToFirst();
	 * while(!cursor.isAfterLast()){ Lens lens = new Lens();
	 * lens.setLens_code(cursor
	 * .getString(cursor.getColumnIndex(LENS_LENS_CODE)));
	 * lens.setDescription(cursor
	 * .getString(cursor.getColumnIndex(LENS_DESCRIPTION)));
	 * 
	 * lenses.add(lens); cursor.moveToNext(); } cursor.close();
	 * //if(Constant.log) Log.i(tag, "Status length : "+statuses.size()); return
	 * lenses; }
	 */

	private List<Video> convertCursorToVideo(Cursor cursor) {

		List<Video> videos = new ArrayList<Video>();
		cursor.moveToFirst();
		Video video;
		while (!cursor.isAfterLast()) {
			video = new Video();
			video.setUrl(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
			video.setVideo_description(cursor.getString(cursor
					.getColumnIndex(VIDEO_VIDEO_DESCRIPTION)));

			videos.add(video);
			cursor.moveToNext();
		}
		cursor.close();
		// if(Constant.log) Log.i(tag, "Status length : "+statuses.size());
		return videos;
	}
	
	private List<CustRepVisit> convertCursorToCustRepVisit(Cursor cursor) {

		List<CustRepVisit> custRepVisits = new ArrayList<CustRepVisit>();
		cursor.moveToFirst();
		CustRepVisit custRepVisit;
		while (!cursor.isAfterLast()) {
			custRepVisit = new CustRepVisit();
			custRepVisit.setId(cursor.getInt(cursor.getColumnIndex(CUST_REP_VISIT_ID)));
			custRepVisit.setPurpose(cursor.getString(cursor.getColumnIndex(CUST_REP_VISIT_PURPOSE)));

			custRepVisits.add(custRepVisit);
			cursor.moveToNext();
		}
		cursor.close();
		// if(Constant.log) Log.i(tag, "Status length : "+statuses.size());
		return custRepVisits;
	}

	// ----------------- convert _______ to contentvalues
	// -----------------------------

	private ContentValues convertUserToCV(User user){
		
		ContentValues cv = new ContentValues();
		cv.put(LOGIN_USERNAME, user.getUserName());
		cv.put(LOGIN_PASSWORD, user.getPassword());
		cv.put(LOGIN_R_PLANT, user.getR_plant());
		cv.put(LOGIN_CUSTOMER_NO, user.getCustomer_no());
		cv.put(LOGIN_LAST_USERPLANTSRIGHTS_SYNC_TIME, user.getUserPlantsRightsLastSyncTime());
		cv.put(LOGIN_LAST_CUSTOMER_SYNC_TIME, user.getCustomerLastSyncTime());
		cv.put(LOGIN_FULL_NAME, user.getFull_name());
		
		return cv;
	}

	private ContentValues convertLensToCV(Lens lens){
		ContentValues cv = new ContentValues();
		cv.put(LENS_LENS_CODE, lens.getLens_code());
		cv.put(LENS_DESCRIPTION, lens.getDescription());
		cv.put(LENS_LENS_SIZE, lens.getLens_size());
		cv.put(LENS_PRODUCT_BRAND, lens.getProduct_brand());
		cv.put(LENS_ACTIVE_ID, lens.getActive_id());
		cv.put(LENS_SPHMAX, lens.getSphmax());
		cv.put(LENS_SPHMIN, lens.getSphmin());
		cv.put(LENS_LENS_DESIGN, lens.getLens_design());
		cv.put(LENS_LENS_INDEX_OE, lens.getLens_index_oe());
		cv.put(LENS_VISION_TYPE, lens.getVision_type());
		cv.put(LENS_CYLMAX, lens.getCylmax());
		cv.put(LENS_CYLMIN, lens.getCylmin());
		cv.put(LENS_ADDMAX, lens.getAddmax());
		cv.put(LENS_ADDMIN, lens.getAddmin());
		cv.put(LENS_DISPLAY_NEME, lens.getDisplay_name());
		cv.put(LENS_LTYP, lens.getLtyp());
		return cv;
		}

	
	private ContentValues convertLensDesignToCV(LensDesign lensDesign){
		
		ContentValues cv = new ContentValues();
		cv.put(LENS_DESIGN_DESIGN, lensDesign.getDesign());
		cv.put(LENS_DESIGN_DESIGN_DESC, lensDesign.getDesign_desc());
		
		return cv;
	}



	private ContentValues convertLensCoatToCV(LensCoat lensCoat) {

		ContentValues cv = new ContentValues();

		cv.put(LENS_COAT_ACTIVE, lensCoat.getActive());
		cv.put(LENS_COAT_COATING_CODE, lensCoat.getCoating_code());
		cv.put(LENS_COAT_ID, lensCoat.getId().toString());
		cv.put(LENS_COAT_LENS_CODE, lensCoat.getLens_code());

		return cv;
	}

	private ContentValues convertCoatingToCV(Coating coating) {

		ContentValues cv = new ContentValues();

		cv.put(COATING_COATING_CODE, coating.getCoating_code());
		cv.put(COATING_COATING_DESC, coating.getCoating_desc());
		cv.put(COATING_COATING_DESC2, coating.getCoating_desc2());
		cv.put(COATING_ROUTE_ID, coating.getRoute_id().toString());

		return cv;
	}

	private ContentValues convertPlantToCV(Plant plant) {

		ContentValues cv = new ContentValues();

		cv.put(PLANT_ADDRESS1, plant.getAddress1());
		cv.put(PLANT_ADDRESS2, plant.getAddress2());
		cv.put(PLANT_CITY, plant.getCity());
		cv.put(PLANT_COMPANY_CODE, plant.getCompany_code());
		cv.put(PLANT_COUNTRY_CODE, plant.getCountry_code());
		cv.put(PLANT_FAX, plant.getFax());
		cv.put(PLANT_GMANAGER, plant.getGmanager());
		cv.put(PLANT_MAIN_AREA, plant.getMain_area());
		cv.put(PLANT_MANAGER, plant.getManager());
		cv.put(PLANT_PHONE, plant.getPhone());
		cv.put(PLANT_PLANT_DESC, plant.getPlant_desc());
		cv.put(PLANT_R_PLANT, plant.getR_plant());
		cv.put(PLANT_STATE_CODE, plant.getState_code());
		cv.put(PLANT_LAST_CUSTOMER_SYNC_TIME, plant.getCustomerLastSyncTime());

		return cv;
	}

	private ContentValues convertProductBrandToCV(ProductBrand productBrand) {

		ContentValues cv = new ContentValues();

		cv.put(PRODUCT_BRAND_BRAND_CODE, productBrand.getBrand_code());
		cv.put(PRODUCT_BRAND_BRAND_DESC, productBrand.getBrand_desc());
		cv.put(PRODUCT_BRAND_SAP_BRAND_CODE, productBrand.getSap_brand_code());
		cv.put(PRODUCT_BRAND_PRICE_GROUP, productBrand.getPrice_group());
		cv.put(PRODUCT_BRAND_ACTIVE, productBrand.getActive());
		cv.put(PRODUCT_BRAND_ACTIVE_ON_MOBILE,
				productBrand.getActive_on_mobile());

		return cv;
	}

	private ContentValues convertStatusToCV(Status status) {

		ContentValues cv = new ContentValues();

		cv.put(STATUS_STATUS_CODE, status.getStatus_code());
		cv.put(STATUS_STATUS_DESC, status.getStatus_desc());

		return cv;
	}

	private ContentValues convertServiceToCV(Service service) {

		ContentValues cv = new ContentValues();

		cv.put(SERVICE_ACTIVE, service.getActive());
		cv.put(SERVICE_LENS_INDEX, service.getLens_index());
		cv.put(SERVICE_RATE, service.getRate());
		cv.put(SERVICE_SERVICE_CODE, service.getService_code());
		cv.put(SERVICE_SERVICE_DESC, service.getService_desc());
		cv.put(SERVICE_LTYP, service.getLtyp());
		return cv;
		}


	private ContentValues convertCustomerToCV(Customer customer) {

		ContentValues cv = new ContentValues();

		cv.put(CUSTOMER_ADDRESS1, customer.getAddress1());
		cv.put(CUSTOMER_ADDRESS2, customer.getAddress2());
		cv.put(CUSTOMER_CITY, customer.getCity());
		cv.put(CUSTOMER_COUNTRY_CODE, customer.getCountry_code());
		cv.put(CUSTOMER_CUST_CODE, customer.getCust_code());
		cv.put(CUSTOMER_CUST_NAME, customer.getCust_name());
		cv.put(CUSTOMER_EMAIL, customer.getEmail());
		cv.put(CUSTOMER_FAX, customer.getFax());
		cv.put(CUSTOMER_MOBILE, customer.getMobile());
		cv.put(CUSTOMER_PHONE, customer.getPhone());
		cv.put(CUSTOMER_R_PLANT, customer.getR_plant());
		cv.put(CUSTOMER_STATE_CODE, customer.getState_code());
		cv.put(CUSTOMER_ACTIVE, customer.getActive());
		cv.put(CUSTOMER_LATITUDE, customer.getLatitude());
		cv.put(CUSTOMER_LONGITUDE, customer.getLongitude());
		cv.put(CUSTOMER_CONTACT_PERSON, customer.getContact_person());
		cv.put(CUSTOMER_PIN, customer.getPin());
		cv.put(CUSTOMER_CUST_TYPE_BIFORCATE, customer.getCust_type_biforcate());
		cv.put(CUSTOMER_CUSTOMER_INCHARGE, customer.getCustomer_incharge());

		return cv;
	}

	private ContentValues convertInhouseBankToCV(InhouseBank inhouseBank) {

		ContentValues cv = new ContentValues();

		cv.put(INHOUSE_BANK_ID, inhouseBank.getId());
		cv.put(INHOUSE_BANK_SAP_GL, inhouseBank.getSap_gl());
		cv.put(INHOUSE_BANK_PROFIT_CENTER, inhouseBank.getProfit_center());
		cv.put(INHOUSE_BANK_BANK_DESC, inhouseBank.getBank_desc());

		return cv;
	}

	private ContentValues convertVideoToCV(Video video) {

		ContentValues cv = new ContentValues();

		cv.put(VIDEO_ID, video.getId());
		cv.put(VIDEO_VIDEO_DESCRIPTION, video.getVideo_description());
		cv.put(VIDEO_URL, video.getUrl());
		cv.put(VIDEO_ACTIVE, video.getActive());

		return cv;
	}
	
	private ContentValues convertUserPlantsRightsToCV(UserPlantsRights userPlantsRight) {

		ContentValues cv = new ContentValues();
		
		cv.put(USER_PLANTS_RIGHTS_ID, userPlantsRight.getId());
		cv.put(USER_PLANTS_RIGHTS_USERNAME, userPlantsRight.getUsername());
		cv.put(USER_PLANTS_RIGHTS_R_PLANT, userPlantsRight.getR_plant());

		return cv;
	}
	private ContentValues convertCustRepVisitToCV(CustRepVisit custRepVisit) {

		ContentValues cv = new ContentValues();
		
		cv.put(CUST_REP_VISIT_ID, custRepVisit.getId());
		cv.put(CUST_REP_VISIT_PURPOSE, custRepVisit.getPurpose());

		return cv;
	}

	public void clearDatabase() {

	   /*query = "delete from " + LOGIN;
	   db.execSQL(query);
	   query = "delete from "+LENS;
	   db.execSQL(query);
	   query = "delete from "+LENS_COAT;
	   db.execSQL(query);
	   query ="delete from "+COATING; 
	   db.execSQL(query);
	   query ="delete from "+PLANT;
	   db.execSQL(query);
	   query ="delete from "+PRODUCT_BRAND; 
	   db.execSQL(query);
	   query = "delete from "+STATUS;
	   db.execSQL(query);
	   query = "delete from "+SERVICE; 
	   db.execSQL(query); */
	   query = "delete from "+CUSTOMER; 
	   db.execSQL(query); 
	   /*query = "delete from "+INHOUSE_BANK; 
	   db.execSQL(query);*/
	   query = "delete from "+USER_PLANTS_RIGHTS; 
	   db.execSQL(query); 

		if (Constant.log)
			Log.i(tag, "Database cleared");
	}
	
	public void clearUserPlantsRights(){
		query = "delete from "+USER_PLANTS_RIGHTS; 
		db.execSQL(query); 
		
		if (Constant.log)Log.i(tag, "USER_PLANTS_RIGHTS cleared");
	}
	
	public Boolean isLoginTableExist() {
		if (Constant.log)
			Log.i(tag, "check login table exist or not");
		// Cursor c =
		// db.rawQuery("SELECT CASE WHEN "+CATEGORY+" THEN 1 ELSE 0 END FROM "+DATABASE_NAME+" WHERE type = 'table'",
		// null);
		// Cursor c =
		// db.rawQuery("select case when tbl_name = '"+LOGIN+"' then 1 else 0 end from sqlite_master where tbl_name = '"+LOGIN+"' and type = 'table'",
		// null);
		Cursor cursor = db.rawQuery(
				"select tbl_name from sqlite_master where tbl_name = '" + LOGIN
						+ "' and type = 'table'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).equals(LOGIN)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean isLensTableExist() {
		if (Constant.log)
			Log.i(tag, "check lens table exist or not");
		Cursor cursor = db.rawQuery(
				"select tbl_name from sqlite_master where tbl_name = '" + LENS
						+ "' and type = 'table'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).equals(LENS)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean isLensCoatTableExist() {
		if (Constant.log)
			Log.i(tag, "check lens coat table exist or not");
		Cursor cursor = db.rawQuery(
				"select tbl_name from sqlite_master where tbl_name = '"
						+ LENS_COAT + "' and type = 'table'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).equals(LENS_COAT)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean isCoatingTableExist() {
		if (Constant.log)
			Log.i(tag, "check coating table exist or not");
		Cursor cursor = db.rawQuery(
				"select tbl_name from sqlite_master where tbl_name = '"
						+ COATING + "' and type = 'table'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).equals(COATING)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean isPlantTableExist() {
		if (Constant.log)
			Log.i(tag, "check coating table exist or not");
		Cursor cursor = db.rawQuery(
				"select tbl_name from sqlite_master where tbl_name = '" + PLANT
						+ "' and type = 'table'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).equals(PLANT)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

}
