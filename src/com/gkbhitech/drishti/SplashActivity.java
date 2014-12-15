package com.gkbhitech.drishti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.google.android.gcm.GCMRegistrar;

public class SplashActivity extends Activity{
	
	private static final String tag = "SplashActivity";
	private ImageView splash;
	private Handler splashHandler;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		setContentView(R.layout.activity_splash);
				
		mApp =  (DrishtiApplication) getApplication();
		
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		
		String regId = GCMRegistrar.getRegistrationId(this);
		
		if (regId.equals("")) {
            GCMRegistrar.register(this, Constant.GCM_APP_ID);
		}else{
			if(Constant.log)Log.i(tag, "Reg Id : "+regId);
		}

		
		splashHandler = new SplashHandler(getApplicationContext());
		
        splash = (ImageView) findViewById(R.id.splashScreen);
        Message msg = new Message();
        msg.what = Constant.STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, Constant.SPLASHTIME);
	}
	
	class SplashHandler extends Handler{
		private Context context;
		public SplashHandler(Context context){
			this.context=context;
		}
	    @Override
	    public void handleMessage(Message msg) {
            switch (msg.what) {
            case Constant.STOPSPLASH:
            	Intent i;
                if(mApp.getIsUserLogedIn()){
                	
                	if(mApp.getUserType() == 1){
                		if(Constant.log) Log.i(tag, "welcome "+mApp.getUserName());
                    	i = new Intent(context,HomeActivity.class);
                	}else{
                    	if(Constant.log) Log.i(tag, "welcome "+mApp.getUserName());
                    	i = new Intent(context,SelectPlantActivity.class);
                	}
                	startActivity(i);
                }else{
                	if(Constant.log) Log.i(tag, "please login");
                	i=new Intent(context,LoginActivity.class);
                	startActivity(i);
                }
                finish();	
                break;
            }
            super.handleMessage(msg);
	    }
	}

	/*class SplashHandler extends Handler{
		private Context context;
		public SplashHandler(Context context){
			this.context=context;
		}
	    @Override
	    public void handleMessage(Message msg) {
            switch (msg.what) {
            case Constant.STOPSPLASH:
                    if(mApp.getIsUserLogedIn()){
                    	if(Constant.log) Log.i(tag, "welcome "+mApp.getUserName());
                    	Intent i=new Intent(context,SelectPlantActivity.class);
                    	startActivity(i);
                    }else{
                    	if(Constant.log) Log.i(tag, "please login");
                    	Intent i=new Intent(context,LoginActivity.class);
                    	startActivity(i);
                    }
                    finish();	
                    break;
            }
            super.handleMessage(msg);
	    }
	}*/
	
}
