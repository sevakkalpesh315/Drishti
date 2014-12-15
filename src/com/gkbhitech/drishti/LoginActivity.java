package com.gkbhitech.drishti;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.User;

public class LoginActivity extends Activity{
	
	/*String sessionName;
	
	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}*/

	private static final String tag = "LoginActivity";
	
	//.............. variable used in UI ................
	 EditText edtUserName;
	private EditText edtPassword;
	private Button btnLogIn;
	
	//.............. variable used in validation .........
	private Boolean isUsernameValid = false;
	private Boolean isPasswordValid = false;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_login);
		
		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		
		edtUserName = (EditText) findViewById(R.id.edt_username);
		edtPassword = (EditText) findViewById(R.id.edt_password);
		btnLogIn = (Button) findViewById(R.id.btn_login);
		
		btnLogIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName = edtUserName.getText().toString();
				String password = edtPassword.getText().toString();
				
				userName.trim();
				password.trim();
				
				if(!userName.equals("") && !password.equals("")){
					isUsernameValid = true;
					isPasswordValid = true;
				}
				
				if(isUsernameValid && isPasswordValid){
					User user = dataBaseAdapter.validateUser(userName, password);
					if(user == null){
						if(Constant.log) Log.i(tag, "user not exist in local database");
						user = new User();
						user.setUserName(userName);
						user.setPassword(password);
						user.setUserPlantsRightsLastSyncTime(0);
						user.setCustomerLastSyncTime(0);
					}
					LoginAysncTask loginAysncTask = new LoginAysncTask(LoginActivity.this, mApp, dataBaseAdapter, user);
					loginAysncTask.execute();
					/*else{
						if(Constant.log) Log.i(tag, "user exist in local database");
						mApp.setIsUserLogedIn(true);
						Intent i = new Intent(getApplicationContext(), SelectPlantActivity.class);
						startActivity(i);
						finish();
					}*/
				}
			}
		});
	}
	
	public void registerReceiver(){
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("com.package.ACTION_LOGOUT");
	    getApplicationContext().registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				finish();
			}
		}, intentFilter);
	}
	
}
