package com.gkbhitech.drishti.order;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.OrderAndPowerDetail;

public class PowerDetailActivity extends Activity {

	private static final String tag = "PowerDetailActivity";

	// .............. variable used in UI .............
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtLeftSph;
	private TextView txtRightSph;
	private TextView txtLeftCyl;
	private TextView txtRightCyl;
	private TextView txtLeftAxis;
	private TextView txtRightAxis;
	private TextView txtLeftAdd;
	private TextView txtRightAdd;
	private TextView txtLeftCT;
	private TextView txtRightCT;
	private TextView txtLeftET;
	private TextView txtRightET;
	private TextView txtLeftCribDia;
	private TextView txtRightCribDia;
	private TextView txtLeftAmount;
	private TextView txtRightAmount;
	
	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	//private Order order;
	private OrderAndPowerDetail[] orderAndPowerDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_power_detail);

		mApp = (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();

		orderAndPowerDetails = mApp.getOrderAndPowerDetails();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtLeftSph = (TextView) findViewById(R.id.txt_left_sph); 
		txtRightSph = (TextView) findViewById(R.id.txt_right_sph); 
		txtLeftCyl = (TextView) findViewById(R.id.txt_left_cyl); 
		txtRightCyl = (TextView) findViewById(R.id.txt_right_cyl); 
		txtLeftAxis = (TextView) findViewById(R.id.txt_left_axis); 
		txtRightAxis = (TextView) findViewById(R.id.txt_right_axis); 
		txtLeftAdd = (TextView) findViewById(R.id.txt_left_add); 
		txtRightAdd = (TextView) findViewById(R.id.txt_right_add); 
		txtLeftCT = (TextView) findViewById(R.id.txt_left_ct); 
		txtRightCT = (TextView) findViewById(R.id.txt_right_ct); 
		txtLeftET = (TextView) findViewById(R.id.txt_left_et); 
		txtRightET = (TextView) findViewById(R.id.txt_right_et); 
		txtLeftCribDia = (TextView) findViewById(R.id.txt_left_crib_dia); 
		txtRightCribDia = (TextView) findViewById(R.id.txt_right_crib_dia); 
		txtLeftAmount = (TextView) findViewById(R.id.txt_left_amount); 
		txtRightAmount = (TextView) findViewById(R.id.txt_right_amount); 
		
		if (orderAndPowerDetails != null && orderAndPowerDetails.length >0) {
			
			//if(Constant.log) Log.i(tag, "detail length : "+orderAndPowerDetails.length);
			
			OrderAndPowerDetail firstDetail = null;
			OrderAndPowerDetail secondDetail = null;
			if(orderAndPowerDetails.length == 1){
				firstDetail = orderAndPowerDetails[0];
			}else if(orderAndPowerDetails.length == 2){
				firstDetail = orderAndPowerDetails[0];
				secondDetail = orderAndPowerDetails[1];
			}
			
			if(firstDetail != null){
				if(firstDetail.getEye().equals("L")){
					setLeftEyeDetail(firstDetail);
				}else if(firstDetail.getEye().equals("R")){
					setRightEyeDetail(firstDetail);
				}
			}
			if(secondDetail != null){
				if(secondDetail.getEye().equals("L")){
					setLeftEyeDetail(secondDetail);
				}else if(secondDetail.getEye().equals("R")){
					setRightEyeDetail(secondDetail);
				}
			}
		}
		
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
	
	private void setLeftEyeDetail(OrderAndPowerDetail left){
		txtLeftSph.setText(left.getSph()+"");
		txtLeftCyl.setText(left.getCyl()+"");
		txtLeftAxis.setText(left.getAxis()+"");
		txtLeftAdd.setText(left.getAddi()+"");
		txtLeftCT.setText(left.getMin_ct()+"");
		txtLeftET.setText(left.getMin_et()+"");
		txtLeftCribDia.setText(left.getCrib_dia()+"");
		txtLeftAmount.setText(left.getTotal_amnt()+"");
	}
	private void setRightEyeDetail(OrderAndPowerDetail right){
		txtRightSph.setText(right.getSph()+"");
		txtRightCyl.setText(right.getCyl()+"");
		txtRightAxis.setText(right.getAxis()+"");
		txtRightAdd.setText(right.getAddi()+"");
		txtRightCT.setText(right.getMin_ct()+"");
		txtRightET.setText(right.getMin_et()+"");
		txtRightCribDia.setText(right.getCrib_dia()+"");
		txtRightAmount.setText(right.getTotal_amnt()+"");
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
