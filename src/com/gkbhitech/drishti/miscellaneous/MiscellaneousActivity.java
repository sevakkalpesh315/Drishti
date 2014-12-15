package com.gkbhitech.drishti.miscellaneous;

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
import android.widget.ImageView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.order.OrderActivity;

public class MiscellaneousActivity extends Activity {

	private static final String tag = "MiscellaneousActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private Button feedback;
	private Button phonebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (Constant.log)
			Log.i(tag, tag + " start");

		registerReceiver();

		setContentView(R.layout.activity_miscellaneous_home);

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		feedback = (Button) findViewById(R.id.btn_feedback);
		phonebook = (Button) findViewById(R.id.btn_phonebook);
		
		 addListenerOnButton();
}

private void addListenerOnButton() {

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
				Intent i = new Intent(getApplicationContext(),HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle fd = new Bundle();
				Intent feedbk = new Intent(getApplicationContext(),Feedback.class);
				feedbk.putExtras(fd);
				startActivity(feedbk);
			}
		});
		
		phonebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle pb = new Bundle();
				Intent phnbook = new Intent(getApplicationContext(),PhoneBook.class);
				phnbook.putExtras(pb);
				startActivity(phnbook);
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public void registerReceiver() {
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
