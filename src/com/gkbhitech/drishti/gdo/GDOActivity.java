package com.gkbhitech.drishti.gdo;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.account.PPLPBalanceActivity;
import com.gkbhitech.drishti.account.SelectCustomerForCustomerBalanceActivity;
import com.gkbhitech.drishti.account.PPLPBalanceActivity.PPLPBalanceAsynTask;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerBalance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GDOActivity extends Activity{

	private static final String tag = "GDOActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlant;
	private TextView txtCustomer;
	private Button btnShow;
	private TextView txtGDOBalancePoint;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_gdo);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlant = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		btnShow = (Button) findViewById(R.id.btn_show);
		txtGDOBalancePoint = (TextView) findViewById(R.id.txt_gdo_balance_point);
		
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
		
		txtPlant.setText(""+mApp.getPlant());
		
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerForGDOActivity.class);
				startActivity(i);
			}
		});
		
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//PPLPBalanceAsynTask pplpBalanceAsynTask = new PPLPBalanceAsynTask(PPLPBalanceActivity.this, webServiceObjectClient, customerCode);
				//pplpBalanceAsynTask.execute();
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
			txtGDOBalancePoint.setVisibility(View.GONE);
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
	
	public class GDOAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private WebServiceObjectClient webServiceObjectClient;
		private ProgressDialog progressDialog;
		private MethodResponseCustomerBalance methodResponseCustomerBalance;
		private String customerCode;
		private String errorMessage = "error";
		private double balance;
		
		public GDOAsynTask(Context context ,WebServiceObjectClient webServiceObjectClient, String customerCode){
			this.context = context;
			this.webServiceObjectClient = webServiceObjectClient;
			this.customerCode = customerCode;
			progressDialog = new ProgressDialog(context);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading ...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try{
				if(Constant.log) Log.i(tag, tag+" start");
				
				methodResponseCustomerBalance = webServiceObjectClient.checkCustomerBalance(customerCode);
				if(methodResponseCustomerBalance != null){
					if(methodResponseCustomerBalance.getResponseCode() == Constant.RESULT_SUCCESS){
						balance = methodResponseCustomerBalance.getData().getCustomerBalance();
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseCustomerBalance.getResponseMessage();
						return methodResponseCustomerBalance.getResponseCode();
					}
				}
				//Log.i(tag, "Response : "+response);
				return Constant.RESULT_NULL_RESPONSE;
			}catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
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
			
			progressDialog.dismiss();
			
			switch (result) {
			case Constant.RESULT_SUCCESS:
				if(Constant.log) Log.i(tag, "success");
				txtGDOBalancePoint.setText("Your balance is Rs."+balance);
				txtGDOBalancePoint.setVisibility(View.VISIBLE);
				break;
			default:
				MyToast.show(context, result, errorMessage);
				break;
			}
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
