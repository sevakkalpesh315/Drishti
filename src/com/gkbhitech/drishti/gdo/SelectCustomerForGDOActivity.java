package com.gkbhitech.drishti.gdo;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter.FilterListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Customer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomer;

public class SelectCustomerForGDOActivity extends Activity {

	private static final String tag = "SelectCustomerActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private ListView lvCustomer;
	private EditText edtCustomerSearchBox;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;

	//private List<Customer> customers = new ArrayList<Customer>();
	private Customer[] customers;
	//private List<String> customerNames = new ArrayList<String>();

	private ProgressDialog progressDialog;
	
	private ArrayAdapter<String> arrayAdapterCustomerName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_select_items);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		lvCustomer = (ListView) findViewById(R.id.lv_items);
		edtCustomerSearchBox = (EditText) findViewById(R.id.edt_search_box);
		
		edtCustomerSearchBox.addTextChangedListener(textWatcher);
		
		FatchCustomerAsynTask fatchCustomerAsynTask = new FatchCustomerAsynTask(SelectCustomerForGDOActivity.this);
		fatchCustomerAsynTask.execute();
		
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
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    edtCustomerSearchBox.removeTextChangedListener(textWatcher);
	}
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			//arrayAdapterCustomerName.getFilter().filter(s);
			arrayAdapterCustomerName.getFilter().filter(s, new FilterListener() {
				
				@Override
				public void onFilterComplete(int count) {
					// TODO Auto-generated method stub
					
				}
			});
			arrayAdapterCustomerName.notifyDataSetChanged();
			//arrayAdapterCustomerName.
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};

	public class FatchCustomerAsynTask extends AsyncTask<Void, Void, Integer> {
		
		private Context context;
		private MethodResponseCustomer methodResponseCustomer;
		private String errorMessage = "Error";
		
		public FatchCustomerAsynTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog((Activity)context);
			progressDialog.setMessage("Loding Customer...");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			try{
				if(Constant.log) Log.i(tag, tag+" start");
				
				methodResponseCustomer = webServiceObjectClient.getCustomerForGDO(mApp.getPlant());
				if(methodResponseCustomer != null){
					if(methodResponseCustomer.getResponseCode() == Constant.RESULT_SUCCESS){
						customers = methodResponseCustomer.getDataArray();
						return Constant.RESULT_SUCCESS;
					}else{
						errorMessage = methodResponseCustomer.getResponseMessage();
						return methodResponseCustomer.getResponseCode();
					}
				}
				//Log.i(tag, "Response : "+response);
				return Constant.RESULT_NULL_RESPONSE;
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

			switch (result) {
			case Constant.RESULT_SUCCESS:
				if (customers != null && customers.length > 0) {
					if (Constant.log)
						Log.i(tag, "lens length :" + customers.length);
					/*
					 * for (Customer customer : customers) {
					 * customerNames.add(customer.getCust_name()); }
					 */
					arrayAdapterCustomerName = new ArrayAdapter<String>(
							getApplicationContext(),
							R.layout.spinner_simple_item_1);

					for (Customer customer : customers) {
						arrayAdapterCustomerName.add(customer.getCust_name()
								+ " - " + customer.getCust_code());
					}

					lvCustomer.setAdapter(arrayAdapterCustomerName);
					lvCustomer.setSelected(true);

					Customer selectedCustomer = mApp.getCustomer();
					if (selectedCustomer != null) {
						for (int i = 0; i < customers.length; i++) {
							Customer customer = customers[i];
							if (customer.getCust_name().equals(
									selectedCustomer.getCust_name())) {
								lvCustomer.setSelection(i);
							}
						}
					}
				}

				lvCustomer.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {

						for (Customer customer : customers) {

							if (arrayAdapterCustomerName.getItem(position)
									.contains(customer.getCust_name())) {
								mApp.setCustomer(customer);
							}
						}
						onBackPressed();
					}
				});
				break;
			default:
				MyToast.show(context, result, errorMessage);
				break;
			}
			
			progressDialog.dismiss();
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
