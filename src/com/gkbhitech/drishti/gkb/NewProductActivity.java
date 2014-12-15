package com.gkbhitech.drishti.gkb;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;

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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

public class NewProductActivity extends Activity implements AnimationListener{

private static final String tag = "NewProductActivity";
	
	//private Bitmap[] bitmapLenses; 
	
	private ImageView imvBack;
	private ImageView imvHome;
	private GridView gvCategory;
	private HorizontalScrollView hsvDescription;
	
	private ImageView imvDescImage;
	private TextView imvDescText;
	
	private Animation zoomIn,zoomOut;	
	private boolean isScrollViewShown = false;
	
	String[] categoryDescrip = 
	{ 
			"MR8 Poster", "Ashahi Lite V Reality Poster","KDK_PrecisePB-Poster Bkside", "Kodak NXT Poster", "Photo Speed Poster", 
			"Signet Navigator poster", "Trivex Poster"
	};
	
	
	//int[] imvDescImageId={R.drawable.kodak_singlevision,R.drawable.kodak_bifocal,R.drawable.kodak_unique,R.drawable.kodak_easy,R.drawable.kodak_precise,R.drawable.kodak_concise};

	private Integer[] imvDescImageId = {
    		
			R.drawable.new_prod__mr_poster,
    		R.drawable.new_prod_ashahi_lite_v_reality_poster,
    		R.drawable.new_prod_kdk_precise_pb_poster,
    		R.drawable.new_prod_kodak_nxt_poster,
    		R.drawable.new_prod_photo_speed_poster,
    		R.drawable.new_prod_signet_navigator_poster,
    		R.drawable.new_prod_trivex_poster
    		
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_new_product);
		
		initAnimation();

		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		gvCategory = (GridView) findViewById(R.id.gv_new_products);
		MyAdapter adapter=new MyAdapter(getApplicationContext());
		gvCategory.setAdapter(adapter);
		//bitmapLenses=new Bitmap[adapter.getCount()];
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
		gvCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

				//Intent i = new Intent(getApplicationContext(), CoverFlowExample.class);
				//if(Constant.log) Log.i(tag, "selected position : "+position);
				//i.putExtra("position", position);
				//startActivity(i);
				
				Intent i = new Intent(getApplicationContext(), CatlogNewProductActivity.class);
				if(Constant.log) Log.i(tag, "selected position : "+position);
				//i.putExtra("position", position);
				i.putExtra("extra_image", position);
				startActivity(i);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		clearBitmap();
	}

	
	public void clearBitmap(){
		Bitmap bitmap;
		for(int i = 0; i < gvCategory.getChildCount(); i++){
			ViewGroup viewGroup = (ViewGroup) gvCategory.getChildAt(i);
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
	
	/*public void clearCallbacks(){
		GridView gridView = (GridView) findViewById(R.id.gv_kodak);
	    int count = gridView.getCount();
	    for (int i = 0; i < count; i++) {
	        ViewGroup v = (ViewGroup) gridView.getChildAt(i);
	        if(v != null){
		        ImageView imgView = (ImageView) v.getChildAt(0); 
		        if (imgView != null) {
		            if (imgView.getDrawable() != null) imgView.getDrawable().setCallback(null);
		        }
	        }
	    }
	}*/
	
/*
 * Grid View Adapter Class
 */
	public class MyAdapter extends BaseAdapter {

		private Context context;

		public MyAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return categoryDescrip.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		public class ViewHolder {
			public ImageView imgProduct;
			public TextView txtProduct;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder view;

			if (convertView == null) {
				view = new ViewHolder();
				LayoutInflater layoutInflater = getLayoutInflater();
				convertView = layoutInflater.inflate(R.layout.adapter_new_product, null);

				view.imgProduct = (ImageView) convertView.findViewById(R.id.imv_new_product_category);
				view.txtProduct = (TextView) convertView.findViewById(R.id.txt_new_product_category);
				
				convertView.setTag(view);
			} else {
				view = (ViewHolder) convertView.getTag();
			}
			
			view.imgProduct.setImageResource(imvDescImageId[position]);
			//view.txtProduct.setText(categoryDescrip[position]);
			
			return convertView;
		}
	}
/*
 * Animation Listener Functions
 */
	@Override
	public void onAnimationEnd(Animation animation) {
		if (zoomIn==animation){
		//	category.setVisibility(View.INVISIBLE);
			hsvDescription.setVisibility(View.VISIBLE);
			isScrollViewShown=true;
		}
		if (zoomOut==animation){
		//	gvCategory.setVisibility(View.VISIBLE);
			hsvDescription.setVisibility(View.INVISIBLE);

		}
	}

/*
 *  Initialization Functions for Various Various Views And Animation
 * 	
 */
	
	void initAnimation(){
		zoomIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.zoom_in);
		zoomIn.setAnimationListener(this);
		zoomOut = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.zoom_out);
		zoomOut.setAnimationListener(this);
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

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
