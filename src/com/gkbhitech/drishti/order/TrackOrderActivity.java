package com.gkbhitech.drishti.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gkbhitech.drishti.model.LensDesign;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrders;

public class TrackOrderActivity extends Activity{

	private static final String tag = "TrackOrderByCustomerAndDateActivity";
	
	//.............. variable used in UI ................
	private LinearLayout llDesign,llIndex,llAdd;
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer, txtFrom, txtTo;
	private Spinner spnMaterialType, spnBrand, spnIndex, spnDesign, spnAdd;
	private Button btnShow;
	private EditText edtCustomerRefNo;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode;
	private Long longDateFrom;
	private Long longDateTo;
	private Date dateFrom;
	private Calendar selectedForm;
	private Calendar selectedTo;
	
	private List<Customer> customers = new ArrayList<Customer>();
	private List<String> customerNames = new ArrayList<String>();
	private List<String> indexOes = new ArrayList<String>();
	private List<LensDesign> lensDesigns = new ArrayList<LensDesign>();
	
	private MethodResponseOrders methodResponseOrders;
	private String materialType = "", orderType = "", brandName, design = "", indexOe = "";
	private int brandCode;
	private float add = 0;
	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_track_order_customer_and_date);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer); 
		spnMaterialType = (Spinner) findViewById(R.id.spn_material_type);
		spnBrand = (Spinner) findViewById(R.id.spn_brand);
		llDesign = (LinearLayout) findViewById(R.id.ll_design);
		llIndex = (LinearLayout) findViewById(R.id.ll_index);
		llAdd = (LinearLayout) findViewById(R.id.ll_add);
		spnDesign = (Spinner) findViewById(R.id.spn_design);
		spnIndex = (Spinner) findViewById(R.id.spn_index);
		spnAdd = (Spinner) findViewById(R.id.spn_add);
		edtCustomerRefNo = (EditText) findViewById(R.id.edt_cust_ref_no);
		txtFrom = (TextView) findViewById(R.id.txt_from);
		txtTo = (TextView) findViewById(R.id.txt_to);
		btnShow = (Button) findViewById(R.id.btn_show);
		
		FetchBrandAsynTask fetchBrandAsynTask = new FetchBrandAsynTask(TrackOrderActivity.this);
		fetchBrandAsynTask.execute();
		
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
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
		}
		
		ArrayAdapter<String> arrayAdapterMaterialType = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				Constant.arrayMaterialTypeOptional);
		spnMaterialType.setAdapter(arrayAdapterMaterialType);

		spnMaterialType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == 1) {
					materialType = "stock";
					orderType = "S";
					llDesign.setVisibility(View.GONE);
					llIndex.setVisibility(View.GONE);
					llAdd.setVisibility(View.GONE);
				} else if (position == 2) {
					materialType = "rx";
					orderType = "P";
					llDesign.setVisibility(View.VISIBLE);
					llIndex.setVisibility(View.VISIBLE);
					llAdd.setVisibility(View.VISIBLE);
					
					FetchDesignAsynTask fetchDesignAsynTask = new FetchDesignAsynTask(TrackOrderActivity.this);
					fetchDesignAsynTask.execute();
				}else{
					materialType = "";
					orderType = "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		ArrayAdapter<String> arrayAdapterAdd = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1,Constant.addZeroToFourOptional);
		spnAdd.setAdapter(arrayAdapterAdd);
		spnAdd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if(position != 0){
					add = new Float(Constant.addZeroToFourOptional[position]);
				}else{
					add = 0;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		InputFilter filter = new InputFilter() {public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) {

				String temp = source.toString();
				Pattern p = Pattern.compile("[^a-zA-Z0-9]");
				Matcher m = p.matcher(source.toString());
	
				if (m.find()) {
					return temp.substring(start, end - 1);
				}
				return null;
			}
		};
		edtCustomerRefNo.setFilters(new InputFilter[] { filter });
		
		final Date date = new Date();
		selectedForm = Calendar.getInstance();
		selectedForm.add(Calendar.DAY_OF_MONTH, -7);
		txtFrom.setText(selectedForm.get(selectedForm.DATE)+"-"+(selectedForm.get(selectedForm.MONTH)+1)+"-"+selectedForm.get(selectedForm.YEAR));
		longDateFrom = createLongDate(selectedForm.get(selectedForm.YEAR), selectedForm.get(selectedForm.MONTH)+1, selectedForm.get(selectedForm.DATE));
		
		selectedTo = Calendar.getInstance();
		txtTo.setText(selectedTo.get(selectedTo.DATE)+"-"+(selectedTo.get(selectedTo.MONTH)+1)+"-"+selectedTo.get(selectedTo.YEAR));
		longDateTo = createLongDate(selectedTo.get(selectedTo.YEAR), selectedTo.get(selectedTo.MONTH)+1, selectedTo.get(selectedTo.DATE));
		
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
					dialog = new AlternativeDateSlider(TrackOrderActivity.this, onFromDateSetListener, new GregorianCalendar(), null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
				}else{
					dialog = new AlternativeDateSlider(TrackOrderActivity.this, onFromDateSetListener, selectedForm, null, new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
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
			
				//if(Constant.log) Log.i(tag, "To date limit : "+dateFrom.getDate());
				
				if(longDateFrom != null){
					Dialog dialog = null;
					if(selectedTo == null){
						dialog = new AlternativeDateSlider(TrackOrderActivity.this, onToDateSetListener, new GregorianCalendar(), new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
					}else{
						dialog = new AlternativeDateSlider(TrackOrderActivity.this, onToDateSetListener, selectedTo, new GregorianCalendar(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate()), new GregorianCalendar(date.getYear()+1900,date.getMonth(),date.getDate()));
					}
					dialog.show();
				}else{
					Toast.makeText(TrackOrderActivity.this, "First select From date", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
		
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//customerCode = "C2001D0039";
				
				if(customerCode==null){
					Toast.makeText(getApplicationContext(), "Please select Customer", Toast.LENGTH_SHORT).show();
					return;
				}
				String custRefNo = edtCustomerRefNo.getText().toString().trim();
				
				if(Constant.log) Log.i(tag, "From : "+longDateFrom);
				if(Constant.log) Log.i(tag, "To : "+longDateTo);
				if(Constant.log) Log.i(tag, "customerCode : "+customerCode);
				if(Constant.log) Log.i(tag, "materialType : "+materialType);
				if(Constant.log) Log.i(tag, "brandCode : "+brandCode);
				if(Constant.log) Log.i(tag, "design : "+indexOe);
				if(Constant.log) Log.i(tag, "add : "+add);
				if(Constant.log) Log.i(tag, "custRefNo : "+custRefNo);
				
				TrackOrderAsynTask trackOrderAsynTask = new TrackOrderAsynTask(TrackOrderActivity.this,
						mApp, customerCode, orderType, brandCode, design,indexOe,add,custRefNo, longDateFrom, longDateTo);
				trackOrderAsynTask.execute();
				
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
	
	private class FetchBrandAsynTask extends AsyncTask<Void, Void, Void>{

		private Context context;
		
		public FetchBrandAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Brand ...");
			progressDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			productBrands = dataBaseAdapter.getProductBrandForComputeQuote(null);
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//int selectedPosition = 0;
			ArrayAdapter<String> arrayAdapterProductBrand = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
			if(productBrands != null && productBrands.size() > 0){
				
				arrayAdapterProductBrand.add("Select Brand");
				for(ProductBrand productBrand : productBrands){ 
					arrayAdapterProductBrand.add(productBrand.getBrand_desc());
				}
				if(Constant.log) Log.i(tag, ""+spnBrand);
				if(Constant.log) Log.i(tag, ""+arrayAdapterProductBrand.getCount());
			}
			spnBrand.setAdapter(arrayAdapterProductBrand);
			arrayAdapterProductBrand.notifyDataSetChanged();
			
			spnBrand.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						ProductBrand productBrand = productBrands.get(position-1);
						brandName = productBrand.getBrand_desc();
						brandCode = productBrand.getBrand_code();
						if(Constant.log) Log.i(tag, ".......product brand code : "+productBrand.getBrand_code()+" : "+productBrand.getBrand_desc());
						
					}else{
						brandCode = 0;
					}
					if(materialType.equals("rx")){
						FetchDesignAsynTask fetchDesignAsynTask = new FetchDesignAsynTask(TrackOrderActivity.this);
						fetchDesignAsynTask.execute();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			progressDialog.dismiss();
		}
	}
	
	private class FetchDesignAsynTask extends AsyncTask<Void, Void, Void>{

		private Context context;
		public FetchDesignAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Lens Design ...");
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			lensDesigns = dataBaseAdapter.getLensDesigns(brandCode);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			ArrayAdapter<String> arrayAdapterLensDesign = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
			if(lensDesigns != null && lensDesigns.size() > 0){
				arrayAdapterLensDesign.add("Select Design");
				for(LensDesign lensDesign : lensDesigns){
					arrayAdapterLensDesign.add(lensDesign.getDesign_desc());
				}
			}else{
				progressDialog.dismiss();
			}
			spnDesign.setAdapter(arrayAdapterLensDesign);
			arrayAdapterLensDesign.notifyDataSetChanged();
			
			//progressDialog.dismiss();
			
			spnDesign.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					
					if(position != 0){
						design = lensDesigns.get(position-1).getDesign();
					}else{
						design = "";
					}
					
					FetchIndexAsyncTask fetchIndexAsyncTask = new FetchIndexAsyncTask(TrackOrderActivity.this);
					fetchIndexAsyncTask.execute();
					
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});

		}
		
	}
	
	private class FetchIndexAsyncTask extends AsyncTask<Void, Void, Void>{

		private Context context;

		public FetchIndexAsyncTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Index ...");
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (Constant.log)Log.i(tag, "before fetch index");
			indexOes = dataBaseAdapter.getLensIndexForOrderTracking(materialType, brandCode, design);
			if (Constant.log)Log.i(tag, "after fetch index");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			ArrayAdapter<String> arrayAdapterIndex = new ArrayAdapter<String>(
					getApplicationContext(),
					R.layout.spinner_simple_item_1);
			if(indexOes != null && indexOes.size() > 0){
				arrayAdapterIndex.add("Select Index");
				for(String indexOe : indexOes){
					arrayAdapterIndex.add(indexOe);
				}
			}else{
				progressDialog.dismiss();
			}
			spnIndex.setAdapter(arrayAdapterIndex);
			arrayAdapterIndex.notifyDataSetChanged();
			
			spnIndex.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						indexOe = indexOes.get(position-1);
						if (Constant.log)Log.i(tag, "indexOe : "+indexOe);
					}
					//FetchCoridorAsyncTask fetchCoridorAsyncTask = new FetchCoridorAsyncTask(RxOrderActivity.this);
					//fetchCoridorAsyncTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			progressDialog.dismiss();
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
