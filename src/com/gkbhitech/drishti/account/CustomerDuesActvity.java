package com.gkbhitech.drishti.account;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDate;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.LastPayment;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLastPayment;

public class CustomerDuesActvity extends Activity {

	private static final String tag = "CustomerDuesActvity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer, txtClosingBalance, txtLastBalance;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;
	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	private String customerCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_customer_dues);
		
		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer); 
		txtClosingBalance = (TextView) findViewById(R.id.txt_closing_balance);
		txtLastBalance = (TextView) findViewById(R.id.txt_last_payment_received_on);
		
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
		
		if(mApp.getUserType() != 1){
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerForReceiptActivity.class);
				startActivity(i);
			}
		});
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtClosingBalance.setVisibility(View.VISIBLE);
			txtLastBalance.setVisibility(View.VISIBLE);
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
			CustomerDuesAsynTask customerDuesAsynTask = new CustomerDuesAsynTask(CustomerDuesActvity.this);
			customerDuesAsynTask.execute();
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
	
	private class CustomerDuesAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private MethodResponseLastPayment methodResponseLastPayment;
		private String errorMessage;
		private ProgressDialog progressDialog;
		
		public CustomerDuesAsynTask(Context context) {
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
				
				methodResponseLastPayment = webServiceObjectClient.getCustomerLastPayment(customerCode);
				if(methodResponseLastPayment != null){
					if(methodResponseLastPayment.getResponseCode() == 0){
						
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseLastPayment.getResponseMessage();
						return methodResponseLastPayment.getResponseCode();
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
				
				LastPayment lastPayment = methodResponseLastPayment.getData();
				
				if(lastPayment.getReceiptNo() != 0){
					String temp = MyDate.convertIntegerToStringDate1(lastPayment.getCurrentDate());
					if(Constant.log)Log.i(tag, "date : "+temp);
					Date date = new Date(MyDate.convertIntegerToStringDate1(lastPayment.getCurrentDate()));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					
					if(Constant.log)Log.i(tag, "Year : "+calendar.get(Calendar.YEAR));
					if(Constant.log)Log.i(tag, "Month : "+calendar.get(Calendar.MONTH));
					if(Constant.log)Log.i(tag, "Date : "+calendar.get(Calendar.DATE));
					
					calendar.set(Calendar.DATE, 1);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					
					if(Constant.log)Log.i(tag, "Year : "+calendar.get(Calendar.YEAR));
					if(Constant.log)Log.i(tag, "Month : "+calendar.get(Calendar.MONTH));
					if(Constant.log)Log.i(tag, "Date : "+calendar.get(Calendar.DATE));
					
					BigDecimal receiptAmt = lastPayment.getReceiptAmt();
					if(receiptAmt.compareTo(new BigDecimal(0)) == -1){
						receiptAmt = receiptAmt.multiply(new BigDecimal(-1)); 
					}
					String debitCredit = "";
					if(lastPayment.getClosingBalance().compareTo(new BigDecimal(0)) == -1){
						debitCredit = "Cr.";
					}else{
						debitCredit = "Dr.";
					}
					
					txtClosingBalance.setText("Closing balance as on "+MyDate.createStringDate(calendar)+" : "+lastPayment.getClosingBalance()+" "+debitCredit);
					txtLastBalance.setText("Last payment of Rs."+receiptAmt+" received on : "+lastPayment.getReceiptDate()+" wide receipt No. "+lastPayment.getReceiptNo());
				}else{
					MyToast myToast = new MyToast();
					myToast.show(getApplication(), Constant.RESULT_RECORD_NOT_FOUND, errorMessage);
				}
				
			}else if(result == Constant.RESULT_SAP_ERROR){
				//MyToast myToast = new MyToast();
				MyToast.show(getApplication(), -1, errorMessage);
			} else{
				MyToast.show(getApplication(), result, errorMessage);
			}
			
			progressDialog.dismiss();
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
