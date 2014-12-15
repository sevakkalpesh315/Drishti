package com.gkbhitech.drishti.settings;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;

public class ChangePasswordActivity extends Activity implements OnClickListener {

	private static final String tag = "ChangePasswordActivity";
	
	private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
	private Button btnOk;

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private WebServiceObjectClient webServiceObjectClient;
	
	private String oldPassword;
	private String newPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_change_password);

		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		edtOldPassword = (EditText) findViewById(R.id.etxt_old_password);
		edtNewPassword = (EditText) findViewById(R.id.etxt_new_password);
		edtConfirmPassword = (EditText) findViewById(R.id.etxt_conf_password);

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
		btnOk = (Button) findViewById(R.id.btn_settings_ok);
		btnOk.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {

		if(v.getId() == btnOk.getId()){
			
			if(verifyOldPass() && validateNewPass()){
				
				ChangePasswordAsynTask changePasswordAsynTask = new ChangePasswordAsynTask(ChangePasswordActivity.this, webServiceObjectClient, mApp.getUserName(), oldPassword, newPassword);
				changePasswordAsynTask.execute();
			}
		}
	}

	private Boolean verifyOldPass() {
		oldPassword = edtOldPassword.getText().toString();
		
		return oldPassword != null;
	}

	private Boolean validateNewPass() {
		newPassword = edtNewPassword.getText().toString();
		String temp2 = edtConfirmPassword.getText().toString();
		if ((newPassword != null) && (temp2 != null)) {
			if (newPassword.equals(temp2)) {
				//setNewPass();
				//Toast.makeText(ChangePasswordActivity.this, "Password Changed",
				//		Toast.LENGTH_SHORT).show();
				return true;
			} else {
				Toast.makeText(ChangePasswordActivity.this,
						"Password Mismatch", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	void setNewPass() {
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
