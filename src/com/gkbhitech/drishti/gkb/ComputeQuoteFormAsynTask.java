package com.gkbhitech.drishti.gkb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Quote;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseQuote;

public class ComputeQuoteFormAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "InventorySummaryFormAsynTask";
	private String errorMessage = "Error";
	private DrishtiApplication mApp; 
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	//private int brandCode;
	private String lensCode;
	private String coatingCode;
	/*private Double sph;
	private Double cyl;
	private int axis;
	private Double prism;*/
	//private String materialType;
	private ProgressDialog progressDialog;
	
	private MethodResponseQuote methodResponseQuote;
	
	public ComputeQuoteFormAsynTask(Context context, DrishtiApplication mApp, String lensCode, String coatingCode){
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		//this.brandCode = brandCode;
		this.lensCode = lensCode;
		this.coatingCode = coatingCode;
		/*this.sph = sph;
		this.cyl = cyl;
		this.axis = axis;
		this.prism = prism;*/
		//this.materialType = materialType;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Compute Price ...");
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			methodResponseQuote = webServiceObjectClient.getComputedPrice(lensCode, coatingCode);
			if(methodResponseQuote != null){
				if(methodResponseQuote.getResponseCode() == Constant.RESULT_SUCCESS){
					//Quote quote = methodResponseQuote.getData();
					//if(quote != null){
						//mApp.setQuote(quote);
						return Constant.RESULT_SUCCESS;
					//}
				}else{
					errorMessage = methodResponseQuote.getResponseMessage();
					return methodResponseQuote.getResponseCode();
				}
			}
			return Constant.RESULT_SUCCESS;
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
		
		if(Constant.log) Log.i(tag, "Result code : "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			
			if(Constant.log) Log.i(tag, "success");
			
			Intent i = new Intent(context,PriceActivity.class);
			context.startActivity(i);
			
			/*if(methodResponseQuote != null){
				int responseCode = methodResponseQuote.getResponseCode();
				String responseMessage = methodResponseQuote.getResponseMessage();
				
				if(responseCode == Constant.RESULT_SUCCESS){
					Quote quote = methodResponseQuote.getData();
					if(quote != null){
						mApp.setQuote(quote);
						Intent i = new Intent(context,PriceActivity.class);
						context.startActivity(i);
					}
				}else{
					MyToast.show(context, responseCode, responseMessage);
				}
			}*/
			
			break;
		default:
			MyToast.show(context, result, errorMessage);
			break;
		}
		
		progressDialog.cancel();
	}
}
