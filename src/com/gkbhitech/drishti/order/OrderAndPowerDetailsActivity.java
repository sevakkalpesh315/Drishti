package com.gkbhitech.drishti.order;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;

public class OrderAndPowerDetailsActivity extends TabActivity{

	private static final String tag = "OrderAndPowerDetailsActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.tab_activity_order_and_power_details);
		
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab
		MyView view = null;
		
		view = new MyView(getApplicationContext(), R.drawable.order_detail_icon, R.drawable.order_detail_icon, "Order Details");
		//view.setPadding(5, 10,5,10);
		intent = new Intent().setClass(getApplicationContext(), OrderDetailActivity.class);
		spec = tabHost.newTabSpec("Order Details").setIndicator(view).setContent(intent);
		if(Constant.log) Log.i(tag, "view : "+view);
		if(Constant.log) Log.i(tag, "intent : "+intent);
		if(Constant.log) Log.i(tag, "tabHost : "+tabHost);
		tabHost.addTab(spec);
		
		view = new MyView(getApplicationContext(), R.drawable.power_detail_icon, R.drawable.power_detail_icon, "Power Details");
		intent = new Intent().setClass(getApplicationContext(), PowerDetailActivity.class);
		spec = tabHost.newTabSpec("Power Details").setIndicator(view).setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
	private class MyView extends LinearLayout {
		ImageView iv;
		TextView tv;

		public MyView(Context c, Integer drawable, Integer drawableselec, String label) {
			super(c);
			iv = new ImageView(c);
			StateListDrawable listDrawable = new StateListDrawable();
			listDrawable.addState(SELECTED_STATE_SET, this.getResources().getDrawable(drawableselec));
			listDrawable.addState(ENABLED_STATE_SET, this.getResources().getDrawable(drawable));
			iv.setImageDrawable(listDrawable);
			iv.setBackgroundColor(Color.TRANSPARENT);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, (float) 0.0);
			iv.setLayoutParams(params);
			setGravity(Gravity.CENTER);
			tv = new TextView(c);
			tv.setText(label);
			//tv.setTypeface(FontFace.getFontFace((Activity) c));
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundColor(Color.TRANSPARENT);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(12);
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, (float) 1.0));
			setOrientation(LinearLayout.VERTICAL);
			addView(iv);
			addView(tv);
			//setBackgroundDrawable(this.getResources().getDrawable(R.layout.selecttabbackground));
		}
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
