package com.gkbhitech.drishti.order;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.dateslider.AlternativeDateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider;
import com.gkbhitech.drishti.dateslider.DateSlider.OnDateSetListener;
import com.gkbhitech.drishti.model.Customer;

public class CheckWarrantyActivity extends Activity{

	private static final String tag = "CheckWarrantyActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlantNo;
	private TextView txtCustomer;
	private TextView txtFrom;
	private TextView txtTo;
	private Spinner spnLeSph;
	private Spinner spnLeCyl;
	private Spinner spnReSph;
	private Spinner spnReCyl;
	private DatePicker dpFrom;
	private DatePicker dpTo;
	private Button btnShow;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private String customerCode;
	private Double leSph;
	private Double leCyl;
	private Double reSph;
	private Double reCyl;
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
		
		setContentView(R.layout.activity_checkwarranty);
		
		mApp =  (DrishtiApplication) getApplication();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer); 
		spnLeCyl = (Spinner) findViewById(R.id.spn_le_cyl);
		spnLeSph = (Spinner) findViewById(R.id.spn_le_sph);
		spnReCyl = (Spinner) findViewById(R.id.spn_re_cyl);
		spnReSph = (Spinner) findViewById(R.id.spn_re_sph);
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
		
		txtPlantNo.setText(mApp.getPlant()+"");
		
		ArrayAdapter<Double> arrayAdapterSph = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.sph);
		spnLeSph.setAdapter(arrayAdapterSph);
		spnReSph.setAdapter(arrayAdapterSph);
		ArrayAdapter<Double> arrayAdapterCyl = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.cylZeroToMinusFour);
		spnLeCyl.setAdapter(arrayAdapterCyl);
		spnReCyl.setAdapter(arrayAdapterCyl);
		
		spnLeSph.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				leSph = Constant.sph[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnReSph.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				reSph = Constant.sph[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnLeCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				leCyl = Constant.cylZeroToMinusFour[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnReCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				reCyl = Constant.cylZeroToMinusFour[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
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
					dialog = new AlternativeDateSlider(CheckWarrantyActivity.this, onFromDateSetListener, new GregorianCalendar(), null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
				}else{
					dialog = new AlternativeDateSlider(CheckWarrantyActivity.this, onFromDateSetListener, selectedForm, null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
				}
				dialog.show();
				txtTo.setText("");
				longDateTo = null;
				selectedTo = null;
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
						dialog = new AlternativeDateSlider(CheckWarrantyActivity.this, onToDateSetListener, new GregorianCalendar(), new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
					}else{
						dialog = new AlternativeDateSlider(CheckWarrantyActivity.this, onToDateSetListener, selectedTo, new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
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
				
				//customerCode = "C2005D0001";
				if(customerCode == null){
					Toast.makeText(getApplicationContext(), "Please select customer", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(longDateFrom == null){
					Toast.makeText(getApplicationContext(), "Please select From date", Toast.LENGTH_SHORT).show();
					return;
				}
				if(longDateTo == null){
					Toast.makeText(getApplicationContext(), "Please select To date", Toast.LENGTH_SHORT).show();
					return;
				}
				
				CheckWarrantyAsynTask checkWarrantyAsynTask = new CheckWarrantyAsynTask(CheckWarrantyActivity.this, mApp, customerCode, leSph, leCyl, reSph, reCyl, longDateFrom, longDateTo);
				checkWarrantyAsynTask.execute();
				
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
