package com.gkbhitech.drishti.account;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;

public class AccountActivity extends Activity {

	private static final String tag = "AccountActivity";
	
	
	private DrishtiApplication mApp;
	
	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private Button btnActivatePPLP;
	private Button btnPPLPReport;
	private Button btnPPLPBalance;
	private Button btnReceipt;
	private Button btnStatementOfAccount;
	private Button btnGoodWillCode;
	private Button btnCustomerDues;
	private Button btnSalesBooster;
	private static Intent i;
	private LinearLayout llNotForCustomer;
	private TextView txtPplpBalance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_account_home);
		
		mApp = (DrishtiApplication) getApplication();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		llNotForCustomer = (LinearLayout) findViewById(R.id.ll_not_for_customer) ;
		btnActivatePPLP = (Button) findViewById(R.id.btn_activate_pplp);
		//btnPPLPReport = (Button) findViewById(R.id.btn_pplp_reports);
		btnPPLPBalance = (Button) findViewById(R.id.btn_pplp_balance);
		btnReceipt = (Button) findViewById(R.id.btn_receipt);
		btnStatementOfAccount = (Button) findViewById(R.id.btn_statement_of_account);
		btnGoodWillCode = (Button) findViewById(R.id.btn_goodwill_code);
		btnCustomerDues = (Button) findViewById(R.id.btn_customer_dues);
		btnSalesBooster = (Button) findViewById(R.id.btn_sales_booster);
		//text_field_two
		txtPplpBalance = (TextView) findViewById(R.id.text_field_two);
		
		
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
				i = new Intent(getApplicationContext(), HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		if(Constant.log)Log.i(tag, "usertype : "+mApp.getUserType());
		if(mApp.getUserType() != 1){
			
			llNotForCustomer.setVisibility(View.VISIBLE);
			btnActivatePPLP.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					i = new Intent(getApplicationContext(), ActivatePPLPActivity.class);
					startActivity(i);
				}
			});
			btnSalesBooster.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					i = new Intent(getApplicationContext(), SalesBoosterActivity.class);
					startActivity(i);
				}
			});
			btnReceipt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					i = new Intent(getApplicationContext(), CustomerReceiptFormActivity.class);
					startActivity(i);
				}
			});
			btnGoodWillCode.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					i = new Intent(getApplicationContext(), GoodWillCodeFormActivity.class);
					startActivity(i);
				}
			});
		}
		/*btnPPLPReport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(getApplicationContext(), PPLPReportFormActivity.class);
				startActivity(i);
			}
		});*/
		
		if(mApp.getUserType() == 1){
			int active = mApp.getCustomer().getActive(); 
			
			if(active == 4 || active == 8){
				
				btnPPLPBalance.setVisibility(View.VISIBLE);	
				txtPplpBalance.setVisibility(View.VISIBLE);
			}
		}
		if(mApp.getUserType() != 1){
			btnPPLPBalance.setVisibility(View.VISIBLE);	
			txtPplpBalance.setVisibility(View.VISIBLE);
		}
		
		btnPPLPBalance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(getApplicationContext(), PPLPBalanceActivity.class);
				startActivity(i);
			}
		});
		
		btnStatementOfAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(getApplicationContext(), CustomerAccountStatementFormActivity.class);
				startActivity(i);
			}
		});
		
		btnCustomerDues.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(getApplicationContext(), CustomerDuesActvity.class);
				startActivity(i);
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
