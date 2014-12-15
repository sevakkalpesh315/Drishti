package com.gkbhitech.drishti.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseChangePassword;

public class ChangePasswordAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "CheckWarrantyAsynTask";
	private String errorMessage = "error";
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private ProgressDialog progressDialog;
	private String userName;
	private String oldPassword;
	private String newPassword;
	
    public ChangePasswordAsynTask(Context context, WebServiceObjectClient webServiceObjectClient, String userName, String oldPassword, String newPassword) {
		// TODO Auto-generated constructor stub
    	this.context = context;
		this.webServiceObjectClient = webServiceObjectClient;
		this.userName = userName;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
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
		try {
			MethodResponseChangePassword methodResponseChangePassword = webServiceObjectClient.changePassword(userName, oldPassword, newPassword);
			if(methodResponseChangePassword != null){
				if(methodResponseChangePassword.getResponseCode() == 0){
					return Constant.RESULT_SUCCESS;
				}else{
					errorMessage = methodResponseChangePassword.getResponseMessage();
					return methodResponseChangePassword.getResponseCode();
				}
			}
		}catch (NetworkUnavailableException e) {
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(Constant.log) Log.i(tag, "Result code : "+result);
		
		switch (result) {
		case Constant.RESULT_SUCCESS:
			
			if(Constant.log) Log.i(tag, "success");
			Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show();
			
			break;
		default:
			if(Constant.log) Log.i(tag, "error");
			MyToast.show(context, result, errorMessage);
			break;
		}
		
		progressDialog.dismiss();
	}
}
