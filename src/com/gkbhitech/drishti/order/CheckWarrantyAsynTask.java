package com.gkbhitech.drishti.order;

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
import com.gkbhitech.drishti.model.Order;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrders;

public class CheckWarrantyAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "CheckWarrantyAsynTask";
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private String customerCode;
	private Double leSph;
	private Double leCyl;
	private Double reSph;
	private Double reCyl;
	private Long dateFrom;
	private Long dateTo;
	private ProgressDialog progressDialog;
	
	private MethodResponseOrders methodResponseOrders;
	
	public CheckWarrantyAsynTask(Context context, DrishtiApplication mApp,String customerCode,
			Double leSph, Double leCyl, Double reSph, Double reCyl, Long dateFrom, Long dateTo){
		
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.customerCode = customerCode;
		this.leSph = leSph;
		this.leCyl = leCyl;
		this.reSph = reSph;
		this.reCyl = reCyl;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
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
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			methodResponseOrders = webServiceObjectClient.checkWarranty(customerCode, leSph, leCyl, reSph, reCyl, dateFrom, dateTo);
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
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		progressDialog.cancel();
		
		if(Constant.log) Log.i(tag, "Result code : "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			
			if(Constant.log) Log.i(tag, "success");
			
			if(methodResponseOrders != null){
				int responseCode = methodResponseOrders.getResponseCode();
				String responseMessage = methodResponseOrders.getResponseMessage();
				
				if(responseCode == Constant.RESULT_SUCCESS){
					if(methodResponseOrders != null){
						Order[] orders = methodResponseOrders.getDataArray();
						if(orders != null){
							mApp.setOrders(orders);
							Intent i = new Intent(context,OrderSummaryActivity.class);
							context.startActivity(i);
						}
					}
					
				}else{
					MyToast.show(context, responseCode, responseMessage);
				}
			}
			
			break;
		default:
			MyToast.show(context, result, "Error");
			break;
		}
	}
}
