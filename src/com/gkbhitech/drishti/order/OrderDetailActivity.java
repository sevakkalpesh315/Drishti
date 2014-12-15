package com.gkbhitech.drishti.order;

import java.util.Calendar;

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
import com.gkbhitech.drishti.common.MyDate;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Coating;
import com.gkbhitech.drishti.model.Lens;
import com.gkbhitech.drishti.model.OrderAndPowerDetail;
import com.gkbhitech.drishti.model.Status;

public class OrderDetailActivity extends Activity {

	private static final String tag = "OrderDetailActivity";

	// .............. variable used in UI .............
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtCustomerName,txtOrderType,txtLensMaterial,txtFrameDetail,txtFitting,txtTinting,
			txtStatus,txtOrderDateAndTime,txtOrderNo,txtCustomerRefNo,txtCoating,txtEngravingName,
			txtExtraPrice,txtWarranty;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	//private Order order;
	private OrderAndPowerDetail orderAndPowerDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_detail);

		mApp = (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		dataBaseAdapter = mApp.getDataBaseAdapter();

		//order = mApp.getSelectedOrder();
		orderAndPowerDetail = mApp.getOrderAndPowerDetails()[0];
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtStatus = (TextView) findViewById(R.id.txt_actual_status);
		txtOrderDateAndTime = (TextView) findViewById(R.id.txt_actual_order_date_time);
		txtOrderNo = (TextView) findViewById(R.id.txt_actual_order_no);
		txtCustomerName = (TextView) findViewById(R.id.txt_actual_customer_name);
		txtOrderType = (TextView) findViewById(R.id.txt_actual_order_type);
		txtLensMaterial = (TextView) findViewById(R.id.txt_actual_lens_material);
		txtCoating = (TextView) findViewById(R.id.txt_actual_coating);
		txtCustomerRefNo = (TextView) findViewById(R.id.txt_actual_cust_ref_no);
		txtFrameDetail = (TextView) findViewById(R.id.txt_actual_frame_details);
		txtFitting = (TextView) findViewById(R.id.txt_actual_fitting);
		txtTinting = (TextView) findViewById(R.id.txt_actual_tinting);
		txtExtraPrice = (TextView) findViewById(R.id.txt_actual_extra_price);
		txtEngravingName = (TextView) findViewById(R.id.txt_actual_engraving_name);
		txtWarranty = (TextView) findViewById(R.id.txt_warranty);
		
		if (orderAndPowerDetail != null) {
			Status status = dataBaseAdapter.getStatusByStatusCode(orderAndPowerDetail.getStatus_code()); 
			txtStatus.setText(status.getStatus_desc());
			txtOrderDateAndTime.setText(convertIntegerToStringDate(orderAndPowerDetail.getOrder_date()) + " "
					+ convertIntegerToStringTime(orderAndPowerDetail.getOrder_time()));
			txtOrderNo.setText(orderAndPowerDetail.getOrder_no());
			txtCustomerName.setText(orderAndPowerDetail.getCust_name());
			if(orderAndPowerDetail.getOrder_type().equals("S")){
				txtOrderType.setText("Stock order");
			}else if(orderAndPowerDetail.getOrder_type().equals("P")){
				txtOrderType.setText("Rx order");
			}
			//Lens lens = dataBaseAdapter.getLensByLensCode(orderAndPowerDetail.getLens_code());
			//if(lens != null){
			txtLensMaterial.setText(orderAndPowerDetail.getDescription());
			//}
			Coating coating = dataBaseAdapter.getCoatingByCoatingCode(orderAndPowerDetail.getCoating_code());
			txtCoating.setText(coating.getCoating_desc());
			txtCustomerRefNo.setText(orderAndPowerDetail.getCust_refno());
			
			String frameDetail = "BW:"+orderAndPowerDetail.getFrm_b()+" BH:"+orderAndPowerDetail.getFrm_a()+" Shape:"+orderAndPowerDetail.getFrm_shape();
			txtFrameDetail.setText(frameDetail);
			txtFitting.setText(orderAndPowerDetail.getFitting_rate() + "");
			txtTinting.setText(orderAndPowerDetail.getTinting_rate() + "");
			txtExtraPrice.setText(orderAndPowerDetail.getExtra_price() + "");
			txtEngravingName.setText(orderAndPowerDetail.getIndiv_engr());
			if(coating.getRoute_id() == 2 || coating.getRoute_id() == 3){
				/*Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.YEAR, -1);
				int intDate = MyDate.createIntegerDate(calendar.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH);
				
				if(Constant.log)Log.i(tag, "Order date : "+orderAndPowerDetail.getOrder_date()+" Date : "+intDate);*/
				int day = orderAndPowerDetail.getCurrentDate()-orderAndPowerDetail.getOrder_date();
				if(day <= 365){
					txtWarranty.setText("Warranty valid on Decoat-Recoat");
				}else{
					txtWarranty.setText("No Warranty");
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
	
	private String convertIntegerToStringDate(Integer date){
		String stringDate = date.toString();
		return stringDate.substring(6, 8)+"/"+stringDate.substring(4, 6)+"/"+stringDate.substring(0,4);
	}
	private String convertIntegerToStringTime(Integer time){
		String stringTime = time.toString();
		//if(stringTime.length() == 6){
		//	return stringTime.substring(0,2)+":"+stringTime.substring(2,4)+":"+stringTime.substring(4,6);
		//}else{
			int length = stringTime.length();
			while(length < 6){
				stringTime = "0"+stringTime;
				length = stringTime.length();
			}
			return stringTime.substring(0,2)+":"+stringTime.substring(2,4)+":"+stringTime.substring(4,6);
		//}
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
