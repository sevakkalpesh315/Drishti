package com.gkbhitech.drishti.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.LoginActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;

public class SettingsActivity extends Activity{

	private static final String tag = "SettingsActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtChangePassword;
	//private TextView txtLogout;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_settings_home);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtChangePassword = (TextView) findViewById(R.id.txt_change_password);
		//txtLogout = (TextView) findViewById(R.id.txt_logout);
		//Context context = getApplicationContext();
		progressDialog = new ProgressDialog(SettingsActivity.this);
		progressDialog.setMessage("Logout ...");
		
		imvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		imvHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		txtChangePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ChangePasswordActivity.class);
				startActivity(i);
			}
		});
		
		/*txtLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
					progressDialog.show();
					
					dataBaseAdapter.clearDatabase();
					dataBaseAdapter.close();
					mApp.clearCredentials();
					Toast.makeText(getApplicationContext(), "Data Cleared ...", Toast.LENGTH_SHORT).show();
					
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.package.ACTION_LOGOUT");
					sendBroadcast(broadcastIntent);
					
					Intent i = new Intent(getApplicationContext(), LoginActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
					progressDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
				}
			}
		});*/
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
