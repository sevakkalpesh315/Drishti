package com.gkbhitech.drishti.gkb;

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
import com.gkbhitech.drishti.common.FontFace;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.LensPrice;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.Quote;
import com.gkbhitech.drishti.order.SelectCustomerActivity;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseQuote;

public class ComputeQuoteFormActivity extends Activity {

	private static final String tag = "ComputeQuoteActivity";

	// .............. variable used in UI ................
	private ImageView imvBack,imvHome;
	//private TextView txtCustomer;
	private Spinner spnBrand, spnDesign, spnIndex;
	private LinearLayout llDesign, llIndex, llCoating;
	
	private Spinner spnLensCode;
	private Spinner spnCoating;
	private Spinner spnSph;
	private Spinner spnCyl;
	private EditText edtAxis;
	private EditText edtPrism;
	private Spinner spnMaterialType;
	private Button btnShow; 
	private TextView txtRate;
	private TextView txtRatePerPair,txtMessage;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	//private String customerCode;
	private String materialType, design, indexOe; // F5C547
	private int brandCode;
	private String lensCode;
	private String coatingCode;
	//private Double sph;
	//private Double cyl;
	//private int axis;
	//private Double prism;

	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<Lens> lenses = new ArrayList<Lens>();
	private List<LensForInventory> lensForInventories = new ArrayList<LensForInventory>();
	private List<Coating> coatings = new ArrayList<Coating>();
	private List<String> indexOes = new ArrayList<String>();

	

	private ProgressDialog progressDialog;
	//ArrayAdapter<String> arrayAdapterLensCoats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (Constant.log)
			Log.i(tag, tag + " start");

		registerReceiver();

		setContentView(R.layout.activity_compute_quote);

		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		//txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		spnBrand = (Spinner) findViewById(R.id.spn_brand);
		spnDesign = (Spinner) findViewById(R.id.spn_design);
		llDesign = (LinearLayout) findViewById(R.id.ll_design);
		llIndex = (LinearLayout) findViewById(R.id.ll_index);
		spnIndex = (Spinner) findViewById(R.id.spn_index);
		spnLensCode = (Spinner) findViewById(R.id.spn_lens_code);
		llCoating = (LinearLayout) findViewById(R.id.ll_coating);
		spnCoating = (Spinner) findViewById(R.id.spn_coating);
		/*spnSph = (Spinner) findViewById(R.id.spn_sph);
		spnCyl = (Spinner) findViewById(R.id.spn_cyl);
		edtAxis = (EditText) findViewById(R.id.edt_axis);
		edtPrism = (EditText) findViewById(R.id.edt_prism);*/
		spnMaterialType = (Spinner) findViewById(R.id.spn_material_type);
		btnShow = (Button) findViewById(R.id.btn_show);
		txtRate = (TextView) findViewById(R.id.txt_rate);
		txtRatePerPair = (TextView) findViewById(R.id.txt_rate_per_pair);
		txtMessage = (TextView) findViewById(R.id.txt_message);
		
		txtRate.setTypeface(FontFace.getFontFaceRupee(this));
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
				Intent i = new Intent(getApplicationContext(),
						HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		//arrayAdapterLensCoats = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_simple_item_1);
		
		ArrayAdapter<String> arrayAdapterLensCoats = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				Constant.arrayMaterialType);
		spnMaterialType.setAdapter(arrayAdapterLensCoats);

		spnMaterialType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position == 0) {
					materialType = "'S'";
					llIndex.setVisibility(View.GONE);
					llCoating.setVisibility(View.GONE);
					llDesign.setVisibility(View.GONE);
				} else if (position == 1) {
					materialType = "'P','F'";
					llIndex.setVisibility(View.VISIBLE);
					llCoating.setVisibility(View.VISIBLE);
					llDesign.setVisibility(View.VISIBLE);
				}

				FatchProductBrandAsynTask fatchProductBrandAsynTask = new FatchProductBrandAsynTask(
						ComputeQuoteFormActivity.this);
				fatchProductBrandAsynTask.execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		/*ArrayAdapter<String> arrayAdapterDesign = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_simple_item_1,
				arrayDesign);
		spnDesign.setAdapter(arrayAdapterDesign);
		spnDesign.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				design = arrayDesign[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
		btnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ComputeQuoteFormAsynTask computeQuoteFormAsynTask = new ComputeQuoteFormAsynTask(
						ComputeQuoteFormActivity.this, mApp,brandCode, lensCode, coatingCode, materialType);
				computeQuoteFormAsynTask.execute();
			}
		});
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	//private class FetchDesi

	private class FatchProductBrandAsynTask extends AsyncTask<Void, Void, Void> {

		private Context context;
		//private ProgressDialog progressDialog;

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
				spnBrand.setAdapter(arrayAdapterProductBrand);
			}else{
				progressDialog.dismiss();
			}

			spnBrand.setOnItemSelectedListener(new OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> arg0,
						View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					ProductBrand productBrand = productBrands.get(position);
					brandCode = productBrand.getBrand_code();
					if (Constant.log)
						Log.i(tag, ".......product brand code : "
								+ productBrand.getBrand_code() + " : "
								+ productBrand.getBrand_desc());

					if(materialType.equals("'S'")){
						FatchLensAsynTask fatchLensAsynTask = new FatchLensAsynTask();
						fatchLensAsynTask.execute();
					}else{
						ArrayAdapter<String> arrayAdapterDesign = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.spinner_simple_item_1,
								Constant.arrayDesign);
						spnDesign.setAdapter(arrayAdapterDesign);
						spnDesign.setOnItemSelectedListener(new OnItemSelectedListener() {
	
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								design = Constant.arrayDesignValues[position];
								
								FetchIndexAsyncTask fetchIndexAsyncTask = new FetchIndexAsyncTask(ComputeQuoteFormActivity.this);
								fetchIndexAsyncTask.execute();
							}
	
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	private class FetchIndexAsyncTask extends AsyncTask<Void, Void, Void>{

		//private Context context;
		//private ProgressDialog progressDialog;

		public FetchIndexAsyncTask(Context context) {
			//this.context = context;
			//progressDialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//progressDialog.setCancelable(false);
			//progressDialog.setMessage("Loading Index ...");
			//progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (Constant.log)Log.i(tag, "before fetch index");
			indexOes = dataBaseAdapter.getLensIndexForRxOrder(null, brandCode, design);
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

		ArrayAdapter<String> arrayAdapterLensCode = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.spinner_simple_item_1);
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
			if(materialType == "'S'"){
				lensForInventories = dataBaseAdapter.getLens(brandCode);
				if(lensForInventories != null && lensForInventories.size() > 0){
					for(LensForInventory lensForInventory : lensForInventories){
						arrayAdapterLensCode.add(lensForInventory.getDescription()+" - "+lensForInventory.getLens_code()+" - "+lensForInventory.getCoating_desc());
					}
				}
			}else{
				lenses = dataBaseAdapter.getLens(brandCode, design, indexOe);
				if (lenses != null && lenses.size() > 0) {
					for (Lens lens : lenses) {
						arrayAdapterLensCode.add(lens.getDescription()+" - "+lens.getLens_code());
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			/*if (lenses != null && lenses.size() > 0) {

				for (Lens lens : lenses) {
					arrayAdapterLensCode.add(lens.getDescription());
				}
				
			}else{
				arrayAdapterLensCoats.clear();
				arrayAdapterLensCoats.notifyDataSetChanged();
				progressDialog.cancel();
			}*/
			
			/*if(arrayAdapterLensCode.isEmpty()){
				arrayAdapterLensCoats.clear();
				arrayAdapterLensCoats.notifyDataSetChanged();
				progressDialog.cancel();
			}*/
			
			spnLensCode.setAdapter(arrayAdapterLensCode);
			
			spnLensCode.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub

					if(materialType.equals("'P','F'")){
						Lens lens = lenses.get(position);
						lensCode = lens.getLens_code();
						FatchCoatingAsynTask fatchCoatingAsynTask = new FatchCoatingAsynTask();
						fatchCoatingAsynTask.execute();
					}else{
						LensForInventory lens = lensForInventories.get(position);
						lensCode = lens.getLens_code();
						coatingCode = lens.getCoating_code();
						progressDialog.dismiss();
					}
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
			coatings = null;
			if(lensCode != null){
				Log.i(tag, "lensCode : "+lensCode);
				coatings = dataBaseAdapter.getCoatingForComputeQuote(lensCode);
				//Log.i(tag, "coating length : "+coatings.size());
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
				
			}

			progressDialog.cancel();
			
			spnCoating.setAdapter(arrayAdapterLensCoats);

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

	public class ComputeQuoteFormAsynTask extends AsyncTask<Void, Void, Integer>{

		private static final String tag = "InventorySummaryFormAsynTask";
		private String errorMessage = "Error";
		private DrishtiApplication mApp; 
		private WebServiceObjectClient webServiceObjectClient;
		private Context context;
		private int brandCode;
		private String lensCode;
		private String coatingCode;
		/*private Double sph;
		private Double cyl;
		private int axis;
		private Double prism;*/
		private String materialType;
		private ProgressDialog progressDialog;
		
		private MethodResponseQuote methodResponseQuote;
		
		public ComputeQuoteFormAsynTask(Context context, DrishtiApplication mApp, 
				int brandCode, String lensCode, String coatingCode, String materialType){
			this.context = context;
			this.mApp = mApp;
			this.webServiceObjectClient = mApp.getWebserviceObjectClient();
			this.brandCode = brandCode;
			this.lensCode = lensCode;
			this.coatingCode = coatingCode;
			/*this.sph = sph;
			this.cyl = cyl;
			this.axis = axis;
			this.prism = prism;*/
			this.materialType = materialType;
			progressDialog = new ProgressDialog(context);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Compute Price ...");
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				methodResponseQuote = webServiceObjectClient.getComputedPrice(lensCode, coatingCode);
				if(methodResponseQuote != null){
					if(methodResponseQuote.getResponseCode() == Constant.RESULT_SUCCESS){
						//LensPrice lensPrice = methodResponseQuote.getData();
						//if(lensPrice != null){
							
							//mApp.setQuote(quote);
							return Constant.RESULT_SUCCESS;
						//}
					}else{
						errorMessage = methodResponseQuote.getResponseMessage();
						return methodResponseQuote.getResponseCode();
					}
				}
				return Constant.RESULT_SUCCESS;
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
			
			if(Constant.log) Log.i(tag, "Result code : "+result);
			
			switch (result) {
			case Constant.RESULT_SUCCESS:
				
				if(Constant.log) Log.i(tag, "success");
				
				txtRatePerPair.setVisibility(View.VISIBLE);
				txtRate.setVisibility(View.VISIBLE);
				txtMessage.setVisibility(View.VISIBLE);
				//String rate = "Rate per pair : ` "+(methodResponseQuote.getData().getRate()*2);
				txtRate.setText("` "+(methodResponseQuote.getData().getRate()*2));
				//Intent i = new Intent(context,PriceActivity.class);
				//context.startActivity(i);
				
				/*if(methodResponseQuote != null){
					int responseCode = methodResponseQuote.getResponseCode();
					String responseMessage = methodResponseQuote.getResponseMessage();
					
					if(responseCode == Constant.RESULT_SUCCESS){
						Quote quote = methodResponseQuote.getData();
						if(quote != null){
							mApp.setQuote(quote);
							Intent i = new Intent(context,PriceActivity.class);
							context.startActivity(i);
						}
					}else{
						MyToast.show(context, responseCode, responseMessage);
					}
				}*/
				
				break;
			default:
				MyToast.show(context, result, errorMessage);
				break;
			}
			
			progressDialog.cancel();
		}
	}
	
	public void registerReceiver() {
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
