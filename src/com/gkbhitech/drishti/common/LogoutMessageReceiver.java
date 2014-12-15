package com.gkbhitech.drishti.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class LogoutMessageReceiver {

	private static IntentFilter intentFilter = new IntentFilter();
	
	public static void setReceiver(final Context context){
		
	    intentFilter.addAction("com.package.ACTION_LOGOUT");
	    context.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				((Activity)context).finish();
			}
		}, intentFilter);
	}
}
