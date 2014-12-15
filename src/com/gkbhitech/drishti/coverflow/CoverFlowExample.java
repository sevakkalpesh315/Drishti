package com.gkbhitech.drishti.coverflow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.TouchImageView;



public class CoverFlowExample extends Activity{
    /** Called when the activity is first created. */
	
	Context context;
	private ImageView imvBack;
	private ImageView imvHome;
	private static final String tag = "CoverFlowExample";
	CoverFlow coverFlow;
	private int position = 0; 
	
	ImageAdapter coverImageAdapter;
	
	Bitmap originalImage, reflectionImage,bitmapWithReflection;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
	    if(Constant.log) Log.i(tag, tag+" start");
	    
	    registerReceiver();
	    
	    setContentView(R.layout.activity_coverflow);
	    
	    context = getApplicationContext();
	    
	    /*imvBack = (ImageView) findViewById(R.id.imv_back);
	    imvHome = (ImageView) findViewById(R.id.imv_exit);*/
	    
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null) {
	    	position = extras.getInt("position");
	    	if(Constant.log) Log.i(tag, "selected position : "+position);
	    }

	    /*imvBack.setOnClickListener(new OnClickListener() {
			
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
				Intent i = new Intent(context, HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				//clearBitmap();
			}
		});*/
	    
	    coverFlow = (CoverFlow) findViewById(R.id.coverflow);
	    
	    //coverFlow.setAdapter(new ImageAdapter(context));

	    coverImageAdapter =  new ImageAdapter(context);
	    
	    coverImageAdapter.createReflectedImages();
	    
	    coverFlow.setAdapter(coverImageAdapter);
	    
	    
	    coverFlow.setSpacing(-15);
	    coverFlow.setSelection(position);
	    
	    
	    
	    //Use this if you want to use XML layout file
	    //setContentView(R.layout.main);
	    //coverFlow =  (CoverFlow) findViewById(R.id.coverflow);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//clearBitmap();
	}
	
	/*public void clearBitmap(){
		Bitmap bitmap;
		for(ImageView imageView : coverImageAdapter.getItemArray()){
			if(imageView != null){
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
*/	
	public class VideoAdapter extends BaseAdapter{

		private Integer[] mImageIds = {
	    		/*R.drawable.kodak_singlevision,
	            R.drawable.kodak_bifocal,
	    		R.drawable.kodak_unique,
	            R.drawable.kodak_easy,
	            R.drawable.kodak_precise,
	            R.drawable.kodak_concise*/
	    		
	    		R.drawable.asahi_lite_1,
	    		R.drawable.asahi_lite_2,
	    		R.drawable.asahi_lite_3,
	    		R.drawable.asahi_lite_4,
	    		R.drawable.asahi_lite_5,
	    		R.drawable.asahi_lite_14,
	    		R.drawable.asahi_lite_15,
	    		R.drawable.asahi_lite_16,
	    		R.drawable.asahi_lite_17
	    };
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageIds.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public class ViewHolder {
			public ImageView imgView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			/*ViewHolder view;
			
			if (convertView==null){
				
				view = new ViewHolder();
				
				LayoutInflater layoutInflater = getLayoutInflater();
				convertView = layoutInflater.inflate(R.layout.adapter_gkbcatalog, null);
				
				view.imgView = (ImageView)convertView.findViewById(R.id.imv_brands_adp);
				view.imgView.setImageResource(mImageIds[position]);
				
				convertView.setTag(view);
				
			} else {
				view = (ViewHolder) convertView.getTag();
			}
			
			return convertView;*/
			return null;
		}
		
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;
	    
	    //private FileInputStream fis;
	       
	    private Integer[] mImageIds = {
	    		/*R.drawable.kodak_singlevision,
	            R.drawable.kodak_bifocal,
	    		R.drawable.kodak_unique,
	            R.drawable.kodak_easy,
	            R.drawable.kodak_precise,
	            R.drawable.kodak_concise*/
	    		
	    		R.drawable.asahi_lite_1,
	    		R.drawable.asahi_lite_2,
	    		R.drawable.asahi_lite_3,
	    		R.drawable.asahi_lite_4,
	    		R.drawable.asahi_lite_5,
	    		R.drawable.asahi_lite_14,
	    		R.drawable.asahi_lite_15,
	    		R.drawable.asahi_lite_16,
	    		R.drawable.asahi_lite_17
	    };
	    
	   private ImageView[] mImages;
	   private TouchImageView[] mZoomImages;
	    
	    public ImageAdapter(Context c) {
	    	mContext = c;
	    	mImages = new ImageView[mImageIds.length];
	    	mZoomImages = new TouchImageView[mImageIds.length];
	    }
	    
	    public ImageView[] getItemArray(){
	    	return mImages;
	    }
	    
		public boolean createReflectedImages() {
		        //The gap we want between the reflection and the original image
			try{
		        final int reflectionGap = 4;
		       
		        int index = 0;
		        for (int imageId : mImageIds) {
			    	/*originalImage = BitmapFactory.decodeResource(getResources(), 
			    			imageId);
			        int width = originalImage.getWidth();
			        int height = originalImage.getHeight();
			        
		   
			        //This will not scale but will flip on the Y axis
			        Matrix matrix = new Matrix();
			        matrix.preScale(1, -1);
			        
			        //Create a Bitmap with the flip matrix applied to it.
			        //We only want the bottom half of the image
			        reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);
			        
			       
			            
			        //Create a new bitmap with same width but taller to fit reflection
			        bitmapWithReflection = Bitmap.createBitmap(width 
			          , (height + height/2), Config.ARGB_8888);
			        
			       //Create a new Canvas with the bitmap that's big enough for
			       //the image plus gap plus reflection
			       Canvas canvas = new Canvas(bitmapWithReflection);
			       //Draw in the original image
			       canvas.drawBitmap(originalImage, 0, 0, null);
			       //Draw in the gap
			       Paint deafaultPaint = new Paint();
			       canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
			       //Draw in the reflection
			       canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
			       
			       //Create a shader that is a linear gradient that covers the reflection
			       Paint paint = new Paint(); 
			       LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, 
			         bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
			         TileMode.CLAMP); 
			       //Set the paint to use this shader (linear gradient)
			       
			       paint.setShader(shader); 
			       
			       //Set the Transfer mode to be porter duff and destination in
			       paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
			       //Draw a rectangle using the paint with our linear gradient
			       canvas.drawRect(0, height, width,bitmapWithReflection.getHeight() + reflectionGap, paint); 
			       */
		        	
		        	
			       ImageView imageView = new ImageView(mContext);
			       imageView.setImageResource(imageId);
			     //  ZoomControls zoomControls = new ZoomControls(mContext);
			       
			       //imageView.setImageBitmap(bitmapWithReflection);
			       
			       Display display = getWindowManager().getDefaultDisplay(); 
			       
			       imageView.setLayoutParams(new CoverFlow.LayoutParams(display.getWidth(),display.getHeight()));
			       imageView.setScaleType(ScaleType.MATRIX);
			       mImages[index++] = imageView;
			       
			       
			       /*TouchImageView touchImageView = new TouchImageView(mContext);
			       mBitmap = BitmapFactory.decodeResource(getResources(), imageId);
			       touchImageView.setImageBitmap(mBitmap);
			       touchImageView.setScaleType(ScaleType.MATRIX);
			       mZoomImages[index++] = touchImageView;*/
		        	
		        	/*Display display = getWindowManager().getDefaultDisplay(); 
			       
		        	//mZoomControl = new DynamicZoomControl();
		        	
		        	//mPinchZoomListener = new PinchZoomListener(getApplicationContext());
		            // mPinchZoomListener.setZoomControl(mZoomControl);
		        	
			       ImageZoomView imageZoomView = new ImageZoomView(mContext);
			       mBitmap = BitmapFactory.decodeResource(getResources(), imageId);
			       //imageZoomView.setZoomState(mZoomControl.getZoomState());
			       imageZoomView.setImage(mBitmap);
			       
			       //mZoomControl.setAspectQuotient(imageZoomView.getAspectQuotient());
			       
			       //imageZoomView.setOnTouchListener(mPinchZoomListener);
			       
			       imageZoomView.setLayoutParams(new LayoutParams(display.getWidth(),display.getHeight()));
			       mZoomImages[index++] = imageZoomView;*/
			       
			       //originalImage.recycle();
			       //reflectionImage.recycle();
			       //bitmapWithReflection.recycle();
			       
		        }
		    	return true;
			}catch (OutOfMemoryError e) {
				// TODO: handle exception
				e.printStackTrace();
				System.gc();
			}
			return false;
		}

	    public int getCount() {
	        return mImageIds.length;
	    	//return mZoomImages.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public class ViewHolder {
			public ImageView imgView;
		}
	    
	    public View getView(int position, View convertView, ViewGroup parent) {

	    	//Use this code if you want to load from resources
	        //ImageView i = new ImageView(mContext);
	        //i.setImageResource(mImageIds[position]);
	        //i.setLayoutParams(new CoverFlow.LayoutParams(130, 130));
	        //i.setScaleType(ImageView.ScaleType.MATRIX);	        
	        //return i;
	    	
	    	/*ImageView imgView = mImages[position];
	    	imgView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					
					if(Constant.log) Log.i("tag......", "...........");
					
					return true;
				}
			});*/
	    	
	    	/*ViewHolder view;
			
			if (convertView==null){
				
				view = new ViewHolder();
				
				//LayoutInflater layoutInflater = getLayoutInflater();
				//convertView = layoutInflater.inflate(R.layout.adapter_gkbcatalog, null);
				
				//view.imgView = (ImageView)convertView.findViewById(R.id.imv_brands_adp);
				view.imgView = new ImageView(mContext); 
				
				convertView.setTag(view);
				
			} else {
				view = (ViewHolder) convertView.getTag();
			}
			
			Display display = getWindowManager().getDefaultDisplay(); 
			view.imgView.setImageResource(mImageIds[position]);
			
			view.imgView.setLayoutParams(new CoverFlow.LayoutParams(display.getWidth(),display.getHeight()));
			return convertView;*/
	    	
	    	
	    	return mImages[position];
	    	//resetZoomState();
	    	//return mZoomImages[position];
	    }
		 /** Returns the size (0.0f to 1.0f) of the views 
	     * depending on the 'offset' to the center. */ 
	     //public float getScale(boolean focused, int offset) { 
	       /* Formula: 1 / (2 ^ offset) */ 
	     //    return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
	     //} 

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
