package com.gkbhitech.drishti.account;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import com.gkbhitech.drishti.order.SelectCustomerActivity;

public class CustomerAccountStatementFormActivity extends Activity{

	private static final String tag = "StatementOfAccountActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtCustomer;
	private TextView txtFrom;
	private TextView txtTo;
	private Button btnShow;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	private Long longDateFrom;
	private Long longDateTo;
	private Date dateFrom;
	private Calendar selectedForm;
	private Calendar selectedTo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_customer_account_statement_form);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		txtFrom = (TextView) findViewById(R.id.txt_from);
		txtTo = (TextView) findViewById(R.id.txt_to);
		btnShow = (Button) findViewById(R.id.btn_show);
		
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
		
		if(mApp.getUserType() != 1){
		txtCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
		}
		
		final Date date = new Date();
		
		final OnDateSetListener onFromDateSetListener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				// TODO Auto-generated method stub
				selectedForm = selectedDate;
				if(Constant.log) Log.i(tag, selectedDate.get(selectedDate.DATE)+"-"+selectedDate.get(selectedDate.MONTH)+"-"+selectedDate.get(selectedDate.YEAR));
				txtFrom.setText(selectedDate.get(selectedDate.DATE)+"-"+(selectedDate.get(selectedDate.MONTH)+1)+"-"+selectedDate.get(selectedDate.YEAR));
				longDateFrom = createLongDate(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH)+1, selectedDate.get(selectedDate.DATE));
				dateFrom = new Date(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH), selectedDate.get(selectedDate.DATE));
			}
		};
		
		txtFrom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Dialog dialog = null;
				if(selectedForm == null){
					dialog = new AlternativeDateSlider(CustomerAccountStatementFormActivity.this, onFromDateSetListener, new GregorianCalendar(), null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
				}else{
					dialog = new AlternativeDateSlider(CustomerAccountStatementFormActivity.this, onFromDateSetListener, selectedForm, null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
				}
				dialog.show();
				txtTo.setText("");
				longDateTo = null;
			}
		});
		
		final OnDateSetListener onToDateSetListener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				// TODO Auto-generated method stub
				selectedTo = selectedDate;
				if(Constant.log) Log.i(tag, selectedDate.get(selectedDate.DATE)+"-"+selectedDate.get(selectedDate.MONTH)+"-"+selectedDate.get(selectedDate.YEAR));
				txtTo.setText(selectedDate.get(selectedDate.DATE)+"-"+(selectedDate.get(selectedDate.MONTH)+1)+"-"+selectedDate.get(selectedDate.YEAR));
				longDateTo = createLongDate(selectedDate.get(selectedDate.YEAR), selectedDate.get(selectedDate.MONTH)+1, selectedDate.get(selectedDate.DATE));
			}
		};
		
		txtTo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if(longDateFrom != null){
					Dialog dialog = null;
					if(selectedTo == null){
						dialog = new AlternativeDateSlider(CustomerAccountStatementFormActivity.this, onToDateSetListener, new GregorianCalendar(), new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
					}else{
						dialog = new AlternativeDateSlider(CustomerAccountStatementFormActivity.this, onToDateSetListener, selectedTo, new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
					}
					dialog.show();
				}else{
					Toast.makeText(getApplicationContext(), "First select From date", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//customerCode = "C2003B0222";
				if(customerCode == null){
					Toast.makeText(getApplicationContext(), "Please select customer", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(longDateFrom == null || longDateTo == null){
					Toast.makeText(getApplicationContext(), "Please select date range", Toast.LENGTH_SHORT).show();
					return;
				}
				
				CustomerAccountStatementFormAsynTask customerAccountStatementFormAsynTask = new CustomerAccountStatementFormAsynTask(CustomerAccountStatementFormActivity.this, mApp, webServiceObjectClient, customerCode, longDateFrom,longDateTo);
				customerAccountStatementFormAsynTask.execute();
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
		if(mApp.getUserType() != 1){
			mApp.setCustomer(null);
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
