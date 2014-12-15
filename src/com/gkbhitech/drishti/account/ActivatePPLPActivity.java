package com.gkbhitech.drishti.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.dateslider.AlternativeDateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider.OnDateSetListener;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.CustomerReceipt;
import com.gkbhitech.drishti.model.Executive;
import com.gkbhitech.drishti.model.InhouseBank;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseSoldBy;

public class ActivatePPLPActivity extends Activity {

	private static final String tag = "ActivatePPLPActivity";

	// .............. variable used in UI ................
	private ImageView imvBack,imvHome;
	private TextView txtCustomer,txtChequeDate,txtPostingDate,txtInhouseBank,txtPlant;
	private Spinner spnSoldBy,spnInhouseBank,spnPaymentMode;
	private EditText etMasterCode,edtBank,edtChequeNo,edtAmount;
	private Button btnSave;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode,paymentMode,chequeNo,bankName,sap_gl,soldBy;
	private Long chequeDate;
	
	private CustomerReceipt customerReceipt = new CustomerReceipt();
	private Calendar selectedChequeDate;
	
	private ProgressDialog progressDialog;
	private List<InhouseBank> inhouseBanks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_activate_pplp);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlant = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		etMasterCode = (EditText) findViewById(R.id.edt_mastercode);
		spnSoldBy = (Spinner) findViewById(R.id.spn_soldby);
		spnPaymentMode = (Spinner) findViewById(R.id.spn_payment_mode);
		edtChequeNo = (EditText) findViewById(R.id.edt_cheque_no);
		txtChequeDate = (TextView) findViewById(R.id.txt_cheque_date);
		edtBank = (EditText) findViewById(R.id.edt_bank);
		spnInhouseBank = (Spinner) findViewById(R.id.spn_inhouse_bank);
		txtInhouseBank = (TextView) findViewById(R.id.txt_inhouse_bank);
		btnSave = (Button) findViewById(R.id.btn_save);
		
		FetchSolByAsynTask fetchSolByAsynTask = new FetchSolByAsynTask();
		fetchSolByAsynTask.execute();
		
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
		
		FatchInhouseBankAsynTask fatchInhouseBankAsynTask = new FatchInhouseBankAsynTask();
		fatchInhouseBankAsynTask.execute();
		
		txtPlant.setText(mApp.getPlant()+"");
		customerReceipt.setRplant(mApp.getPlant());
		
		if(mApp.getUserType() != 1){
			txtCustomer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getApplicationContext(), SelectCustomerForActivatePPLPActivity.class);
					startActivity(i);
				}
			});
		}
		
		/*InputFilter filter = new InputFilter() {public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) {

				String temp = source.toString();
				Pattern p = Pattern.compile("[^a-zA-Z0-9]");
				Matcher m = p.matcher(source.toString());
	
				if (m.find()) {
					return temp.substring(start, end - 1);
				}
				return null;

			}
		};
		etMasterCode.setFilters(new InputFilter[] { filter });*/
	
		ArrayAdapter<String> arrayAdapterPaymentMode = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.paymentModeArray);
		spnPaymentMode.setAdapter(arrayAdapterPaymentMode);
		
		spnPaymentMode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				paymentMode = Constant.paymentModeArray[position];
				customerReceipt.setPaymentMode(paymentMode);
				if(position == 0){
					edtChequeNo.setVisibility(View.GONE);
					txtChequeDate.setVisibility(View.GONE);
					txtInhouseBank.setVisibility(View.GONE);
					spnInhouseBank.setVisibility(View.GONE);
					edtBank.setVisibility(View.GONE);
				}else if(position == 1){
					edtChequeNo.setVisibility(View.VISIBLE);
					txtChequeDate.setVisibility(View.VISIBLE);
					txtInhouseBank.setVisibility(View.VISIBLE);
					spnInhouseBank.setVisibility(View.VISIBLE);
					edtBank.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Date date = new Date();
		
		/*final OnDateSetListener onChequeDateSetListener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				// TODO Auto-generated method stub
				if(Constant.log) Log.i(tag, selectedDate.get(selectedDate.DATE)+"-"+selectedDate.get(selectedDate.MONTH)+"-"+selectedDate.get(selectedDate.YEAR));
				txtChequeDate.setText(selectedDate.get(selectedDate.DATE)+"-"+(selectedDate.get(selectedDate.MONTH)+1)+"-"+selectedDate.get(selectedDate.YEAR));
				chequeDate = createLongDate(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH)+1, selectedDate.get(selectedDate.DATE));
			}
		};*/
		
		/*txtChequeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Dialog dialog = new AlternativeDateSlider(ActivatePPLPActivity.this, onChequeDateSetListener, new GregorianCalendar(), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()), null);
				dialog.show();
			}
		});*/
		
		final OnDateSetListener onChequeDateSetListener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				// TODO Auto-generated method stub
				if(Constant.log) Log.i(tag, selectedDate.get(selectedDate.DATE)+"-"+selectedDate.get(selectedDate.MONTH)+"-"+selectedDate.get(selectedDate.YEAR));
				txtChequeDate.setText(selectedDate.get(selectedDate.DATE)+"-"+(selectedDate.get(selectedDate.MONTH)+1)+"-"+selectedDate.get(selectedDate.YEAR));
				chequeDate = createLongDate(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH)+1, selectedDate.get(selectedDate.DATE));
				customerReceipt.setcDate(chequeDate);
			}
		};
		
		txtChequeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Calendar before = Calendar.getInstance();
				before.add(Calendar.MONTH, -3);
				Calendar after = Calendar.getInstance();
				after.add(Calendar.MONTH, 3);
			
				Dialog dialog = null;
				if(selectedChequeDate == null){
					dialog = new AlternativeDateSlider(ActivatePPLPActivity.this, onChequeDateSetListener, new GregorianCalendar(), before, after);
				}else{
					dialog = new AlternativeDateSlider(ActivatePPLPActivity.this, onChequeDateSetListener, selectedChequeDate, before, after);
				}
				dialog.show();
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(customerReceipt.getCustomer() == null){
					Toast.makeText(getApplicationContext(), "Please select customer", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String masterCode = etMasterCode.getText().toString().trim();
				if(masterCode == null && masterCode.equals("")){
					Toast.makeText(getApplicationContext(), "Please enter master code", Toast.LENGTH_SHORT).show();
					return;
				}
				customerReceipt.setMasterCode(masterCode);
				
				if(customerReceipt.getSold_by() == null){
					Toast.makeText(getApplicationContext(), "Sold by is mandtory", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(customerReceipt.getPaymentMode().equals(Constant.paymentModeArray[1])){
				
					chequeNo = edtChequeNo.getText().toString();
					
					try{
						new Long(chequeNo);
					}catch (NumberFormatException e) {
						// TODO: handle exception
					}
					
					bankName = edtBank.getText().toString();
					
					customerReceipt.setBankName(bankName);
					customerReceipt.setChequeNo(chequeNo);
					customerReceipt.setcDate(chequeDate);
					customerReceipt.setBank(sap_gl);
					
					if(customerReceipt.getcDate() == null){
						Toast.makeText(getApplicationContext(), "Please enter cheque date", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if(chequeNo == null || chequeNo.equals("")){
						Toast.makeText(getApplicationContext(), "Please enter cheque number", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if(bankName == null || bankName.equals("")){
						Toast.makeText(getApplicationContext(), "Please enter bank", Toast.LENGTH_SHORT).show();
						return;
					}
					
				}else{
					customerReceipt.setBankName(null);
					customerReceipt.setChequeNo(null);
					customerReceipt.setcDate(null);
					customerReceipt.setBank(null);
				}
				
				CustomerReceiptFormAsynTask customerReceiptFormAsynTask = new CustomerReceiptFormAsynTask(ActivatePPLPActivity.this, mApp, dataBaseAdapter, Constant.URL_ACTIVATE_PPLP, customerReceipt);
				customerReceiptFormAsynTask.execute();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerReceipt.setCustomer(customer.getCust_code());
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
	
	private class FetchSolByAsynTask extends AsyncTask<Void, Void, Integer>{

		private List<String> executive = new ArrayList<String>();
		private MethodResponseSoldBy methodResponseSoldBy = new MethodResponseSoldBy();
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(ActivatePPLPActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading executive ...");
			progressDialog.show();
		}
		@Override
		protected Integer doInBackground(Void... params) {
			
			try {
				methodResponseSoldBy = webServiceObjectClient.getSoldBy(mApp.getAccessToken(), mApp.getPlant());
				if(methodResponseSoldBy != null){
					Constant.responseCode = methodResponseSoldBy.getResponseCode();
					Constant.responseMsg = methodResponseSoldBy.getResponseMessage();
					return Constant.responseCode;
					
				}else{
					return Constant.RESULT_NULL_RESPONSE;
				}
			} catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			} catch (Exception e) {
				e.printStackTrace();
				return Constant.RESULT_ERROR;
			}
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			progressDialog.dismiss();
			
			if(result == Constant.RESULT_SUCCESS){
				final Executive[] executiveArray = methodResponseSoldBy.getDataArray();
				
				ArrayAdapter<String> arrayAdapterSoldBy = new ArrayAdapter<String>(ActivatePPLPActivity.this, R.layout.spinner_simple_item_1);
				
				for(Executive executive : executiveArray){
					arrayAdapterSoldBy.add(executive.getExecutive_name());
				}
				
				spnSoldBy.setAdapter(arrayAdapterSoldBy);
				
				spnSoldBy.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
						// TODO Auto-generated method stub
						customerReceipt.setSold_by(executiveArray[position].getExecutive_name());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}else{
				MyToast myToast = new MyToast();
				myToast.show(getApplicationContext(), result, Constant.responseMsg);
			}
		}
	}
	
	private class FatchInhouseBankAsynTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*progressDialog = new ProgressDialog(ActivatePPLPActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Inhouse Bank...");
			progressDialog.show();*/
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			inhouseBanks = dataBaseAdapter.getInhouseBank(mApp.getPlant());
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			if(inhouseBanks != null && inhouseBanks.size() > 0){
				
				List<String> inhouseBankDesc = new ArrayList<String>();
				for(InhouseBank inhouseBank : inhouseBanks){ 
					 inhouseBankDesc.add(inhouseBank.getBank_desc());
				}
				
				ArrayAdapter<String> arrayAdapterInhouseBank = new ArrayAdapter<String>(ActivatePPLPActivity.this, R.layout.spinner_simple_item_1, inhouseBankDesc);
				spnInhouseBank.setAdapter(arrayAdapterInhouseBank);
			}
			
			spnInhouseBank.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					InhouseBank inhouseBank = inhouseBanks.get(position);
					sap_gl = inhouseBank.getSap_gl();
					customerReceipt.setBank(sap_gl);
					if(Constant.log) Log.i(tag, "Bank Name : "+inhouseBank.getBank_desc()+" sap_gl : "+inhouseBank.getSap_gl());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			/*progressDialog.cancel();*/
		}
	}
	
	private Long createLongDate(int year, int monthOfYear,int dayOfMonth){
		
		String month = "";
		String day = "";
		
		if(monthOfYear < 10){
			month = "0"+monthOfYear;
		}else{
			month = monthOfYear+"";
		}
		
		if(dayOfMonth < 10){
			day = "0"+dayOfMonth;
		}else{
			day = dayOfMonth+"";
		}
		
		return new Long(year+month+day);
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*

	private static final String tag = "ActivatePPLPActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtCustomer;
	private TextView txtChequeDate;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	private Long chequeDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_activate_pplp);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		txtChequeDate = (TextView) findViewById(R.id.txt_cheque_date);
		
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
		
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
	
		final Date date = new Date();
		
		final OnDateSetListener onChequeDateSetListener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				// TODO Auto-generated method stub
				if(Constant.log) Log.i(tag, selectedDate.get(selectedDate.DATE)+"-"+selectedDate.get(selectedDate.MONTH)+"-"+selectedDate.get(selectedDate.YEAR));
				txtChequeDate.setText(selectedDate.get(selectedDate.DATE)+"-"+(selectedDate.get(selectedDate.MONTH)+1)+"-"+selectedDate.get(selectedDate.YEAR));
				chequeDate = createLongDate(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH)+1, selectedDate.get(selectedDate.DATE));
			}
		};
		
		txtChequeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Dialog dialog = new AlternativeDateSlider(ActivatePPLPActivity.this, onChequeDateSetListener, new GregorianCalendar(), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()), null);
				dialog.show();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mApp.setCustomer(null);
	}
	private Long createLongDate(int year, int monthOfYear,int dayOfMonth){
		
		String month = "";
		String day = "";
		
		if(monthOfYear < 10){
			month = "0"+monthOfYear;
		}else{
			month = monthOfYear+"";
		}
		
		if(dayOfMonth < 10){
			day = "0"+dayOfMonth;
		}else{
			day = dayOfMonth+"";
		}
		
		return new Long(year+month+day);
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
*/}
