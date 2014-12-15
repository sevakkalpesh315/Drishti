package com.gkbhitech.drishti.gkb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;

public class GKBCatalogActivity extends Activity {

	private static final String tag = "GKBCatalogActivity";
	private ImageView imvBack;
	private ImageView imvHome;
	private GridView gvBrands;
	//private int[] imageId={R.drawable.kodak,R.drawable.signate,R.drawable.kplus,R.drawable.matrix};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_gkbcatalog);

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		gvBrands=(GridView)findViewById(R.id.gv_brands);
		gvBrands.setAdapter(new MyImageAdapter(this));
		
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
				clearBitmap();
			}
		});
		gvBrands.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

				goToClass(position);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		clearBitmap();
	}
	
	public void clearBitmap(){
		Bitmap bitmap;
		for(int i = 0; i < gvBrands.getChildCount(); i++){
			ViewGroup viewGroup = (ViewGroup) gvBrands.getChildAt(i);
			if(viewGroup != null){
				ImageView imageView = (ImageView) viewGroup.getChildAt(0); 
				imageView.buildDrawingCache();
				bitmap = imageView.getDrawingCache();
				if(bitmap != null){
					if(Constant.log) Log.i(tag, "image recycled");
					bitmap.recycle();
					if(Constant.log) Log.i(tag, "recyled :"+bitmap.isRecycled());
				}
			}
		}
	}
	
	static class MyImageAdapter extends BaseAdapter{
		
		private Activity context;
		private int[] imageId={R.drawable.kodak,R.drawable.asahilogo};
		
		public MyImageAdapter(Activity context){
			this.context=context;	
		}

		@Override
		public int getCount() {
			return imageId.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		private static class ViewHolder {
			public ImageView imgView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder view;
			
			if (convertView==null){
				
				view = new ViewHolder();
				
				LayoutInflater layoutInflater = context.getLayoutInflater();
				convertView = layoutInflater.inflate(R.layout.adapter_gkbcatalog, null);
				
				view.imgView = (ImageView)convertView.findViewById(R.id.imv_brands_adp);
				
				convertView.setTag(view);
				
			} else {
				view = (ViewHolder) convertView.getTag();
			}
			
			view.imgView.setImageResource(imageId[position]);
			
			return convertView;
		}
		
	}
	void goToClass(int position){
		switch (position) {
		case 0:
			startActivity(new Intent(getApplicationContext(),KodakCatalogActivity.class));
			break;
		case 1:
			startActivity(new Intent(getApplicationContext(),AsahiLiteCatalogActivity.class));
			break;

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