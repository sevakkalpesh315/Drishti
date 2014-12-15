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

public class PhoneBook extends Activity{
	private static final String tag = "PhoneBookActivity";
	SQLiteDatabase PhoneBookSQLiteDatabase;
	
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
		setContentView(R.layout.activity_phonebook);
		
		PhoneBookSQLiteDatabase = openOrCreateDatabase("phonebook.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tbl_phnbook(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number INTEGER);";
		
		PhoneBookSQLiteDatabase.execSQL(CREATE_TABLE);
		
		final TextView Name = (TextView) findViewById(R.id.etName);
		final TextView Number = (TextView) findViewById(R.id.etNumber);
		
		Submit = (Button) findViewById(R.id.button1);	
		Submit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (Name.getText().toString().trim().length() > 0
							&& Number.getText().toString().trim().length() > 0) {
						PhoneBookSQLiteDatabase
						.execSQL("insert into tbl_phnbook(name, number) values ('"+ Name.getText()
								+ "', '"
								+ Number.getText() + "' );");

				Toast.makeText(getApplicationContext(),
						"Record Inserted Successfully",Toast.LENGTH_SHORT).show();
			} else {
				Name.setText("");
				Number.setText("");
				Toast.makeText(getApplicationContext(),
						"Please fill all the fields!",Toast.LENGTH_SHORT).show();
			}

		} catch (SQLiteException se) {
			Toast.makeText(getApplicationContext(),
					"Please fill all the fields!", Toast.LENGTH_SHORT).show();
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