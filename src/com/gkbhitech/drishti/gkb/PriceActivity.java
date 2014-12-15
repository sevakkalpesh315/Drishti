package com.gkbhitech.drishti.gkb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.model.Quote;

public class PriceActivity extends Activity{

	private static final String tag = "PriceActivity";

	// .............. variable used in UI .............
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPrice;
	private TextView txtTintingRate;
	private TextView txtFittingRate;
	
	// .............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private Quote quote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		registerReceiver();
		
		setContentView(R.layout.activity_price);
		
		mApp = (DrishtiApplication) getApplication();
		quote = mApp.getQuote();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPrice = (TextView) findViewById(R.id.txt_actual_price);
		txtFittingRate = (TextView) findViewById(R.id.txt_actual_fitting_rate);
		txtTintingRate = (TextView) findViewById(R.id.txt_actual_tinting_rate);
		
		if(quote != null){
			txtPrice.setText(quote.getRate()+"");
			txtFittingRate.setText(quote.getFitting_rate()+"");
			txtTintingRate.setText(quote.getTinting_rate()+"");
		}
		
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
				Intent i = new Intent(PriceActivity.this, HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
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
