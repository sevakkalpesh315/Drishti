package com.gkbhitech.drishti.common;

import android.content.Context;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;

public class SyncManager1 {

	private String errorMessage = "error";
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

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private WebServiceObjectClient webServiceObjectClient;
	
	public SyncManager1(DrishtiApplication mApp) {
		this.mApp = mApp;
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();
	}
	
	public Integer startSync(){/*
		try{
			
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
			
			MethodResponseNeedSync methodResponseNeedSync = webServiceObjectClient.getNeedSync(lensLastSyncTime, 
					lensCoatsLastSyncTime, coatingLastSyncTime, productBrandLastSyncTime, statusLastSyncTime,
					serviceLastSyncTime, plantLastSyncTime, customerLastSyncTime, inhouseBankLastSyncTime, mApp.getUserName());
			if(methodResponseNeedSync != null){
				if(methodResponseNeedSync.getResponseCode() == 0){
					NeedSync needSync = methodResponseNeedSync.getData();
					if(needSync != null){
						//dataBaseAdapter.insertPlant(plants);
						
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
						if(needSync.isCustomers()){
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
						}
						if(needSync.isInhouseBanks()){
							publishProgress(SYNC_INHOUSE_BANK);
							MethodResponseInhouseBank methodResponseInhouseBank = webServiceObjectClient.getInhouseBank(mApp.getUserName(), inhouseBankLastSyncTime);
							if(methodResponseInhouseBank != null){
								if(methodResponseInhouseBank.getResponseCode() == 0){
									InhouseBank[] inhouseBanks= methodResponseInhouseBank.getDataArray();
									
									if(inhouseBanks != null){
										for(int i =0; i<inhouseBanks.length; i++){
											inhouseBanks[i].setProfit_center(plant);
										}
										dataBaseAdapter.insertInhouseBank(inhouseBanks);
									}
								}else{
									errorMessage = methodResponseInhouseBank.getResponseMessage();
									return methodResponseInhouseBank.getResponseCode();
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
			Log.i(tag, "last catch :"+e.getMessage());
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (UnauthorizedException e) {
			Log.i(tag, "last catch :"+e.getMessage());
			return Constant.RESULT_AUTHENTICATION_FAILURE;
		}catch (Exception e) {
			e.printStackTrace();
			Log.i(tag, "Exception : "+e.getMessage());
			return 3;
		}
	*/
		return null;}
}
