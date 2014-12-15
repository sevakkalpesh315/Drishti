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
import android.widget.Button;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;

public class OrderActivity extends Activity {

	private static final String tag = "OrderActivity";

	private DrishtiApplication mApp;
	
	// .............. variable used in UI ................
	private ImageView imvBack,imvHome;
	private Button btnOrderStockLenses,btnCustomerAndDate,btnRXorder,btnOffers;
	private TextView txtOrderStockLens, txtRXOrder;
	
	// private Button btnTrackOrderByCustRefNo;
	// private Button btnTrackOrderByOrderNo;
	//private Button btnCheckWarranty;
	//private Button btnTOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (Constant.log)
			Log.i(tag, tag + " start");

		registerReceiver();

		setContentView(R.layout.activity_order_home);
		
		mApp = (DrishtiApplication) getApplication();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		btnOrderStockLenses = (Button) findViewById(R.id.btn_order_stock_lenses);
		txtOrderStockLens = (TextView) findViewById(R.id.textView2);
		btnRXorder = (Button) findViewById(R.id.btn_rx_order);
		txtRXOrder = (TextView) findViewById(R.id.textView1);
		btnCustomerAndDate = (Button) findViewById(R.id.btn_track_by_customer_and_date);
		// btnTrackOrderByCustRefNo = (Button)
		// findViewById(R.id.btn_track_order_by_customer_ref_no);
		// btnTrackOrderByOrderNo = (Button)
		// findViewById(R.id.btn_track__order_by_order_no);
		//btnCheckWarranty = (Button) findViewById(R.id.btn_check_warranty);
		btnOffers = (Button) findViewById(R.id.btn_offers);
		// btnTOrder = (Button) findViewById(R.id.btnTackOrder);

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

		if(mApp.getUserType() == 1){
			int active = mApp.getCustomer().getActive(); 
			
			if(active == 4 || active == 8){
				
				btnOrderStockLenses.setVisibility(View.GONE);	
				txtOrderStockLens.setVisibility(View.GONE);
				btnRXorder.setVisibility(View.GONE);
				txtRXOrder.setVisibility(View.GONE);
			}
		}
		
		btnOrderStockLenses.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						OrderStockLensesActivity.class);
				startActivity(i);
			}
		});
		
		btnRXorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), RxOrderActivity.class);
				startActivity(i);
			}
		});

		btnCustomerAndDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						TrackOrderActivity.class);
				startActivity(i);
			}
		});
		/*
		 * btnTrackOrderByCustRefNo.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent i = new Intent(getApplicationContext(),
		 * TrackOrderByCustRefNoActivity.class); startActivity(i); } });
		 * btnTrackOrderByOrderNo.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent i = new Intent(getApplicationContext(),
		 * TrackOrderByOrderNoActivity.class); startActivity(i); } });
		 */
		/*btnCheckWarranty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						CheckWarrantyActivity.class);
				startActivity(i);
			}
		});*/

		/*
		 * btnTOrder.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent i = new Intent(getApplicationContext(),
		 * TrackOrder.class); startActivity(i); } });
		 */

		btnOffers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),AllOffersActivity.class);
				startActivity(i);
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
