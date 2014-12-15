package com.gkbhitech.drishti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomer;

public class CustomerAsyncTask extends AsyncTask<Void, String, Integer>{

	private static final String tag = "customerAsyncTask";
	private String errorMessage = "error";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private DataBaseAdapter dataBaseAdapter;
	private ProgressDialog progressDialog;
	private static final String LOADING_CUSTOMER = "Loading Customers...";
	private static final String LOADING_INHOUSE_BANK = "Loading Inhouse Banks...";
	
	private int plant;
	
	public CustomerAsyncTask(Context context,
			DrishtiApplication mApp, DataBaseAdapter dataBaseAdapter, int plant){
		
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.dataBaseAdapter = dataBaseAdapter;
		progressDialog = new ProgressDialog(context);
		this.plant = plant;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Loading Customer...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try{
			publishProgress(LOADING_CUSTOMER);
			MethodResponseCustomer methodResponseCustomer = webServiceObjectClient.getCustomer(mApp.getUserName(), mApp.getLastSyncTime(Constant.CUSTOMER_LAST_SYNC_TIME),0);
			if(methodResponseCustomer != null){
				if(methodResponseCustomer.getResponseCode() == 0){
					Customer[] customers = methodResponseCustomer.getDataArray();
					if(customers != null){
						dataBaseAdapter.insertCustomer(customers);
					}
				}else{
					errorMessage = methodResponseCustomer.getResponseMessage();
					return methodResponseCustomer.getResponseCode();
				}
			}
			publishProgress(LOADING_INHOUSE_BANK);
			/*MethodResponseInhouseBank methodResponseInhouseBank = webServiceObjectClient.getInhouseBank(plant);
			if(methodResponseInhouseBank != null){
				if(methodResponseInhouseBank.getResponseCode() == 0){
					InhouseBank[] inhouseBanks= methodResponseInhouseBank.getDataArray();
					
					if(inhouseBanks != null){
						for(int i =0; i<inhouseBanks.length; i++){
							inhouseBanks[i].setProfit_center(plant);
						}
						//dataBaseAdapter.insertCustomer(customers);
						dataBaseAdapter.insertInhouseBank(inhouseBanks);
					}
				}else{
					errorMessage = methodResponseInhouseBank.getResponseMessage();
					return methodResponseInhouseBank.getResponseCode();
				}
			}*/
			
			return Constant.RESULT_SUCCESS;
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
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
		progressDialog.dismiss();
		
		if(Constant.log) Log.i(tag, "result Code: "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			if(Constant.log) Log.i(tag, "success");
			//Intent i = new Intent(context, FieldForceActivity.class);
			//Intent i = new Intent(context, InventorySummaryForm.class);
			Intent i = new Intent(context, HomeActivity.class);
			context.startActivity(i);
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
			break;
		case 3:
			if(Constant.log) Log.i(tag, "error");
			Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
}
