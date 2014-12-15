package com.gkbhitech.drishti.order;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.gkb.ComputeQuoteFormActivity;
import com.gkbhitech.drishti.gkb.ComputeQuoteFormAsynTask;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.order.SelectCustomerActivity;

public class TrackOrder extends Activity{

	private static final String tag = "TrackOrderActivity";
	
	private ImageView imvBack;
	private ImageView imvHome;
	//private Spinner spnCustomer;
	private TextView txtCustomer;
	private LinearLayout llCustomer;

	private Spinner spnProductBrand;
	private Spinner spnLensCode;
	private Spinner spnCoating;
	private Spinner spnMaterialType;
	private Button btnShow;
		
	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	private List<Customer> customers = new ArrayList<Customer>();
	private List<String> customerNames = new ArrayList<String>();
	
	private String customerCode;
	private Customer customer;
	
	private int brandCode;
	private String lensCode;
	private String coatingCode;
	private String materialType; // F5C547

	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<Lens> lenses = new ArrayList<Lens>();
	private List<Coating> coatings = new ArrayList<Coating>();

	private String[] arrayMaterialType = { "Stock", "Rx" };
	
	/*************** Change buttons to imageView ****************************/
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_track_order);
		
		
		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		/*************** Change buttons to imageView ****************************/
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		llCustomer = (LinearLayout) findViewById(R.id.ll_select_customer);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		
		spnProductBrand = (Spinner) findViewById(R.id.spn_product_brand);
		spnLensCode = (Spinner) findViewById(R.id.spn_lens_code);
		spnCoating = (Spinner) findViewById(R.id.spn_coating);
		spnMaterialType = (Spinner) findViewById(R.id.spn_material_type);
		btnShow = (Button) findViewById(R.id.btn_show);
		
		ArrayAdapter<Double> arrayAdapterSph = new ArrayAdapter<Double>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				Constant.sph);
		ArrayAdapter<Double> arrayAdapterCyl = new ArrayAdapter<Double>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				Constant.cylZeroToMinusFour);

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
				startActivity(i);
			}
		});
		
		llCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
		
		ArrayAdapter<String> arrayAdapterLensCoats = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				arrayMaterialType);
		spnMaterialType.setAdapter(arrayAdapterLensCoats);

		spnMaterialType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == 0) {
					materialType = "'P','F'";
				} else if (position == 1) {
					materialType = "'S'";
				}

				FatchProductBrandAsynTask fatchProductBrandAsynTask = new FatchProductBrandAsynTask(
						TrackOrder.this);
				fatchProductBrandAsynTask.execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (customerCode == null) {
					Toast.makeText(getApplicationContext(),
							"Please select customer", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				try {
				} catch (NumberFormatException e) {
					Toast.makeText(TrackOrder.this,
							"Please select proper values of axis,prism",
							Toast.LENGTH_SHORT).show();
					return;
				}

				/*TrackOrderAsynTask trackOrAsynTask = new TrackOrderAsynTask(
						TrackOrder.this, mApp, customerCode,
						brandCode, lensCode, coatingCode, materialType);
				trackOrAsynTask.execute();*/
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mApp.setCustomer(null);if(mApp.getUserType() != 1){
			mApp.setCustomer(null);
		}
	}

	private class FatchProductBrandAsynTask extends AsyncTask<Void, Void, Void> {

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

			productBrands = dataBaseAdapter.getProductBrand(materialType);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (productBrands != null && productBrands.size() > 0) {

				List<String> productBrandDesc = new ArrayList<String>();
				for (ProductBrand productBrand : productBrands) {
					productBrandDesc.add(productBrand.getBrand_desc());
				}

				ArrayAdapter<String> arrayAdapterProductBrand = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_simple_item_1, productBrandDesc);
				spnProductBrand.setAdapter(arrayAdapterProductBrand);
			}else{
				progressDialog.cancel();
			}

			spnProductBrand.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long arg3) {
							// TODO Auto-generated method stub
							ProductBrand productBrand = productBrands
									.get(position);
							brandCode = productBrand.getBrand_code();
							if (Constant.log)
								Log.i(tag, ".......product brand code : "
										+ productBrand.getBrand_code() + " : "
										+ productBrand.getBrand_desc());

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

	private class FatchLensAsynTask extends AsyncTask<Void, Void, Void> {

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
			lenses = dataBaseAdapter.getLens(brandCode, materialType);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (lenses != null && lenses.size() > 0) {

				List<String> lensDescription = new ArrayList<String>();
				for (Lens lens : lenses) {
					lensDescription.add(lens.getDescription());
				}

				ArrayAdapter<String> arrayAdapterLensCode = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_simple_item_1, lensDescription);
				spnLensCode.setAdapter(arrayAdapterLensCode);
			}

			// progressDialog.cancel();

			spnLensCode.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Lens lens = lenses.get(position);
					lensCode = lens.getLens_code();
					if (Constant.log)
						Log.i(tag, ".......lens code : " + lens.getLens_code()
								+ " : " + lens.getDescription());

					FatchCoatingAsynTask fatchCoatingAsynTask = new FatchCoatingAsynTask();
					fatchCoatingAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	private class FatchCoatingAsynTask extends AsyncTask<Void, Void, Void> {

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

			if (coatings != null && coatings.size() > 0) {

				List<String> coatingDescription = new ArrayList<String>();
				for (Coating coating : coatings) {
					coatingDescription.add(coating.getCoating_desc());
				}

				ArrayAdapter<String> arrayAdapterLensCoats = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_simple_item_1, coatingDescription);
				spnCoating.setAdapter(arrayAdapterLensCoats);
			}

			progressDialog.cancel();

			spnCoating.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Coating coating = coatings.get(position);
					coatingCode = coating.getCoating_code();
					if (Constant.log)
						Log.i(tag,
								".......coating code : "
										+ coating.getCoating_code() + " : "
										+ coating.getCoating_desc());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
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
