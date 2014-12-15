package com.gkbhitech.drishti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.FtpFileDownloader;
import com.gkbhitech.drishti.common.MyToast;
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
import com.gkbhitech.drishti.model.LensDesign;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.RegisterDevice;
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
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensDesign;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLogin;
import com.gkbhitech.drishti.services.methodresponse.MethodResponsePlant;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseRegisterDevice;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseService;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseStatus;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseUserPlantsRights;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseVideo;
import com.google.android.gcm.GCMRegistrar;

public class LoginAysncTask extends AsyncTask<Void, String, Integer>{

	private static final String tag = "LoginAysncTask";
	
	private String errorMessage = "error";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private DataBaseAdapter dataBaseAdapter;
	private User user;
	private ProgressDialog progressDialog;
	private static final String LOGGING = "Logging In...";
	private static final String LOADING_LENS = "Loading Lens...";
	private static final String LOADING_LENSCOAT = "Loading Lens Coats...";
	private static final String LOADING_COATING = "Loading Coating...";
	private static final String LOADING_PRODUCT_BRAND = "Loading Product Brand...";
	private static final String LOADING_STATUS = "Loading Status...";
	private static final String LOADING_SERVICE = "Loading Service...";
	private static final String LOADING_PLANT = "Loading Plants...";
	private static final String LOADING_CUSTOMER = "Loading Customer...";
	private static final String LOADING_INHOUSE_BANK = "Loading Inhouse Bank...";
	private static final String LOADING_VIDEOS = "Loading videos...";
	private static final String LOADING_LENS_DESIGN = "Loading lens design...";
	private static final String LOADING_USER_PLANTS = "Loading user plants...";
	private static final String LOADING_REGISTER_DEVICE = "Register device for notification...";
	private boolean isSDPresent = false;
	
	public LoginAysncTask(Context context,
			DrishtiApplication mApp, DataBaseAdapter dataBaseAdapter, User user){
		
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.dataBaseAdapter = dataBaseAdapter;
		this.user = user;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage(LOGGING);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		try{
			
			if(Constant.log) Log.i(tag, tag+" start");
			
			if(!mApp.getIsUserLogedIn()){
				
				MethodResponseLogin methodResponseLogin = webServiceObjectClient.loginUser(user);
				if(methodResponseLogin != null){
					
					if(Constant.log) Log.i(tag, "get login respone");
					
					String accessToken = methodResponseLogin.getData().getAuth_token();
					String userName = methodResponseLogin.getData().getUsername();
					int userType = methodResponseLogin.getData().getUser_type();
					user.setR_plant(methodResponseLogin.getData().getR_plant());
					user.setCustomer_no(methodResponseLogin.getData().getCustomer_no());
					user.setFull_name(methodResponseLogin.getData().getFull_name());
					
					//userType = 0;
					if(userType == 0){
						throw new Exception("You are not mobile user.");
					}
					
					if(Constant.log) Log.i(tag, "UserType : "+ userType);
					
					mApp.setUserType(userType);
					if(accessToken != null && userName != null){
						mApp.setAccessToken(accessToken);
						webServiceObjectClient.setAccessToken(accessToken);
						webServiceObjectClient.setUserName(userName);
						mApp.setUserName(userName);
						mApp.setIsUserLogedIn(true);
						dataBaseAdapter.insertUser(user);
						methodResponseLogin = null;
					}
				}

				publishProgress(LOADING_CUSTOMER);
				Log.i(tag, "customer last sync time : "+user.getCustomerLastSyncTime());
				MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(), user.getCustomerLastSyncTime(),0);
				if(methodResponseCustomer != null){
					if(methodResponseCustomer.getResponseCode() == 0){
						Customer[] customers = methodResponseCustomer.getDataArray();
						if(customers != null){
							dataBaseAdapter.insertCustomer(customers);
						}
						user.setCustomerLastSyncTime(methodResponseCustomer.getSys_time());
						//Log.i(tag, "customer last sync time : "+methodResponseCustomer.getSys_time());
						dataBaseAdapter.insertUser(user);
						mApp.setLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME, methodResponseCustomer.getSys_time());
						//Log.i(tag, "customer last sync time : "+mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME));
						methodResponseCustomer = null;
						customers = null;
					}else{
						errorMessage = methodResponseCustomer.getResponseMessage();
						return methodResponseCustomer.getResponseCode();
					}
				}
				
				publishProgress(LOADING_USER_PLANTS);
				MethodResponseUserPlantsRights methodResponseUserPlantsRights = 
						webServiceObjectClient.getUserPlantsRights(mApp.getUserName(), user.getUserPlantsRightsLastSyncTime());
				if(methodResponseUserPlantsRights != null){
					if(methodResponseUserPlantsRights.getResponseCode() == 0){
						UserPlantsRights[] userPlantsRights = methodResponseUserPlantsRights.getDataArray();
						if(userPlantsRights != null){
							dataBaseAdapter.insertUserPlantsRights(userPlantsRights);
						}
						user.setUserPlantsRightsLastSyncTime(methodResponseUserPlantsRights.getSys_time());
						dataBaseAdapter.insertUser(user);
						mApp.setLastSyncTime(Constant.USER_PLANTS_RIGHTS_LAST_SYNC_TIME, methodResponseUserPlantsRights.getSys_time());
					}
				}
				
				publishProgress(LOADING_LENS);
				MethodResponseLens methodResponseLens = webServiceObjectClient.getLens(mApp.getLastSyncTime(Constant.LENS_LAST_SYNC_TIME));
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

				publishProgress(LOADING_LENSCOAT);
				MethodResponseLensCoats methodResponseLensCoats = webServiceObjectClient.getLensCoats(mApp.getLastSyncTime(Constant.LENS_COATS_LAST_SYNC_TIME));
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
				
				publishProgress(LOADING_COATING);
				MethodResponseCoating methodResponseCoating = webServiceObjectClient.getCoating(mApp.getLastSyncTime(Constant.COATING_LAST_SYNC_TIME));
				if(methodResponseCoating != null){
					if(methodResponseCoating.getResponseCode() == 0){
						Coating[] coatings = methodResponseCoating.getDataArray();
						if(coatings != null){
							dataBaseAdapter.insertCoating(coatings);
						}
						mApp.setLastSyncTime(Constant.COATING_LAST_SYNC_TIME, methodResponseCoating.getSys_time());
						methodResponseCoating = null;
						coatings = null;
					}else{
						errorMessage = methodResponseCoating.getResponseMessage();
						return methodResponseCoating.getResponseCode();
					}
				}
				
				publishProgress(LOADING_PRODUCT_BRAND);
				MethodResponseProductBrand methodResponseProductBrand = webServiceObjectClient.getProductBrand(mApp.getLastSyncTime(Constant.PRODUCTBRAND_LAST_SYNC_TIME));
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
				
				publishProgress(LOADING_STATUS);
				MethodResponseStatus methodResponseStatus = webServiceObjectClient.getStatus(mApp.getLastSyncTime(Constant.STATUS_LAST_SYNC_TIME));
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
				
				publishProgress(LOADING_SERVICE);
				MethodResponseService methodResponseService = webServiceObjectClient.getService(mApp.getLastSyncTime(Constant.SERVICE_LAST_SYNC_TIME));
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
				
				publishProgress(LOADING_PLANT);
				MethodResponsePlant methodResponsePlant = webServiceObjectClient.getPlant(mApp.getLastSyncTime(Constant.PLANT_LAST_SYNC_TIME));
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
				/*publishProgress(LOADING_CUSTOMER);
				Log.i(tag, "customer last sync time : "+mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME));
				MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(), mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME));
				if(methodResponseCustomer != null){
					if(methodResponseCustomer.getResponseCode() == 0){
						Customer[] customers = methodResponseCustomer.getDataArray();
						if(customers != null){
							dataBaseAdapter.insertCustomer(customers);
						}
						Log.i(tag, "customer last sync time : "+methodResponseCustomer.getSys_time());
						mApp.setLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME, methodResponseCustomer.getSys_time());
						Log.i(tag, "customer last sync time : "+mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME));
						methodResponseCustomer = null;
						customers = null;
					}else{
						errorMessage = methodResponseCustomer.getResponseMessage();
						return methodResponseCustomer.getResponseCode();
					}
				}*/
				publishProgress(LOADING_INHOUSE_BANK);
				MethodResponseInhouseBank methodResponseInhouseBank = webServiceObjectClient.getInhouseBank(mApp.getUserName(), mApp.getLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME));
				if(methodResponseInhouseBank != null){
					if(methodResponseInhouseBank.getResponseCode() == 0){
						InhouseBank[] inhouseBanks= methodResponseInhouseBank.getDataArray();
						if(inhouseBanks != null){
							dataBaseAdapter.insertInhouseBank(inhouseBanks);
						}
						mApp.setLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME, methodResponseInhouseBank.getSys_time());
					}else{
						errorMessage = methodResponseInhouseBank.getResponseMessage();
						return methodResponseInhouseBank.getResponseCode();
					}
				}
				
				publishProgress(LOADING_LENS_DESIGN);
				MethodResponseLensDesign methodResponseLensDesign = webServiceObjectClient.getLensDesign(mApp.getLastSyncTime(Constant.LENS_DESIGN_LAST_SYNC_TIME));
				if(methodResponseLensDesign != null){
					if(methodResponseLensDesign.getResponseCode() == 0){
						LensDesign[] lensDesigns = methodResponseLensDesign.getDataArray();
						if(lensDesigns != null){
							dataBaseAdapter.insertLensDesign(lensDesigns);
						}
						mApp.setLastSyncTime(Constant.LENS_DESIGN_LAST_SYNC_TIME, methodResponseLensDesign.getSys_time());
					}else{
						errorMessage = methodResponseLensDesign.getResponseMessage();
						return methodResponseLensDesign.getResponseCode();
					}
				}
				
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
				
				publishProgress(LOADING_VIDEOS);
				MethodResponseVideo methodResponseVideos = webServiceObjectClient.getVideos(mApp.getLastSyncTime(Constant.VIDEOS_LAST_SYNC_TIME));
				Video[] videos = null;
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
							temp = "";
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
				
				/*publishProgress(LOADING_REGISTER_DEVICE);
				
				Log.i(tag, "reg id : "+GCMRegistrar.getRegistrationId(context));
				MethodResponseRegisterDevice methodResponseRegisterDevice = webServiceObjectClient.registerDevice(GCMRegistrar.getRegistrationId(context));
				
				if(methodResponseRegisterDevice != null){
					if(methodResponseRegisterDevice.getResponseCode() == 0){
						RegisterDevice registerDevice = methodResponseRegisterDevice.getData();
					}else{
						errorMessage = methodResponseRegisterDevice.getResponseMessage();
						return methodResponseRegisterDevice.getResponseCode();
					}
				}*/
				
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
			return -2;
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
		
		if(Constant.log) Log.i(tag, "result Code: "+result);
		
		if(result != Constant.RESULT_SUCCESS){
			dataBaseAdapter.clearDatabase();
			mApp.clearCredentials();
		}
		
		switch (result) {
		
		case Constant.RESULT_SUCCESS:
			
			if(Constant.log) Log.i(tag, "success");
			
			RegisterDeviceAsynTask registerDeviceAsynTask = new RegisterDeviceAsynTask(context, webServiceObjectClient);
			registerDeviceAsynTask.execute();
			
			mApp.setIsSyncBlocked(true);
			Intent i;
			if(mApp.getUserType() == 1){
				mApp.setPlant(user.getR_plant());
				mApp.setCustomer(dataBaseAdapter.getCustomerByCustCode(user.getCustomer_no()));
				i = new Intent(context, HomeActivity.class);
			}else{
				i = new Intent(context, SelectPlantActivity.class);
			}
			if(!isSDPresent){
				i.putExtra("isSDPresent", isSDPresent);
			}
			context.startActivity(i);
			((Activity)context).finish();
			
			break;
		/*case Constant.RESULT_AUTHENTICATION_FAILURE:
			Log.i(tag, "authentication failure");
			Toast.makeText(context, "Authentication fail", Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_NETWORK_UNAVAILABLE:
			Log.i(tag, "network unavailable");
			Toast.makeText(context, "network unavailable", Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_INVALID_AUTH_TOKEN:
			Log.i(tag, "Invalid auth token");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_RECORD_NOT_FOUND:
			Log.i(tag, "Record not found");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_SERVER_DATABASE_ERROR:
			Log.i(tag, "Server database error");
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			break;*/
		default:
			MyToast.show(context, result, errorMessage);
			break;
		}
		progressDialog.dismiss();
	}
}
