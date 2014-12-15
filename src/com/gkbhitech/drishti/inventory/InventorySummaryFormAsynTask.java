package com.gkbhitech.drishti.inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDialog;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseInventory;

public class InventorySummaryFormAsynTask extends AsyncTask<Void, Void, Integer>{
	
	private static final String tag = "InventorySummaryFormAsynTask";
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private int plant; 
	private int brandCode;
	private String lensCode;
	private String coatingCode;
	private String userName;
	private float sph;
	private float cyl;
	private String brandName;
	private String lensName;
	private String coatingName;
	private ProgressDialog progressDialog;

	private MethodResponseInventory methodResponseInventory;
	
	public InventorySummaryFormAsynTask(Context context, WebServiceObjectClient webServiceObjectClient,
			int plant, int brandCode, String lensCode, String coatingCode,String userName, float sph, float cyl,
			String brandName, String lensName, String coatingName){
		this.context = context;
		this.webServiceObjectClient = webServiceObjectClient;
		this.plant = plant;
		this.brandCode = brandCode;
		this.lensCode = lensCode;
		this.coatingCode = coatingCode;
		this.userName = userName;
		this.sph = sph;
		this.cyl = cyl;
		this.brandName = brandName;
		this.lensName = lensName;
		this.coatingName = coatingName;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Inventory ...");
		progressDialog.show();
	}
	
	
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			methodResponseInventory = webServiceObjectClient.inventory(plant, brandCode, lensCode, coatingCode, userName, cyl, sph);
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
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			
			Log.i(tag, "success");
			
			if(methodResponseInventory != null){
				int responseCode = methodResponseInventory.getResponseCode();
				String responseMessage = methodResponseInventory.getResponseMessage();
				
				if(responseCode == Constant.RESULT_SUCCESS){
					Intent i = new Intent(context,InventorySummaryActivity.class);
					i.putExtra("brandCode", brandCode);
					i.putExtra("lensCode", lensCode);
					i.putExtra("coatingCode", coatingCode);
					i.putExtra("brandName", brandName);
					i.putExtra("lensName", lensName);
					i.putExtra("coatingName", coatingName);
					i.putExtra("sph", sph);
					i.putExtra("cyl", cyl);
					i.putExtra("available_at", methodResponseInventory.getData().getQty());
					context.startActivity(i);
				}else{
					//MyToast.show(context, responseCode, responseMessage);
					MyDialog myDialog = new MyDialog(context);
					myDialog.displayDialog(Constant.DIALOG_TITLE_ERROR_MESSAGE, responseMessage);
				}
			}
			break;
		default:
			MyToast.show(context, result, "Error");
			break;
		}
	}
}
