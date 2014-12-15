package com.gkbhitech.drishti.order;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.Offer;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.model.Service;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderStockLens;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class OfferActivity extends Activity{

	private static final String tag = "OfferActivity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo,txtCustomer,txtLensAndCoating, txtAxis, txtAdd;
	/*txtProductBrand*/
	private EditText edtCustomerRefNo;
	private Button btnPlaceOrder;
	private LinearLayout llFitting,llDia;
	private Spinner spnLeSph, spnLeCyl, spnReSph, spnReCyl, spnReAxis, spnLeAxis , spnReQty, spnLeQty, spnLeAdd,spnReAdd,
			spnFitting,spnDia;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerRefNo,fittingCode, orderType;
	private int plant, axisRe, axisLe, qtyRe, qtyLe, dia;
	private float sphLe, sphRe, cylLe, cylRe, addLe, addRe;
	private Integer[] qtys = {1,0};
	
	private Offer offer;
	private List<Service> fittings = new ArrayList<Service>();
	private Integer[] dias = {50, 55, 60, 65, 70};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_offered_stock_lenses);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		/*txtProductBrand =(TextView) findViewById(R.id.txt_product_brand);*/
		txtLensAndCoating = (TextView) findViewById(R.id.txt_lens_coating);
		txtAxis = (TextView) findViewById(R.id.txt_axis);
		txtAdd = (TextView) findViewById(R.id.txt_add);
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
		llFitting = (LinearLayout) findViewById(R.id.ll_fitting);
		llDia = (LinearLayout) findViewById(R.id.ll_dia);
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
		
		offer = mApp.getOffer();
		
		/*ArrayAdapter<Double> arrayAdapterCyl = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.cylZeroToMinusTwo);
		spnLeCyl.setAdapter(arrayAdapterCyl);
		spnReCyl.setAdapter(arrayAdapterCyl);*/
		
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
		
		orderType = offer.getLens_code().substring(0, 1);
		Log.i(tag, "Order Type : "+orderType);
		
		if(!orderType.equals("S")){
			llFitting.setVisibility(View.VISIBLE);
			llDia.setVisibility(View.VISIBLE);
			txtAxis.setVisibility(View.VISIBLE);
			txtAdd.setVisibility(View.VISIBLE);
			spnLeAxis.setVisibility(View.VISIBLE);
			spnReAxis.setVisibility(View.VISIBLE);
			spnLeAdd.setVisibility(View.VISIBLE);
			spnReAdd.setVisibility(View.VISIBLE);
		}
		
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
		
		FillDataAsyncTask fillDataAsyncTask = new FillDataAsyncTask(OfferActivity.this);
		fillDataAsyncTask.execute();
		
		
		btnPlaceOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(qtyLe == 0 && qtyRe == 0){
					Toast.makeText(getApplicationContext(), "Quantity must not be zero", Toast.LENGTH_SHORT).show();
					return;
				}
				
				PlaceOrderAsynTask placeOrderAsynTask = new PlaceOrderAsynTask(OfferActivity.this);
				placeOrderAsynTask.execute();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
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
	
	private class FillDataAsyncTask extends AsyncTask<Void, Void, Void>{

		private Context context;
		private ProgressDialog progressDialog;
		private Customer customer;
		/*private ProductBrand productBrand;*/
		private LensForInventory lensForInventory;
		
		
		public FillDataAsyncTask(Context context){
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(OfferActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading ...");
			//progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			/*if(mApp.getR_plant() != 0){
				plant = dataBaseAdapter.getPlant(mApp.getR_plant()).getR_plant();
				txtPlantNo.setText(""+plant);
			}
			if(mApp.getC_code() != null){
				Customer customer = dataBaseAdapter.getCustomerByCustCode(mApp.getPlant(), mApp.getC_code());
				txtCustomer.setText(customer.getCust_name());
				customerCode = customer.getCust_code();
			}
			
			if(mApp.getP_brand() != null){
				ProductBrand productBrand = dataBaseAdapter.getProductBranByCode(mApp.getP_brand());
				txtProductBrand.setText(productBrand.getBrand_desc());
			}
			
			if(mApp.getL_code() != null && mApp.getCt_code() != null){
				LensForInventory lensForInventory = dataBaseAdapter.getLensAndCoatingByCode(mApp.getL_code(), mApp.getCt_code());
				txtLensAndCoating.setText(lensForInventory.getDescription()+" "+lensForInventory.getCoating_desc());
			}*/
			
			
			if(offer != null){
				if(Constant.log)Log.i(tag, "Coat : "+offer.getCoat());
				customer = dataBaseAdapter.getCustomerByCustCode(offer.getCust_code());
				/*productBrand = dataBaseAdapter.getProductBranByCode(offer.getProduct_brand());*/
				lensForInventory = dataBaseAdapter.getLensAndCoatingByCode(offer.getLens_code(), offer.getCoat(),orderType);
				fittings = dataBaseAdapter.getFitting(offer.getLens_code());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(offer != null){
				txtPlantNo.setText(""+offer.getR_plant());
				
				//Customer customer = dataBaseAdapter.getCustomerByCustCode(offer.getR_plant(), offer.getCust_code());
				txtCustomer.setText(customer.getCust_name());
				//customerCode = customer.getCust_code();
				
				//ProductBrand productBrand = dataBaseAdapter.getProductBranByCode(offer.getProduct_brand());
				/*txtProductBrand.setText(productBrand.getBrand_desc());*/
				
				//LensForInventory lensForInventory = dataBaseAdapter.getLensAndCoatingByCode(offer.getLens_code(), offer.getCoat());
				txtLensAndCoating.setText(lensForInventory.getDescription()+" "+lensForInventory.getCoating_desc());
				
				generatePowerDetail(lensForInventory.getSphmin(), lensForInventory.getAddmax(), 
						lensForInventory.getCylmin(), lensForInventory.getCylmax(), 
						lensForInventory.getAddmin(), lensForInventory.getAddmax());
				/*final List<Float> listOfSph = new ArrayList<Float>();
				for(float i = lensForInventory.getSphmin(); i <= lensForInventory.getSphmax(); i+=.25){
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
				});*/
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
			progressDialog.dismiss();
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
				
				Offer offer = mApp.getOffer();
				String rGrp = calculateGroup(sphRe, cylRe);
				String lGrp = calculateGroup(sphLe, cylLe);
				String custRefNo = edtCustomerRefNo.getText().toString().trim();
				
				if(orderType.equals("S")){
					methodResponseOrderStockLens = webServiceObjectClient.orderOfferedStockLens(offer.getId(),
						custRefNo,sphRe,cylRe,sphLe,cylLe,qtyRe,qtyLe,mApp.getUserName());
				}else{
					
					/*int plant,
					String customerCode, String lensCode, String coatingCode,
					String custRefNo, float sphRe, float cylRe, float sphLe,
					float cylLe, int axisRe, int axisLe, float addRe, float addLe,
					int rQty, int lQty, String fittingCode, int dia, String username*/
					methodResponseOrderStockLens = webServiceObjectClient.orderOfferedRxLens((int) offer.getId(),mApp.getPlant(), offer.getCust_code(), 
						offer.getLens_code(), offer.getCoat(), custRefNo, sphRe, cylRe, sphLe, cylLe, axisRe, axisLe, addRe, 
						addLe, qtyRe, qtyLe, fittingCode, dia, mApp.getUserName());
				}
				
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
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfferActivity.this);
				alertDialog.setTitle("Order")
							.setMessage("Your order place successfully. Order No : "+methodResponseOrderStockLens.getData().getOrderNo())
							.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			
								@Override
								public void onClick(DialogInterface dialog,int which) {
			
									//onBackPressed();
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
			
		for(float i = addMin; i <= addMax; i+=.25){
			arrayAdapterAdd.add(i);
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
