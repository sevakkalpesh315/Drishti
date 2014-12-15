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
import com.gkbhitech.drishti.model.OrderHistory;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderHistory;

public class OrderSummaryAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "OrderSummaryAsynTask";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private Order order;
	private ProgressDialog progressDialog;
	
	private MethodResponseOrderHistory methodResponseOrderHistory;
	
	public OrderSummaryAsynTask(Context context, DrishtiApplication mApp, Order order){
		
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.context = context;
		//this.order = order;
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
			methodResponseOrderHistory = webServiceObjectClient.getOrderHistory(mApp.getOrderNo());
			return Constant.RESULT_SUCCESS;
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
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
			
			if(methodResponseOrderHistory != null){
				int responseCode = methodResponseOrderHistory.getResponseCode();
				String responseMessage = methodResponseOrderHistory.getResponseMessage();
				
				if(responseCode == Constant.RESULT_SUCCESS){
					if(methodResponseOrderHistory != null){
						OrderHistory orderHistory = methodResponseOrderHistory.getData();
						if(orderHistory != null){
							mApp.setOrderHistory(orderHistory);
							mApp.setSelectedOrder(order);
							Intent i = new Intent(context, OrderHistoryActivity.class);
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
		
		progressDialog.cancel();
	}
}
