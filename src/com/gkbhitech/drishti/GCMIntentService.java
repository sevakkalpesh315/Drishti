package com.gkbhitech.drishti;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Offer;
import com.gkbhitech.drishti.order.OfferActivity;
import com.gkbhitech.drishti.order.OrderStockLensesActivity;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * author parth mehta
 * 
 * 
 * GCMIntentService class is the GCM Service receiver class of the FishEye App.
 * It handles the push notifications and necessary action taken on receiving the
 * notification.
 * 
 * 
 * 
 */

public class GCMIntentService extends GCMBaseIntentService {
	private String tag = "GCMIntentService";
	private static int count = 0;
	DrishtiApplication mApp;
	private static final String TOKEN = Long.toBinaryString(new Random()
			.nextLong());
	WebServiceObjectClient webServiceObjectClient;

	private static int id = 1;
	private GsonBuilder m_GsonBuilder;
	CharSequence contentTitle = "Drishti";
	CharSequence contentMessage = "Drishti";
	String message;

	protected void onRegistered(Context context, String regId) {
		Log.i(tag, "onRegistered: " + regId);
		// feApp = (FishEyeApplication) context.getApplicationContext();
		// feApp.setRegistrationId(regId);
		try {
			webServiceObjectClient.registerDevice(GCMRegistrar.getRegistrationId(context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onUnregistered(Context context, String regId) {
		Log.i(tag, "onUnregistered: " + regId);
		// feApp = (FishEyeApplication) context.getApplicationContext();
		// feApp.setRegistrationId(regId);

	}

	protected void onMessage(Context context, Intent intent) {
		Log.i(tag, "OnMessage");
		handleMessage(context, intent);

		// This is how to get values from the push message (data)

	}
	
	

	protected void onError(Context context, String error) {
		Log.i(tag, "onError Id: " + error);

		if (error != null) {
			if ("SERVICE_NOT_AVAILABLE".equals(error)) {
				long backoffTimeMs = 3000;
				long nextAttempt = SystemClock.elapsedRealtime()
						+ backoffTimeMs;
				Intent retryIntent = new Intent("com.example.gcm.intent.RETRY");
				retryIntent.putExtra("token", TOKEN);
				PendingIntent retryPendingIntent = PendingIntent.getBroadcast(
						context, 0, retryIntent, 0);
				AlarmManager am = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.ELAPSED_REALTIME, nextAttempt,
						retryPendingIntent);
				backoffTimeMs *= 2; // Next retry should wait longer.
				// update back-off time on shared preferences
			} else if ("ACCOUNT_MISSING".equals(error)) {
				Toast.makeText(getApplicationContext(),
						"Please set an email account on the device",
						Toast.LENGTH_LONG).show(); // update back-off time on
													// shared preferences
			} else {
				// Unrecoverable error, log it
				Log.i(TAG, "Received error: " + error);
			}
		}

	}

	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(tag, "onRecoverableError: " + errorId);
		return false;
	}

	private void handleMessage(Context context, Intent intent) {

		Log.e(tag, "Handle GCM Message");

		mApp = (DrishtiApplication) context.getApplicationContext();
		
		Bundle bundle = intent.getExtras();
		Offer offer = new Offer();
		if(bundle.containsKey("id")){
			//message = bundle.getString("c_code");
			//Log.i(tag, "message : "+message);
			//mApp.setC_code(bundle.getString("c_code"));
			offer.setId(new Long(bundle.getString("id")));
			Log.i(tag, "id : "+offer.getId());
		}

		if(bundle.containsKey("c_code")){
			//message = bundle.getString("c_code");
			//Log.i(tag, "message : "+message);
			//mApp.setC_code(bundle.getString("c_code"));
			offer.setCust_code(bundle.getString("c_code"));
			Log.i(tag, "CC : "+offer.getCust_code());
		}
		/*if(bundle.containsKey("p_brand")){
			//message = bundle.getString("p_brand");
			//Log.i(tag, "message : "+message);
			//mApp.setP_brand(bundle.getString("p_brand"));
			offer.setProduct_brand(bundle.getString("p_brand"));
			Log.i(tag, "P : "+offer.getProduct_brand());
		}*/
		if(bundle.containsKey("l_code")){
			//Log.i(tag, "msg exist");
			//message = bundle.getString("l_code");
			//Log.i(tag, "message : "+message);
			//mApp.setL_code(bundle.getString("l_code"));
			offer.setLens_code(bundle.getString("l_code"));
			Log.i(tag, "LC : "+offer.getLens_code());
		}
		if(bundle.containsKey("ct_code")){
			//Log.i(tag, "msg exist");
			//message = bundle.getString("l_code");
			//Log.i(tag, "message : "+message);
			//mApp.setCt_code(bundle.getString("ct_code"));
			offer.setCoat(bundle.getString("ct_code"));
			Log.i(tag, "Ct : "+offer.getCoat());
		}
		if(bundle.containsKey("r_plant")){
			Log.i(tag, "r_plant exist");
			//message = bundle.getString("l_code");
			//Log.i(tag, "message : "+message);
			//mApp.setR_plant(new Integer(bundle.getString("r_plant")));
			offer.setR_plant(new Integer(bundle.getString("r_plant")));
			Log.i(tag, "RP : "+offer.getR_plant());
		}
		if(bundle.containsKey("msg")){
			Log.i(tag, "msg exist");
			message = bundle.getString("msg");
			Log.i(tag, "message : "+message);
		}
		mApp.setOffer(offer);
		//mApp.setFromPushNotification(true);
		
		/*try {
			//JSONObject jsonContent = new JSONObject(message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//String orderType = offer.getLens_code().substring(0, 1);
		//Log.i(tag, "Order Type : "+orderType);
		
		Intent notificationIntent = new Intent(context, OfferActivity.class);
		/*if(orderType.equals("s")){
			orderType = new Intent(context, OfferActivity.class);
		}else{
			
		}*/
		
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int icon = R.drawable.gkb_red_125_125;
		CharSequence tickerText = "Drishti Notification";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, message,
				contentIntent);
		notification.number = count++;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNotificationManager.notify(id, notification);
		++id;
	}

}
