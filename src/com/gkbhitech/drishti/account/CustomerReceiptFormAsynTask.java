package com.gkbhitech.drishti.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDialog;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.CustomerReceipt;
import com.gkbhitech.drishti.report.CustomerDetailForVisitActivity;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseReceipt;

public class CustomerReceiptFormAsynTask extends AsyncTask<Void, String, Integer>{

	private static final String tag = "CustomerReceiptAsynTask";
	private String errorMessage = "error";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private DataBaseAdapter dataBaseAdapter;
	private String url;
	private ProgressDialog progressDialog;
	private CustomerReceipt customerReceipt;
	private MethodResponseReceipt methodResponseReceipt;
	
	public CustomerReceiptFormAsynTask(Context context, DrishtiApplication mApp, DataBaseAdapter dataBaseAdapter, String url,CustomerReceipt customerReceipt){
		
		
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.dataBaseAdapter = dataBaseAdapter;
		this.url = url;
		progressDialog = new ProgressDialog(context);
		this.customerReceipt = customerReceipt;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Loading ...");
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			if(Constant.log) Log.i(tag, tag+" start");
			
			methodResponseReceipt = webServiceObjectClient.getCustomerReceipt(url, customerReceipt);
			if(methodResponseReceipt != null){
				if(methodResponseReceipt.getResponseCode() == Constant.RESULT_SUCCESS){
					mApp.setPostCustomerReceiptResponse(methodResponseReceipt.getData());
					return Constant.RESULT_SUCCESS;
				}else{
					errorMessage = methodResponseReceipt.getResponseMessage();
					return methodResponseReceipt.getResponseCode();
				}
			}
			//Log.i(tag, "Response : "+response);
			return Constant.RESULT_NULL_RESPONSE;
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
			return -1;
		}
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(Constant.log) Log.i(tag, "result : "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			if(Constant.log) Log.i(tag, "success");
			
			//Toast.makeText(context, "Customer Receipt created successfully with SAP Document no: "+methodResponseReceipt.getData().getDocNumber(), Toast.LENGTH_LONG).show();
			//Intent i = new Intent(context, CustomerReceiptActivity.class);
			//context.startActivity(i);
			MyDialog myDialog = new MyDialog(context);
			myDialog.displayDialog("Receipt No", methodResponseReceipt.getData().getDocNumber());
			
			break;
		case Constant.RESULT_NETWORK_UNAVAILABLE:
			if(Constant.log) Log.i(tag, "network unavailable");
			Toast.makeText(context, "network unavailable", Toast.LENGTH_LONG).show();
			break;
		case Constant.RESULT_INVALID_AUTH_TOKEN:
			if(Constant.log) Log.i(tag, "Invalid auth token");
			Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
			break;
		case Constant.RESULT_SAP_ERROR:
			if(Constant.log) Log.i(tag, "Record not found");
			//Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			MyDialog myDialog1 = new MyDialog(context);
			myDialog1.displayDialog(Constant.DIALOG_TITLE_ERROR_MESSAGE, errorMessage);
			break;
		case Constant.RESULT_NULL_RESPONSE:
			if(Constant.log) Log.i(tag, "error");
			Toast.makeText(context, "Null response", Toast.LENGTH_LONG).show();
			break;
		default:
			MyToast.show(context, result, errorMessage);
			break;
		}
		
		progressDialog.dismiss();
	}

}
