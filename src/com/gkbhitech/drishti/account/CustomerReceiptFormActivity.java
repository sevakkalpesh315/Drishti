package com.gkbhitech.drishti.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.dateslider.AlternativeDateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider.OnDateSetListener;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.CustomerReceipt;
import com.gkbhitech.drishti.model.InhouseBank;

public class CustomerReceiptFormActivity extends Activity{

	private static final String tag = "ReceiptActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlant;
	private TextView txtCustomer;
	private TextView txtChequeDate;
	private TextView txtPostingDate;
	private TextView txtInhouseBank;
	private Spinner spnInhouseBank;
	private Spinner spnPaymentMode;
	private EditText edtNarration;
	private EditText edtBank;
	private EditText edtChequeNo;
	private EditText edtAmount;
	private Button btnSave;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	private Long chequeDate;
	private Long postingDate;
	private String sap_gl;
	private String paymentMode;
	private String narration;
	private String bankName;
	private String chequeNo;
	private BigDecimal amount;
	private Calendar selectedChequeDate;
	//private String narrationRang = "~!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
	
	private String[] paymentModeArray = {"CASH","CHEQUE"};
	
	private List<InhouseBank> inhouseBanks;
	
	private CustomerReceipt customerReceipt;
	
	private Calendar now = Calendar.getInstance();
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_receipt_form);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		customerReceipt = new CustomerReceipt();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlant = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		txtPostingDate = (TextView) findViewById(R.id.txt_posting_date);
		edtAmount = (EditText) findViewById(R.id.edt_amount);
		edtNarration = (EditText) findViewById(R.id.edt_narration);
		spnPaymentMode = (Spinner) findViewById(R.id.spn_payment_mode);
		edtChequeNo = (EditText) findViewById(R.id.edt_cheque_no);
		txtChequeDate = (TextView) findViewById(R.id.txt_cheque_date);
		spnInhouseBank = (Spinner) findViewById(R.id.spn_inhouse_bank);
		txtInhouseBank = (TextView) findViewById(R.id.txt_inhouse_bank);
		edtBank = (EditText) findViewById(R.id.edt_bank);
		btnSave = (Button) findViewById(R.id.btn_save);
		
		edtChequeNo.setVisibility(View.GONE);
		txtChequeDate.setVisibility(View.GONE);
		txtInhouseBank.setVisibility(View.GONE);
		spnInhouseBank.setVisibility(View.GONE);
		edtBank.setVisibility(View.GONE);
		
		FatchInhouseBankAsynTask fatchInhouseBankAsynTask = new FatchInhouseBankAsynTask();
		fatchInhouseBankAsynTask.execute();
		
		txtPlant.setText(mApp.getPlant()+"");
		
		customerReceipt.setRplant(mApp.getPlant());
		
		txtPostingDate.setText(now.get(Calendar.DAY_OF_MONTH)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.YEAR));
		
		ArrayAdapter<String> arrayAdapterPaymentMode = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, paymentModeArray);
		spnPaymentMode.setAdapter(arrayAdapterPaymentMode);
		
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
				Intent i = new Intent(getApplicationContext(), SelectCustomerForReceiptActivity.class);
				startActivity(i);
			}
		});
		
		spnPaymentMode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				paymentMode = paymentModeArray[position];
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
		
		postingDate = createLongDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH));
		customerReceipt.settDate(postingDate);
		customerReceipt.setDocDate(postingDate);
		
		InputFilter filter = new InputFilter() { public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
	           
				String temp = source.toString();
				Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");  
			    Matcher m = p.matcher(source.toString()); 
				
			    if(m.find()){
			    	return temp.substring(start,end-1);
			    }
			    return null;
			
	        } 
		}; 
		edtNarration.setFilters(new InputFilter[]{filter});
		
		edtChequeNo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
					dialog = new AlternativeDateSlider(CustomerReceiptFormActivity.this, onChequeDateSetListener, new GregorianCalendar(), before, after);
				}else{
					dialog = new AlternativeDateSlider(CustomerReceiptFormActivity.this, onChequeDateSetListener, selectedChequeDate, before, after);
				}
				dialog.show();
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				narration = edtNarration.getText().toString();
				//customerReceipt.setCustomer("C2003B0222");
				
				if(customerReceipt.getCustomer() == null){
					Toast.makeText(getApplicationContext(), "Please select customer", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(customerReceipt.gettDate() == null && customerReceipt.getDocDate() == null){
					Toast.makeText(getApplicationContext(), "Please select posting date", Toast.LENGTH_SHORT).show();
					return;
				}
				
				try{
					amount = new BigDecimal(edtAmount.getText().toString());
				}catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(customerReceipt.getPaymentMode().equals(paymentModeArray[1])){
				
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
				
				if(narration != null && !narration.equals("")){
					customerReceipt.setNarration(narration);
				}
				customerReceipt.setAmount(amount);
				
				CustomerReceiptFormAsynTask customerReceiptFormAsynTask = new CustomerReceiptFormAsynTask(CustomerReceiptFormActivity.this, mApp, dataBaseAdapter,Constant.URL_CUSTOMER_RECEIPT, customerReceipt);
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
			customerCode = customer.getCust_code();
			customerReceipt.setCustomer(customerCode);
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
	
	private class FatchInhouseBankAsynTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(CustomerReceiptFormActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Inhouse Bank...");
			progressDialog.show();
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
				
				ArrayAdapter<String> arrayAdapterInhouseBank = new ArrayAdapter<String>(CustomerReceiptFormActivity.this, R.layout.spinner_simple_item_1, inhouseBankDesc);
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
			
			progressDialog.cancel();
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
}
