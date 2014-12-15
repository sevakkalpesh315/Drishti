package com.gkbhitech.drishti.inventory;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDialog;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseInventory;

public class InventorySummaryFormActivity extends Activity{

	private static final String tag = "InventorySummaryForm";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlantNo;
	private Spinner spnProductBrand;
	private Spinner spnLensCode;
	//private Spinner spnCoating;
	private Button btnShow;
	private Button btnValidate;
	private Spinner spnSph;
	private Spinner spnCyl;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
		
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
		
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private MyDialog myDialog;
	
	private Boolean isSphValid = false;
	private Boolean isCylValid = false;
	
	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<LensForInventory> lensForInventories = new ArrayList<LensForInventory>();
	private List<Coating> coatings = new ArrayList<Coating>();
	
	private MethodResponseInventory methodResponseInventory;
	
	private int brandCode;
	private String brandName;
	private String lensCode;
	private String coatingCode;
	private Float sph = new Float(0.00);
	private Float cyl = new Float(0.00);
	private String lensName;
	private String coatingName;
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_inventory_summary_form);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		myDialog = new MyDialog(this);
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		spnProductBrand = (Spinner) findViewById(R.id.spn_product_brand);
		spnLensCode = (Spinner) findViewById(R.id.spn_lens_code);
		//spnCoating = (Spinner) findViewById(R.id.spn_coating);
		spnSph = (Spinner) findViewById(R.id.spn_sph);
		spnCyl = (Spinner) findViewById(R.id.spn_cyl);
		btnShow = (Button) findViewById(R.id.btn_show);
		
		txtPlantNo.setText(mApp.getPlant()+"");
		
		FatchProductBrandAsynTask fatchProductBrandAsynTask = new FatchProductBrandAsynTask(InventorySummaryFormActivity.this);
		fatchProductBrandAsynTask.execute();
		
		ArrayAdapter<Double> arrayAdapterCyl = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.cylZeroToMinusTwo);
		spnCyl.setAdapter(arrayAdapterCyl);
		
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
				Intent i = new Intent(InventorySummaryFormActivity.this, HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		
		spnCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				cyl = new Float(Constant.cylZeroToMinusTwo[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				InventorySummaryFormAsynTask inventorySummaryFormAsynTask = new InventorySummaryFormAsynTask(InventorySummaryFormActivity.this, webServiceObjectClient, mApp.getPlant(), brandCode, lensCode, coatingCode, mApp.getUserName(), sph, cyl, brandName, lensName, coatingName);
				inventorySummaryFormAsynTask.execute();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	private class FatchProductBrandAsynTask extends AsyncTask<Void, Void, Void>{

		private Context context;
		public FatchProductBrandAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Product Brand...");
			progressDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			productBrands = dataBaseAdapter.getProductBrand();
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			if(productBrands != null && productBrands.size() > 0){
				
				ArrayAdapter<String> arrayAdapterProductBrand = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
				
				for(ProductBrand productBrand : productBrands){ 
					arrayAdapterProductBrand.add(productBrand.getBrand_desc());
				}
				
				spnProductBrand.setAdapter(arrayAdapterProductBrand);
			}else{
				progressDialog.dismiss();
			}
			
			spnProductBrand.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					ProductBrand productBrand = productBrands.get(position);
					brandName = productBrand.getBrand_desc();
					brandCode = productBrand.getBrand_code();
					if(Constant.log) Log.i(tag, ".......product brand code : "+productBrand.getBrand_code()+" : "+productBrand.getBrand_desc());
					
					FatchLensAsynTask fatchLensAsynTask = new FatchLensAsynTask();
					fatchLensAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	private class FatchLensAsynTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading Lens...");
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			lensForInventories = dataBaseAdapter.getLens(brandCode);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(lensForInventories != null && lensForInventories.size() > 0){
				
				ArrayAdapter<String> arrayAdapterLensCode = new ArrayAdapter<String>(InventorySummaryFormActivity.this, R.layout.spinner_simple_item_1);
				
				for(LensForInventory lensForInventory : lensForInventories){
					arrayAdapterLensCode.add(lensForInventory.getDescription()+" - "+lensForInventory.getCoating_desc());
				}
				spnLensCode.setAdapter(arrayAdapterLensCode);
			}else{
				progressDialog.dismiss();
			}
			
			progressDialog.dismiss();
			
			spnLensCode.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					LensForInventory lens = lensForInventories.get(position);
					lensName = lens.getDescription();
					lensCode = lens.getLens_code();
					coatingCode = lens.getCoating_code();
					coatingName = lens.getCoating_desc();
					
					if(Constant.log) Log.i(tag, "coating code : "+coatingCode);
					if(Constant.log) Log.i(tag, "max : "+lens.getSphmax()+" min : "+lens.getSphmin());
					
					final List<Float> listOfSph = new ArrayList<Float>();
					for(float i = lens.getSphmin(); i <= lens.getSphmax(); i+=.25){
						listOfSph.add(i);
					}
					
					ArrayAdapter<Float> arrayAdapterSph = new ArrayAdapter<Float>(getApplicationContext(), R.layout.spinner_simple_item_1, listOfSph);
					spnSph.setAdapter(arrayAdapterSph);
					
					spnSph.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
							// TODO Auto-generated method stub
							sph = listOfSph.get(position);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					
					if(Constant.log) Log.i(tag, ".......lens code : "+lens.getLens_code()+" : "+lens.getDescription());
					
					//FatchCoatingAsynTask fatchCoatingAsynTask = new FatchCoatingAsynTask();
					//fatchCoatingAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	/*private class FatchCoatingAsynTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading Coating...");
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			coatings = dataBaseAdapter.getCoatingForInventory(lensCode);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(coatings != null && coatings.size() > 0){
				
				ArrayAdapter<String> arrayAdapterLensCoats = new ArrayAdapter<String>(InventorySummaryFormActivity.this, R.layout.spinner_simple_item_1);
				
				for(Coating coating : coatings){
					arrayAdapterLensCoats.add(coating.getCoating_desc());
				}
				
				spnCoating.setAdapter(arrayAdapterLensCoats);
			}
			
			progressDialog.cancel();
			
			spnCoating.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					Coating coating = coatings.get(position);
					coatingName = coating.getCoating_desc();
					coatingCode = coating.getCoating_code();
					if(Constant.log) Log.i(tag, ".......coating code : "+coating.getCoating_code()+" : "+coating.getCoating_desc());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}*/
	
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
