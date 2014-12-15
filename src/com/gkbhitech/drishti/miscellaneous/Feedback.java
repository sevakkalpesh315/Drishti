package com.gkbhitech.drishti.miscellaneous;

import com.gkbhitech.drishti.LoginActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.database.DataBaseHelper;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends Activity {
	
	private static final String tag = "FeedbackActivity";
	SQLiteDatabase finalSQLiteDatabase;
	
	private Button Submit;
	
	//.............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	
	//.............. variable used to access Application...........
	private DrishtiApplication mApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		if(Constant.log) Log.i(tag, tag+" start");		
		setContentView(R.layout.activity_feedback_form);
		
		finalSQLiteDatabase = openOrCreateDatabase("feedback.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tbl_feed(id INTEGER PRIMARY KEY AUTOINCREMENT,feedback TEXT);";
		
		finalSQLiteDatabase.execSQL(CREATE_TABLE);
		
		final TextView Feedback = (TextView) findViewById(R.id.etFeedback);
		
		Submit = (Button) findViewById(R.id.btnSubmit);	
		Submit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (Feedback.getText().toString().trim().length() > 0) {
						finalSQLiteDatabase
								.execSQL("insert into tbl_feed(feedback) values ('"+ Feedback.getText() + "' );");

						Toast.makeText(getApplicationContext(),
								"Feedback successfully submitted!",Toast.LENGTH_SHORT).show();
					} else {
						Feedback.setText("");
						Toast.makeText(getApplicationContext(),
								"Please fill the field!",Toast.LENGTH_SHORT).show();
					}

				} catch (SQLiteException se) {
					Toast.makeText(getApplicationContext(),
							"Please fill the field!", Toast.LENGTH_SHORT).show();
				}
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