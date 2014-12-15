package com.gkbhitech.drishti.account;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDialog;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.gkb.PriceActivity;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseActivatePPLP;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class GoodWillCodeFormAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "InventorySummaryFormAsynTask";
	private String errorMessage = "Error";
	private DrishtiApplication mApp; 
	private WebServiceObjectClient webServiceObjectClient;
	private Context context;
	private String customerCode;
	private ProgressDialog progressDialog;
	private MethodResponseActivatePPLP methodResponseActivatePPLP;
	
	public GoodWillCodeFormAsynTask(Context context, DrishtiApplication mApp, 
			String customerCode){
		this.context = context;
		this.mApp = mApp;
		this.webServiceObjectClient = mApp.getWebserviceObjectClient();
		this.customerCode = customerCode;
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
			
			/*FTPClient client = new FTPClient();
		    BufferedOutputStream fos = null;

		    try {
		        client.connect("121.242.11.72",21);
		        client.login("gkbsap","gkb@123");
		        
		        if(Constant.log) Log.i(tag, "Connected ......");
		        
		        client.enterLocalPassiveMode();
		        client.setFileType(FTPClient.BINARY_FILE_TYPE);
		        
		        File sdcard = Environment.getExternalStorageDirectory();
		        
		        fos = new BufferedOutputStream(
                        new FileOutputStream(sdcard.getAbsolutePath()));
		        
		        
		        client.retrieveFile("webapps/Drishti.war", fos);
		        if(Constant.log) Log.i(tag, "done ......");

		    }catch (Exception e) {
				// TODO: handle exception
		    	e.printStackTrace();
			}*/
			methodResponseActivatePPLP = webServiceObjectClient.activatePPLP(customerCode,mApp.getUserName());
			if(methodResponseActivatePPLP != null){
				errorMessage = methodResponseActivatePPLP.getResponseMessage();
				return methodResponseActivatePPLP.getResponseCode();
			}
			return Constant.RESULT_NULL_RESPONSE;
		} catch (NetworkUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constant.RESULT_NETWORK_UNAVAILABLE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
				MyDialog myDialog = new MyDialog(context);
				myDialog.displayDialog(Constant.DIALOG_TITLE_MESSAGE, "Your PPLP code : "+methodResponseActivatePPLP.getData().getPplpPk());
				//Intent i = new Intent(context,PriceActivity.class);
				//context.startActivity(i);
				break;
			default:
				MyToast.show(context, result, errorMessage);
				break;
		}
		
		progressDialog.dismiss();
	}
}
