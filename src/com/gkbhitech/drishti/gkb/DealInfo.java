package com.gkbhitech.drishti.gkb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.TouchImageView;

public class DealInfo extends Activity implements OnGestureListener,OnTouchListener{

	private static final String tag = "DealInfo";
	private ViewFlipper viewFlipper;
	
	private Button btnNext;
	private Button btnPrevious;
	private ImageView imgNext;
	private ImageView imgPrevious;
	private RelativeLayout rlNavigationBar;
	private int start = 0; 
	
	private Integer[] mImageIds = {
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
	private GestureDetector mGestureDetector;
	
	private Animation inAnimationFromLeft;
	private Animation inAnimationFromRight;
	private Animation outAnimationFromLeft;
	private Animation outAnimationFromRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dealinfo);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		//btnNext = (Button) findViewById(R.id.btn_next);
		//btnPrevious = (Button) findViewById(R.id.btn_previous);
		imgNext = (ImageView) findViewById(R.id.img_next);
		imgPrevious = (ImageView) findViewById(R.id.img_previous);
		//rlNavigationBar = (RelativeLayout) findViewById(R.id.rl_navogation_bar);
		//Display display = getWindowManager().getDefaultDisplay(); 
		
		mGestureDetector = new GestureDetector(this);
		viewFlipper.setOnTouchListener(this);
		//viewSwitcher.setLayoutParams(new ViewSwitcher.LayoutParams(display.getWidth(),display.getHeight()));
		
		Bundle extras = getIntent().getExtras();
	    if(extras !=null) {
	    	start = extras.getInt("position");
	    	if(Constant.log) Log.i(tag, "selected position : "+start);
	    }
		
	    int total = mImageIds.length;
		int end = start + total;
		
		for(int i = start; i<end; i++){
			int id = i%total;
			Log.i(tag, "id :"+id);
			
			TouchImageView touchImageView = new TouchImageView(getApplicationContext());
			Bitmap snoop = BitmapFactory.decodeResource(getResources(),mImageIds[id]);
			touchImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			touchImageView.setImageBitmap(snoop);
			
			LinearLayout ll = new LinearLayout(getApplicationContext());
			ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			ll.addView(touchImageView);
			
			viewFlipper.addView(ll);
		}
		
		/*btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
				viewFlipper.showNext();
			}
		});
		btnPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);culation
				viewFlipper.showPrevious();
			}
		});*/
		
		imgNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
				viewFlipper.showNext();
				//imgNext.setVisibility(View.INVISIBLE);
				//imgPrevious.setVisibility(View.INVISIBLE);
			}
		});
		imgPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
				viewFlipper.showPrevious();
				//imgNext.setVisibility(View.INVISIBLE);
				//imgPrevious.setVisibility(View.INVISIBLE);
			}
		});
		
		imgNext.setVisibility(View.INVISIBLE);
		imgPrevious.setVisibility(View.INVISIBLE);
		
		inAnimationFromLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
		inAnimationFromRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
		outAnimationFromLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_left);
		outAnimationFromRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(Constant.log) Log.i(tag, "velocity x : "+velocityX+" velocity y : "+velocityY);
		
		/*if(velocityX < 0){
			if(velocityX < -200){
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
				viewFlipper.showNext();
			}
		}else{
			if(velocityX > 200){
				viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
				viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
				viewFlipper.showPrevious();
			}
		}*/
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		
		if(imgNext.getVisibility() == View.INVISIBLE){
			imgNext.startAnimation(inAnimationFromRight);
			imgPrevious.startAnimation(inAnimationFromLeft);
			//rlNavigationBar.setVisibility(View.INVISIBLE);
			imgNext.setVisibility(View.VISIBLE);
			imgPrevious.setVisibility(View.VISIBLE);
			
			
		}else{
			imgNext.startAnimation(outAnimationFromRight);
			imgPrevious.startAnimation(outAnimationFromLeft);
			//rlNavigationBar.setVisibility(View.INVISIBLE);
			imgNext.setVisibility(View.INVISIBLE);
			imgPrevious.setVisibility(View.INVISIBLE);
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		mGestureDetector.onTouchEvent(event);
		return true;
	}
}
