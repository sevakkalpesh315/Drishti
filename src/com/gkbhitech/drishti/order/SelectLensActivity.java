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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.CallbackInterface;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.model.Lens;

public class SelectLensActivity extends Activity{

	private static final String tag = "SelectLensActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private ListView lvLens;
	private EditText edtLensSearchBox;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	private List<Lens> lenses = new ArrayList<Lens>();
	private List<String> lensDescription = new ArrayList<String>();

	private ProgressDialog progressDialog;
	
	private ArrayAdapter<String> arrayAdapterLensDescription;
	
	
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
		lvLens = (ListView) findViewById(R.id.lv_items);
		edtLensSearchBox = (EditText) findViewById(R.id.edt_search_box);
		
		edtLensSearchBox.addTextChangedListener(textWatcher);
		
		CallbackInterface callbackInterface = new CallbackInterface() {
			
			@Override
			public void callback() {
				// TODO Auto-generated method stub
				
				if(Constant.log) Log.i(tag, "lens callback called");
				
				if (lenses != null) {
					if(Constant.log) Log.i(tag, "lens length :" + lenses.size());
					for (Lens lens : lenses) {
						lensDescription.add(lens.getDescription());
					}
					arrayAdapterLensDescription = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_dropdown_item,
							lensDescription);
					lvLens.setAdapter(arrayAdapterLensDescription);
					
					Lens selectedLens = mApp.getLens();
					if(selectedLens != null){
						for(int i=0; i < lenses.size(); i++){
							Lens lens = lenses.get(i);
							if(lens.getDescription().equals(selectedLens.getDescription())){
								lvLens.setSelection(i);
							}
						}
					}
				}
			}

			@Override
			public void callback(Double l, Double t, String maxThicknessAt,
					String minThicknessAt) {
				// TODO Auto-generated method stub
				
			}
		};
		
		lvLens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				for(Lens lens : lenses){
					if(lens.getDescription().equals(arrayAdapterLensDescription.getItem(position))){
						mApp.setLens(lens);
					}
				}
				onBackPressed();
			}
		});
		
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
		
		FatchCustomerAsynTask fatchCustomerAsynTask = new FatchCustomerAsynTask(SelectLensActivity.this, callbackInterface);
		fatchCustomerAsynTask.execute();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    edtLensSearchBox.removeTextChangedListener(textWatcher);
	    progressDialog.dismiss();
	}
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				arrayAdapterLensDescription.getFilter().filter(s);
				arrayAdapterLensDescription.notifyDataSetChanged();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
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
		
		private Context context;
		private CallbackInterface callbackInterface;
		
		public FatchCustomerAsynTask(Context context, CallbackInterface callbackInterface) {
			this.context = context;
			this.callbackInterface = callbackInterface;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Loding Lenses ...");
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				if(Constant.log) Log.i(tag, "Brfore");
				lenses = dataBaseAdapter.getLensesForOrderStockLens();
				if(Constant.log) Log.i(tag, "After");
				//Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// Log.i(tag, "error");
				e.printStackTrace();
				progressDialog.dismiss();
				//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			/*if (lenses != null) {
				Log.i(tag, "lens length :" + lenses.size());
				for (Lens lens : lenses) {
					lensDescription.add(lens.getDescription());
				}
				arrayAdapterLensDescription = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_spinner_dropdown_item,
						lensDescription);
				lvLens.setAdapter(arrayAdapterLensDescription);
				
				Lens selectedLens = mApp.getLens();
				if(selectedLens != null){
					for(int i=0; i < lenses.size(); i++){
						Lens lens = lenses.get(i);
						if(lens.getDescription().equals(selectedLens.getDescription())){
							lvLens.setSelection(i);
						}
					}
				}
			}

			lvLens.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					
					for(Lens lens : lenses){
						if(lens.getDescription().equals(arrayAdapterLensDescription.getItem(position))){
							mApp.setLens(lens);
						}
					}
					onBackPressed();
				}
			});*/
			
			if(lenses != null && lenses.size() > 0){
				if(callbackInterface != null){
					callbackInterface.callback();
				}
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
