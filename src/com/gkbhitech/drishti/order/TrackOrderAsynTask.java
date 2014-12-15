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

public class TrackOrderAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "TrackOrderByCustomerAndDateAsynTask";
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private String customerCode, orderType,design,indexOe,custRefNo;
	private Long dateFrom,dateTo;
	private int brandCode;
	float add;
	private ProgressDialog progressDialog;
	
	private MethodResponseOrders methodResponseOrders;
	
	public TrackOrderAsynTask(Context context, DrishtiApplication mApp,
			String customerCode, String orderType, int brandCode,String design, String indexOe,float add,String custRefNo, Long dateFrom, Long dateTo){
		
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.context = context;
		this.customerCode = customerCode;
		this.orderType = orderType;
		this.brandCode = brandCode;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.design = design;
		this.indexOe = indexOe;
		this.add = add;
		this.custRefNo = custRefNo;
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
			methodResponseOrders = webServiceObjectClient.getOrderByDate(customerCode,orderType,brandCode, design,indexOe,add,custRefNo, dateFrom, dateTo);
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
		
		progressDialog.dismiss();
	}
}
