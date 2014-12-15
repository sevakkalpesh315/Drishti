package com.gkbhitech.drishti.order;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Offer;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseAllOffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllOffersActivity extends Activity{

	private static final String tag = "AllOfferActivity";

	// .............. variable used in UI ................
	private ImageView imvBack, imvHome;
	private LinearLayout llAllOffers;
	
	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used for request ....................
	private WebServiceObjectClient webServiceObjectClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_all_offer);
		
		mApp =  (DrishtiApplication) getApplication();
		//dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		llAllOffers = (LinearLayout) findViewById(R.id.ll_all_offer);
		
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		llAllOffers.removeAllViews();
		AllOfferAsynTask allOfferAsynTask = new AllOfferAsynTask(AllOffersActivity.this);
		allOfferAsynTask.execute();
	}
	
	private class AllOfferAsynTask extends AsyncTask<Void, Void, Integer>{

		private Context context;
		private ProgressDialog progressDialog;
		private MethodResponseAllOffer methodResponseAllOffer;
		
		
		public AllOfferAsynTask(Context context){
			this.context = context;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading ...");
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				methodResponseAllOffer = webServiceObjectClient.getAllOffer(mApp.getAccessToken(), mApp.getUserName());
				if(methodResponseAllOffer != null){
					Constant.responseCode = methodResponseAllOffer.getResponseCode();
					Constant.responseMsg = methodResponseAllOffer.getResponseMessage();
					if(Constant.responseCode == Constant.RESULT_SUCCESS){
						return Constant.responseCode;
					}else{
						return Constant.responseCode;
					}
				}else{
					return Constant.RESULT_NULL_RESPONSE;
				}
			} catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			} catch (Exception e) {
				e.printStackTrace();
				return Constant.RESULT_ERROR;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(Constant.log)Log.i(tag, "Result : "+result);
			
			progressDialog.dismiss();
			
			if(result == Constant.RESULT_SUCCESS){
				createOfferList(methodResponseAllOffer.getDataArray());
			}else{
				MyToast myToast = new MyToast();
				myToast.show(getApplicationContext(), result, Constant.responseMsg);
			}
		}
	}
	
	private void createOfferList(Offer[] offers){
		
		final Intent i = new Intent(AllOffersActivity.this, OfferActivity.class);
		for(final Offer offer : offers){
			if(Constant.log)Log.i(tag, "Coat : "+offer.getCoat());
			TextView tvOfferDes = new TextView(getApplicationContext());
			tvOfferDes.setTextColor(getResources().getColor(R.color.black));
			tvOfferDes.setPadding(5, 5, 5, 5);
			String discount = "";
			if(offer.getDiscount_type().equals("P")){
				discount = offer.getDiscount()+"%";
			}else if(offer.getDiscount_type().equals("V")){
				discount = "Rs."+offer.getDiscount();				
			}
			tvOfferDes.setText("Your offer for the day "+discount+" discount on "+offer.getDescription());
			
			tvOfferDes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mApp.setOffer(offer);
					startActivity(i);
				}
			});
			
			llAllOffers.addView(tvOfferDes);
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
