package com.gkbhitech.drishti.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gkbhitech.drishti.common.Constant;

public class DataBaseHelper extends SQLiteOpenHelper{

	private static final String tag = "DataBaseHelper";
	
	private static final String TABLE_LOGIN = "CREATE TABLE if not exists login("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"username TEXT primary key not null, "+
			"password TEXT not null, " +
			"r_plant INTEGER not null, "+
			"customer_no TEXT, "+
			"last_user_plants_rights_sync_time NUMERIC, " +
			"last_customer_sync_time NUMERIC, " +
			"full_name TEXT not null)";
	
	private static final String TABLE_COATING = "CREATE TABLE if not exists coating("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"coating_code TEXT primary key not null, "+
			"coating_desc TEXT not null, "+
			"coating_desc2 TEXT, "+
			"route_id INTEGER not null)";
	private static final String TABLE_LENS = "CREATE TABLE if not exists lens("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"lens_code TEXT primary key not null, "+
			"description TEXT not null, "+
			"lens_size INTEGER not null, "+
			"product_brand INTEGER not null, "+
			"active_id INTEGER not null, "+
			"sphmax NUMERIC not null, "+
			"sphmin NUMERIC not null, " +
			"lens_design TEXT, " +
			"lens_index_oe TEXT, " +
			"vision_type TEXT, " +
			"cylmax NUMERIC not null, " +
			"cylmin NUMERIC not null, " +
			"addmax NUMERIC not null, " +
			"addmin NUMERIC not null, " +
			"display_name TEXT, " +
			"ltyp TEXT)";


	private static final String TABLE_LENS_COAT = "CREATE TABLE if not exists lenscoat("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"active INTEGER not null, "+
			"coating_code TEXT not null, "+
			"id NUMERIC primary key not null, "+
			"lens_code TEXT not null)";
	private static final String TABLE_PLANT = "CREATE TABLE if not exists plant("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"address1 TEXT, "+
			"address2 TEXT, "+
			"city TEXT, "+
			"company_code INTEGER not null, "+
			"country_code TEXT not null, "+
			"fax TEXT, "+
			"gmanager TEXT, "+
			"main_area TEXT not null, "+
			"manager TEXT, "+
			"phone TEXT, "+
			"plant_desc TEXT not null, "+
			"r_plant INTEGER primary key not null, "+
			"state_code TEXT, " +
			"last_customer_sync_time NUMERIC default 0)";
	private static final String TABLE_PRODUCT_BRAND = "CREATE TABLE if not exists productbrand("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"brand_code INTEGER primary key not null, "+
			"brand_desc TEXT not null, "+
			"sap_brand_code TEXT not null, "+
			"price_group INTEGER, "+
			"active_on_mobile INTEGER not null, "+
			"active INTEGER not null)";
	private static final String TABLE_STATUS = "CREATE TABLE if not exists status("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"status_code INTEGER primary key not null, "+
			"status_desc TEXT not null)";
	private static final String TABLE_SERVICE = "CREATE TABLE if not exists service("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"active INTEGER not null, "+
			"lens_index TEXT, "+
			"rate NUMERIC, "+
			"service_code TEXT primary key not null, "+
			"service_desc TEXT not null, " +
			"ltyp TEXT)";

	private static final String TABLE_CUSTOMER = "CREATE TABLE if not exists customer("+
			//"_id INTEGER primary key autoincrement not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"address1 TEXT, "+
			"address2 TEXT, "+
			"city TEXT, "+
			"country_code TEXT, "+
			"cust_code TEXT not null, "+
			"cust_name TEXT not null, "+
			"email TEXT, "+
			"fax TEXT, "+
			"mobile TEXT, "+
			"phone TEXT, "+
			"r_plant TEXT not null, "+
			"state_code TEXT, "+
			"active NUMERIC, "+
			"latitude NUMERIC, "+
			"longitude NUMERIC, " +
			"contact_person TEXT, " +
			"cust_type_biforcate TEXT, " +
			"pin TEXT, " +
			"customer_incharge TEXT, " +
			"PRIMARY KEY (r_plant,cust_code))";

	
	private static final String TABLE_INHOUSE_BANK = "CREATE TABLE if not exists inhousebank("+
			"id INTEGER primary key  not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"sap_gl TEXT not null, "+
			"profit_center INTEGER not null, "+
			"bank_desc TEXT not null)";
	
	private static final String TABLE_VIDEO = "CREATE TABLE if not exists video("+
			"id INTEGER primary key  not null, "+
			//"_id TEXT primary key not null, "+
			//"updated_at NUMERIC not null, "+
			"video_description TEXT not null, "+
			"url TEXT not null, "+
			"active INTEGER not null)";
	
	private static final String TABLE_LENS_DESIGN = "CREATE TABLE if not exists lensdesign("+
			"design TEXT primary key not null, "+
			"design_desc TEXT)"; 
	
	private static final String TABLE_USER_PLANTS_RIGHTS = "CREATE TABLE if not exists userplantsrights("+
			"id INTEGER primary key  not null, "+
			"username TEXT not null, "+
			"r_plant INTEGER not null)";
	
	private static final String TABLE_CUST_REP_VISIT = "CREATE TABLE if not exists custrepvisit("+
			"id INTEGER primary key not null, "+
			"purpose TEXT not null)";

	
	/*private static final String TABLE_CATEGORY = "CREATE TABLE if not exist category("+
			"_id INTEGER primary key autoincrement not null, "+
			"categoryId TEXT not null, "+
			"version INTEGER not null, "+
			"name TEXT not null, "+
			"description TEXT, "+
			"photoURL TEXT, "+
			"valid INTEGER, "+ //Boolean...0 = false, 1 = true
			"createdOn TEXT not null, "+
			"leaf INTEGER not null);";//Boolean...0 = false, 1 = true
*/	
	public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if(Constant.log) Log.i(tag, "on create : before creating table");
		db.execSQL(TABLE_LOGIN);
		db.execSQL(TABLE_COATING);
		db.execSQL(TABLE_LENS);
		db.execSQL(TABLE_LENS_COAT);
		db.execSQL(TABLE_PLANT);
		db.execSQL(TABLE_PRODUCT_BRAND);
		db.execSQL(TABLE_SERVICE);
		db.execSQL(TABLE_STATUS);
		db.execSQL(TABLE_CUSTOMER);
		db.execSQL(TABLE_INHOUSE_BANK);
		db.execSQL(TABLE_VIDEO);
		db.execSQL(TABLE_LENS_DESIGN);
		db.execSQL(TABLE_USER_PLANTS_RIGHTS);
		db.execSQL(TABLE_CUST_REP_VISIT);
		if(Constant.log) Log.i(tag, "on create : after creating table");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
