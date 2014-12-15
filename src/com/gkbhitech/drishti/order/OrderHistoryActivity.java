package com.gkbhitech.drishti.order;


import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.OrderStatus;
import com.gkbhitech.drishti.model.Status;

public class OrderHistoryActivity extends Activity implements OnTouchListener{

	private static final String tag = "OrderHistoryActivity";
	
	//.............. variable used in UI .............
	private ImageView imvBack;
	private ImageView imvHome;
	private Button btnOrderDetails;
	private TableLayout tblOrderHistory;
	private TableLayout tblOrderHeader;
	private TableLayout tblRowNo;
    private HorizontalScrollView hsvOrderHistoryTableHeader;
    private HorizontalScrollView hsvOrderHistoryTable;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private float mx, my;
    
    private List<Status> statuses;
    
    private TextView txtRowNo, txtOrderNo, txtDate, txtUser, txtTime, txtStatus, txtReason, txtRejectEye,
    			txtHeaderRowNo, txtHeaderOrderNo, txtHeaderDate, txtHeaderUser, txtHeaderTime, txtHeaderStatus,
    			txtHeaderReason, txtHeaderRejectEye;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_history);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		tblRowNo = (TableLayout) findViewById(R.id.tbl_row_no);
		tblOrderHeader = (TableLayout) findViewById(R.id.tbl_order_history_table_header);
		tblOrderHistory = (TableLayout)findViewById(R.id.tbl_order_history);
		hsvOrderHistoryTableHeader = (HorizontalScrollView)findViewById(R.id.hsv_table_header);
		hsvOrderHistoryTable = (HorizontalScrollView)findViewById(R.id.hsv_table);
		btnOrderDetails = (Button) findViewById(R.id.btn_order_detail);
		txtHeaderOrderNo = (TextView) findViewById(R.id.txt_order_no);
		txtHeaderDate = (TextView) findViewById(R.id.txt_date);
		txtHeaderUser = (TextView) findViewById(R.id.txt_user);
		txtHeaderTime = (TextView) findViewById(R.id.txt_time);
		txtHeaderStatus = (TextView) findViewById(R.id.txt_status);
		txtHeaderReason = (TextView) findViewById(R.id.txt_reason);
		
		txtHeaderOrderNo.setPadding(5,5,5,5);
		txtHeaderDate.setPadding(5,5,5,5);
		txtHeaderUser.setPadding(5,5,5,5);
		txtHeaderTime.setPadding(5,5,5,5);
		txtHeaderStatus.setPadding(5,5,5,5);
		txtHeaderReason.setPadding(5,5,5,5);
		
		statuses = dataBaseAdapter.getStatus();
		
		if(mApp.getOrderHistory() != null){
			fillTable(mApp.getOrderHistory().getOrderStatus());
		}
		
		hsvOrderHistoryTableHeader.setOnTouchListener(this);
		hsvOrderHistoryTable.setOnTouchListener(this);
		
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

		btnOrderDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				OrderAndPowerDetailsAsynTask orderAndPowerDetailsAsynTask = new OrderAndPowerDetailsAsynTask(OrderHistoryActivity.this, mApp);
				orderAndPowerDetailsAsynTask.execute();
				
				//Intent i = new Intent(getApplicationContext(), OrderAndPowerDetailsActivity.class);
				//startActivity(i);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-geonnerated method stub
		super.onBackPressed();
	}
	
	void fillTable(final OrderStatus[] orderStatuses) {

		if (statuses == null) {
			if(Constant.log) Log.i(tag, "status is blenk");
			return;
		}
		
		TableRow rowNo;
		TableRow row;
		
		String OrderNumber = mApp.getOrderHistory().getOrderNumber().toString();
		
		for (int current = 0; current < orderStatuses.length; current++) {
			
			OrderStatus orderStatus = orderStatuses[current];
			
			final int position = current;
			row = new TableRow(getApplicationContext());
			rowNo = new TableRow(this);
			
			txtRowNo = new TextView(getApplicationContext());
			txtRowNo.setText((current+1)+"");
			
			txtOrderNo = new TextView(getApplicationContext());
			txtDate = new TextView(getApplicationContext());
			txtTime = new TextView(getApplicationContext());
			txtUser = new TextView(getApplicationContext());
			txtStatus = new TextView(getApplicationContext());
			txtReason = new TextView(getApplicationContext());
			
			txtOrderNo.setText(OrderNumber);
			txtDate.setText(convertIntegerToStringDate(orderStatus.getSdate()));
			txtTime.setText(convertIntegerToStringTime(orderStatus.getStime()));
			txtUser.setText(orderStatus.getUsername());
			
			for(Status status : statuses){
				if(status.getStatus_code() == orderStatus.getStatus_code()){
					txtStatus.setText(status.getStatus_desc());
				}
			}
			if(orderStatus.getReject_code() == null){
				txtReason.setText(" ");
				txtReason.setTextColor(Color.WHITE);
			}else{
				txtReason.setText(orderStatus.getReject_code());
				txtReason.setTextColor(Color.BLACK);
			}

			txtRowNo.setTextColor(Color.WHITE);
			txtOrderNo.setSingleLine();
			txtDate.setSingleLine();
			txtTime.setSingleLine();
			txtStatus.setSingleLine();
			txtUser.setSingleLine();
			txtReason.setSingleLine();
			
			txtRowNo.setTypeface(null, 1);
			txtOrderNo.setTypeface(null, 1);
			txtDate.setTypeface(null, 1);
			txtTime.setTypeface(null, 1);
			txtStatus.setTypeface(null, 1);
			txtUser.setTypeface(null, 1);
			txtReason.setTypeface(null, 1);

			txtRowNo.setTextSize(12);
			txtOrderNo.setTextSize(12);
			txtDate.setTextSize(12);
			txtTime.setTextSize(12);
			txtUser.setTextSize(12);
			txtStatus.setTextSize(12);
			txtReason.setTextSize(12);
			
			txtRowNo.setPadding(5, 5, 5, 5);
			txtOrderNo.setPadding(5, 5, 5, 5);
			txtDate.setPadding(5, 5, 5, 5);
			txtTime.setPadding(5, 5, 5, 5);
			txtStatus.setPadding(5, 5, 5, 5);
			txtUser.setPadding(5, 5, 5, 5);
			txtReason.setPadding(5, 5, 5, 5);
			
			txtRowNo.setGravity(Gravity.CENTER);
			txtOrderNo.setGravity(Gravity.CENTER);
			txtDate.setGravity(Gravity.CENTER);
			txtTime.setGravity(Gravity.CENTER);
			txtUser.setGravity(Gravity.CENTER);
			txtStatus.setGravity(Gravity.CENTER);
			txtReason.setGravity(Gravity.CENTER);
			
			txtOrderNo.setTextColor(Color.BLACK);
			txtDate.setTextColor(Color.BLACK);
			txtTime.setTextColor(Color.BLACK);
			txtUser.setTextColor(Color.BLACK);
			txtStatus.setTextColor(Color.BLACK);
			
			rowNo.addView(txtRowNo);
			
			row.addView(txtOrderNo);
			row.addView(txtDate);
			row.addView(txtTime);
			row.addView(txtUser);
			row.addView(txtStatus);
			row.addView(txtReason);
			
			txtRowNo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for(int i=1; i <= orderStatuses.length; i++){
						
						if(Constant.log) Log.i(tag, "Selected Position : "+position);
						if(i == (position+1)){
							tblOrderHistory.getChildAt(i-1).setBackgroundColor(R.color.lightgrey);
						}else{
							tblOrderHistory.getChildAt(i-1).setBackgroundColor(Color.WHITE);
						}
					}
				}
			});
			
			row.setBackgroundColor(Color.WHITE);
			
			tblOrderHistory.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tblRowNo.addView(rowNo, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		
		ViewTreeObserver vtoForRowNo = txtRowNo.getViewTreeObserver();
		vtoForRowNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
            	tblOrderHeader.setPadding(txtRowNo.getWidth(), 0,0,0);
            	ViewTreeObserver obs = txtRowNo.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForOrderNo = txtOrderNo.getViewTreeObserver();
		vtoForOrderNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderOrderNo = txtHeaderOrderNo.getViewTreeObserver();
				vtoForHeaderOrderNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfOrderNo = txtOrderNo.getWidth();
						int widthOfHeaderOrderNo = txtHeaderOrderNo.getWidth();
						if(widthOfHeaderOrderNo > widthOfOrderNo){
							txtOrderNo.setWidth(widthOfHeaderOrderNo);
						}else{
							txtHeaderOrderNo.setWidth(widthOfOrderNo);
						}
						ViewTreeObserver obs = txtHeaderOrderNo.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtOrderNo.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForDate = txtDate.getViewTreeObserver();
		vtoForDate.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderDate = txtHeaderDate.getViewTreeObserver();
				vtoForHeaderDate.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfDate = txtDate.getWidth();
						int widthOfHeaderDate = txtHeaderDate.getWidth();
						if(widthOfHeaderDate > widthOfDate){
							txtDate.setWidth(widthOfHeaderDate);
						}else{
							txtHeaderDate.setWidth(widthOfDate);
						}
						ViewTreeObserver obs = txtHeaderDate.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtDate.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForTime = txtTime.getViewTreeObserver();
		vtoForTime.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderTime = txtHeaderTime.getViewTreeObserver();
				vtoForHeaderTime.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfTime = txtTime.getWidth();
						int widthOfHeaderTime = txtHeaderTime.getWidth();
						if(widthOfHeaderTime > widthOfTime){
							txtTime.setWidth(widthOfHeaderTime);
						}else{
							txtHeaderTime.setWidth(widthOfTime);
						}
						ViewTreeObserver obs = txtHeaderTime.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtTime.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForUser = txtUser.getViewTreeObserver();
		vtoForUser.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderUser = txtHeaderUser.getViewTreeObserver();
				vtoForHeaderUser.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfUser = txtUser.getWidth();
						int widthOfHeaderUser = txtHeaderUser.getWidth();
						if(widthOfHeaderUser > widthOfUser){
							txtUser.setWidth(widthOfHeaderUser);
						}else{
							txtHeaderUser.setWidth(widthOfUser);
						}
						ViewTreeObserver obs = txtHeaderUser.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtUser.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForStatus = txtStatus.getViewTreeObserver();
		vtoForStatus.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderStatus = txtHeaderStatus.getViewTreeObserver();
				vtoForHeaderStatus.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfStatus = txtStatus.getWidth();
						int widthOfHeaderStatus = txtHeaderStatus.getWidth();
						if(widthOfHeaderStatus > widthOfStatus){
							txtStatus.setWidth(widthOfHeaderStatus);
						}else{
							txtHeaderStatus.setWidth(widthOfStatus);
						}
						ViewTreeObserver obs = txtHeaderStatus.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtStatus.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		ViewTreeObserver vtoForReason = txtReason.getViewTreeObserver();
		vtoForReason.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderReason = txtHeaderReason.getViewTreeObserver();
				vtoForHeaderReason.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfReason = txtReason.getWidth();
						int widthOfHeaderReason = txtHeaderReason.getWidth();
						if(widthOfHeaderReason > widthOfReason){
							txtReason.setWidth(widthOfHeaderReason);
						}else{
							txtHeaderReason.setWidth(widthOfReason);
						}
						ViewTreeObserver obs = txtHeaderReason.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtReason.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                hsvOrderHistoryTableHeader.scrollBy((int) (mx - curX), 0);
                hsvOrderHistoryTable.scrollBy((int) (mx - curX),0);
                mx = curX;
                my = curY;
                break;
        }

        return true;
	}
	private String convertIntegerToStringDate(Integer date){
		String stringDate = date.toString();
		return stringDate.substring(6, 8)+"/"+stringDate.substring(4, 6)+"/"+stringDate.substring(0,4);
	}
	private String convertIntegerToStringTime(Integer time){
		String stringTime = time.toString();
		/*int i = 6 - stringTime.length();
		String padding = "";
		for (int j = 0 ; j < i ; i++){
			padding += "0";
		}
		stringTime = padding+stringTime;*/
		int length = stringTime.length();
		while (length < 6) {
			stringTime = "0" + stringTime;
			length = stringTime.length();
		}
		return stringTime.substring(0,2)+":"+stringTime.substring(2,4)+":"+stringTime.substring(4,6);
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
