package com.gkbhitech.drishti.geotagging;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.order.SelectCustomerActivity;
import com.gkbhitech.drishti.report.CustomerDetailForVisitActivity;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseGeotagging;

public class GeoTaggingActivity extends Activity implements OnClickListener,LocationListener {

	private static final String tag = "GeoTaggingActivity";
	
	private ImageView imvBack;
	private ImageView imvHome;
	//private Spinner spnCustomer;
	private TextView txtCustomer;
	private LinearLayout llCustomer;
	private EditText etMobileNo, etContactPerson, etEmail, etPin;
	
	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	private List<Customer> customers = new ArrayList<Customer>();
	private List<String> customerNames = new ArrayList<String>();
	
	private String customerCode;
	private String mobileNo = "", contactPerson = "",  email = "", pin = "";
	private Customer customer;

	/*************** Change buttons to imageView ****************************/
	
	Button btnGeoTag, btnLogVisit;
	
	private ProgressDialog progressDialog;
	private LocationManager locationManager;
	private String provider;
	private Location location;
	private Handler handler;
	private Runnable runnable;
	
	private boolean isLocationPosted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_geo_tagging);
		
		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		/*************** Change buttons to imageView ****************************/
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		llCustomer = (LinearLayout) findViewById(R.id.ll_select_customer);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer); 
		etMobileNo = (EditText) findViewById(R.id.edt_mobile);
		etContactPerson = (EditText) findViewById(R.id.edt_contact_person);
		etEmail = (EditText) findViewById(R.id.edt_email);
		etPin = (EditText) findViewById(R.id.edt_pin);
		btnGeoTag = (Button) findViewById(R.id.imv_geoTag);
		btnGeoTag.setOnClickListener(this);
		//btnGeoTag.setVisibility(View.INVISIBLE);
		//btnLogVisit = (Button) findViewById(R.id.imv_logVisit);
		//btnLogVisit.setOnClickListener(this);
		//btnLogVisit.setVisibility(View.INVISIBLE);

		//spnCustomer = (Spinner) findViewById(R.id.spn_customer);
		
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
				startActivity(i);
			}
		});
		llCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
		
		//FatchCustomerAsynTask fatchCustomerAsynTask = new FatchCustomerAsynTask();
		//fatchCustomerAsynTask.execute();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
			/*if(customer.getLatitude() != 0.0 && customer.getLongitude() != 0.0){
				//btnLogVisit.setVisibility(View.VISIBLE);
				//btnGeoTag.setVisibility(View.INVISIBLE);
			}else{
				//btnGeoTag.setVisibility(View.VISIBLE);
				//btnLogVisit.setVisibility(View.INVISIBLE);
			}*/
			if(customer.getMobile() != null){
				etMobileNo.setText(customer.getMobile());
			}
			if(customer.getContact_person() != null){
				etContactPerson.setText(customer.getContact_person());
			}
			if(customer.getEmail() != null){
				etEmail.setText(customer.getEmail());
			}
			if(customer.getPin() != null){
				etPin.setText(customer.getPin());
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mApp.getUserType() != 1){
			mApp.setCustomer(null);
		}
	}

	@Override
	public void onClick(View v) {
		
		mobileNo = etMobileNo.getText().toString();
		contactPerson = etContactPerson.getText().toString().trim();
		email = etEmail.getText().toString().trim();
		pin = etPin.getText().toString();
		
		switch (v.getId()) {
		case R.id.imv_geoTag:
			isLocationPosted = false;
			getLocation();
			break;
		/*case R.id.imv_logVisit:
			logVisit();
			break;
		}*/}
	}
	
	

	void postLocation() {
		MethodResponseGeotagging methodResponseGeotagging = new MethodResponseGeotagging();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		try {
			Toast.makeText(this, "location is being posted....",Toast.LENGTH_SHORT).show();
			methodResponseGeotagging = webServiceObjectClient.geoTagging(mApp.getUserName(), customerCode, mobileNo, contactPerson, email, pin, latitude, longitude);
		} catch (NetworkUnavailableException e) {
			methodResponseGeotagging = null;
			e.printStackTrace();
			//Toast.makeText(getApplicationContext(), "network unavailable", Toast.LENGTH_SHORT).show();
			MyToast.show(getApplicationContext(), Constant.RESULT_NETWORK_UNAVAILABLE, null);
		} catch (Exception e) {
			methodResponseGeotagging = null;
			e.printStackTrace();
			//Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
			MyToast.show(getApplicationContext(), -1, e.getMessage());
		}
		
		if(methodResponseGeotagging != null){
			
			int responseCode = methodResponseGeotagging.getResponseCode();
			String responseMessage = methodResponseGeotagging.getResponseMessage();
			
			if(Constant.log)Log.i(tag,"response code : "+responseCode);
			
			if(responseCode == Constant.RESULT_SUCCESS){
				Toast.makeText(this, "location posted succesfully",Toast.LENGTH_SHORT).show();
				dataBaseAdapter.setLatLong(mApp.getPlant(), customerCode, latitude, longitude);
			}else{
				MyToast.show(getApplicationContext(), responseCode, responseMessage);
			}
		}
		isLocationPosted = true;
		progressDialog.dismiss();
	}
	
	void logVisit() {
		MethodResponseGeotagging methodResponseGeotagging = new MethodResponseGeotagging();
		Location location = GeoTagging.getLocation();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		try {
			Toast.makeText(getApplicationContext(), "location is being posted....",Toast.LENGTH_SHORT).show();
			methodResponseGeotagging = webServiceObjectClient.logVisit(customerCode, latitude, longitude);
		} catch (NetworkUnavailableException e) {
			e.printStackTrace();
			//Toast.makeText(getApplicationContext(), "network unavailable", Toast.LENGTH_SHORT).show();
			MyToast.show(getApplicationContext(), Constant.RESULT_NETWORK_UNAVAILABLE, null);
		} catch (Exception e) {
			e.printStackTrace();
			//Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
			MyToast.show(getApplicationContext(), -1, e.getMessage());
		}
		
		if(methodResponseGeotagging != null){
			
			int responseCode = methodResponseGeotagging.getResponseCode();
			String responseMessage = methodResponseGeotagging.getResponseMessage();
			
			if(responseCode == Constant.RESULT_SUCCESS){
				Toast.makeText(getApplicationContext(), "location posted succesfully",Toast.LENGTH_SHORT).show();
			}else{
				MyToast.show(getApplicationContext(), responseCode, responseMessage);
			}
		}
		
	}
	
	private void getLocation(){
		progressDialog = new ProgressDialog(GeoTaggingActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Gps Data.......");
		handler = new Handler();
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.GPS_PROVIDER;
		runnable = new Runnable() {

			@Override
			public void run() {
				progressDialog.cancel();
				Toast.makeText(GeoTaggingActivity.this, "GPS Not Avialable",
						Toast.LENGTH_SHORT).show();
			}
		};

		boolean isGpsEnabled = locationManager.isProviderEnabled(provider);
		if (!isGpsEnabled) {
			startActivity(new Intent(
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
		// TODO Auto-generated method stub
		this.location = location;
		handler.removeCallbacks(runnable);
		
		if(location != null){
			if(!isLocationPosted){
				if(Constant.log)Log.i(tag, "Location : "+location.getLatitude());
				postLocation();
			}
		}else{
			progressDialog.dismiss();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
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
