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
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.CustomerAccountStatement;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerAccountStatement;

public class CustomerAccountStatementFormAsynTask extends AsyncTask<Void, String, Integer>{

	private static final String tag = "CustomerAccountStatementFormAsynTask";
	private String errorMessage = "error";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private ProgressDialog progressDialog;
	private String customerCode;
	private int plant;
	private Long fromDate;
	private Long toDate;
	private MethodResponseCustomerAccountStatement methodResponseCustomerAccountStatement;
	private CustomerAccountStatement[] customerAccountStatement;
	
	public CustomerAccountStatementFormAsynTask(Context context, DrishtiApplication mApp, WebServiceObjectClient webServiceObjectClient,
			String customerCode, Long fromDate, Long toDate){
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = webServiceObjectClient;
		this.customerCode = customerCode;
		this.fromDate = fromDate;
		this.toDate = toDate;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Loading ...");
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try{
			
			if(Constant.log) Log.i(tag, tag+" start");
			
			methodResponseCustomerAccountStatement = webServiceObjectClient.getCustomerAccountStatement(customerCode, plant, fromDate, toDate);
			if(methodResponseCustomerAccountStatement != null){
				if(methodResponseCustomerAccountStatement.getResponseCode() == Constant.RESULT_SUCCESS){
					methodResponseCustomerAccountStatement.setFromDate(fromDate);
					methodResponseCustomerAccountStatement.setToDate(toDate);
					mApp.setMethodResponseCustomerAccountStatement(methodResponseCustomerAccountStatement);
					return Constant.RESULT_SUCCESS;
				}else{
					errorMessage = methodResponseCustomerAccountStatement.getResponseMessage();
					return methodResponseCustomerAccountStatement.getResponseCode();
				}
			}
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
			Intent i = new Intent(context, CustomerAccountStatementActivity.class);
			context.startActivity(i);
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
			MyDialog myDialog = new MyDialog(context);
			myDialog.displayDialog(Constant.DIALOG_TITLE_ERROR_MESSAGE,errorMessage);
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
