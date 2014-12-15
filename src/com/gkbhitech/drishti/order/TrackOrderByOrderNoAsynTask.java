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
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrder;

public class TrackOrderByOrderNoAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "TrackOrderByOrderNoAsynTask";
	private Context context;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private String orderNo;
	private ProgressDialog progressDialog;
	
	private MethodResponseOrder methodResponseOrder;
	
	public TrackOrderByOrderNoAsynTask(Context context, DrishtiApplication mApp,
			String orderNo){
		
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.context = context;
		this.orderNo = orderNo;
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
			methodResponseOrder = webServiceObjectClient.getOrderByOrderNo(orderNo);
			return Constant.RESULT_SUCCESS;
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
			Log.i(tag, "Exception : "+e.getMessage());
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
			
			if(methodResponseOrder != null){
				int responseCode = methodResponseOrder.getResponseCode();
				String responseMessage = methodResponseOrder.getResponseMessage();
				
				if(responseCode == Constant.RESULT_SUCCESS){
					if(methodResponseOrder != null){
						Order[] orders = new Order[1]; 
						orders[0] = methodResponseOrder.getData();
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
		
		progressDialog.dismiss();
	}
}
