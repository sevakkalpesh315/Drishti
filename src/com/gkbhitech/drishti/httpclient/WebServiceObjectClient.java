/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gkbhitech.drishti.httpclient;

import java.util.HashMap;

import android.util.Log;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.model.CustomerReceipt;
import com.gkbhitech.drishti.model.User;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseActivatePPLP;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseAllOffer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCalBaseCurve;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseChangePassword;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCoating;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustRepVisit;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerAccountStatement;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerBalance;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerDetailForVisit;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseGeotagging;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseInhouseBank;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseInventory;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLastPayment;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLens;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensCoats;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensDesign;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensSpecification;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLogin;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseNeedSync;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOk;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrder;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderAndPowerDetail;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderHistory;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderStockLens;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrders;
import com.gkbhitech.drishti.services.methodresponse.MethodResponsePlant;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseQuote;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseReceipt;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseRegisterDevice;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseService;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseSoldBy;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseStatus;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseUserPlantsRights;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseVideo;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * 
 * @author root
 */
public class WebServiceObjectClient {
	private static final String tag = "WebServiceObjectClient";
	private static final String dateFormat = "MM/dd/yyyy hh:mm:ss a";
	private WebServiceClient m_Client;
	private GsonBuilder m_GsonBuilder;
	private String accessToken;
	private String userName;

	private static HashMap<String, Object> map;

	public WebServiceObjectClient(String baseUri) {
		m_Client = new WebServiceClient(baseUri);
		m_GsonBuilder = new GsonBuilder();
		m_GsonBuilder.setDateFormat(dateFormat);
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * public Company getCompany(){ String response=m_Client.doPost("company");
	 * return m_GsonBuilder.create().fromJson(response,Company.class); }
	 */
	public MethodResponseLogin loginUser(User user)
			throws NetworkUnavailableException, Exception {
		// HashMap<String,Object>map=new HashMap<String,Object>();
		// map.put("userName",user.getUserName());
		// map.put("password", user.getPassword());

		// String request = m_GsonBuilder.create().toJson(user);

		String response = null;
		try {
			response = m_Client.login(user.getUserName(), user.getPassword());
			// System.out.println(response);
		} catch (NetworkUnavailableException e) {
			// TODO: handle exception
			throw new NetworkUnavailableException(e.getMessage());
		} catch (UnauthorizedException e) {
			// TODO: handle exception
			throw new UnauthorizedException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseLogin status = m_GsonBuilder.create().fromJson(
					response, MethodResponseLogin.class);
			return status;
		}
		return null;
	}

	public MethodResponseInventory testRequest()
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_TEST, map);
		} catch (NetworkUnavailableException e) {
			// TODO: handle exception
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseInventory status = m_GsonBuilder.create().fromJson(
					response, MethodResponseInventory.class);
			return status;
		}
		return null;
	}

	public MethodResponseInventory inventoryByMaterialCode(int plantNo,
			String materialCode, String sloc)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_INVENTORY + "/" + plantNo
					+ "/inventory/" + materialCode + "/" + sloc, map);
		} catch (NetworkUnavailableException e) {
			// TODO: handle exception
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseInventory status = m_GsonBuilder.create().fromJson(
					response, MethodResponseInventory.class);
			return status;
		}
		return null;
	}

	public MethodResponseInventory inventory(int plant, int brandCode,
			String lensCode, String coatingCode, String user, Float cyl,
			Float sph) throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("plant", plant);
		map.put("user", user);
		map.put("productBrand", brandCode);
		map.put("lensCode", lensCode);
		map.put("cyl", cyl);
		map.put("sph", sph);
		map.put("coatingCode", coatingCode);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_INVENTORY, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseInventory status = m_GsonBuilder.create().fromJson(
					response, MethodResponseInventory.class);
			return status;
		}
		return null;
	}

	public MethodResponseLens getLens(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_LENS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseLens status = m_GsonBuilder.create().fromJson(
					response, MethodResponseLens.class);
			return status;
		}
		return null;
	}

	public MethodResponseLensCoats getLensCoats(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_LENSCOATS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseLensCoats status = m_GsonBuilder.create().fromJson(
					response, MethodResponseLensCoats.class);
			return status;
		}
		return null;
	}

	public MethodResponseCoating getCoating(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_COATING, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCoating status = m_GsonBuilder.create().fromJson(
					response, MethodResponseCoating.class);
			return status;
		}
		return null;
	}

	public MethodResponseProductBrand getProductBrand(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);
		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_PRODUCT_BRANDS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseProductBrand status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseProductBrand.class);
			return status;
		}
		return null;
	}

	public MethodResponseStatus getStatus(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_STATUS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseStatus status = m_GsonBuilder.create().fromJson(
					response, MethodResponseStatus.class);
			return status;
		}
		return null;
	}

	public MethodResponseService getService(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		// HashMap<String,Object>map=new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_SERVICES, map);
			// System.out.println(response);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseService status = m_GsonBuilder.create().fromJson(
					response, MethodResponseService.class);
			return status;
		}
		return null;
	}

	public MethodResponsePlant getPlant(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("username", userName);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_PLANT, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponsePlant status = m_GsonBuilder.create().fromJson(
					response, MethodResponsePlant.class);
			return status;
		}
		return null;
	}

	public MethodResponseCustomer getCustomer(String userName,
			double customerLastSyncTime, int r_plant) throws NetworkUnavailableException,
			Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("userName", userName);
		map.put("sys_time", customerLastSyncTime);
		map.put("r_plant", r_plant);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CUSTOMER, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustomer status = m_GsonBuilder.create().fromJson(
					response, MethodResponseCustomer.class);
			return status;
		}
		return null;
	}

	public MethodResponseInhouseBank getInhouseBank(String userName,
			double inhouseBankLastSyncTime) throws NetworkUnavailableException,
			Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("userName", userName);
		map.put("sys_time", inhouseBankLastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_INHOUSE_BANK, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseInhouseBank status = m_GsonBuilder.create().fromJson(
					response, MethodResponseInhouseBank.class);
			return status;
		}
		return null;
	}

	public MethodResponseNeedSync getNeedSync(double lensLastSyncTime,
			double lensCoatsLastSyncTime, double coatingLastSyncTime,
			double productBrandLastSyncTime, double statusLastSyncTime,
			double serviceLastSyncTime, double plantLastSyncTime,
			double customerLastSyncTime, double inhouseBankLastSyncTime,
			double videoLastSyncTime, 
			double userPlnatsRightsLastSyncTime,
			double custRepVisitLastSyncTime,
			String userName)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("lensLastSyncTime", lensLastSyncTime);
		map.put("lensCoatsLastSyncTime", lensCoatsLastSyncTime);
		map.put("coatingLastSyncTime", coatingLastSyncTime);
		map.put("productBrandLastSyncTime", productBrandLastSyncTime);
		map.put("statusLastSyncTime", statusLastSyncTime);
		map.put("serviceLastSyncTime", serviceLastSyncTime);
		map.put("plantLastSyncTime", plantLastSyncTime);
		map.put("customerLastSyncTime", customerLastSyncTime);
		map.put("inhouseBankLastSyncTime", inhouseBankLastSyncTime);
		map.put("videoLastSyncTime", videoLastSyncTime);
		map.put("userPlantsRightsLastSyncTime", userPlnatsRightsLastSyncTime);
		map.put("custRepVisitLastSyncTime", custRepVisitLastSyncTime);
		map.put("userName", userName);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_NEED_SYNC, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseNeedSync status = m_GsonBuilder.create().fromJson(
					response, MethodResponseNeedSync.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrders getOrderByDate(String customerCode,String orderType, int brandCode, 
    		String design, String indexOe,float add,String custRefNo, Long fromDate, Long toDate)throws NetworkUnavailableException, Exception{
    	 
         map=new HashMap<String,Object>();
         map.put("authToken",accessToken);
         map.put("customerCode",customerCode);
         map.put("order_type",orderType);
         map.put("brand_code",brandCode);
         map.put("vision_type",design);
         map.put("lens_index_oe",indexOe);
         map.put("add",add);
         map.put("cust_refno",custRefNo);
         
         if(fromDate != null){
        	map.put("fromDate",fromDate);
         }else{
        	map.put("fromDate","");
         }
         if(toDate != null){
        	map.put("toDate",toDate);
         }else{
        	map.put("toDate","");
         }
         
         String response = null;
         try{
         	response = m_Client.doGet(Constant.URL_TRACK_ORDER_BY_DATE, map);
         }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		}
         if(response != null){
        	MethodResponseOrders status=m_GsonBuilder.create().fromJson(response, MethodResponseOrders.class);
         	return status;
         }
         return null;
     }



	public MethodResponseOrderHistory getOrderHistory(String orderNumber)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER_HISTORY + orderNumber,
					map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderHistory status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseOrderHistory.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrders getOrderByCustomerRefNo(String customerRefNo)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		if (Constant.log)
			Log.i(tag, ".....customerRefNo : " + customerRefNo);
		map.put("authToken", accessToken);
		map.put("customerRefNo", customerRefNo);

		String response = null;
		try {
			response = m_Client.doGet(
					Constant.URL_TRACK_ORDER_BY_CUSTOMER_REF_NO, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrders status = m_GsonBuilder.create().fromJson(
					response, MethodResponseOrders.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrder getOrderByOrderNo(String orderNo)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER + "/" + orderNo, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrder status = m_GsonBuilder.create().fromJson(
					response, MethodResponseOrder.class);
			return status;
		}
		return null;
	}

	public MethodResponseQuote getComputedPrice(String lens_code,
			String coating_code) throws NetworkUnavailableException, Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("lens_code", lens_code);
		map.put("coating_code", coating_code);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_QUOTE, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseQuote status = m_GsonBuilder.create().fromJson(
					response, MethodResponseQuote.class);
			return status;
		}

		return null;
	}

	public MethodResponseOrders checkWarranty(String customerCode,
			Double sphLeftEye, Double cylLeftEye, Double sphRightEye,
			Double cylRightEye, Long orderDateFrom, Long orderDateTo)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("customerCode", customerCode);
		map.put("sphLeftEye", sphLeftEye);
		map.put("cylLeftEye", cylLeftEye);
		map.put("sphRightEye", sphRightEye);
		map.put("cylRightEye", cylRightEye);
		map.put("orderDateFrom", orderDateFrom);
		map.put("orderDateTo", orderDateTo);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CHECK_WARRANTY, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrders status = m_GsonBuilder.create().fromJson(
					response, MethodResponseOrders.class);
			return status;
		}
		return null;
	}

	public MethodResponseGeotagging geoTagging(String username,String customerCode, String mobileNo, String contactPerson,
    		String email, String pin, double latitude, double longitude)throws NetworkUnavailableException, Exception{
    	 
         map=new HashMap<String,Object>();
         map.put("authToken",accessToken);
         map.put("entered_by",username);
         map.put("latitude",latitude);
         map.put("longitude",longitude);
         map.put("mobile",mobileNo);
         map.put("contact_person",contactPerson);
         map.put("email",email);
         map.put("pin",pin);

         String response = null;
         try{
         	response = m_Client.doGet(Constant.URL_GEOTAGGING+"/"+customerCode+"/geolocation", map);
         }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		}
         if(response != null){
        	MethodResponseGeotagging status=m_GsonBuilder.create().fromJson(response, MethodResponseGeotagging.class);
         	return status;
         }
         return null;
     }

	public MethodResponseOk registerVisit(String username,String customerCode,int visit_purpose,
			double latitude, double longitude)throws NetworkUnavailableException, Exception{
    	 
         map=new HashMap<String,Object>();
         map.put("authToken",accessToken);
         map.put("entered_by",username);
         map.put("latitude",latitude);
         map.put("longitude",longitude);
         map.put("customerCode",customerCode);
         map.put("visit_purpose",visit_purpose);

         String response = null;
         try{
         	response = m_Client.doGet(Constant.URL_REGISTER_VISIT, map);
         }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		}
         if(response != null){
        	 MethodResponseOk status=m_GsonBuilder.create().fromJson(response, MethodResponseOk.class);
         	return status;
         }
         return null;
     }
	

	public MethodResponseGeotagging logVisit(String customerCode,
			double latitude, double longitude)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("latitude", latitude);
		map.put("longitude", longitude);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_LOG_VISIT + "/"
					+ customerCode + "/geotagvisit", map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseGeotagging status = m_GsonBuilder.create().fromJson(
					response, MethodResponseGeotagging.class);
			return status;
		}
		return null;
	}

	public MethodResponseVideo getVideos(double lastSyncTime)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_VIDEOS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseVideo status = m_GsonBuilder.create().fromJson(
					response, MethodResponseVideo.class);
			return status;
		}
		return null;
	}

	 public MethodResponseReceipt getCustomerReceipt(String url,CustomerReceipt customerReceipt)throws NetworkUnavailableException, Exception{
         
	    	customerReceipt.setAuthToken(accessToken);
	         String customerReceiptJson = m_GsonBuilder.create().toJson(customerReceipt);
	         
	         String response = null;
	         try{
	         	response = m_Client.doPost(url, customerReceiptJson);
	         }catch (NetworkUnavailableException e) {
	         	throw new NetworkUnavailableException(e.getMessage());
	 		 }catch (Exception e) {
	 			 throw new Exception(e.getMessage());
			}
	         if(response != null){
	        	MethodResponseReceipt status=m_GsonBuilder.create().fromJson(response, MethodResponseReceipt.class);
	         	return status;
	         }
	         return null;
	     }

	public MethodResponseCustomerAccountStatement getCustomerAccountStatement(
			String customerCode, int plant, Long fromDate, Long toDate)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("customerCode", customerCode);
		map.put("plant", plant);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CUSTOMER_ACCOUNT_STATEMENT,
					map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustomerAccountStatement status = m_GsonBuilder
					.create().fromJson(response,
							MethodResponseCustomerAccountStatement.class);
			return status;
		}
		return null;
	}

	public MethodResponseRegisterDevice registerDevice(String regId)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("reg_id", regId);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_REGISTER_DEVICE, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseRegisterDevice status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseRegisterDevice.class);
			return status;
		}
		return null;
	}

	public MethodResponseChangePassword changePassword(String userName,
			String oldPassword, String newPassword)
			throws NetworkUnavailableException, Exception {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("authToken", accessToken);
		jsonObject.addProperty("username", userName);
		jsonObject.addProperty("oldPassword", oldPassword);
		jsonObject.addProperty("newPassword", newPassword);

		String response = null;
		try {
			response = m_Client
					.doPost(Constant.URL_CHANGE_PASSWORD, jsonObject);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseChangePassword status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseChangePassword.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrderAndPowerDetail getOrderAndPowerDetail(
			String orderNumber) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER_AND_POWER_DETAIL + "/"
					+ orderNumber, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderAndPowerDetail status = m_GsonBuilder
					.create()
					.fromJson(response, MethodResponseOrderAndPowerDetail.class);
			return status;
		}
		return null;
	}

	public MethodResponseActivatePPLP activatePPLP(String customerCode,
			String userName) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("customerCode", customerCode);
		map.put("userName", userName);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ACTIVATE_PPLP, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseActivatePPLP status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseActivatePPLP.class);
			return status;
		}
		return null;
	}

	public MethodResponseCustomerBalance checkCustomerBalance(
			String customerCode) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("customerCode", customerCode);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CHECK_CUSTOMER_BALANCE, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustomerBalance status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseCustomerBalance.class);
			return status;
		}
		return null;
	}

	public MethodResponseCustomer getCustomerForGDO(int plant)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("plant", plant);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_GET_CUSTOMER_FOR_GDO, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustomer status = m_GsonBuilder.create().fromJson(
					response, MethodResponseCustomer.class);
			return status;
		}
		return null;
	}

	public MethodResponseCustomerBalance checkCustomerGDOBalance(
			String customerCode) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("customerCode", customerCode);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CHECK_CUSTOMER_GDO_BALANCE,
					map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustomerBalance status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseCustomerBalance.class);
			return status;
		}
		return null;
	}

	public MethodResponseCustomerDetailForVisit getCustomerDetailForVisit(String customerCode)throws NetworkUnavailableException, Exception{
        
    	map=new HashMap<String,Object>();
         map.put("authToken",accessToken);
         map.put("cust_code",customerCode);
        
         String response = null;
         try{
         	response = m_Client.doGet(Constant.URL_CUSTOMER_DETAIL_FOR_VISIT, map);
         }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		}
         if(response != null){
        	MethodResponseCustomerDetailForVisit status=m_GsonBuilder.create().fromJson(response, MethodResponseCustomerDetailForVisit.class);
         	return status;
         }
         return null;
     }


	public MethodResponseOrderStockLens orderStockLens(int plant,
			String customerCode, String custRefNo, String lensCode,
			String coatingCode, float sphRe, float cylRe, float sphLe,
			float cylLe, String rGrp, String lGrp, int rQty, int lQty,
			String username) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("cust_code", customerCode);
		map.put("lens_code", lensCode);
		map.put("coating_code", coatingCode);
		map.put("r_sph", sphRe);
		map.put("r_cyl", cylRe);
		map.put("l_sph", sphLe);
		map.put("l_cyl", cylLe);
		map.put("r_grp", rGrp);
		map.put("l_grp", lGrp);
		map.put("r_qty", rQty);
		map.put("l_qty", lQty);
		map.put("cust_refno", custRefNo);
		map.put("username", username);
		map.put("r_plant", plant);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER_STOCK_LENS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderStockLens status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseOrderStockLens.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrderStockLens orderRxLens(int plant,
			String customerCode, String lensCode, String coatingCode,
			String custRefNo, float sphRe, float cylRe, float sphLe,
			float cylLe, int axisRe, int axisLe, float addRe, float addLe,
			int rQty, int lQty, String fittingCode, int dia, String username)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("r_plant", plant);
		map.put("cust_code", customerCode);
		map.put("lens_code", lensCode);
		map.put("coating_code", coatingCode);
		map.put("r_sph", sphRe);
		map.put("r_cyl", cylRe);
		map.put("l_sph", sphLe);
		map.put("l_cyl", cylLe);
		map.put("r_axis", axisRe);
		map.put("l_axis", axisLe);
		map.put("r_add", addRe);
		map.put("l_add", addLe);
		map.put("r_qty", rQty);
		map.put("l_qty", lQty);
		map.put("fitting_code", fittingCode);
		map.put("dia", dia);
		map.put("cust_refno", custRefNo);
		map.put("username", username);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER_RX_LENS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderStockLens status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseOrderStockLens.class);
			return status;
		}
		return null;
	}
	
	public MethodResponseOrderStockLens orderOfferedRxLens(int id,int plant,
			String customerCode, String lensCode, String coatingCode,
			String custRefNo, float sphRe, float cylRe, float sphLe,
			float cylLe, int axisRe, int axisLe, float addRe, float addLe,
			int rQty, int lQty, String fittingCode, int dia, String username)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("id", id);
		map.put("r_plant", plant);
		map.put("cust_code", customerCode);
		map.put("lens_code", lensCode);
		map.put("coating_code", coatingCode);
		map.put("r_sph", sphRe);
		map.put("r_cyl", cylRe);
		map.put("l_sph", sphLe);
		map.put("l_cyl", cylLe);
		map.put("r_axis", axisRe);
		map.put("l_axis", axisLe);
		map.put("r_add", addRe);
		map.put("l_add", addLe);
		map.put("r_qty", rQty);
		map.put("l_qty", lQty);
		map.put("fitting_code", fittingCode);
		map.put("dia", dia);
		map.put("cust_refno", custRefNo);
		map.put("username", username);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_ORDER_OFFERED_RX_LENS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderStockLens status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseOrderStockLens.class);
			return status;
		}
		return null;
	}

	public MethodResponseAllOffer getAllOffer(String accessToken,
			String userName) throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("userName", userName);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_OFFERS, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseAllOffer status = m_GsonBuilder.create().fromJson(
					response, MethodResponseAllOffer.class);
			return status;
		}
		return null;
	}

	public MethodResponseLensDesign getLensDesign(double lensDesignLastSyncTime)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", lensDesignLastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_LENS_DESIGN, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseLensDesign status = m_GsonBuilder.create().fromJson(
					response, MethodResponseLensDesign.class);
			return status;
		}
		return null;
	}
	public MethodResponseCustRepVisit getCustRepVisit(double custRepVisitLastSyncTime)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("sys_time", custRepVisitLastSyncTime);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_CUST_REP_VISIT, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseCustRepVisit status = m_GsonBuilder.create().fromJson(
					response, MethodResponseCustRepVisit.class);
			return status;
		}
		return null;
	}

	public MethodResponseOrderStockLens orderOfferedStockLens(long id,
			String custRefNo, float sphRe, float cylRe, float sphLe,
			float cylLe, int rQty, int lQty, String username)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("id", id);
		map.put("r_sph", sphRe);
		map.put("r_cyl", cylRe);
		map.put("l_sph", sphLe);
		map.put("l_cyl", cylLe);
		map.put("r_qty", rQty);
		map.put("l_qty", lQty);
		map.put("cust_refno", custRefNo);
		map.put("username", username);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_OFFERED_ORDER_STOCK_LENS,
					map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseOrderStockLens status = m_GsonBuilder.create()
					.fromJson(response, MethodResponseOrderStockLens.class);
			return status;
		}
		return null;
	}
	
	public MethodResponseLastPayment getCustomerLastPayment(String customerCode)throws NetworkUnavailableException, Exception{
        
    	map=new HashMap<String,Object>();
         map.put("authToken",accessToken);
         map.put("cust_code",customerCode);
        
         String response = null;
         try{
         	response = m_Client.doGet(Constant.URL_CUSTOMER_DUES, map);
         }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		}
         if(response != null){
        	MethodResponseLastPayment status=m_GsonBuilder.create().fromJson(response, MethodResponseLastPayment.class);
         	return status;
         }
         return null;
     }

	public MethodResponseSoldBy getSoldBy(String accessToken, int plant)
			throws NetworkUnavailableException, Exception {

		map = new HashMap<String, Object>();
		map.put("authToken", accessToken);
		map.put("plant", plant);

		String response = null;
		try {
			response = m_Client.doGet(Constant.URL_SOLD_BY, map);
		} catch (NetworkUnavailableException e) {
			throw new NetworkUnavailableException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (response != null) {
			MethodResponseSoldBy status = m_GsonBuilder.create().fromJson(
					response, MethodResponseSoldBy.class);
			return status;
		}
		return null;
	}
	
	public MethodResponseCalBaseCurve getCalculatedBaseCurve(float sph, float cyl, float index, int type)throws NetworkUnavailableException, Exception{
        
   	 map=new HashMap<String,Object>();
        map.put("authToken",accessToken);
        map.put("sph",sph);
        map.put("cyl",cyl);
        map.put("index",index);
        map.put("type",type);
       
        String response = null;
        try{
        	response = m_Client.doGet(Constant.URL_CAL_BASE_CURVE, map);
        }catch (NetworkUnavailableException e) {
        	throw new NetworkUnavailableException(e.getMessage());
		 }catch (Exception e) {
			 throw new Exception(e.getMessage());
		 }
        if(response != null){
       	 MethodResponseCalBaseCurve status=m_GsonBuilder.create().fromJson(response, MethodResponseCalBaseCurve.class);
        	return status;
        }
        return null;
    }

	public MethodResponseUserPlantsRights getUserPlantsRights(String username,double lastSyncTime)throws NetworkUnavailableException,Exception{
   	 
   	 //HashMap<String,Object>map=new HashMap<String,Object>();
   	 map = new HashMap<String, Object>();
        map.put("authToken",accessToken);
        map.put("userName",userName);
        map.put("sys_time",lastSyncTime);
        
   	 String response = null;
   	 try{
   		 response = m_Client.doGet(Constant.URL_USER_PLANTS_RIGHTS, map);
   	 }catch (NetworkUnavailableException e) {
         	throw new NetworkUnavailableException(e.getMessage());
 		 }catch (Exception e) {
 			 throw new Exception(e.getMessage());
		 }
   	 if(response != null){
   		 MethodResponseUserPlantsRights status=m_GsonBuilder.create().fromJson(response, MethodResponseUserPlantsRights.class);
         	return status;
         }
   	 return null;
    }

	/*
	 * public City[]getCities(String stateId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("stateId",stateId); String
	 * response=m_Client.doPost("cities",map); City[]
	 * cities=m_GsonBuilder.create().fromJson(response, City[].class); return
	 * cities; } public Country[] getCountries(){ String
	 * response=m_Client.doPost("countries"); return
	 * m_GsonBuilder.create().fromJson(response,Country[].class); } public
	 * State[] getStates(String countryId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("countryId",countryId); String
	 * response=m_Client.doPost("states",map); State[]
	 * states=m_GsonBuilder.create().fromJson(response, State[].class); return
	 * states; } public RegisterUserMethodResult registerUser(ApplicationUser
	 * user){
	 * 
	 * String usrJson=m_GsonBuilder.create().toJson(user); String
	 * response=m_Client.doPost("registerUser",usrJson); Log.i(tag, response);
	 * 
	 * return
	 * m_GsonBuilder.create().fromJson(response,RegisterUserMethodResult.class);
	 * } public UnRegisterUserMethodResult unRegisterUser(String
	 * applicationUserId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("applicationUserId",applicationUserId);
	 * String response=m_Client.doPost("unRegisterUser", map);
	 * UnRegisterUserMethodResult
	 * result=m_GsonBuilder.create().fromJson(response,
	 * UnRegisterUserMethodResult.class); return result; } public Category[]
	 * getRootCategories(String companyId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("companyId", companyId); String
	 * result=m_Client.doPost("rootCategories", map);
	 * Category[]categories=m_GsonBuilder.create().fromJson(result,
	 * Category[].class); return categories; } public Category[]
	 * getChildCategories(String categoryId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("parentCategoryId", categoryId); String
	 * result=m_Client.doPost("childCategories", map);
	 * Category[]categories=m_GsonBuilder.create().fromJson(result,
	 * Category[].class); return categories; } public Product[]
	 * getProducts(String categoryId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("categoryId", categoryId); String
	 * result=m_Client.doPost("products", map);
	 * Product[]products=m_GsonBuilder.create().fromJson(result,
	 * Product[].class); return products; } public CreateOrderMethodResult
	 * createOrder(String applicationUserId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("applicationUserId",
	 * applicationUserId); String response=m_Client.doPost("createOrder", map);
	 * Log.i(tag,response); return
	 * m_GsonBuilder.create().fromJson(response,CreateOrderMethodResult.class);
	 * } public AddItemToCartMethodResult addItemToCart(String orderId,String
	 * productId,int quantity){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("orderId", orderId);
	 * map.put("productId", productId); map.put("quantity",quantity); String
	 * response=m_Client.doPost("addItemToCart", map); Log.i(tag, response);
	 * return m_GsonBuilder.create().fromJson(response,
	 * AddItemToCartMethodResult.class); } public RemoveItemFromCartMethodResult
	 * removeItemFromCart(String orderId,String productId){
	 * HashMap<String,Object>map=new HashMap<String,Object>();
	 * map.put("orderId", orderId); map.put("productId", productId); return
	 * m_GsonBuilder.create().fromJson(m_Client.doPost("removeItemFromCart",
	 * map), RemoveItemFromCartMethodResult.class); } public
	 * CancelOrderMethodResult cancelOrder(String orderId){
	 * HashMap<String,Object>map=new HashMap<String,Object>();
	 * map.put("orderId", orderId); return
	 * m_GsonBuilder.create().fromJson(m_Client.doPost("cancelOrder", map),
	 * CancelOrderMethodResult.class); } public GetOrderMethodResult
	 * getOrder(String orderId){ HashMap<String,Object>map=new
	 * HashMap<String,Object>(); map.put("orderId", orderId); String
	 * response=m_Client.doPost("getOrder", map); System.out.println(response);
	 * return m_GsonBuilder.create().fromJson(response,
	 * GetOrderMethodResult.class); }
	 */
}
