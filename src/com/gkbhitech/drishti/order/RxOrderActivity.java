package com.gkbhitech.drishti.order;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.LensDesign;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.Service;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderStockLens;

public class RxOrderActivity extends Activity{

	private static final String tag = "RxOrderActivity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer;
	private EditText edtCustomerRefNo;
	private Spinner spnBrand, spnDesign, spnProduct, spnIndex, spnCorridor, spnLensCode, spnCoating, spnFitting, spnDia,
			spnLeSph, spnLeCyl, spnReSph, spnReCyl, spnReAxis, spnLeAxis, spnReAdd, spnLeAdd, spnReQty, spnLeQty;
	private Button btnPlaceOrder;
	
	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode, product, indexOe, corridor, lensCode, design, coatingName, coatingCode, brandName,
			fittingCode;
	private int brandCode, axisRe, axisLe, qtyRe, qtyLe, dia;
	private float sphLe, sphRe, cylRe, cylLe, addRe, addLe;
	
	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<Lens> lenses = new ArrayList<Lens>();
	private List<Lens> products = new ArrayList<Lens>();
	private List<String> indexOes = new ArrayList<String>();
	private List<LensDesign> lensDesigns = new ArrayList<LensDesign>();
	private List<Lens> corridors = new ArrayList<Lens>();
	private List<Coating> coatings = new ArrayList<Coating>();
	private List<Service> fittings = new ArrayList<Service>();
	
	private Integer[] qtys = {1,0};
	private Integer[] dias = {50, 55, 60, 65, 70};
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_rx_order);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		spnProduct = (Spinner) findViewById(R.id.spn_product);
		spnBrand = (Spinner) findViewById(R.id.spn_brand);
		spnDesign = (Spinner) findViewById(R.id.spn_design);
		spnIndex = (Spinner) findViewById(R.id.spn_index);
		spnCorridor = (Spinner) findViewById(R.id.spn_corridor);
		spnLensCode = (Spinner) findViewById(R.id.spn_lens_code);
		spnCoating = (Spinner) findViewById(R.id.spn_coating);
		edtCustomerRefNo = (EditText) findViewById(R.id.edt_cust_ref_no);
		spnLeCyl = (Spinner) findViewById(R.id.spn_le_cyl);
		spnLeSph = (Spinner) findViewById(R.id.spn_le_sph);
		spnReCyl = (Spinner) findViewById(R.id.spn_re_cyl);
		spnReSph = (Spinner) findViewById(R.id.spn_re_sph);
		spnLeAxis = (Spinner) findViewById(R.id.spn_le_axis);
		spnReAxis = (Spinner) findViewById(R.id.spn_re_axis);
		spnLeAdd = (Spinner) findViewById(R.id.spn_le_add);
		spnReAdd = (Spinner) findViewById(R.id.spn_re_add);
		spnReQty = (Spinner) findViewById(R.id.spn_re_quantity);
		spnLeQty = (Spinner) findViewById(R.id.spn_le_quantity);
		spnFitting = (Spinner) findViewById(R.id.spn_fitting);
		spnDia = (Spinner) findViewById(R.id.spn_dia);
		btnPlaceOrder = (Button) findViewById(R.id.btn_place_order);
		
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
		
		ArrayAdapter<Integer> arrayAdapterAxis = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1);
		
		for(int i = 0; i <= 360; i++){
			arrayAdapterAxis.add(i);
		}
		
		spnLeAxis.setAdapter(arrayAdapterAxis);
		spnReAxis.setAdapter(arrayAdapterAxis);
		
		spnLeAxis.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				axisLe = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnReAxis.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				axisRe = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnReAxis.setSelection(90);
		spnLeAxis.setSelection(90);
		
		ArrayAdapter<Integer> arrayAdapterQty = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, qtys);
		spnReQty.setAdapter(arrayAdapterQty);
		spnLeQty.setAdapter(arrayAdapterQty);
		
		spnReQty.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				qtyRe = qtys[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnLeQty.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				qtyLe = qtys[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ArrayAdapter<Integer> arrayAdapterDia = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, dias);
		spnDia.setAdapter(arrayAdapterDia);
		spnDia.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub
				dia = dias[postion];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		FetchProductAsynTask fetchProductAsynTask = new FetchProductAsynTask(RxOrderActivity.this);
		fetchProductAsynTask.execute();
		
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
		
		btnPlaceOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(customerCode==null){
					Toast.makeText(getApplicationContext(), "Please select Customer", Toast.LENGTH_SHORT).show();
					return;
				}
				if(qtyLe == 0 && qtyRe == 0){
					Toast.makeText(getApplicationContext(), "Quantity must not be zero", Toast.LENGTH_SHORT).show();
					return;
				}
				
				PlaceOrderAsynTask placeOrderAsynTask = new PlaceOrderAsynTask(RxOrderActivity.this);
				placeOrderAsynTask.execute();
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
	
	private class FetchProductAsynTask extends AsyncTask<Void, Void, Void>{

		private Context context;
		public FetchProductAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Product ...");
			progressDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			products = dataBaseAdapter.getProduct();
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//int selectedPosition = 0;
			if(products != null && products.size() > 0){
				ArrayAdapter<String> arrayAdapterProduct = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
				
				arrayAdapterProduct.add("Select Product");
				for(Lens product : products){ 
					arrayAdapterProduct.add(product.getDisplay_name());
				}
				spnProduct.setAdapter(arrayAdapterProduct);
			}else{
				progressDialog.dismiss();
			}
			
			progressDialog.dismiss();
			
			spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						Lens lens = products.get(position-1);
						product = lens.getDisplay_name();
						if(Constant.log) Log.i(tag, "product : "+lens.getDisplay_name());
					}else{
						product = null;
					}
					
					FetchBrandAsynTask fetchBrandAsynTask = new FetchBrandAsynTask(RxOrderActivity.this);
					fetchBrandAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
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
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Brand ...");
			progressDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			productBrands = dataBaseAdapter.getProductBrandForComputeQuote(product);
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//int selectedPosition = 0;
			if(productBrands != null && productBrands.size() > 0){
				ArrayAdapter<String> arrayAdapterProductBrand = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
				
				for(ProductBrand productBrand : productBrands){ 
					arrayAdapterProductBrand.add(productBrand.getBrand_desc());
				}
				if(Constant.log) Log.i(tag, ""+spnBrand);
				if(Constant.log) Log.i(tag, ""+arrayAdapterProductBrand.getCount());
				spnBrand.setAdapter(arrayAdapterProductBrand);
			}else{
				progressDialog.dismiss();
			}
			
			//progressDialog.dismiss();
			
			spnBrand.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					ProductBrand productBrand = productBrands.get(position);
					brandName = productBrand.getBrand_desc();
					brandCode = productBrand.getBrand_code();
					if(Constant.log) Log.i(tag, ".......product brand code : "+productBrand.getBrand_code()+" : "+productBrand.getBrand_desc());
					
					FetchDesignAsynTask fetchDesignAsynTask = new FetchDesignAsynTask(RxOrderActivity.this);
					fetchDesignAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			//progressDialog.dismiss();
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
			
			lensDesigns = dataBaseAdapter.getLensDesigns(product, brandCode);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(lensDesigns != null && lensDesigns.size() > 0){
				ArrayAdapter<String> arrayAdapterLensDesign = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
				
				for(LensDesign lensDesign : lensDesigns){
					arrayAdapterLensDesign.add(lensDesign.getDesign_desc());
				}
				spnDesign.setAdapter(arrayAdapterLensDesign);
			}
			
			//progressDialog.dismiss();
			
			spnDesign.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					design = lensDesigns.get(position).getDesign();
					
					FetchIndexAsyncTask fetchIndexAsyncTask = new FetchIndexAsyncTask(RxOrderActivity.this);
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
			indexOes = dataBaseAdapter.getLensIndexForRxOrder(product, brandCode, design);
			if (Constant.log)Log.i(tag, "after fetch index");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(indexOes != null && indexOes.size() > 0){
				ArrayAdapter<String> arrayAdapterIndex = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_simple_item_1, indexOes);
				spnIndex.setAdapter(arrayAdapterIndex);
			}else{
				progressDialog.dismiss();
			}
			
			
			spnIndex.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					indexOe = indexOes.get(position);
					if (Constant.log)Log.i(tag, "indexOe : "+indexOe);
					
					FetchCoridorAsyncTask fetchCoridorAsyncTask = new FetchCoridorAsyncTask(RxOrderActivity.this);
					fetchCoridorAsyncTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
	}
	
	private class FetchCoridorAsyncTask extends AsyncTask<Void, Void, Void>{

		private Context context;

		public FetchCoridorAsyncTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Coridor ...");
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			corridors = null;
			corridors = dataBaseAdapter.getCorridor(design, brandCode, product, indexOe);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			corridor = null;
			ArrayAdapter<String> arrayAdapterCorridor = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
			if(corridors != null && corridors.size() > 0){
				
				arrayAdapterCorridor.add("Select Corridor");
				for(Lens corridor : corridors){ 
					arrayAdapterCorridor.add(corridor.getLens_design());
				}
				
			}else{
				FetchLensAsynTask feLensAsynTask = new FetchLensAsynTask(RxOrderActivity.this);
				feLensAsynTask.execute();
			}
			spnCorridor.setAdapter(arrayAdapterCorridor);
			arrayAdapterCorridor.notifyDataSetChanged();
			
			spnCorridor.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						corridor = corridors.get(position-1).getLens_design();
					}else{
						corridor = null;
					}
					FetchLensAsynTask feLensAsynTask = new FetchLensAsynTask(RxOrderActivity.this);
					feLensAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	private class FetchLensAsynTask extends AsyncTask<Void, Void, Void> {

		private Context context;

		public FetchLensAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Lens...");
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			lenses = dataBaseAdapter.getLens(product, brandCode, design, indexOe, corridor);
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//arrayAdapterLensCode.clear();
			if (lenses != null && lenses.size() > 0) {
				
				ArrayAdapter<String> arrayAdapterLensCode = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.spinner_simple_item_1);
				
				for (Lens lens : lenses) {
					arrayAdapterLensCode.add(lens.getDescription()+" - "+lens.getLens_code());
				}
				
				spnLensCode.setAdapter(arrayAdapterLensCode);
			}else{
				progressDialog.cancel();
			}
			
			
			//arrayAdapterLensCode.notifyDataSetChanged();
			
			spnLensCode.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					
					Lens lens = lenses.get(position);
					lensCode = lens.getLens_code();
					
					FetchCoatingAsynTask fetchCoatingAsynTask = new FetchCoatingAsynTask(RxOrderActivity.this);
					fetchCoatingAsynTask.execute();
					
					FetchFittingAsynTask fetchFittingAsynTask = new FetchFittingAsynTask(RxOrderActivity.this);
					fetchFittingAsynTask.execute();
					
					generatePowerDetail(lens.getSphmin(), lens.getSphmax(), lens.getCylmin(), lens.getCylmax(), 
							lens.getAddmin(), lens.getAddmax());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	private class FetchCoatingAsynTask extends AsyncTask<Void, Void, Void> {

		private Context context;

		public FetchCoatingAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.dismiss();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading Coating...");
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(lensCode != null){
				Log.i(tag, "lensCode : "+lensCode);
				coatings = dataBaseAdapter.getCoatingForComputeQuote(lensCode);
				Log.i(tag, "coating length : "+coatings.size());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			ArrayAdapter<String> arrayAdapterLensCoats = new ArrayAdapter<String>(
					getApplicationContext(),
					R.layout.spinner_simple_item_1);
			if (coatings != null && coatings.size() > 0) {

				for (Coating coating : coatings) {
					arrayAdapterLensCoats.add(coating.getCoating_desc());
				}
				
				spnCoating.setAdapter(arrayAdapterLensCoats);
			}

			progressDialog.dismiss();

			spnCoating.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Coating coating = coatings.get(position);
					coatingCode = coating.getCoating_code();
					if (Constant.log)Log.i(tag,"coating code : "
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
	
	private class FetchFittingAsynTask extends AsyncTask<Void, Void, Void> {

		private Context context;
		//private ProgressDialog progressDialog;

		public FetchFittingAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//progressDialog = new ProgressDialog(context);
			//progressDialog.setCancelable(false);
			//progressDialog.setMessage("Loading Fitting...");
			//progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i(tag, "design : "+design);
			fittings = null;
			fittings = dataBaseAdapter.getFitting(lensCode);
			//Log.i(tag, "fitting length : "+fittings.size());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			ArrayAdapter<String> arrayAdapterFitting = new ArrayAdapter<String>(
					getApplicationContext(),
					R.layout.spinner_simple_item_1);
			if (fittings != null && fittings.size() > 0) {

				arrayAdapterFitting.add("Select Fitting");
				for (Service service : fittings) {
					arrayAdapterFitting.add(service.getService_desc());
				}
				spnFitting.setAdapter(arrayAdapterFitting);
			}
			
			arrayAdapterFitting.notifyDataSetChanged();
			
			//progressDialog.dismiss();

			spnFitting.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(position != 0){
						Service service = fittings.get(position-1);
						fittingCode = service.getService_code();
						if (Constant.log)Log.i(tag,"fitting code : "+ service.getService_code());
					}else{
						fittingCode = "";
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	}
	
	private class PlaceOrderAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private MethodResponseOrderStockLens methodResponseOrderStockLens;
		private String errorMessage;
		private ProgressDialog progressDialog;
		
		public PlaceOrderAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Place Order...");
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				
				Log.i(tag, "customerCode : "+customerCode);
				Log.i(tag, "lensCode : "+lensCode);
				Log.i(tag, "coatingCode : "+coatingCode);
				
				String custRefNo = edtCustomerRefNo.getText().toString().trim();
				Log.i(tag, "custRefNo : "+custRefNo);
				
				methodResponseOrderStockLens = webServiceObjectClient.orderRxLens(mApp.getPlant(),customerCode,lensCode, coatingCode, custRefNo
						,sphRe,cylRe,sphLe,cylLe, axisRe, axisLe, addRe, addLe, qtyRe, qtyLe, fittingCode, dia, mApp.getUserName());
				if(methodResponseOrderStockLens != null){
					if(methodResponseOrderStockLens.getResponseCode() == 0){
						
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseOrderStockLens.getResponseMessage();
						return methodResponseOrderStockLens.getResponseCode();
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
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(RxOrderActivity.this);
				alertDialog.setTitle("Order")
							.setMessage("Your order place successfully. Order No : "+methodResponseOrderStockLens.getData().getOrderNo())
							.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			
								@Override
								public void onClick(DialogInterface dialog,int which) {
			
									onBackPressed();
									dialog.cancel();
								}
							});
				AlertDialog alertDialog2 = alertDialog.create();
				
				alertDialog2.show();
				//onBackPressed();
			}else{
				MyToast myToast = new MyToast();
				myToast.show(getApplication(), result, errorMessage);
			}
			
			progressDialog.cancel();
		}
	}
	
	private void generatePowerDetail(float sphMin, float sphMax, float cylMin, float cylMax, float addMin, float addMax){
		final List<Float> listOfSph = new ArrayList<Float>();
		for(float i = sphMin; i <= sphMax; i+=.25){
			listOfSph.add(i);
		}
		
		ArrayAdapter<Float> arrayAdapterSph = new ArrayAdapter<Float>(getApplicationContext(), R.layout.spinner_simple_item_1, listOfSph);
		spnLeSph.setAdapter(arrayAdapterSph);
		spnReSph.setAdapter(arrayAdapterSph);
		
		spnLeSph.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				sphLe = listOfSph.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		try{
			if(sphMin < 0){
				spnLeSph.setSelection((int) (sphMin/0.25)*-1);
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		spnReSph.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				sphRe = listOfSph.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		try{
			if(sphMin < 0){
				spnReSph.setSelection((int) (sphMin/0.25)*-1);
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//---------------------------------------------------- cyl ------------------------------------------------------
		
		final List<Float> listOfCyl = new ArrayList<Float>();
		for(float i = cylMin; i <= cylMax; i+=.25){
			listOfCyl.add(i);
		}
		
		ArrayAdapter<Float> arrayAdapterCyl = new ArrayAdapter<Float>(getApplicationContext(), R.layout.spinner_simple_item_1, listOfCyl);
		spnLeCyl.setAdapter(arrayAdapterCyl);
		spnReCyl.setAdapter(arrayAdapterCyl);
		
		spnLeCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				cylLe = listOfCyl.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		try{
			if(cylMin < 0){
				spnLeCyl.setSelection((int) (cylMin/0.25)*-1);
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		spnReCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				cylRe = listOfCyl.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		try{
			if(cylMin < 0){
				spnReCyl.setSelection((int) (cylMin/0.25)*-1);
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//---------------------------------------------------- add ------------------------------------------------------
		
		ArrayAdapter<Float> arrayAdapterAdd = new ArrayAdapter<Float>(getApplicationContext(), R.layout.spinner_simple_item_1);
		
		if(!design.equals("SV")){
			
			for(float i = addMin; i <= addMax; i+=.25){
				arrayAdapterAdd.add(i);
			}
		}
		
		spnLeAdd.setAdapter(arrayAdapterAdd);
		spnReAdd.setAdapter(arrayAdapterAdd);
		
		arrayAdapterAdd.notifyDataSetChanged();
		
		spnLeAdd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				//addLe = listOfSph.get(position);
				addLe = (Float) adapter.getItemAtPosition(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnReAdd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				addRe = (Float) adapter.getItemAtPosition(position);
				try{
					spnLeAdd.setSelection(position);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
