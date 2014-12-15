package com.gkbhitech.drishti.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {

	private Context context;
	private AlertDialog.Builder adBCart;
	private AlertDialog adCart;
	
	public MyDialog(Context context){
		this.context = context;
		
		adBCart = new AlertDialog.Builder(context);
	    adBCart.setNegativeButton("Close", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
	}
	
	public void displayDialog(String title, String message){
	    
		adBCart.setTitle(title);
		adBCart.setMessage(message);
		adCart = adBCart.create();
		adCart.show();
	}
}
