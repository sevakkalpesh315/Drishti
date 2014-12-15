package com.gkbhitech.drishti.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.model.LensForInventory;
import com.gkbhitech.drishti.model.ProductBrand;
import com.gkbhitech.drishti.order.OrderStockLensesActivity;
import com.gkbhitech.drishti.order.SelectCustomerActivity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class OrderThroughInventoryActivity extends Activity{

	private static final String tag = "OrderThroughInventoryActivity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtCustomer, txtProductBrand,txtLensAndCoating, txtQty, txtReSph, txtReCyl;
	private EditText edtCustomerRefNo;
	private Button btnPlaceOrder;
	
	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	private String customerCode, lensCode, lensName, coatingName, coatingCode, brandName;
	//private List<Coating> coatings;
	private int brandCode, qtyRe, qtyLe;
	private float reSph, reCyl;
	private String customerRefNo;
	
	private List<ProductBrand> productBrands = new ArrayList<ProductBrand>();
	private List<LensForInventory> lensForInventories = new ArrayList<LensForInventory>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_through_inventory);
		
		mApp =  (DrishtiApplication) getApplication();
		//dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtCustomer = (TextView) findViewById(R.id.txt_select_customer);
		txtProductBrand =(TextView) findViewById(R.id.txt_product_brand);
		txtLensAndCoating = (TextView) findViewById(R.id.txt_lens_coating);
		edtCustomerRefNo = (EditText) findViewById(R.id.edt_cust_ref_no);
		txtReCyl = (TextView) findViewById(R.id.txt_re_cyl);
		txtReSph = (TextView) findViewById(R.id.txt_re_sph);
		txtQty = (TextView) findViewById(R.id.txt_qty);
		btnPlaceOrder = (Button) findViewById(R.id.btn_place_order);
		
		
		Bundle bundle = getIntent().getExtras();
		brandCode = bundle.getInt("brandCode");
		brandName = bundle.getString("brandName");
		lensCode = bundle.getString("lensCode");
		lensName = bundle.getString("lensName");
		coatingCode = bundle.getString("coatingCode");
		coatingName = bundle.getString("coatingName");
		reSph = bundle.getFloat("sph");
		reCyl = bundle.getFloat("cyl");
		// get lens, coating, product brand from extras
		// set lens name and coating name
		// set product brand 
		
		txtProductBrand.setText(brandName);
		txtLensAndCoating.setText(lensName+" : "+coatingName);
		
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
				Intent i = new Intent(getApplicationContext(), SelectCustomerActivity.class);
				startActivity(i);
			}
		});
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
		
		txtReSph.setText(""+reSph);
		txtReCyl.setText(""+reCyl);
		
		btnPlaceOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(customerCode==null){
					Toast.makeText(getApplicationContext(), "Please select Customer", Toast.LENGTH_SHORT).show();
					return;
				}
				
				PlaceOrderAsynTask placeOrderAsynTask = new PlaceOrderAsynTask(OrderThroughInventoryActivity.this);
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
				String rGrp = calculateGroup(reSph, reCyl);
				String lGrp = calculateGroup(0, 0);
				Log.i(tag, "r_grp : "+rGrp);
				Log.i(tag, "l_grp : "+lGrp);
				String custRefNo = edtCustomerRefNo.getText().toString().trim();
				Log.i(tag, "custRefNo : "+custRefNo);
				
				methodResponseOrderStockLens = webServiceObjectClient.orderStockLens(mApp.getPlant(),customerCode,custRefNo,lensCode,
						coatingCode,reSph,reCyl,0,0,rGrp,lGrp,1,0,mApp.getUserName());
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
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderThroughInventoryActivity.this);
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
