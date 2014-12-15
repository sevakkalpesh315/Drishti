package com.gkbhitech.drishti;

import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.RegisterDevice;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseRegisterDevice;
import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterDeviceAsynTask extends AsyncTask<Void, Void, Integer>{

	private String tag = "RegisterDeviceAsynTask";
	private Context context;
	private WebServiceObjectClient webServiceObjectClient;
	private String errorMessage;
	
	public RegisterDeviceAsynTask(Context context, WebServiceObjectClient webServiceObjectClient){
		this.context = context;
		this.webServiceObjectClient = webServiceObjectClient;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		
		String regId = GCMRegistrar.getRegistrationId(context);
		
		if (regId.equals("")) {
            GCMRegistrar.register(context, Constant.GCM_APP_ID);
		}else{
			if(Constant.log)Log.i(tag, "Reg Id : "+regId);
		}
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try{
			MethodResponseRegisterDevice methodResponseRegisterDevice = webServiceObjectClient.registerDevice(GCMRegistrar.getRegistrationId(context));
			
			if(methodResponseRegisterDevice != null){
				if(methodResponseRegisterDevice.getResponseCode() == 0){
					RegisterDevice registerDevice = methodResponseRegisterDevice.getData();
					if(registerDevice.isDeviceRegisterd()){
						return Constant.RESULT_SUCCESS;
					}
				}else{
					errorMessage = methodResponseRegisterDevice.getResponseMessage();
					return methodResponseRegisterDevice.getResponseCode();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return Constant.RESULT_ERROR;
		}
		return Constant.RESULT_ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(Constant.log) Log.i(tag, "result Code: "+result);
		
		if(result == Constant.RESULT_SUCCESS){
			
		}else{
			//new RegisterDeviceAsynTask(context, webServiceObjectClient).execute();
		}
	}
}
