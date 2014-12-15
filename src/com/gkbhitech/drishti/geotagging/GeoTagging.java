package com.gkbhitech.drishti.geotagging;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

public class GeoTagging implements LocationListener {

	private LocationManager locationManager;
	private String provider;
	public static Location location;
	private Context mContext;
	private ProgressDialog progressDialog;
	private Handler handler;
	private Runnable runnable;

	public GeoTagging(Context context) {
		this.mContext = context;
	}

	public void init(){
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage("Loading Gps Data.......");
		handler = new Handler();
		locationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.GPS_PROVIDER;
		runnable = new Runnable() {

			@Override
			public void run() {
				progressDialog.cancel();
				Toast.makeText(mContext, "GPS Not Avialable",
						Toast.LENGTH_SHORT).show();
			}
		};

		boolean isGpsEnabled = locationManager.isProviderEnabled(provider);
		if (!isGpsEnabled) {
			mContext.startActivity(new Intent(
					Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
		if (isGpsEnabled) {
			progressDialog.show();
			locationManager.requestLocationUpdates(provider, 200, 1, this);
			handler.postDelayed(runnable, 30000);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		handler.removeCallbacks(runnable);
		progressDialog.cancel();
		//mContext.startActivity(new Intent(mContext, GeoTaggingActivity.class));
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	public static Location getLocation() {
		return location;
	}

}
