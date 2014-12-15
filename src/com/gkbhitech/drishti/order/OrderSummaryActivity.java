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
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.Order;
import com.gkbhitech.drishti.model.Status;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseOrderHistory;

public class OrderSummaryActivity extends Activity implements OnTouchListener{

	private static final String tag = "OrderSummary";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private ListView lvOrderSummary;
	private TableLayout tblOrderSummary;
	private TableLayout tblOrderHeader;
	private TableLayout tblRowNo;
    private HorizontalScrollView hsvOrderSummaryTableHeader;
    private HorizontalScrollView hsvOrderSummaryTable;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private float mx, my;
	
	//private OrderSummaryAdapter orderSummaryAdapter;
	private MethodResponseOrderHistory methodResponseOrderHistory;
	
	private List<Status> statuses;
	
	private TextView txtRowNo, txtOrderNo, txtRefNo, txtDate, txtStatus, txtGo,
		txtHeaderRowNo, txtHeaderOrderNo, txtHeaderRefNo, txtHeaderDate, txtHeaderStatus, txtHeaderGo;
	//private int widthOfRowNo, widthOfOrderNo, widthOfRefNO, widthOfDate, widthOfStatus, widthOfGo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_order_summary);
		
		mApp =  (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		tblRowNo = (TableLayout) findViewById(R.id.tbl_row_no);
		tblOrderHeader = (TableLayout) findViewById(R.id.tbl_order_summary_table_header);
		tblOrderSummary = (TableLayout)findViewById(R.id.tbl_order_summary_table);
		hsvOrderSummaryTableHeader = (HorizontalScrollView)findViewById(R.id.hsv_order_summary_table_header);
		hsvOrderSummaryTable = (HorizontalScrollView)findViewById(R.id.hsv_order_summary_table);
		txtHeaderOrderNo = (TextView) findViewById(R.id.txt_order_no);
		txtHeaderRefNo = (TextView) findViewById(R.id.txt_ref_no);
		txtHeaderDate = (TextView) findViewById(R.id.txt_date);
		txtHeaderStatus = (TextView) findViewById(R.id.txt_status);
		
		txtHeaderOrderNo.setPadding(5,5,5,5);
		txtHeaderRefNo.setPadding(5,5,5,5);
		txtHeaderDate.setPadding(5,5,5,5);
		txtHeaderStatus.setPadding(5,5,5,5);
		
		statuses = dataBaseAdapter.getStatus();
		
		if(mApp.getOrders() != null){
			fillTable(mApp.getOrders());
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
		
		hsvOrderSummaryTableHeader.setOnTouchListener(this);
		hsvOrderSummaryTable.setOnTouchListener(this);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	
	void fillTable(final Order[] orders) {
		
		if (statuses == null) {
			if(Constant.log) Log.i(tag, "status is blenk");
			return;
		}
		
		TableRow rowNo;
		TableRow row;
		
		for (int current = 0; current < orders.length; current++) {
			
			final Order order = orders[current];
			
			final int position = current;
			row = new TableRow(getApplicationContext());
			rowNo = new TableRow(getApplicationContext());
			
			txtRowNo = new TextView(getApplicationContext());
			txtRowNo.setText((current+1)+"");
			
			txtOrderNo = new TextView(getApplicationContext());
			txtRefNo = new TextView(getApplicationContext());
			txtDate = new TextView(getApplicationContext());
			txtStatus = new TextView(getApplicationContext());
			
			txtRowNo.setSingleLine();
			txtOrderNo.setSingleLine();
			txtRefNo.setSingleLine();
			txtDate.setSingleLine();
			txtStatus.setSingleLine();
			
			txtGo = new TextView(getApplicationContext());
			txtGo.setText("Go");
			
			txtOrderNo.setText(order.getOrder_no());
			txtRefNo.setText(order.getCust_refno());
			txtDate.setText(convertIntegerToStringDate(order.getOrder_date()));
			for(Status status : statuses){
				if(status.getStatus_code() == order.getStatus_code()){
					txtStatus.setText(status.getStatus_desc());
				}
			}

			txtGo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					//Log.i(tag, "R_Plant : "+order.getR_plant());
					//Log.i(tag, "S_Plant : "+order.getS_plant());
					
					mApp.setOrderNo(order.getOrder_no());
					OrderSummaryAsynTask orderSummaryAsynTask = new OrderSummaryAsynTask(OrderSummaryActivity.this, mApp, order);
					orderSummaryAsynTask.execute();
				}
			});
			
			txtRowNo.setTextColor(Color.WHITE);
			txtRowNo.setTypeface(null, 1);
			txtOrderNo.setTypeface(null, 1);
			txtRefNo.setTypeface(null, 1);
			txtDate.setTypeface(null, 1);
			txtStatus.setTypeface(null, 1);
			txtGo.setTypeface(null, 1);

			txtRowNo.setTextSize(12);
			txtOrderNo.setTextSize(12);
			txtRefNo.setTextSize(12);
			txtDate.setTextSize(12);
			txtStatus.setTextSize(12);
			txtGo.setTextSize(12);
			
			txtRowNo.setPadding(5, 5, 5, 5);
			txtOrderNo.setPadding(5, 5, 5, 5);
			txtRefNo.setPadding(5, 5, 5, 5);
			txtDate.setPadding(5, 5, 5, 5);
			txtStatus.setPadding(5, 5, 5, 5);
			txtGo.setPadding(5, 5, 5, 5);
			
			txtRowNo.setGravity(Gravity.CENTER);
			txtOrderNo.setGravity(Gravity.CENTER);
			txtDate.setGravity(Gravity.CENTER);
			txtRefNo.setGravity(Gravity.CENTER);
			txtStatus.setGravity(Gravity.CENTER);
			txtGo.setGravity(Gravity.CENTER);
			
			txtOrderNo.setTextColor(Color.BLACK);
			txtRefNo.setTextColor(Color.BLACK);
			txtDate.setTextColor(Color.BLACK);
			txtStatus.setTextColor(Color.BLACK);
			txtGo.setTextColor(Color.BLUE);
			
			rowNo.addView(txtRowNo);
			
			row.addView(txtOrderNo);
			row.addView(txtRefNo);
			row.addView(txtDate);
			row.addView(txtStatus);
			row.addView(txtGo);
			
			row.setBackgroundColor(Color.WHITE);
			
			txtRowNo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for(int i=1; i<=orders.length; i++){
						
						if(Constant.log) Log.i(tag, "Selected Position : "+position);
						if(i == (position+1)){
							tblOrderSummary.getChildAt(i-1).setBackgroundColor(R.color.lightgrey);
						}else{
							tblOrderSummary.getChildAt(i-1).setBackgroundColor(Color.WHITE);
						}
					}
				}
			});
			
			tblOrderSummary.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//Log.i(tag, rowNo.toString());
			tblRowNo.addView(rowNo, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		
		ViewTreeObserver vtoForRowNo = txtRowNo.getViewTreeObserver();
		vtoForRowNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				int widthOfRowNo = txtRowNo.getWidth();
            	//if(Constant.log) Log.i(tag, "Row No lenght : "+widthOfRowNo);
            	tblOrderHeader.setPadding(widthOfRowNo, 0,25,0);
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
		
		ViewTreeObserver vtoForRefNo = txtRefNo.getViewTreeObserver();
		vtoForRefNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
            	ViewTreeObserver vtoForHeaderRefNo = txtHeaderRefNo.getViewTreeObserver();
            	vtoForHeaderRefNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						int widthOfRefNO = txtRefNo.getWidth();
						int widthOfHeaderRefNo = txtHeaderRefNo.getWidth();
						if(widthOfHeaderRefNo > widthOfRefNO){
							txtRefNo.setWidth(widthOfHeaderRefNo);
						}else{
							txtHeaderRefNo.setWidth(widthOfRefNO);
						}
						//if(Constant.log) Log.i(tag, "Ref header no lenght : "+txtHeaderRefNo.getWidth());
						//if(Constant.log) Log.i(tag, "Ref no lenght : "+txtRefNo.getWidth());
						ViewTreeObserver obs = txtHeaderRefNo.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtRefNo.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
		
		ViewTreeObserver vtoForDate = txtDate.getViewTreeObserver();
		vtoForDate.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
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
		
		ViewTreeObserver vtoForStatus = txtStatus.getViewTreeObserver();
		vtoForStatus.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
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
                hsvOrderSummaryTableHeader.scrollBy((int) (mx - curX), 0);
                hsvOrderSummaryTable.scrollBy((int) (mx - curX),0);
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
