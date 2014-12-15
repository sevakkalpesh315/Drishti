package com.gkbhitech.drishti.common;

import java.io.File;

import android.os.Environment;

public class Constant {

	public static final int STOPSPLASH = 0;
	public static final int SPLASHTIME = 2000;

	public static final String BASE_URI = "http://121.242.11.72:8080/DrishtiProduction/rest";
	public static final int PORT = 8080;
	//public static final String BASE_URI = "http://121.242.11.72:8090/Drishti/rest";
	//public static int String PORT = 8090;
	public static final String BASE_IP = "121.242.11.72";
	
	

	public static final int CONNECTION_TIMEOUT = 30000;
	public static final int SOCKET_TIMEOUT = 600000;

	public static final String CASH = "CASH";
	public static final String CHEQUE = "CHEQUE";

	public static final Boolean log = true;

	public static final String URL_LOGIN = "user";
	public static final String URL_TEST = "/plant/2001/inventory/FB001DTL/ZREJ";
	public static final String URL_INVENTORY = "sap/inventory";
	public static final String URL_PRODUCT_BRANDS = "productbrands";
	public static final String URL_LENS = "lenses";
	public static final String URL_LENSCOATS = "lenscoats";
	public static final String URL_COATING = "coating";
	public static final String URL_PLANT = "plants";
	public static final String URL_CUSTOMER = "customers";
	public static final String URL_TRACK_ORDER_BY_DATE = "customer/order/summary";
	public static final String URL_ORDER_HISTORY = "customer/order/history/";
	public static final String URL_TRACK_ORDER_BY_CUSTOMER_REF_NO = "customer/ordersummarybycustomerrefno";
	public static final String URL_ORDER = "customer/order";
	public static final String URL_CHECK_WARRANTY = "customer/ordersummarywarranty";
	public static final String URL_STATUS = "statuscodes";
	public static final String URL_SERVICES = "services";
	public static final String URL_GEOTAGGING = "customer";
	public static final String URL_LOG_VISIT = "customer";
	public static final String URL_QUOTEGROUP = "quote/group";
	public static final String URL_QUOTE = "quote";
	public static final String URL_INHOUSE_BANK = "inhousebanks";
	public static final String URL_CUSTOMER_RECEIPT = "sap/customerreceipt";
	public static final String URL_CUSTOMER_ACCOUNT_STATEMENT = "sap/customeraccountsatatement";
	public static final String URL_NEED_SYNC = "modulestobesynced";
	public static final String URL_CHANGE_PASSWORD = "changepassword";
	public static final String URL_ORDER_AND_POWER_DETAIL = "orderandpowerdetail";
	public static final String URL_ACTIVATE_PPLP = "activatepplp";
	public static final String URL_VIDEOS = "videos";
	public static final String URL_CHECK_CUSTOMER_BALANCE = "checkcustomerbalance";
	public static final String URL_CHECK_CUSTOMER_GDO_BALANCE = "getgdobalancepoint";
	public static final String URL_GET_CUSTOMER_FOR_GDO = "getcustomerforgdo";
	public static final String URL_ORDER_STOCK_LENS = "orderstocklens";
	public static final String URL_CUSTOMER_DETAIL_FOR_VISIT = "customerdetail";
	public static final String URL_REGISTER_DEVICE = "registerdevice";
	public static final String GCM_APP_ID = "696388359123";
	public static final String URL_OFFERED_ORDER_STOCK_LENS = "orderofferedstocklens";
	public static final String URL_OFFERS = "offers";
	public static final String URL_SOLD_BY = "soldby";
	public static final String URL_CAL_BASE_CURVE = "calbasecurve";
	public static final String URL_USER_PLANTS_RIGHTS = "getuserplantsrights";
	public static final String URL_LENS_DESIGN = "lensdesign";
	public static final String URL_ORDER_RX_LENS = "orderrxlens";
	public static final String URL_CUST_REP_VISIT = "custrepvisit";
	public static final String URL_REGISTER_VISIT = "registervisit";
	public static final String URL_ORDER_OFFERED_RX_LENS = "orderofferedrxlens";
	
	public static final String MESSAGE_ERROR = "Error";
	public static String responseMsg;
	public static final String URL_SALES_BOOSTER = "salesbooster";
	public static final String[] paymentModeArray = {"CASH","CHEQUE"};
	
	//public static final String[] arrayMaterialTypeOptional = { "Select Material", "Stock", "Rx" };

	public static final String[] addZeroToFourOptional = {"Select Addition", "0.75", "1.00", "1.25", "1.50", "1.75"
			,"2.00","2.25","2.50","2.75","3.00","3.25","3.50","3.75","4.00"};


	
	public static final String[] arrayMaterialTypeOptional = { "Select Material", "Stock", "Rx" };

	public static final String[] arrayMaterialType = { "Stock", "Rx" };
	public static final String[] arrayDesignValues = { "SV", "BF", "PROG", "FF" };
	public static final String[] arrayDesign = { "Single Vision", "Bi-Focal",
			"Progressive", "Free Form" };
	
	public static final String URL_CUSTOMER_DUES = "customerdues";

	public static int responseCode;
	public static final int RESULT_ERROR = -1;
	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NETWORK_UNAVAILABLE = 1;
	public static final int RESULT_AUTHENTICATION_FAILURE = 2;
	public static final int RESULT_NULL_RESPONSE = 3;
	public static final int RESULT_INVALID_AUTH_TOKEN = 555;
	public static final int RESULT_RECORD_NOT_FOUND = 100;
	public static final int RESULT_SERVER_DATABASE_ERROR = 500;
	public static final int RESULT_SAP_ERROR = 900;
	public static final int RESULT_PASSWORD_NOT_MATCH = 220;
	public static final int RESULT_SD_CARD_NOT_EXIST = 4;

	public static final String MESSAGE_SUCCESS = "Success";
	public static final String MESSAGE_NETWORK_UNAVAILABLE = "Network unavailable";
	public static final String MESSAGE_AUTHENTICATION_FAILURE = "Authentication fail";
	public static final String MESSAGE_NULL_RESPONSE = "No response";
	public static final String MESSAGE_INVALID_AUTH_TOKEN = "Invalid authentication token";
	public static final String MESSAGE_RECORD_NOT_FOUND = "Record not found";
	public static final String MESSAGE_SERVER_DATABASE_ERROR = "Error in server database";
	public static final String MESSAGE_SAP_ERROR = "Sap error";
	public static final String MESSAGE_PASSWORD_NOT_MATCH = "Password not match";
	public static final String MESSAGE_SD_CARD_NOT_EXIST = "Please insert SD card to store videos";

	public static final String DIALOG_TITLE_ERROR_MESSAGE = "Error Message";
	public static final String DIALOG_TITLE_MESSAGE = "Message";

	public static final String LENS_LAST_SYNC_TIME = "lensLastSyncTime";
	public static final String LENS_COATS_LAST_SYNC_TIME = "lensCoatSLastSyncTime";
	public static final String COATING_LAST_SYNC_TIME = "coatingLastSyncTime";
	public static final String PRODUCTBRAND_LAST_SYNC_TIME = "productBrandLastSyncTime";
	public static final String SERVICE_LAST_SYNC_TIME = "serviceLastSyncTime";
	public static final String STATUS_LAST_SYNC_TIME = "statusLastSyncTime";
	public static final String PLANT_LAST_SYNC_TIME = "plantLastSyncTime";
	public static final String CUSTOMER_LAST_SYNC_TIME = "customerLastSyncTime";
	public static final String INHOUSEBANK_LAST_SYNC_TIME = "inhouseBankLastSyncTime";
	public static final String VIDEOS_LAST_SYNC_TIME = "videosLastSyncTime";
	public static final String USER_PLANTS_RIGHTS_LAST_SYNC_TIME = "userPlantsLastSyncTime";
	public static final String LENS_DESIGN_LAST_SYNC_TIME = "lensDesignLastSyncTime";
	public static final String CUST_REP_VISIT_LAST_SYNC_TIME = "custRepVisitLastSyncTime";

	public static final Double[] sph = { +20.00, +19.75, +19.50, +19.25,
			+19.00, +18.75, +18.50, +18.25, +18.00, +17.75, +17.50, +17.25,
			+17.00, +16.75, +16.50, +16.25, +16.00, +15.75, +15.50, +15.25,
			+15.00, +14.75, +14.50, +14.25, +14.00, +13.75, +13.50, +13.25,
			+13.00, +12.75, +12.50, +12.25, +12.00, +11.75, +11.50, +11.25,
			+11.00, +10.75, +10.50, +10.25, +10.00, +9.75, +9.50, +9.25, +9.00,
			+8.75, +8.50, +8.25, +8.00, +7.75, +7.50, +7.25, +7.00, +6.75,
			+6.50, +6.25, +6.00, +5.75, +5.50, +5.25, +5.00, +4.75, +4.50,
			+4.25, +4.00, +3.75, +3.50, +3.25, +3.00, +2.75, +2.50, +2.25,
			+2.00, 1.75, +1.50, +1.25, +1.00, 0.75, 0.50, 0.25, 0.00, -0.25,
			-0.50, -0.75, -1.00, -1.25, -1.50, -1.75, -2.00, -2.25, -2.50,
			-2.75, -3.00, -3.25, -3.50, -3.75, -4.00, -4.25, -4.50, -4.75,
			-5.00, -5.25, -5.50, -5.75, -6.00, -6.25, -6.50, -6.75, -7.00,
			-7.25, -7.50, -7.75, -8.00, -8.25, -8.50, -8.75, -9.00, -9.25,
			-9.50, -9.75, -10.00, -10.25, -10.50, -10.75, -11.00, -11.25,
			-11.50, -11.75, -12.00, -12.25, -12.50, -12.75, -13.00, -13.25,
			-13.50, -13.75, -14.00, -14.25, -14.50, -14.75, -15.00, -15.25,
			-15.50, -15.75, -16.00, -16.25, -16.50, -16.75, -17.00, -17.25,
			-17.50, -17.75, -18.00, -18.25, -18.50, -18.75, -19.00, -19.25,
			-19.50, -19.75, -20.00 };

	public static final Double[] cylZeroToMinusTwo = { 0.00, -0.25, -0.50,
			-0.75, -1.00, -1.25, -1.50, -1.75, -2.00 };

	public static final Double[] cylZeroToMinusFour = { 0.00, -0.25, -0.50,
			-0.75, -1.00, -1.25, -1.50, -1.75, -2.00, -2.25, -2.50, -2.75,
			-3.00, -3.25, -3.50, -3.75, -4.00 };

	public static final int zero = 0;
	public static final int ninety = 90;
	public static final int oneEighty = 180;
	public static final int twoSeventy = 270;
	public static final int threeSixty = 360;

	public static final String DRISHTI_PATH_ON_SD_CARD = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/.drishti";
}
