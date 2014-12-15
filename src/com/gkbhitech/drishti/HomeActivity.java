package com.gkbhitech.drishti;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gkbhitech.drishti.account.AccountActivity;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.SyncManager;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.geotagging.GeoTagging;
import com.gkbhitech.drishti.geotagging.GeoTaggingActivity;
import com.gkbhitech.drishti.gkb.GkbActivity;
import com.gkbhitech.drishti.inventory.InventorySummaryFormActivity;
import com.gkbhitech.drishti.miscellaneous.MiscellaneousActivity;
import com.gkbhitech.drishti.model.Plant;
import com.gkbhitech.drishti.order.OrderActivity;
import com.gkbhitech.drishti.report.ReportActivity;
import com.gkbhitech.drishti.settings.SettingsActivity;

public class HomeActivity extends Activity{

	private static final String tag = "HomeActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
/*	private GridView gvHome;*/
	private ImageView logout;
	final Context context = this;
	private LinearLayout llReportGeotag;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	private ProgressDialog progressDialog;
	
	private List<Plant> plants = new ArrayList<Plant>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_home1);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		
		llReportGeotag = (LinearLayout) findViewById(R.id.ll_report_geotag);
		
		//geoTagging = new GeoTagging(HomeActivity.this);
		
		progressDialog = new ProgressDialog(HomeActivity.this);
		progressDialog.setMessage("Logout ...");
		
		addListenerOnButton();
			 
		if (!mApp.getIsSyncBlocked()) {
			SyncManager syncManager = new SyncManager(HomeActivity.this, mApp,
					null);
			syncManager.execute();
		} else {
			mApp.setIsSyncBlocked(false);
			// fillPlants();
		}
	}
	
	private void addListenerOnButton() {
				
    	logout = (ImageView) findViewById(R.id.btnLogout);   	
    	logout.setOnClickListener(new View.OnClickListener() {
			
/*			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder adb = new AlertDialog.Builder(context);
	    		adb.setTitle("LOGOUT");
	    		adb.setMessage("Are you sure?");
	    		adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int id) {
	    				// Action for 'Yes' Button
	    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
	    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	        	startActivity(login);
	    	        	// Closing Home screen
	    	        	finish();
	    			}
	    		});
	    		adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int id) {
	    				// Action for 'No' Button
	    				dialog.cancel();
	    			}
	    		});
	    		adb.setIcon(R.drawable.exit1);
	    		adb.show();

	    		return;
			}
				
				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(login);
	        	// Closing Home screen
	        	finish();
			}
		});*/
    	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
					progressDialog.show();
					
					if (mApp.getUserType() == 2) {
						dataBaseAdapter.clearDatabase();
						mApp.clearTimeStamp();
					}else if (mApp.getUserType() == 3) {
						dataBaseAdapter.clearUserPlantsRights();
					}
					//dataBaseAdapter.clearDatabase();
					dataBaseAdapter.close();
					mApp.clearCredentials();
					mApp.setCustomer(null);
					Toast.makeText(getApplicationContext(), "Data Cleared ...", Toast.LENGTH_SHORT).show();
					
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.package.ACTION_LOGOUT");
					sendBroadcast(broadcastIntent);
					
					Intent i = new Intent(getApplicationContext(), LoginActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
					progressDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
    	ImageView b1 = (ImageView)findViewById(R.id.btnGKB);
        b1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle gk = new Bundle();
            	Intent GKB = new Intent (HomeActivity.this, GkbActivity.class);
            	GKB.putExtras(gk);
            	startActivity(GKB);
            }
        });
    	
        ImageView b2 = (ImageView)findViewById(R.id.btnInventory);
        b2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle invent = new Bundle();
            	Intent inventory = new Intent (HomeActivity.this, InventorySummaryFormActivity.class);
            	inventory.putExtras(invent);
            	startActivity(inventory);
            }
        });
        ImageView b3 = (ImageView)findViewById(R.id.btnOrder);
        b3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle or = new Bundle();
            	Intent order = new Intent (HomeActivity.this, OrderActivity.class);
            	order.putExtras(or);
            	startActivity(order);
            }
        });
        
        ImageView b4 = (ImageView)findViewById(R.id.btnAccount);
        b4.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle acc = new Bundle();
            	Intent account = new Intent (HomeActivity.this, AccountActivity.class);
            	account.putExtras(acc);
            	startActivity(account);
            }
        });
        
        if(mApp.getUserType() != 1){
        
        	llReportGeotag.setVisibility(View.VISIBLE);
        	
	        ImageView b5 = (ImageView)findViewById(R.id.btnReport);
	        b5.setOnClickListener(new OnClickListener() {
	        	public void onClick(View v) {
	            	Bundle rep = new Bundle();
	            	Intent report = new Intent (HomeActivity.this, ReportActivity.class);
	            	report.putExtras(rep);
	            	startActivity(report);
	            }
	        });
	        
	        ImageView b8 = (ImageView)findViewById(R.id.btnGEO);
	        b8.setOnClickListener(new OnClickListener() {
	        	public void onClick(View v) {
	            	//Bundle geot = new Bundle();
	            	Intent GEO = new Intent (HomeActivity.this, GeoTaggingActivity.class);
	            	//GEO.putExtras(geot);
	            	startActivity(GEO);
	            	//new GeoTagging(HomeActivity.this);
	            }
	        });
        
        }
        
        /*ImageView b6 = (ImageView)findViewById(R.id.btnMisc);
        b6.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle misc = new Bundle();
            	Intent Miscellaneous = new Intent (HomeActivity.this, MiscellaneousActivity.class);
            	Miscellaneous.putExtras(misc);
            	startActivity(Miscellaneous);
            }
        });*/
        
        ImageView b7 = (ImageView)findViewById(R.id.btnSetting);
        b7.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle sett = new Bundle();
            	Intent Settings = new Intent (HomeActivity.this, SettingsActivity.class);
            	Settings.putExtras(sett);
            	startActivity(Settings);
            }
        });
        
        
        
        
        
		/*Button b1 = (Button)findViewById(R.id.btnGKB);
        b1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle gk = new Bundle();
            	Intent GKB = new Intent (HomeActivity.this, GkbActivity.class);
            	GKB.putExtras(gk);
            	startActivity(GKB);
            }
        });
        
		Button b2 = (Button)findViewById(R.id.btnInventory);
        b2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle invent = new Bundle();
            	Intent inventory = new Intent (HomeActivity.this, InventorySummaryFormActivity.class);
            	inventory.putExtras(invent);
            	startActivity(inventory);
            }
        });
        
		Button b3 = (Button)findViewById(R.id.btnOrder);
        b3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle or = new Bundle();
            	Intent order = new Intent (HomeActivity.this, OrderActivity.class);
            	order.putExtras(or);
            	startActivity(order);
            }
        });
        
		Button b4 = (Button)findViewById(R.id.btnAccount);
        b4.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle acc = new Bundle();
            	Intent account = new Intent (HomeActivity.this, AccountActivity.class);
            	account.putExtras(acc);
            	startActivity(account);
            }
        });
        
		Button b5 = (Button)findViewById(R.id.btnReport);
        b5.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle rep = new Bundle();
            	Intent report = new Intent (HomeActivity.this, ReportActivity.class);
            	report.putExtras(rep);
            	startActivity(report);
            }
        });
        
		Button b6 = (Button)findViewById(R.id.btnMisc);
        b6.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle misc = new Bundle();
            	Intent Miscellaneous = new Intent (HomeActivity.this, MiscellaneousActivity.class);
            	Miscellaneous.putExtras(misc);
            	startActivity(Miscellaneous);
            }
        });
        
		Button b7 = (Button)findViewById(R.id.btnSetting);
        b7.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle sett = new Bundle();
            	Intent Settings = new Intent (HomeActivity.this, SettingsActivity.class);
            	Settings.putExtras(sett);
            	startActivity(Settings);
            }
        });
        
        Button b8 = (Button)findViewById(R.id.btnGEO);
        b8.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
            	Bundle geot = new Bundle();
            	Intent GEO = new Intent (HomeActivity.this, GeoTaggingActivity.class);
            	GEO.putExtras(geot);
            	startActivity(GEO);
            }
        });*/
        
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

