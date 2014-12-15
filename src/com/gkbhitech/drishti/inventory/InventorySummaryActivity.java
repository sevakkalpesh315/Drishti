package com.gkbhitech.drishti.inventory;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Status;

public class InventorySummaryActivity extends Activity{

private static final String tag = "InventorySummary";
	
	//.............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private TextView txtPlantNo, txtProductBrand, txtLensCode, txtCoating, txtSph, txtCyl, txtQty;
	private Button btnClickToOrder;
	//private TableLayout tblInventorySummary;
    //private HorizontalScrollView hsvInventorySummaryTableHeader;
    //private HorizontalScrollView hsvInventorySummaryTable;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private float mx, my;
	private Float sph, cyl;
	private String brandName, lensName, coatingName, qty;
	
	private List<Status> statuses;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_inventory_summary);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtPlantNo = (TextView) findViewById(R.id.txt_plant_no);
		txtProductBrand = (TextView) findViewById(R.id.txt_actual_product_brand);
		txtLensCode = (TextView) findViewById(R.id.txt_actual_lens_code);
		txtCoating = (TextView) findViewById(R.id.txt_actual_coating);
		txtSph = (TextView) findViewById(R.id.txt_actual_sph);
		txtCyl = (TextView) findViewById(R.id.txt_actual_cyl);
		txtQty = (TextView) findViewById(R.id.txt_plant_area);
		btnClickToOrder = (Button) findViewById(R.id.btn_click_to_order);
		//tblInventorySummary = (TableLayout)findViewById(R.id.tbl_order_summary_table);
		//hsvInventorySummaryTableHeader = (HorizontalScrollView)findViewById(R.id.hsv_order_summary_table_header);
		//hsvInventorySummaryTable = (HorizontalScrollView)findViewById(R.id.hsv_order_summary_table);
		
		final Bundle bundle = getIntent().getExtras();
		brandName = bundle.getString("brandName");
		lensName = bundle.getString("lensName");
		coatingName = bundle.getString("coatingName");
		
		sph = bundle.getFloat("sph");
		cyl = bundle.getFloat("cyl");
		qty = bundle.getString("available_at");
				
		statuses = dataBaseAdapter.getStatus();
		txtPlantNo.setText(" "+mApp.getPlant());
		if(brandName != null){
			txtProductBrand.setText(" "+brandName);
		}
		if(lensName != null){
			txtLensCode.setText(" "+lensName);
		}
		if(coatingName != null){
			txtCoating.setText(" "+coatingName);
		}
		if(sph != null){
			txtSph.setText(" "+sph.toString());
		}
		if(cyl != null){
			txtCyl.setText(" "+cyl.toString());
		}
		if(qty != null){
			txtQty.setText(" "+qty.toString());
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
		
		btnClickToOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(InventorySummaryActivity.this, OrderThroughInventoryActivity.class);
				i.putExtra("brandCode", bundle.getString("brandCode"));
				i.putExtra("lensCode", bundle.getString("lensCode"));
				i.putExtra("coatingCode", bundle.getString("coatingCode"));
				i.putExtra("brandName", brandName);
				i.putExtra("lensName", lensName);
				i.putExtra("coatingName", coatingName);
				i.putExtra("sph", sph);
				i.putExtra("cyl", cyl);
				startActivity(i);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	/*void fillTable(InventoryItem[] inventoryItems) {
		
		if (statuses == null) {
			Log.i(tag, "status is blenk");
			return;
		}
		
		TableRow row;
		TextView txtSrNo, txtMaterial, txtSloc, txtQuantity;
		
		for (int current = 0; current < inventoryItems.length; current++) {
			
			InventoryItem inventoryItem = inventoryItems[current];
			
			final int position = current;
			row = new TableRow(this);
			
			
			txtSrNo = new TextView(this);
			txtSrNo.setTextColor(getResources().getColor(R.color.orange));
			txtMaterial = new TextView(this);
			txtMaterial.setTextColor(getResources().getColor(R.color.blue));
			txtSloc = new TextView(this);
			txtSloc.setTextColor(getResources().getColor(R.color.orange));
			txtQuantity = new TextView(this);
			txtQuantity.setTextColor(getResources().getColor(R.color.blue));
			
			txtSrNo.setText((current-1)+"");
			txtMaterial.setText(inventoryItem.getMat_code());
			txtSloc.setText(inventoryItem.getSloc());
			txtQuantity.setText(inventoryItem.getQty()+"");

			txtSrNo.setTypeface(null, 1);
			txtMaterial.setTypeface(null, 1);
			txtSloc.setTypeface(null, 1);
			txtQuantity.setTypeface(null, 1);

			txtSrNo.setTextSize(15);
			txtMaterial.setTextSize(15);
			txtSloc.setTextSize(15);
			txtQuantity.setTextSize(15);

			txtSrNo.setWidth(150);
			txtMaterial.setWidth(150);
			txtSloc.setWidth(150);
			txtQuantity.setWidth(150);
			
			txtSrNo.setGravity(Gravity.CENTER);
			txtMaterial.setGravity(Gravity.CENTER);
			txtSloc.setGravity(Gravity.CENTER);
			txtQuantity.setGravity(Gravity.CENTER);
			
			row.addView(txtSrNo);
			row.addView(txtMaterial);
			row.addView(txtSloc);
			row.addView(txtQuantity);
			
			row.setBackgroundColor(Color.WHITE);
			
			tblInventorySummary.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}*/
	
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
