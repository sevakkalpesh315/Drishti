package com.gkbhitech.drishti.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.CustRepVisit;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.InhouseBank;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.LensCoat;
import com.gkbhitech.drishti.model.NeedSync;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.Service;
import com.gkbhitech.drishti.model.User;
import com.gkbhitech.drishti.model.UserPlantsRights;
import com.gkbhitech.drishti.model.Video;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCoating;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustRepVisit;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseInhouseBank;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLens;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensCoats;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseNeedSync;
import com.gkbhitech.drishti.services.methodresponse.MethodResponsePlant;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseService;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseStatus;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseUserPlantsRights;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseVideo;

public class SyncManager extends AsyncTask<Void, String, Integer>{

	private static final String tag = "SyncAsyncTask";
	
	private String errorMessage = "error";
	
	private WebServiceObjectClient webServiceObjectClient;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private Context context;
	private static final String SYNC_LENS = "sync Lens...";
	private static final String SYNC_LENSCOAT = "sync Lens Coats...";
	private static final String SYNC_COATING = "sync Coating...";
	private static final String SYNC_PRODUCT_BRAND = "sync Product Brand...";
	private static final String SYNC_STATUS = "sync Status...";
	private static final String SYNC_SERVICE = "sync Service...";
	private static final String SYNC_PLANT = "sync Plants...";
	private static final String SYNC_CUSTOMER = "sync Customer...";
	private static final String SYNC_INHOUSE_BANK = "sync Inhouse bank...";
	private static final String SYNC_VIDEOS = "sync Videos...";
	
	//private double lastSyncTime;
	private ProgressDialog progressDialog;
	private OnSyncCompleted onSyncCompleted;
	
	private boolean isSDPresent = false;
	
	public SyncManager(Context context, DrishtiApplication mApp, OnSyncCompleted onSyncCompleted){
		this.context = context;
		this.mApp = mApp;
		this.dataBaseAdapter = mApp.getDataBaseAdapter();
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.onSyncCompleted = onSyncCompleted;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Sync ...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			
			if(Constant.log) Log.i(tag, tag+" start");
			//lastSyncTime = mApp.getLastSyncTime();
			
			double lensLastSyncTime = mApp.getLastSyncTime(Constant.LENS_LAST_SYNC_TIME);
			double lensCoatsLastSyncTime = mApp.getLastSyncTime(Constant.LENS_COATS_LAST_SYNC_TIME);
			double coatingLastSyncTime = mApp.getLastSyncTime(Constant.COATING_LAST_SYNC_TIME);
			double productBrandLastSyncTime = mApp.getLastSyncTime(Constant.PRODUCTBRAND_LAST_SYNC_TIME);
			double statusLastSyncTime = mApp.getLastSyncTime(Constant.STATUS_LAST_SYNC_TIME);
			double serviceLastSyncTime = mApp.getLastSyncTime(Constant.SERVICE_LAST_SYNC_TIME);
			double plantLastSyncTime = mApp.getLastSyncTime(Constant.PLANT_LAST_SYNC_TIME);
			double customerLastSyncTime = mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME);
			double inhouseBankLastSyncTime = mApp.getLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME);
			double videoLastSyncTime = mApp.getLastSyncTime(Constant.VIDEOS_LAST_SYNC_TIME);
			double userPlnatsRightsLastSyncTime = mApp.getLastSyncTime(Constant.USER_PLANTS_RIGHTS_LAST_SYNC_TIME);
			double custRepVisitLastSyncTime = mApp.getLastSyncTime(Constant.CUST_REP_VISIT_LAST_SYNC_TIME);
			
			MethodResponseNeedSync methodResponseNeedSync = webServiceObjectClient.getNeedSync(lensLastSyncTime, 
					lensCoatsLastSyncTime, coatingLastSyncTime, productBrandLastSyncTime, statusLastSyncTime,
					serviceLastSyncTime, plantLastSyncTime, customerLastSyncTime, inhouseBankLastSyncTime,
					videoLastSyncTime, userPlnatsRightsLastSyncTime,custRepVisitLastSyncTime, mApp.getUserName());
			if(methodResponseNeedSync != null){
				if(methodResponseNeedSync.getResponseCode() == 0){
					NeedSync needSync = methodResponseNeedSync.getData();
					if(needSync != null){
						//dataBaseAdapter.insertPlant(plants);
						
						if(needSync.isUserPlantsRights()){
							MethodResponseUserPlantsRights methodResponseUserPlantsRights = 
									webServiceObjectClient.getUserPlantsRights(mApp.getUserName(), userPlnatsRightsLastSyncTime);
							if(methodResponseUserPlantsRights != null){
								if(methodResponseUserPlantsRights.getResponseCode() == 0){
									UserPlantsRights[] userPlantsRights = methodResponseUserPlantsRights.getDataArray();
									if(userPlantsRights != null){
										dataBaseAdapter.insertUserPlantsRights(userPlantsRights);
									}
									User user = dataBaseAdapter.getUser(mApp.getUserName());
									user.setUserPlantsRightsLastSyncTime(methodResponseUserPlantsRights.getSys_time());
									dataBaseAdapter.insertUser(user);
									mApp.setLastSyncTime(Constant.USER_PLANTS_RIGHTS_LAST_SYNC_TIME, methodResponseUserPlantsRights.getSys_time());
								}
							}
						}
						
						if(mApp.getUserType() != 3){
							if(needSync.isCustomers()){
								publishProgress(SYNC_CUSTOMER);
								MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(),customerLastSyncTime,0);
								if(methodResponseCustomer != null){
									if(methodResponseCustomer.getResponseCode() == 0){
										Customer[] customers = methodResponseCustomer.getDataArray();
										if(customers != null){
											dataBaseAdapter.insertCustomer(customers);
										}
										User user = dataBaseAdapter.getUser(mApp.getUserName());
										user.setCustomerLastSyncTime(methodResponseCustomer.getSys_time());
										dataBaseAdapter.insertUser(user);
										mApp.setLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME, methodResponseCustomer.getSys_time());
									}else{
										errorMessage = methodResponseCustomer.getResponseMessage();
										return methodResponseCustomer.getResponseCode();
									}
								}
							}
						}
						if(needSync.isLenses()){
							publishProgress(SYNC_LENS);
							Log.i(tag, "lens sync time : "+lensLastSyncTime);
							MethodResponseLens methodResponseLens = webServiceObjectClient.getLens(lensLastSyncTime);
							if(methodResponseLens != null){
								if(methodResponseLens.getResponseCode() == 0){
									Lens[] lenses = methodResponseLens.getDataArray();
									if(lenses != null){
										dataBaseAdapter.insertLens(lenses);
									}
									mApp.setLastSyncTime(Constant.LENS_LAST_SYNC_TIME, methodResponseLens.getSys_time());
									methodResponseLens = null;
									lenses = null;
								}else{
									errorMessage = methodResponseLens.getResponseMessage();
									return methodResponseLens.getResponseCode();
								}
							}
						}
						if(needSync.isLensCoats()){
							publishProgress(SYNC_LENSCOAT);
							Log.i(tag, "lens coats sync time : "+lensCoatsLastSyncTime);
							MethodResponseLensCoats methodResponseLensCoats = webServiceObjectClient.getLensCoats(lensCoatsLastSyncTime);
							if(methodResponseLensCoats != null){
								if(methodResponseLensCoats.getResponseCode() == 0){
									LensCoat[] lensCoats = methodResponseLensCoats.getDataArray();
									if(lensCoats != null){
										dataBaseAdapter.insertLensCoat(lensCoats);
									}
									mApp.setLastSyncTime(Constant.LENS_COATS_LAST_SYNC_TIME, methodResponseLensCoats.getSys_time());
									methodResponseLensCoats = null;
									lensCoats = null;
								}else{
									errorMessage = methodResponseLensCoats.getResponseMessage();
									return methodResponseLensCoats.getResponseCode();
								}
							}
						}
						if(needSync.isCoating()){
							publishProgress(SYNC_COATING);
							Log.i(tag, "coating sync time : "+coatingLastSyncTime);
							MethodResponseCoating methodResponseCoating = webServiceObjectClient.getCoating(coatingLastSyncTime);
							if(methodResponseCoating != null){
								if(methodResponseCoating.getResponseCode() == 0){
									Coating[] coating = methodResponseCoating.getDataArray();
									if(coating != null){
										dataBaseAdapter.insertCoating(coating);
									}
									mApp.setLastSyncTime(Constant.COATING_LAST_SYNC_TIME, methodResponseCoating.getSys_time());
									methodResponseCoating = null;
									coating = null;
								}else{
									errorMessage = methodResponseCoating.getResponseMessage();
									return methodResponseCoating.getResponseCode();
								}
							}
						}
						if(needSync.isProductBrands()){
							publishProgress(SYNC_PRODUCT_BRAND);
							Log.i(tag, "product brand sync time : "+productBrandLastSyncTime);
							MethodResponseProductBrand methodResponseProductBrand = webServiceObjectClient.getProductBrand(productBrandLastSyncTime);
							if(methodResponseProductBrand != null){
								if(methodResponseProductBrand.getResponseCode() == 0){
									ProductBrand[] productBrands = methodResponseProductBrand.getDataArray();
									if(productBrands != null){
										dataBaseAdapter.insertProductBrand(productBrands);
									}
									mApp.setLastSyncTime(Constant.PRODUCTBRAND_LAST_SYNC_TIME, methodResponseProductBrand.getSys_time());
								}else{
									errorMessage = methodResponseProductBrand.getResponseMessage();
									return methodResponseProductBrand.getResponseCode();
								}
							}
						}
						if(needSync.isStatusCodes()){
							publishProgress(SYNC_STATUS);
							Log.i(tag, "service sync time : "+statusLastSyncTime);
							MethodResponseStatus methodResponseStatus = webServiceObjectClient.getStatus(statusLastSyncTime);
							if(methodResponseStatus != null){
								if(methodResponseStatus.getResponseCode() == 0){
									com.gkbhitech.drishti.model.Status[] status = methodResponseStatus.getDataArray();
									if(status != null){
										dataBaseAdapter.insertStatus(status);
									}
									mApp.setLastSyncTime(Constant.STATUS_LAST_SYNC_TIME, methodResponseStatus.getSys_time());
								}else{
									errorMessage = methodResponseStatus.getResponseMessage();
									return methodResponseStatus.getResponseCode();
								}
							}
						}
						if(needSync.isServices()){
							publishProgress(SYNC_SERVICE);
							Log.i(tag, "service sync time : "+serviceLastSyncTime);
							MethodResponseService methodResponseService = webServiceObjectClient.getService(serviceLastSyncTime);
							if(methodResponseService != null){
								if(methodResponseService.getResponseCode() == 0){
									Service[] service = methodResponseService.getDataArray();
									if(service != null){
										dataBaseAdapter.insertService(service);
									}
									mApp.setLastSyncTime(Constant.SERVICE_LAST_SYNC_TIME, methodResponseService.getSys_time());
								}else{
									errorMessage = methodResponseService.getResponseMessage();
									return methodResponseService.getResponseCode();
								}
							}
						}
						if(needSync.isPlants()){
							publishProgress(SYNC_PLANT);
							Log.i(tag, "plant sync time : "+plantLastSyncTime);
							MethodResponsePlant methodResponsePlant = webServiceObjectClient.getPlant(plantLastSyncTime);
							if(methodResponsePlant != null){
								if(methodResponsePlant.getResponseCode() == 0){
									Plant[] plants = methodResponsePlant.getDataArray();
									if(plants != null){
										dataBaseAdapter.insertPlant(plants);
									}
									mApp.setLastSyncTime(Constant.PLANT_LAST_SYNC_TIME, methodResponsePlant.getSys_time());
								}else{
									errorMessage = methodResponsePlant.getResponseMessage();
									return methodResponsePlant.getResponseCode();
								}
							}
						}
						if(needSync.isCustRepVisit()){
							MethodResponseCustRepVisit methodResponseCustRepVisit = webServiceObjectClient.getCustRepVisit(mApp.getLastSyncTime(Constant.CUST_REP_VISIT_LAST_SYNC_TIME));
							if(methodResponseCustRepVisit != null){
								if(methodResponseCustRepVisit.getResponseCode() == 0){
									CustRepVisit[] custRepVisits = methodResponseCustRepVisit.getDataArray();
									if(custRepVisits != null){
										dataBaseAdapter.insertCustRepVisit(custRepVisits);
									}
									mApp.setLastSyncTime(Constant.CUST_REP_VISIT_LAST_SYNC_TIME, methodResponseCustRepVisit.getSys_time());
								}else{
									errorMessage = methodResponseCustRepVisit.getResponseMessage();
									return methodResponseCustRepVisit.getResponseCode();
								}
							}
						}
						
						/*if(needSync.isCustomers()){
							publishProgress(SYNC_CUSTOMER);
							MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(),customerLastSyncTime);
							if(methodResponseCustomer != null){
								if(methodResponseCustomer.getResponseCode() == 0){
									Customer[] customers = methodResponseCustomer.getDataArray();
									if(customers != null){
										dataBaseAdapter.insertCustomer(customers);
									}
									mApp.setLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME, methodResponseCustomer.getSys_time());
								}else{
									errorMessage = methodResponseCustomer.getResponseMessage();
									return methodResponseCustomer.getResponseCode();
								}
							}
						}*/
						if(needSync.isInhouseBanks()){
							publishProgress(SYNC_INHOUSE_BANK);
							MethodResponseInhouseBank methodResponseInhouseBank = webServiceObjectClient.getInhouseBank(mApp.getUserName(), inhouseBankLastSyncTime);
							if(methodResponseInhouseBank != null){
								if(methodResponseInhouseBank.getResponseCode() == 0){
									InhouseBank[] inhouseBanks= methodResponseInhouseBank.getDataArray();
									
									if(inhouseBanks != null){
										/*for(int i =0; i<inhouseBanks.length; i++){
											inhouseBanks[i].setProfit_center(plant);
										}*/
										dataBaseAdapter.insertInhouseBank(inhouseBanks);
									}
									mApp.setLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME, methodResponseInhouseBank.getSys_time());
								}else{
									errorMessage = methodResponseInhouseBank.getResponseMessage();
									return methodResponseInhouseBank.getResponseCode();
								}
							}
						}
						
						if(needSync.isMobileVideo()){
							Video[] videos = null;
							publishProgress(SYNC_VIDEOS);
							MethodResponseVideo methodResponseVideos = webServiceObjectClient.getVideos(mApp.getLastSyncTime(Constant.VIDEOS_LAST_SYNC_TIME));
							if(methodResponseVideos != null){
								if(methodResponseVideos.getResponseCode() == 0){
									videos = methodResponseVideos.getDataArray();
									
									if(videos != null){
										dataBaseAdapter.insertVideos(videos);
									}
									mApp.setLastSyncTime(Constant.VIDEOS_LAST_SYNC_TIME, methodResponseVideos.getSys_time());
								}else{
									errorMessage = methodResponseVideos.getResponseMessage();
									return methodResponseVideos.getResponseCode();
								}
							}
							isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
							
							if(isSDPresent){
								if(videos != null && videos.length > 0){
									for(Video video : videos){
										//Log.i(tag, "Url : "+video.getUrl());
										String temp = video.getUrl().split("//")[1];
										String[] temp1 = temp.split("/");
										temp = "/";
										for(int i = 0; i < temp1.length; i++){
											if(i != 0){
												temp += "/";
												temp += temp1[i]; 
											}
										}
										FtpFileDownloader.DownloadFile(temp1[0], temp, temp1[temp1.length-1]);
									}
								}
							}
						}
						
					}
				}else{
					errorMessage = methodResponseNeedSync.getResponseMessage();
					return methodResponseNeedSync.getResponseCode();
				}
			}
			
			return Constant.RESULT_SUCCESS;
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (UnauthorizedException e) {
			e.printStackTrace();
			return Constant.RESULT_AUTHENTICATION_FAILURE;
		}catch (Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
			return -1;
		}
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressDialog.setMessage(values[0]);
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(Constant.log) Log.i(tag, "Result code : "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			if(Constant.log) Log.i(tag, "success");
			Toast.makeText(context, "Sync completed", Toast.LENGTH_SHORT).show();
			//mApp.setLastSyncTime(lastSyncTime);
			break;
		/*case Constant.RESULT_AUTHENTICATION_FAILURE:
			Log.i(tag, "authentication failure");
			Toast.makeText(context, "Authentication fail", Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_NETWORK_UNAVAILABLE:
			if(Constant.log) Log.i(tag, "network unavailable");
			Toast.makeText(context, "network unavailable", Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_INVALID_AUTH_TOKEN:
			if(Constant.log) Log.i(tag, "Invalid auth token");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_RECORD_NOT_FOUND:
			if(Constant.log) Log.i(tag, "Record not found");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_SERVER_DATABASE_ERROR:
			if(Constant.log) Log.i(tag, "Server database error");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;*/
		default:
			MyToast.show(context, result, errorMessage);
			break;
		}
		
		//fillPlants();
		if(onSyncCompleted != null){
			onSyncCompleted.onSyncCompleted();
		}
		
		progressDialog.dismiss();
	}
}
