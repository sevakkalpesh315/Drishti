package com.gkbhitech.drishti.account;

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
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.model.Customer;

public class SelectCustomerForReceiptActivity extends Activity {

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

	private List<Customer> customers = new ArrayList<Customer>();
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

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		lvCustomer = (ListView) findViewById(R.id.lv_items);
		edtCustomerSearchBox = (EditText) findViewById(R.id.edt_search_box);
		
		edtCustomerSearchBox.addTextChangedListener(textWatcher);
		
		FatchCustomerAsynTask fatchCustomerAsynTask = new FatchCustomerAsynTask(SelectCustomerForReceiptActivity.this);
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

	public class FatchCustomerAsynTask extends AsyncTask<Void, Void, Void> {
		
		Context context;
		
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
		protected Void doInBackground(Void... params) {

			try {
				//if(Constant.log) Log.i(tag, "Brfore");
				customers = dataBaseAdapter.getCustomerForReceipt(mApp.getUserType(), mApp.getUserName(),mApp.getPlant());
				//if(Constant.log) Log.i(tag, "After");
			} catch (Exception e) {
				// Log.i(tag, "error");
				e.printStackTrace();
				progressDialog.cancel();
				//Toast.makeText(SelectCustomerActivity.this, e.getMessage(),
						//Toast.LENGTH_SHORT).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (customers != null && customers.size() > 0) {
				if(Constant.log) Log.i(tag, "lens length :" + customers.size());
				/*for (Customer customer : customers) {
					customerNames.add(customer.getCust_name());
				}*/
				arrayAdapterCustomerName = new ArrayAdapter<String>(
						getApplicationContext(),
						R.layout.spinner_simple_item_1);
				
				for (Customer customer : customers) {
					arrayAdapterCustomerName.add(customer.getCust_name()+" - "+customer.getCust_code());
				}
				
				lvCustomer.setAdapter(arrayAdapterCustomerName);
				lvCustomer.setSelected(true);
			
				
				Customer selectedCustomer = mApp.getCustomer();
				if(selectedCustomer != null){
					for(int i=0; i < customers.size(); i++){
						Customer customer = customers.get(i);
						if(customer.getCust_name().equals(selectedCustomer.getCust_name())){
							lvCustomer.setSelection(i);
						}
					}
				}
			}
			
			lvCustomer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					
					for(Customer customer : customers){
						
						if(arrayAdapterCustomerName.getItem(position).contains(customer.getCust_name())){
							mApp.setCustomer(customer);
						}
					}
					onBackPressed();
				}
			});
			
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
