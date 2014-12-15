package com.gkbhitech.drishti.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.gkbhitech.drishti.common.CallbackInterface;
import com.gkbhitech.drishti.database.DataBaseAdapter;

public class FatchLensAsynTask extends AsyncTask<Void, Void, Void>{
	
	private Context context;
	private CallbackInterface callbackInterface;
	private ProgressDialog progressDialog;
	private DataBaseAdapter dataBaseAdapter;
	
	public FatchLensAsynTask(Context context, CallbackInterface callbackInterface) {
		this.context = context;
		this.callbackInterface = callbackInterface;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Loding Lenses ...");
		progressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {

		try {
			//Log.i(tag, "Brfore");
			
			//lenses = dataBaseAdapter.getLensesForOrderStockLens();
			//Log.i(tag, "After");
			//Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// Log.i(tag, "error");
			e.printStackTrace();
			progressDialog.cancel();
			//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		/*if (lenses != null) {
			Log.i(tag, "lens length :" + lenses.size());
			for (Lens lens : lenses) {
				lensDescription.add(lens.getDescription());
			}
			arrayAdapterLensDescription = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_dropdown_item,
					lensDescription);
			lvLens.setAdapter(arrayAdapterLensDescription);
			
			Lens selectedLens = mApp.getLens();
			if(selectedLens != null){
				for(int i=0; i < lenses.size(); i++){
					Lens lens = lenses.get(i);
					if(lens.getDescription().equals(selectedLens.getDescription())){
						lvLens.setSelection(i);
					}
				}
			}
		}

		lvLens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				for(Lens lens : lenses){
					if(lens.getDescription().equals(arrayAdapterLensDescription.getItem(position))){
						mApp.setLens(lens);
					}
				}
				onBackPressed();
			}
		});*/
		
		//if(lenses != null && lenses.size() > 0){
		//	if(callbackInterface != null){
		//		callbackInterface.callback();
		//	}
		//}
		
		progressDialog.dismiss();
	}
}
