package com.gkbhitech.drishti.account;

import java.text.DecimalFormat;

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
import android.widget.Toast;

public class PPLPBalanceActivity extends Activity{

	private static final String tag = "ActivatePPLPActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlant;
	private TextView txtCustomer;
	/*private Button btnShow;*/
	private TextView txtCustomerBalance;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	private DecimalFormat df = new DecimalFormat("#.##");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_pplp_balance);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlant = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		/*btnShow = (Button) findViewById(R.id.btn_show);*/
		txtCustomerBalance = (TextView) findViewById(R.id.txt_customer_balance);
		
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
		
		if(mApp.getUserType() != 1){
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerForCustomerBalanceActivity.class);
				startActivity(i);
			}
		});
		}
		/*btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
			txtCustomerBalance.setVisibility(View.GONE);
			PPLPBalanceAsynTask pplpBalanceAsynTask = new PPLPBalanceAsynTask(PPLPBalanceActivity.this, webServiceObjectClient, customerCode);
			pplpBalanceAsynTask.execute();
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
	
	public class PPLPBalanceAsynTask extends AsyncTask<Void, Void, Integer>{

		private static final String tag = "PPLPBalanceAsynTask";
		private Context context;
		private WebServiceObjectClient webServiceObjectClient;
		private ProgressDialog progressDialog;
		private MethodResponseCustomerBalance methodResponseCustomerBalance;
		private String customerCode;
		private String errorMessage = "error";
		private double balance;
		
		public PPLPBalanceAsynTask(Context context ,WebServiceObjectClient webServiceObjectClient, String customerCode){
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
				txtCustomerBalance.setText("Your balance is Rs."+df.format(balance));
				txtCustomerBalance.setVisibility(View.VISIBLE);
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
