package com.gkbhitech.drishti.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.Offer;
import com.gkbhitech.drishti.model.Order;
import com.gkbhitech.drishti.model.OrderAndPowerDetail;
import com.gkbhitech.drishti.model.OrderHistory;
import com.gkbhitech.drishti.model.PostCustomerReceiptResponse;
import com.gkbhitech.drishti.model.Quote;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerAccountStatement;
import com.google.gson.GsonBuilder;

public class DrishtiApplication extends Application{
	
	private static final String tag = "FieldForceApplication";
	
	private static final String SHARED_PREFERENCES_NAME = "gkbhitech";
	private static final String IS_USER_LOGEDIN = "isUserLogedId";
	private static final String IS_MASTER_UP_TO_DATE = "isMasterUpToDate";
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String USERTYPE = "userType";
	private static final String USERNAME = "userName";
	private static final String PLANT = "plant";
	private static final String CUSTOMER = "customer";
	private static final String IS_SYNC_BLOCKED = "isSyncBlocked";
	private WebServiceObjectClient webServiceObjectClient;
	private DataBaseAdapter dataBaseAdapter;
	private GsonBuilder gsonBuilder = new GsonBuilder();
	
	/*private String c_code;
	private String p_brand;
	private String l_code;
	private String ct_code;
	private int r_plant;*/
	//private static LogoutMessageReceiver logoutMessageReceiver;
	//private double last_sync = new Double(0);
	
	private Offer offer;
	
	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	private Order[] orders;
	private Order selectedOrder;
	private OrderHistory orderHistory;
	private Customer customer;
	private Lens lens;
	private Quote quote;
	private MethodResponseCustomerAccountStatement methodResponseCustomerAccountStatement;
	private String orderNo;
	private OrderAndPowerDetail[] orderAndPowerDetails;
	private PostCustomerReceiptResponse postCustomerReceiptResponse;
	
	
	//private static Context context;

	public DrishtiApplication(){
		super();
		webServiceObjectClient = new WebServiceObjectClient(Constant.BASE_URI); 
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		if(Constant.log) Log.i(tag, "Application created");
		
		initializeDatabase();
		webServiceObjectClient.setAccessToken(getAccessToken());
		webServiceObjectClient.setUserName(getUserName());
		//FieldForceApplication.context = getApplicationContext();
		//logoutMessageReceiver = new LogoutMessageReceiver();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		dataBaseAdapter.close();
	}
	
	public WebServiceObjectClient getWebserviceObjectClient(){
		return webServiceObjectClient;
	}
	
	public DataBaseAdapter getDataBaseAdapter(){
		
		if(dataBaseAdapter != null && dataBaseAdapter.isDbOpen()){
			return dataBaseAdapter;
		}
		initializeDatabase();
		return dataBaseAdapter;
	}

	/*public static LogoutMessageReceiver getLogoutMessageReceiver() {
		return logoutMessageReceiver;
	}*/
	
	private void initializeDatabase(){
		dataBaseAdapter = new DataBaseAdapter(this);
		if(Constant.log) Log.i(tag, "Database Adapter initialize");
		
		dataBaseAdapter.open();
	}
	
	public void setIsUserLogedIn(Boolean isUserLogedIn) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putBoolean(IS_USER_LOGEDIN, isUserLogedIn);
		prefsEditor.commit();
	}

	public Boolean getIsUserLogedIn() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getBoolean(IS_USER_LOGEDIN, false);
	}
	
	public void setIsSyncBlocked(Boolean isSyncBlocked) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putBoolean(IS_SYNC_BLOCKED, isSyncBlocked);
		prefsEditor.commit();
	}

	public Boolean getIsSyncBlocked() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getBoolean(IS_SYNC_BLOCKED, false);
	}
	public void setAccessToken(String accessToken) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putString(ACCESS_TOKEN, accessToken);
		prefsEditor.commit();
	}

	public String getAccessToken() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getString(ACCESS_TOKEN, null);
	}
	
	public void setLastSyncTime(String tableName, Double lastSyncTime) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		//Log.i(tag, ""+lastSyncTime.toString());
		prefsEditor.putString(tableName, lastSyncTime.toString());
		prefsEditor.commit();
	}

	public Double getLastSyncTime(String tableName) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		try{
			//Log.i(tag, ""+new Double(myPrefs.getString(LAST_SYNC_TIME, "0")));
			return new Double(myPrefs.getString(tableName, "0"));
		}catch (NumberFormatException e) {
			return new Double(0); 
		}
	}
	
	public void setUserName(String userName) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putString(USERNAME, userName);
		prefsEditor.commit();
	}

	public String getUserName() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getString(USERNAME, null);
	}
	
	public int getUserType() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getInt(USERTYPE, 0);
	}
	public void setUserType(int userType) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt(USERTYPE, userType);
		prefsEditor.commit();
	}
	
	public void setPlant(int plant) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt(PLANT, plant);
		prefsEditor.commit();
	}

	public int getPlant() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getInt(PLANT, 0);
	}
	
	/*public Customer getCustomer() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		String custCode = myPrefs.getString(CUSTOMER, null);
		Log.i(tag, "")
		return dataBaseAdapter.getCustomerByCustCode(custCode);
	}
	public void setCustomer(Customer customer) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		if(customer != null){
			prefsEditor.putString(CUSTOMER, customer.getCust_code());
		}else{
			prefsEditor.putString(CUSTOMER, null);
		}
		prefsEditor.commit();
	}*/
	
	/*public void setCustomer(Customer customer) {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt(PLANT, customer);
		prefsEditor.commit();
	}

	public int getCustomer() {
		SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_WORLD_READABLE);
		return myPrefs.getInt(PLANT, 0);
	}*/

	public Order[] getOrders() {
		return orders;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}
	public Order getSelectedOrder() {
		return selectedOrder;
	}
	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}
	public OrderHistory getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(OrderHistory orderHistory) {
		this.orderHistory = orderHistory;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Lens getLens() {
		return lens;
	}
	public void setLens(Lens lens) {
		this.lens = lens;
	}
	public Quote getQuote() {
		return quote;
	}
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	public MethodResponseCustomerAccountStatement getMethodResponseCustomerAccountStatement() {
		return methodResponseCustomerAccountStatement;
	}
	public void setMethodResponseCustomerAccountStatement(
			MethodResponseCustomerAccountStatement methodResponseCustomerAccountStatement) {
		this.methodResponseCustomerAccountStatement = methodResponseCustomerAccountStatement;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public OrderAndPowerDetail[] getOrderAndPowerDetails() {
		return orderAndPowerDetails;
	}
	public void setOrderAndPowerDetails(
			OrderAndPowerDetail[] orderAndPowerDetails) {
		this.orderAndPowerDetails = orderAndPowerDetails;
	}
	public PostCustomerReceiptResponse getPostCustomerReceiptResponse() {
		return postCustomerReceiptResponse;
	}
	public void setPostCustomerReceiptResponse(
			PostCustomerReceiptResponse postCustomerReceiptResponse) {
		this.postCustomerReceiptResponse = postCustomerReceiptResponse;
	}

	public void clearCredentials(){
		setIsUserLogedIn(false);
		setAccessToken(null);
		setUserName(null);
		//clearTimeStamp();
		if(Constant.log) Log.i(tag, "user credential are cleared");
	}
	
	public void clearTimeStamp(){
		//setLastSyncTime(Constant.LENS_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.LENS_COATS_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.COATING_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.PRODUCTBRAND_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.SERVICE_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.STATUS_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.PLANT_LAST_SYNC_TIME,0.0);
		setLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME,0.0);
		//setLastSyncTime(Constant.VIDEOS_LAST_SYNC_TIME,0.0);
		setLastSyncTime(Constant.USER_PLANTS_RIGHTS_LAST_SYNC_TIME,0.0);
		if(Constant.log) Log.i(tag, "All maseter timestamp set to zero");
	}
	
	

	/*public String getC_code() {
	return c_code;
	}
	public void setC_code(String c_code) {
	this.c_code = c_code;
	}
	public String getP_brand() {
	return p_brand;
	}
	public void setP_brand(String p_brand) {
	this.p_brand = p_brand;
	}
	public String getL_code() {
	return l_code;
	}
	public void setL_code(String l_code) {
	this.l_code = l_code;
	}
	public String getCt_code() {
	return ct_code;
	}
	public void setCt_code(String ct_code) {
	this.ct_code = ct_code;
	}
	public int getR_plant() {
	return r_plant;
	}
	public void setR_plant(int r_plant) {
	this.r_plant = r_plant;
	}*/

}
