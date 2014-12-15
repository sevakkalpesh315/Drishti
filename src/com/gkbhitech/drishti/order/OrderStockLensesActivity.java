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
import android.widget.LinearLayout;
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
import com.gkbhitech.drishti.model.LensCoat;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.MethodResponse;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseLensCoats;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderStockLens;

public class OrderStockLensesActivity extends Activity{

	private static final String tag = "OrderStockLensesActivity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer;
	private EditText edtCustomerRefNo;
	//private LinearLayout llLens;
	//private Spinner spnCoating;
	private Spinner spnLeSph, spnLeCyl, spnReSph, spnReCyl,spnProductBrand, spnLensCode, spnReQty,spnLeQty;
	private Button btnPlaceOrder;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode, lensCode, coatingName, coatingCode, brandName;
	//private List<Coating> coatings;
	private int brandCode, qtyRe, qtyLe;
	private float sphLe, sphRe;
	private float cylLe, cylRe;
	private String customerRefNo;
	
	private ProgressDialog progressDialog;
	
	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<LensForInventory> lensForInventories = new ArrayList<LensForInventory>();
	
	private Integer[] qtys = {1,0};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_stock_lenses);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		//txtLens = (TextView) findViewById(R.id.txt_select_lens);
		spnLensCode = (Spinner) findViewById(R.id.spn_lens_code);
		//spnCoating = (Spinner) findViewById(R.id.spn_coating);
		spnProductBrand = (Spinner) findViewById(R.id.spn_product_brand);
		edtCustomerRefNo = (EditText) findViewById(R.id.edt_cust_ref_no);
		spnLeCyl = (Spinner) findViewById(R.id.spn_le_cyl);
		spnLeSph = (Spinner) findViewById(R.id.spn_le_sph);
		spnReCyl = (Spinner) findViewById(R.id.spn_re_cyl);
		spnReSph = (Spinner) findViewById(R.id.spn_re_sph);
		spnReQty = (Spinner) findViewById(R.id.spn_re_quantity);
		spnLeQty = (Spinner) findViewById(R.id.spn_le_quantity);
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
		txtPlantNo.setText(mApp.getPlant()+"");
		
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
		
		/*txtLens.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SelectLensActivity.class);
				startActivity(i);
			}
		});*/
		
		//FatchProductBrandAsynTask fatchProductBrandAsynTask = new FatchProductBrandAsynTask(OrderStockLensesActivity.this);
		//fatchProductBrandAsynTask.execute();
		
		//ArrayAdapter<Double> arrayAdapterSph = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.sph);
		//spnLeSph.setAdapter(arrayAdapterSph);
		//spnReSph.setAdapter(arrayAdapterSph);
		ArrayAdapter<Double> arrayAdapterCyl = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.cylZeroToMinusTwo);
		spnLeCyl.setAdapter(arrayAdapterCyl);
		spnReCyl.setAdapter(arrayAdapterCyl);
		
		spnLeCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				cylLe = Constant.cylZeroToMinusTwo[position].floatValue();
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
				cylRe = Constant.cylZeroToMinusTwo[position].floatValue();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
				
				PlaceOrderAsynTask placeOrderAsynTask = new PlaceOrderAsynTask(OrderStockLensesActivity.this);
				placeOrderAsynTask.execute();
			}
		});
		
		FatchProductBrandAsynTask fatchProductBrandAsynTask = new FatchProductBrandAsynTask(OrderStockLensesActivity.this);
		fatchProductBrandAsynTask.execute();
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
		Log.i(tag, "On Resume called");
		
		/*if(mApp.getR_plant() != 0){
			mApp.setPlant(dataBaseAdapter.getPlant(mApp.getR_plant()).getR_plant());
		}
		if(mApp.getC_code() != null){
			mApp.setCustomer(dataBaseAdapter.getCustomerByCustCode(mApp.getPlant(), mApp.getC_code()));
		}*/
		
		Customer customer = mApp.getCustomer();
		if(customer != null){
			txtCustomer.setText(customer.getCust_name());
			customerCode = customer.getCust_code();
		}
		/*Lens lens = mApp.getLens();
		if(lens != null){
			txtLens.setText(lens.getDescription());
			lensCode = lens.getLens_code();
			//FatchCoatingAsynTask fatchCoatingAsynTask = new FatchCoatingAsynTask(OrderStockLensesActivity.this);
			//fatchCoatingAsynTask.execute();
		}*/
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mApp.getUserType() != 1){
			mApp.setCustomer(null);
		}
	}
	/*@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if(mApp.getC_code() != null){
			mApp.setCustomer(dataBaseAdapter.getCustomerByCustCode(mApp.getPlant(), mApp.getC_code()));
		}
	}*/
	
	
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
			
			//int selectedPosition = 0;
			if(productBrands != null && productBrands.size() > 0){
				
				ArrayAdapter<String> arrayAdapterProductBrand = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1);
				
				/*if(mApp.getP_brand() != null){
					for(ProductBrand productBrand : productBrands){ 
						if(mApp.getP_brand().equals(""+productBrand.getBrand_code())){
							selectedPosition = productBrands.indexOf(productBrand);
						}
						arrayAdapterProductBrand.add(productBrand.getBrand_desc());
					}
					mApp.setP_brand(null);
				}else{*/
					for(ProductBrand productBrand : productBrands){ 
						arrayAdapterProductBrand.add(productBrand.getBrand_desc());
					}
				//}
				
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
			
			//spnProductBrand.setSelection(selectedPosition);
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
			
			//int selectedPosition = 0;
			if(lensForInventories != null && lensForInventories.size() > 0){
				
				ArrayAdapter<String> arrayAdapterLensCode = new ArrayAdapter<String>(OrderStockLensesActivity.this, R.layout.spinner_simple_item_1);
				
				/*Log.i(tag, "L_code : "+mApp.getL_code()+" "+mApp.getCt_code());
				if(mApp.getL_code() != null && mApp.getCt_code() != null){
					for(LensForInventory lensForInventory : lensForInventories){
						if(mApp.getL_code().equals(lensForInventory.getLens_code()) && mApp.getCt_code().equals(lensForInventory.getCoating_code())){
							selectedPosition = lensForInventories.indexOf(lensForInventory);
						}
						arrayAdapterLensCode.add(lensForInventory.getDescription()+" - "+lensForInventory.getCoating_desc());
					}
					mApp.setL_code(null);
					mApp.setCt_code(null);
				}else{*/
					for(LensForInventory lensForInventory : lensForInventories){
						arrayAdapterLensCode.add(lensForInventory.getDescription()+" - "+lensForInventory.getCoating_desc());
					}
				//}
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
					//lensName = lens.getDescription();
					lensCode = lens.getLens_code();
					coatingCode = lens.getCoating_code();
					
					if(Constant.log) Log.i(tag, "coating code : "+coatingCode);
					if(Constant.log) Log.i(tag, "max : "+lens.getSphmax()+" min : "+lens.getSphmin());
					
					final List<Float> listOfSph = new ArrayList<Float>();
					for(float i = lens.getSphmin(); i <= lens.getSphmax(); i+=.25){
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
					
					if(Constant.log) Log.i(tag, ".......lens code : "+lens.getLens_code()+" : "+lens.getDescription());
					
					//FatchCoatingAsynTask fatchCoatingAsynTask = new FatchCoatingAsynTask();
					//fatchCoatingAsynTask.execute();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			//spnLensCode.setSelection(selectedPosition);
		}
	}
	
	private class PlaceOrderAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private MethodResponseOrderStockLens methodResponseOrderStockLens;
		private String errorMessage;
		
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
				//customerCode
				//lens_code
				//coating_code
				Log.i(tag, "customerCode : "+customerCode);
				Log.i(tag, "lensCode : "+lensCode);
				Log.i(tag, "coatingCode : "+coatingCode);
				String rGrp = calculateGroup(sphRe, cylRe);
				String lGrp = calculateGroup(sphLe, cylLe);
				Log.i(tag, "r_grp : "+rGrp);
				Log.i(tag, "l_grp : "+lGrp);
				//int rQty = 1;
				//int lQty = 1;
				String custRefNo = edtCustomerRefNo.getText().toString().trim();
				Log.i(tag, "custRefNo : "+custRefNo);
				
				methodResponseOrderStockLens = webServiceObjectClient.orderStockLens(mApp.getPlant(),customerCode,custRefNo,lensCode,
						coatingCode,sphRe,cylRe,sphLe,cylLe,rGrp,lGrp,qtyRe,qtyLe,mApp.getUserName());
				if(methodResponseOrderStockLens != null){
					if(methodResponseOrderStockLens.getResponseCode() == 0){
						/*LensCoat[] lensCoats = methodResponseLensCoats.getDataArray();
						if(lensCoats != null){
							dataBaseAdapter.insertLensCoat(lensCoats);
						}
						mApp.setLastSyncTime(Constant.LENS_COATS_LAST_SYNC_TIME, methodResponseLensCoats.getSys_time());
						methodResponseLensCoats = null;
						lensCoats = null;*/
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
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStockLensesActivity.this);
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
	
	private String calculateGroup(float sph, float cyl){
		String tp = "", ctp = "";
		
		sph=Math.abs(sph);
		cyl=Math.abs(cyl);
		
		if (sph == 0)
			tp = "0";
		if (sph > 0 && sph <= 2)
			tp = "2";
		if (sph > 2 && sph <= 4)
			tp = "4";
		if (sph > 4 && sph <= 6)
			tp = "6";
		if (sph > 6 && sph <= 8)
			tp = "8";
		if (sph > 8 && sph <= 10)
			tp = "10";
		if (sph > 10 && sph <= 12)
			tp = "12";
		if (sph > 12 && sph <= 14)
			tp = "14";
		if (cyl > 0 && cyl <= 2)
			ctp = "2";
		if (cyl == 0) {
			tp = "G" + tp;
		} else {
			tp = "G" + tp + ctp;
		}
		
		return tp;
	}
	
}
