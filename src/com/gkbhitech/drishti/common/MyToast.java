package com.gkbhitech.drishti.common;

import android.content.Context;
import android.widget.Toast;


public class MyToast {
	
	public static void show(Context context,int responseCode, String message){
		
		switch (responseCode) {
		case Constant.RESULT_INVALID_AUTH_TOKEN:
			Toast.makeText(context, Constant.MESSAGE_INVALID_AUTH_TOKEN, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_AUTHENTICATION_FAILURE:
			Toast.makeText(context, Constant.MESSAGE_AUTHENTICATION_FAILURE, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_RECORD_NOT_FOUND:
			Toast.makeText(context, Constant.MESSAGE_RECORD_NOT_FOUND, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_SERVER_DATABASE_ERROR:
			Toast.makeText(context, Constant.MESSAGE_SERVER_DATABASE_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_SAP_ERROR:
			Toast.makeText(context, Constant.MESSAGE_SAP_ERROR, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_PASSWORD_NOT_MATCH:
			Toast.makeText(context, Constant.MESSAGE_PASSWORD_NOT_MATCH, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_NETWORK_UNAVAILABLE:
			Toast.makeText(context, Constant.MESSAGE_NETWORK_UNAVAILABLE, Toast.LENGTH_SHORT).show();
			break;
		case Constant.RESULT_NULL_RESPONSE:
			Toast.makeText(context, Constant.MESSAGE_NULL_RESPONSE, Toast.LENGTH_LONG).show();
			break;
		case Constant.RESULT_SD_CARD_NOT_EXIST:
			Toast.makeText(context, Constant.MESSAGE_SD_CARD_NOT_EXIST, Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
