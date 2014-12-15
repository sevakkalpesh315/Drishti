package com.gkbhitech.drishti.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDate;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.geotagging.GeoTagging;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.CustRepVisit;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.CustomerDetailForVisit;
import com.gkbhitech.drishti.model.CustomerVisit;
import com.gkbhitech.drishti.model.TalkAbout;
import com.gkbhitech.drishti.order.OrderStockLensesActivity;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerDetailForVisit;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOk;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderStockLens;

public class CustomerDetailForVisitActivity extends Activity implements LocationListener{

	private static final String tag = "CustomerDetailForVisitActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer,txtClosingBalance,txtSalesOfMonthOne,txtSalesOfMonthTwo,txtSalesOfMonthThree,
					txtAvarageSales, txtReceiptOne, txtReceiptTwo, txtReceiptThree, txtOutstandingDays;
	private LinearLayout llTalkAbout;
	private Button btnRegisterYourVisit;
	private Spinner spnVisitPurpose;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	private DataBaseAdapter dataBaseAdapter;
	
	private String customerCode;
	private int id;
	
	private LocationManager locationManager;
	private String provider;
	public static Location location;
	private ProgressDialog progressDialog;
	private Handler handler;
	private Runnable runnable;
	private boolean isLocationPosted;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_customer_detail_for_visit);
		
		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer); 
		llTalkAbout = (LinearLayout) findViewById(R.id.ll_talk_about);
		txtClosingBalance = (TextView) findViewById(R.id.txt_closing_balance);
		txtSalesOfMonthOne = (TextView) findViewById(R.id.txt_sales_of_month_one);
		txtSalesOfMonthTwo = (TextView) findViewById(R.id.txt_sales_of_month_two);
		txtSalesOfMonthThree = (TextView) findViewById(R.id.txt_sales_of_month_three);
		txtAvarageSales = (TextView) findViewById(R.id.txt_avarage_sale);
		txtReceiptOne = (TextView) findViewById(R.id.txt_receipt_one);
		txtReceiptTwo = (TextView) findViewById(R.id.txt_receipt_two);
		txtReceiptThree = (TextView) findViewById(R.id.txt_receipt_three);
		txtOutstandingDays = (TextView) findViewById(R.id.txt_outstanding_day);
		spnVisitPurpose = (Spinner) findViewById(R.id.spn_visit_purpose);
		btnRegisterYourVisit = (Button) findViewById(R.id.btn_register_your_visit);
		
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
		
		txtPlantNo.setText(mApp.getPlant()+"");
		
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
		
		final List<CustRepVisit> custRepVisits = dataBaseAdapter.getCustRepVisit();
		if(custRepVisits != null && custRepVisits.size() > 0){
			ArrayAdapter<String> arrayAdapterPurpose = new ArrayAdapter<String>(CustomerDetailForVisitActivity.this, R.layout.spinner_simple_item_1);
			for(CustRepVisit custRepVisit : custRepVisits){
				arrayAdapterPurpose.add(custRepVisit.getPurpose());
			}
			spnVisitPurpose.setAdapter(arrayAdapterPurpose);
			
			spnVisitPurpose.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					id = custRepVisits.get(position).getId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		btnRegisterYourVisit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(id != 0 && customerCode != null){
					isLocationPosted = false;
					getLocation();
					//geoTagging = new GeoTagging(CustomerDetailForVisitActivity.this); 
					//geoTagging.init();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			llTalkAbout.removeAllViews();
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
			CustomerDetailForVisitAsynTask customerDetailForVisitAsynTask = new CustomerDetailForVisitAsynTask(CustomerDetailForVisitActivity.this);
			customerDetailForVisitAsynTask.execute();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mApp.getUserType() != 1){
			mApp.setCustomer(null);
		}
	}
	
	private class CustomerDetailForVisitAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private MethodResponseCustomerDetailForVisit methodResponseCustomerDetailForVisit;
		private String errorMessage;
		private ProgressDialog progressDialog;
		
		public CustomerDetailForVisitAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading ...");
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				
				methodResponseCustomerDetailForVisit = webServiceObjectClient.getCustomerDetailForVisit(customerCode);
				if(methodResponseCustomerDetailForVisit != null){
					if(methodResponseCustomerDetailForVisit.getResponseCode() == 0){
						
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseCustomerDetailForVisit.getResponseMessage();
						return methodResponseCustomerDetailForVisit.getResponseCode();
					}
				}else{
					errorMessage = Constant.MESSAGE_NULL_RESPONSE;
					return Constant.RESULT_NULL_RESPONSE;
				}
			}catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			}catch (UnauthorizedException e) {
				e.printStackTrace();
				return Constant.RESULT_AUTHENTICATION_FAILURE;
			}catch (Exception e) {
				e.printStackTrace();
				errorMessage = e.getMessage();
				return -1;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result == Constant.RESULT_SUCCESS){
				
				CustomerVisit customerVisit = methodResponseCustomerDetailForVisit.getData();
				
				TextView tvTalkToCustomerAbout = new TextView(getApplicationContext());
				tvTalkToCustomerAbout.setText("Talk to the customer about :");
				tvTalkToCustomerAbout.setTextColor(getResources().getColor(R.color.black));
				tvTalkToCustomerAbout.setTextAppearance(getApplicationContext(), R.style.textview_bold);
				llTalkAbout.addView(tvTalkToCustomerAbout);
				
				TalkAbout[] talkAbouts = customerVisit.getTalkAbout();
				if(talkAbouts != null && talkAbouts.length > 0){
					for(TalkAbout talkAbout : customerVisit.getTalkAbout()){
						
						TextView tvCategoryDesc = new TextView(getApplicationContext());
						tvCategoryDesc.setText(talkAbout.getPriority()+" "+talkAbout.getCategory_desc());
						tvCategoryDesc.setTextColor(getResources().getColor(R.color.black));
						llTalkAbout.addView(tvCategoryDesc);
					}
				}
				
				Date date = new Date(MyDate.convertIntegerToStringDate1(customerVisit.getCurrentDate()));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				
				calendar.set(Calendar.DATE, 1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				
				if(customerVisit.getReceiptOne().compareTo(new BigDecimal(0)) == -1){
					customerVisit.setReceiptOne(customerVisit.getReceiptOne().multiply(new BigDecimal(-1)));
				}
				if(customerVisit.getReceiptTwo().compareTo(new BigDecimal(0)) == -1){
					customerVisit.setReceiptTwo(customerVisit.getReceiptTwo().multiply(new BigDecimal(-1)));
				}
				if(customerVisit.getReceiptThree().compareTo(new BigDecimal(0)) == -1){
					customerVisit.setReceiptThree(customerVisit.getReceiptThree().multiply(new BigDecimal(-1)));
				}
				//int receiptOne = customerVisit.getReceiptOne()<0?(customerVisit.getReceiptOne().multiply(multiplicand)-1):customerVisit.getReceiptOne();
				
				txtClosingBalance.setText("Closing balance as on "+MyDate.createStringDate(calendar)+" : "+customerVisit.getClosingBalance());
				txtSalesOfMonthOne.setText("Sales of "+getMonthYear(calendar)+" : "+customerVisit.getSalesOfPreviousMonthOne());
				txtReceiptOne.setText("Receipt of "+getMonthYear(calendar)+" : "+customerVisit.getReceiptOne());
				calendar.add(Calendar.MONTH, -1);
				txtSalesOfMonthTwo.setText("Sales of "+getMonthYear(calendar)+" : "+customerVisit.getSalesOfPreviousMonthTwo());
				txtReceiptTwo.setText("Receipt of "+getMonthYear(calendar)+" : "+customerVisit.getReceiptTwo());
				calendar.add(Calendar.MONTH, -1);
				txtSalesOfMonthThree.setText("Sales of "+getMonthYear(calendar)+" : "+customerVisit.getSalesOfPreviousMonthThree());
				txtReceiptThree.setText("Receipt of "+getMonthYear(calendar)+" : "+customerVisit.getReceiptThree());
				txtAvarageSales.setText("Avarage sale : "+customerVisit.getAverageSale());
				txtOutstandingDays.setText("Outstanding days : "+customerVisit.getOutStandingDay());
				
			}else if(result == Constant.RESULT_SAP_ERROR){
				//MyToast myToast = new MyToast();
				MyToast.show(getApplication(), -1, errorMessage);
			} else{
				MyToast.show(getApplication(), result, errorMessage);
			}
			
			progressDialog.dismiss();
		}
	}
	
	private String getMonthYear(Calendar calendar){
		Date date = new Date();
		date.setTime(calendar.getTimeInMillis());
		return new SimpleDateFormat("MMM").format(date)+" - "+calendar.get(Calendar.YEAR);
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

	private void getLocation(){
		progressDialog = new ProgressDialog(CustomerDetailForVisitActivity.this);
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
				Toast.makeText(CustomerDetailForVisitActivity.this, "GPS Not Avialable",
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
				RegisterVisitAsynTask registerVisitAsynTask = new RegisterVisitAsynTask();
				registerVisitAsynTask.execute();
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
	
	private class RegisterVisitAsynTask extends AsyncTask<Void, Void, Integer>{

		private MethodResponseOk methodResponseOk;
		private String errorMessage;
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				/*String username,String customerCode,String visit_purpose,
				double latitude, double longitude*/
				methodResponseOk = webServiceObjectClient.registerVisit(mApp.getUserName(),customerCode,
						id,location.getLatitude(),location.getLongitude());
				if(methodResponseOk != null){
					if(methodResponseOk.getResponseCode() == 0){
						
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseOk.getResponseMessage();
						return methodResponseOk.getResponseCode();
					}
				}else{
					errorMessage = Constant.MESSAGE_NULL_RESPONSE;
					return Constant.RESULT_NULL_RESPONSE;
				}
			}catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			}catch (UnauthorizedException e) {
				e.printStackTrace();
				return Constant.RESULT_AUTHENTICATION_FAILURE;
			}catch (Exception e) {
				e.printStackTrace();
				errorMessage = e.getMessage();
				return -1;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result == Constant.RESULT_SUCCESS){
				Toast.makeText(getApplicationContext(), "Visit registered successfully", Toast.LENGTH_SHORT).show();
			}else{
				MyToast myToast = new MyToast();
				myToast.show(getApplication(), result, errorMessage);
			}
			
			progressDialog.cancel();
		}
	}
}
