package com.gkbhitech.drishti.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.OrderAndPowerDetail;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderAndPowerDetail;

public class OrderAndPowerDetailsAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "CheckWarrantyAsynTask";
	private String errorMessage = "error";
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private ProgressDialog progressDialog;
	
	private MethodResponseOrderAndPowerDetail methodResponseOrderAndPowerDetail;
	
	public OrderAndPowerDetailsAsynTask(Context context, DrishtiApplication mApp) {
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading ...");
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try{
			if(Constant.log) Log.i(tag, "Order detail fatch for : "+mApp.getOrderNo());
			
			MethodResponseOrderAndPowerDetail methodResponseOrderAndPowerDetail = webServiceObjectClient.getOrderAndPowerDetail(mApp.getOrderNo());
			if(methodResponseOrderAndPowerDetail != null){
				if(methodResponseOrderAndPowerDetail.getResponseCode() == 0){
					OrderAndPowerDetail[] orderAndPowerDetails = methodResponseOrderAndPowerDetail.getDataArray();
					if(orderAndPowerDetails != null){
						mApp.setOrderAndPowerDetails(orderAndPowerDetails);
						return Constant.RESULT_SUCCESS;
					}
				}else{
					errorMessage = methodResponseOrderAndPowerDetail.getResponseMessage();
					return methodResponseOrderAndPowerDetail.getResponseCode();
				}
			}
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
		return 3;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(Constant.log) Log.i(tag, "Result code: "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			if(Constant.log) Log.i(tag, "success");
			
			Intent i = new Intent(context, OrderAndPowerDetailsActivity.class);
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
		
		progressDialog.dismiss();
	}
}
