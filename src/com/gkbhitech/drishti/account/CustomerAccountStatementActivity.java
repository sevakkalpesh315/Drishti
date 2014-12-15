package com.gkbhitech.drishti.account;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.model.CustomerAccountStatement;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCustomerAccountStatement;

public class CustomerAccountStatementActivity extends Activity implements OnTouchListener{
	
	private static final String tag = "CustomerAccountStatementActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtCustomerName;
	private TextView txtStmtFromTo;
	private TextView txtOpeningBalance;
	
	private TextView txtHeaderPostingDate;
	private TextView txtHeaderRefNo;
	private TextView txtHeaderDocType;
	private TextView txtHeaderAmount;
	private TextView txtHeaderBalance;
	
	private TableLayout tblStatementOfAccount;
	private TableLayout tblStatementOfAccountHeader;
	private TableLayout tblRowNo;
    private HorizontalScrollView hsvStatementOfAccountTableHeader;
    private HorizontalScrollView hsvStatementOfAccountTable;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;
	
	//.............. variable used for request ..................
	private WebServiceObjectClient webServiceObjectClient;
	
	private MethodResponseCustomerAccountStatement methodResponseCustomerAccountStatement;
	
	private TextView txtRowNo,txtPostingDate,txtRefNo,txtDocType,txtAmount,txtBalance;
	
	private float mx, my;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_customer_account_statement);
		
		mApp =  (DrishtiApplication) getApplication();
		webServiceObjectClient = mApp.getWebserviceObjectClient();
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtCustomerName = (TextView) findViewById(R.id.txt_actual_customer_name);
		txtStmtFromTo = (TextView) findViewById(R.id.txt_statement_from_to);
		txtOpeningBalance = (TextView) findViewById(R.id.txt_actual_opening_balance);
		tblRowNo = (TableLayout) findViewById(R.id.tbl_row_no);
		tblStatementOfAccountHeader = (TableLayout) findViewById(R.id.tbl_statement_of_account_table_header);
		tblStatementOfAccount = (TableLayout)findViewById(R.id.tbl_statement_of_account_table);
		hsvStatementOfAccountTableHeader = (HorizontalScrollView)findViewById(R.id.hsv_statement_of_account_table_header);
		hsvStatementOfAccountTable = (HorizontalScrollView)findViewById(R.id.hsv_statement_of_account_table);
		txtHeaderPostingDate = (TextView) findViewById(R.id.txt_posting_date);
		txtHeaderRefNo = (TextView) findViewById(R.id.txt_ref_no);
		txtHeaderDocType = (TextView) findViewById(R.id.txt_doc_type);
		txtHeaderAmount = (TextView) findViewById(R.id.txt_amount);
		txtHeaderBalance = (TextView) findViewById(R.id.txt_running_balance);
		
		txtHeaderPostingDate.setPadding(5,5,5,5);
		txtHeaderRefNo.setPadding(5,5,5,5);
		txtHeaderDocType.setPadding(5,5,5,5);
		txtHeaderAmount.setPadding(5,5,5,5);
		txtHeaderBalance.setPadding(5,5,5,5);
		
		methodResponseCustomerAccountStatement = mApp.getMethodResponseCustomerAccountStatement();
		
		CustomerAccountStatement[] customerAccountStatements = methodResponseCustomerAccountStatement.getDataArray();
		if(customerAccountStatements != null){
			txtCustomerName.setText(" "+customerAccountStatements[0].getCustomerName());
			txtStmtFromTo.setText("Statement from "+convertLongToStringDate(methodResponseCustomerAccountStatement.getFromDate())+" to "+convertLongToStringDate(methodResponseCustomerAccountStatement.getToDate()));
			txtOpeningBalance.setText(" "+customerAccountStatements[0].getOpeningBalance());
			fillTable(customerAccountStatements);
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
		
		hsvStatementOfAccountTableHeader.setOnTouchListener(this);
		hsvStatementOfAccountTable.setOnTouchListener(this);
	}
	
	void fillTable(final CustomerAccountStatement[] customerAccountStatements) {
		
		TableRow rowNo;
		TableRow row;
		
		for (int current = 0; current < customerAccountStatements.length; current++) {
			
			final CustomerAccountStatement customerAccountStatement = customerAccountStatements[current];
			
			final int position = current;
			row = new TableRow(getApplicationContext());
			rowNo = new TableRow(getApplicationContext());
			
			txtRowNo = new TextView(getApplicationContext());
			txtRowNo.setText((current+1)+"");
			
			txtPostingDate = new TextView(getApplicationContext());
			txtRefNo = new TextView(getApplicationContext());
			txtDocType = new TextView(getApplicationContext());
			txtAmount = new TextView(getApplicationContext());
			txtBalance = new TextView(getApplicationContext());
			
			txtRowNo.setSingleLine();
			txtPostingDate.setSingleLine();
			txtRefNo.setSingleLine();
			txtDocType.setSingleLine();
			txtAmount.setSingleLine();
			txtBalance.setSingleLine();
			
			txtPostingDate.setText(customerAccountStatement.getPostingDate());
			txtRefNo.setText(customerAccountStatement.getItemRefNo());
			txtDocType.setText(customerAccountStatement.getDocType());
			txtAmount.setText(customerAccountStatement.getAmount().toString());
			txtBalance.setText(customerAccountStatement.getRunningAmount().toString());
			
			txtRowNo.setTextColor(Color.WHITE);
			txtRowNo.setTypeface(null, 1);
			txtPostingDate.setTypeface(null, 1);
			txtRefNo.setTypeface(null, 1);
			txtDocType.setTypeface(null, 1);
			txtAmount.setTypeface(null, 1);
			txtBalance.setTypeface(null, 1);

			txtRowNo.setTextSize(12);
			txtPostingDate.setTextSize(12);
			txtRefNo.setTextSize(12);
			txtDocType.setTextSize(12);
			txtAmount.setTextSize(12);
			txtBalance.setTextSize(12);
			
			txtRowNo.setPadding(5, 5, 5, 5);
			txtPostingDate.setPadding(5, 5, 5, 5);
			txtRefNo.setPadding(5, 5, 5, 5);
			txtDocType.setPadding(5, 5, 5, 5);
			txtAmount.setPadding(5, 5, 5, 5);
			txtBalance.setPadding(5, 5, 5, 5);
			
			txtRowNo.setGravity(Gravity.CENTER);
			txtPostingDate.setGravity(Gravity.CENTER);
			txtDocType.setGravity(Gravity.CENTER);
			txtRefNo.setGravity(Gravity.CENTER);
			txtAmount.setGravity(Gravity.CENTER);
			txtBalance.setGravity(Gravity.CENTER);
			
			txtPostingDate.setTextColor(Color.BLACK);
			txtRefNo.setTextColor(Color.BLACK);
			txtDocType.setTextColor(Color.BLACK);
			txtAmount.setTextColor(Color.BLACK);
			txtBalance.setTextColor(Color.BLUE);
			
			rowNo.addView(txtRowNo);
			
			row.addView(txtPostingDate);
			row.addView(txtRefNo);
			row.addView(txtDocType);
			row.addView(txtAmount);
			row.addView(txtBalance);
			
			row.setBackgroundColor(Color.WHITE);
			
			txtRowNo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for(int i=1; i<=customerAccountStatements.length; i++){
						
						if(Constant.log) Log.i(tag, "selcted position : "+position);
						
						if(i == (position+1)){
							tblStatementOfAccount.getChildAt(i-1).setBackgroundColor(R.color.lightgrey);
						}else{
							tblStatementOfAccount.getChildAt(i-1).setBackgroundColor(Color.WHITE);
						}
					}
				}
			});
			
			tblStatementOfAccount.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//if(Constant.log) Log.i(tag, rowNo.toString());
			tblRowNo.addView(rowNo, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		
		ViewTreeObserver vtoForRowNo = txtRowNo.getViewTreeObserver();
		vtoForRowNo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				int widthOfRowNo = txtRowNo.getWidth();
            	tblStatementOfAccountHeader.setPadding(widthOfRowNo, 0,0,0);
            	ViewTreeObserver obs = txtRowNo.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
			}
		});
		
		ViewTreeObserver vtoForPostingDate = txtPostingDate.getViewTreeObserver();
		vtoForPostingDate.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				ViewTreeObserver vtoForHeaderPostingDate = txtHeaderPostingDate.getViewTreeObserver();
				vtoForHeaderPostingDate.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfPostingDate = txtPostingDate.getWidth();
						int widthOfHeaderPostingDate = txtHeaderPostingDate.getWidth();
						if(widthOfHeaderPostingDate > widthOfPostingDate){
							txtPostingDate.setWidth(widthOfHeaderPostingDate);
						}else{
							txtHeaderPostingDate.setWidth(widthOfPostingDate);
						}
						ViewTreeObserver obs = txtHeaderPostingDate.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtPostingDate.getViewTreeObserver();
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
						// TODO Auto-generated method stub
						int widthOfRefNO = txtRefNo.getWidth();
						int widthOfHeaderRefNo = txtHeaderRefNo.getWidth();
						if(widthOfHeaderRefNo > widthOfRefNO){
							txtRefNo.setWidth(widthOfHeaderRefNo);
						}else{
							txtHeaderRefNo.setWidth(widthOfRefNO);
						}
						ViewTreeObserver obs = txtHeaderRefNo.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
				
            	ViewTreeObserver obs = txtRefNo.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
		
		ViewTreeObserver vtoForDocType = txtDocType.getViewTreeObserver();
		vtoForDocType.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
            	ViewTreeObserver vtoForHeaderDocType = txtHeaderDocType.getViewTreeObserver();
				vtoForHeaderDocType.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfDocType = txtDocType.getWidth();
						int widthOfHeaderDocType = txtHeaderDocType.getWidth();
						if(widthOfHeaderDocType > widthOfDocType){
							txtDocType.setWidth(widthOfHeaderDocType);
						}else{
							txtHeaderDocType.setWidth(widthOfDocType);
						}
						ViewTreeObserver obs = txtHeaderDocType.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
            	
            	ViewTreeObserver obs = txtDocType.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
		
		ViewTreeObserver vtoForAmount = txtAmount.getViewTreeObserver();
		vtoForAmount.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
            	ViewTreeObserver vtoForHeaderAmount = txtHeaderAmount.getViewTreeObserver();
				vtoForHeaderAmount.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfAmount = txtAmount.getWidth();
						int widthOfHeaderAmount = txtHeaderAmount.getWidth();
						if(widthOfHeaderAmount > widthOfAmount){
							txtAmount.setWidth(widthOfHeaderAmount);
						}else{
							txtHeaderAmount.setWidth(widthOfAmount);
						}
						ViewTreeObserver obs = txtHeaderAmount.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
            	
            	ViewTreeObserver obs = txtAmount.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
		ViewTreeObserver vtoForBalance = txtBalance.getViewTreeObserver();
		vtoForBalance.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	
            	ViewTreeObserver vtoForHeaderBalance = txtHeaderBalance.getViewTreeObserver();
				vtoForHeaderBalance.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						
						int widthOfBalance = txtBalance.getWidth();
						int widthOfHeaderBalance = txtHeaderBalance.getWidth();
						if(widthOfHeaderBalance > widthOfBalance){
							txtBalance.setWidth(widthOfHeaderBalance);
						}else{
							txtHeaderBalance.setWidth(widthOfBalance);
						}
						ViewTreeObserver obs = txtHeaderBalance.getViewTreeObserver();
		                obs.removeGlobalOnLayoutListener(this);
					}
				});
            	
            	ViewTreeObserver obs = txtBalance.getViewTreeObserver();
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
                hsvStatementOfAccountTableHeader.scrollBy((int) (mx - curX), 0);
                hsvStatementOfAccountTable.scrollBy((int) (mx - curX),0);
                mx = curX;
                my = curY;
                break;
        }

        return true;
	}
	
	private String convertLongToStringDate(Long date){
		String stringDate = date.toString();
		return stringDate.substring(6, 8)+"."+stringDate.substring(4, 6)+"."+stringDate.substring(0,4);
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
