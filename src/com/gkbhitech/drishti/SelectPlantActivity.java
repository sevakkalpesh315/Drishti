package com.gkbhitech.drishti;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.common.OnSyncCompleted;
import com.gkbhitech.drishti.common.SyncManager;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.miscellaneous.Feedback;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomer;
import com.google.android.gcm.GCMRegistrar;

public class SelectPlantActivity extends Activity{
	Button btn;

	private static final String tag = "SelectPlantActivity";
	
	// .............. variable used in UI ................
	private Spinner spnPlant;
	
	private WebServiceObjectClient webServiceObjectClient;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private List<Plant> plants = new ArrayList<Plant>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_select_plant);

		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		
		
/*Button btn = (Button) findViewById(R.id.button1);
		

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					webServiceObjectClient.registerDevice(GCMRegistrar.getRegistrationId(SelectPlantActivity.this));
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});*/
		
		spnPlant = (Spinner) findViewById(R.id.spn_plant);
		
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		if(bundle != null){
			boolean isSDPresent = bundle.getBoolean("isSDPresent");
			if(!isSDPresent){
				MyToast.show(getApplicationContext(), Constant.RESULT_SD_CARD_NOT_EXIST, null);
			}
		}
		
		OnSyncCompleted onSyncCompleted = new OnSyncCompleted() {
			
			@Override
			public void onSyncCompleted() {
				// TODO Auto-generated method stub
				
				if(Constant.log) Log.i(tag, "on sync complete callback called");
				
				//plants = dataBaseAdapter.getPlants();
				
				fillPlants();
				
				/*if(plants != null && plants.size() > 0){
					
					ArrayAdapter<String> arrayAdapterPlantDesc = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_plant_item_simple);
					
					arrayAdapterPlantDesc.add("Select plant");
					for(Plant plant : plants){ 
						arrayAdapterPlantDesc.add(plant.getPlant_desc());
					}
					
					spnPlant.setAdapter(arrayAdapterPlantDesc);
					spnPlant.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
							// TODO Auto-generated method stub
							if(position != 0){
								Plant plant = plants.get(position-1);
								int rPlant = plant.getR_plant();
								mApp.setPlant(rPlant);
								
								int noOfCustomer = dataBaseAdapter.getNoOfCustomer(rPlant);
								
								if(Constant.log) Log.i(tag, "no of customer fatch from local database: "+noOfCustomer);
								
								if(noOfCustomer != 0){
									Intent i = new Intent(getApplicationContext(), HomeActivity.class);
									startActivity(i);
								}
								spnPlant.setSelection(0);
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
				}*/
			}
		};
		
		if(!mApp.getIsSyncBlocked()){
			SyncManager syncManager = new SyncManager(SelectPlantActivity.this,mApp,onSyncCompleted);
			syncManager.execute();
		}else{
			mApp.setIsSyncBlocked(false);
			fillPlants();
		}
		
	}
	
	/*@Override
    public void onBackPressed() {
		finish();
    }*/
	
	@Override
	public void onBackPressed() {
		// do something on back.

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("EXIT");
		adb.setMessage("Are you sure?");
		adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'Yes' Button
				SelectPlantActivity.this.finish();
			}
		});
		adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'No' Button
				dialog.cancel();
			}
		});
		adb.setIcon(R.drawable.exit0);
		adb.show();

		return;
	}
	
	public void registerReceiver(){
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("com.package.ACTION_LOGOUT");
	    getApplicationContext().registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				finish();
			}
		}, intentFilter);
	}
	
	
	
	/*private class SyncAsyncTask extends AsyncTask<Void, String, Integer>{

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
		//private double lastSyncTime;
		private ProgressDialog progressDialog;
		
		public SyncAsyncTask(Context context){
			this.context = context;
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
										mApp.setLastSyncTime(Constant.INHOUSEBANK_LAST_SYNC_TIME, methodResponseInhouseBank.getSys_time());
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
			
			switch (result) {
			case Constant.RESULT_SUCCESS:
				Log.i(tag, "success");
				//mApp.setLastSyncTime(lastSyncTime);
				break;
			case Constant.RESULT_AUTHENTICATION_FAILURE:
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
				break;
			default:
				break;
			}
			
			fillPlants();
			
			progressDialog.dismiss();
		}
	}*/

	private void fillPlants(){
		
		if(mApp.getUserType() == 3){
			
			int plantsCount = dataBaseAdapter.getPlantsCount(mApp.getUserName());
			if(plantsCount == 0){
				plants = dataBaseAdapter.getAllPlants();
			}else{
				plants = dataBaseAdapter.getPlants(mApp.getUserName());
			}
		}else{
			plants = dataBaseAdapter.getPlants(mApp.getUserName());
		}
		
		if(plants != null && plants.size() > 0){
			
			ArrayAdapter<String> arrayAdapterPlantDesc = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_plant_item_simple);
			
			arrayAdapterPlantDesc.add("Select Plant");
			for(Plant plant : plants){ 
				arrayAdapterPlantDesc.add(plant.getPlant_desc());
			}
			
			spnPlant.setAdapter(arrayAdapterPlantDesc);
			
			
			spnPlant.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						Plant plant = plants.get(position-1);
						int rPlant = plant.getR_plant();
						mApp.setPlant(rPlant);
						
						int noOfCustomer = dataBaseAdapter.getNoOfCustomer(rPlant);
						
						if(Constant.log) Log.i(tag, "no of customer fatch from local database: "+noOfCustomer);
						
						if(mApp.getUserType() == 2){
							if(noOfCustomer != 0){
								Intent i = new Intent(getApplicationContext(), HomeActivity.class);
								startActivity(i);
							}
						}else if(mApp.getUserType() == 3){
							CustomerAsyncTask customerAsyncTask = new CustomerAsyncTask();
							customerAsyncTask.execute();
						}
						spnPlant.setSelection(0);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
		}
	}
	
	public class CustomerAsyncTask extends AsyncTask<Void, Void, Integer> {

		private Context context;
		private ProgressDialog progressDialog;
		private String errorMessage;

		private void CustomerAsyncTask(Context context) {
			// TODO Auto-generated method stub
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(SelectPlantActivity.this);
			progressDialog.setMessage("Loading customer ...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				Plant plant = dataBaseAdapter.getPlant(mApp.getPlant());
				Log.i(tag,"customer last sync time : "+ plant.getCustomerLastSyncTime());

				MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(),
								plant.getCustomerLastSyncTime(),mApp.getPlant());
				if (methodResponseCustomer != null) {
					if (methodResponseCustomer.getResponseCode() == 0) {
						Customer[] customers = methodResponseCustomer.getDataArray();
						if (customers != null) {
							dataBaseAdapter.insertCustomer(customers);
						}
						plant.setCustomerLastSyncTime(methodResponseCustomer.getSys_time());
						dataBaseAdapter.updatePlant(plant);
						methodResponseCustomer = null;
						customers = null;
						
						return Constant.RESULT_SUCCESS;
					} else {
						errorMessage = methodResponseCustomer.getResponseMessage();
						return methodResponseCustomer.getResponseCode();
					}
				}

			} catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return Constant.RESULT_AUTHENTICATION_FAILURE;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = e.getMessage();
				return -1;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result == Constant.RESULT_SUCCESS){
					Intent i = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(i);
			}else{
				MyToast.show(getApplicationContext(), result, errorMessage);
			}
			
			progressDialog.dismiss();
		}
	}
	
}
