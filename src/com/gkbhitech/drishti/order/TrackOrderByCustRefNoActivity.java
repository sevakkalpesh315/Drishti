package com.gkbhitech.drishti.order;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrder;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrders;

public class TrackOrderByCustRefNoActivity extends Activity{

	private static final String tag = "TrackOrderByCustRefNoOrOrderNoActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtPlantNo;
	private EditText edtCustomerRefNo;
	private Button btnShow;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private MethodResponseOrders methodResponseOrders;
	private MethodResponseOrder methodResponseOrder;
	
	private String customerRefNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//if(savedInstanceState != null){
		
		super.onCreate(savedInstanceState);
		//return;
		//}
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_track_order_by_cust_ref_no);
		
		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		edtCustomerRefNo = (EditText) findViewById(R.id.edt_cust_ref_no);
		btnShow = (Button) findViewById(R.id.btn_show);
		
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
		
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				customerRefNo = edtCustomerRefNo.getText().toString().trim();
				
				if(customerRefNo == null || customerRefNo.equals("")){
					Toast.makeText(getApplicationContext(), "Please select Customer Ref No", Toast.LENGTH_SHORT).show();
					return;
				}
				
				TrackOrderByCustRefNoAsynTask trackOrderByCustRefNoAsynTask = new TrackOrderByCustRefNoAsynTask(TrackOrderByCustRefNoActivity.this, mApp, customerRefNo);
				trackOrderByCustRefNoAsynTask.execute();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
