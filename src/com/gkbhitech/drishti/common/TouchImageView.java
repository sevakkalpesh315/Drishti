/*
 * TouchImageView.java
 * By: Michael Ortiz
 * Updated By: Patrick Lackemacher
 * -------------------
 * Extends Android ImageView to include pinch zooming and panning.
 */

package com.gkbhitech.drishti.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class TouchImageView extends ImageView implements OnGestureListener,OnTouchListener,OnScaleGestureListener
{

	private final static String tag = "TouchImageView";
	
    Matrix matrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF last = new PointF();
    PointF start = new PointF();
    float minScale = 1f;
    float maxScale = 3f;
    float[] m;
    
    float redundantXSpace, redundantYSpace;
    
    float width, height;
    static final int CLICK = 3;
    float saveScale = 1f;
    float right, bottom, origWidth, origHeight, bmWidth, bmHeight;
    
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    
    private Boolean isTouchDetach = true;
    
    Context context;

    public TouchImageView(Context context) {
        super(context);
        sharedConstructing(context);
    }
    
    public TouchImageView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	sharedConstructing(context);
    }
    
    private void sharedConstructing(Context context) {
    	super.setClickable(true);
        this.context = context;
        //mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mScaleDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(this);
        matrix.setTranslate(1f, 1f);
        m = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(this);
        /*setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	
            	
            	mScaleDetector.onTouchEvent(event);
            	mGestureDetector.onTouchEvent(event);

            	matrix.getValues(m);
            	
            	float x = m[Matrix.MTRANS_X];
            	float y = m[Matrix.MTRANS_Y];
            	PointF curr = new PointF(event.getX(), event.getY());
            	
            	switch (event.getAction()) {
	            	case MotionEvent.ACTION_DOWN:
	                    last.set(event.getX(), event.getY());
	                    start.set(last);
	                    mode = DRAG;
	                    break;
	            	case MotionEvent.ACTION_MOVE:
	            		if (mode == DRAG) {
	            			//if(m[Matrix.MTRANS_X] > 0 && m[Matrix.MTRANS_Y] > 0){
		            			float deltaX = curr.x - last.x;
		            			float deltaY = curr.y - last.y;
		            			float scaleWidth = Math.round(origWidth * saveScale);
		            			float scaleHeight = Math.round(origHeight * saveScale);
		            			
		            			//Log.i(tag, "x : "+m[Matrix.MTRANS_X]+" y : "+m[Matrix.MTRANS_Y]);
		            			
		            			//float deltaX = event.getX()-start.x;
		            			//float deltaY = event.getY()-start.y;
	            				if (scaleWidth <= width) {
	            					
	            					if(deltaX < 0){
			            				if(m[Matrix.MTRANS_X] <= 0){
			            					deltaX = 0;
			            				}
			            			}else{
			            				if((m[Matrix.MTRANS_X]+scaleWidth) >= getWidth()){
			            					deltaX = 0;
			            				}
			            			}
	            					if(Constant.log)Log.i(tag, "scaledwith : 1.......");
		            				//deltaX = 0;
		            				//if (y + deltaY > 0)
			            			//	deltaY = -y;
		            				//else if (y + deltaY < -bottom)
			            			//	deltaY = -(y + bottom); 
	            					//matrix.postTranslate(event.getX(), event.getY());
	            					//deltaX = event.getX()-start.x;
	            				} 
	            				if (scaleHeight <= height) {
	            					
	            					if(deltaY < 0){
			            				if(m[Matrix.MTRANS_Y] < 0){
			            					deltaY = 0;
			            				}
			            			}else{
			            				if((m[Matrix.MTRANS_Y]+scaleHeight) >= getHeight()){
			            					deltaY = 0;
			            				}
			            			}
	            					if(Constant.log)Log.i(tag, "scaledwith : 2.......");
		            				//deltaY = 0;
		            				//if (x + deltaX > 0)
			            			//	deltaX = -x;
			            			//else if (x + deltaX < -right)
			            			//	deltaX = -(x + right);
	            					//deltaY = event.getY()-start.y;
	            				} 
	            				if(scaleWidth > width){
	            					if(Constant.log)Log.i(tag, "scaledwith : 3......");
		            				if (x + deltaX > 0)
			            				deltaX = -x;
			            			else if (x + deltaX < -right)
			            				deltaX = -(x + right);
	            				}
	            				if(scaleHeight > height){
		            				if (y + deltaY > 0)
			            				deltaY = -y;
			            			else if (y + deltaY < -bottom)
			            				deltaY = -(y + bottom);
	            				}
		            			
	            				if(Constant.log)Log.i(tag, "deltax : "+deltaX+" deltay : "+deltaY);
	            				
	                        	matrix.postTranslate(deltaX, deltaY);
	                        	last.set(curr.x, curr.y);
	                        	start.set(event.getX(), event.getY());
		                   // }
	            		}
	            		break;
	            		
	            	case MotionEvent.ACTION_UP:
	            		mode = NONE;
	            		int xDiff = (int) Math.abs(curr.x - start.x);
	                    int yDiff = (int) Math.abs(curr.y - start.y);
	                    if (xDiff < CLICK && yDiff < CLICK)
	                        performClick();
	            		break;
	            		
	            	case MotionEvent.ACTION_POINTER_UP:
	            		mode = NONE;
	            		break;
            	}
                setImageMatrix(matrix);
                invalidate();
                return true; // indicate event was handled
            }

        });*/
    }

    @Override
    public void setImageBitmap(Bitmap bm) { 
        super.setImageBitmap(bm);
        if(bm != null) {
        	bmWidth = bm.getWidth();
        	bmHeight = bm.getHeight();
        }
    }
    
    public void setMaxZoom(float x)
    {
    	maxScale = x;
    }
    
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    	@Override
    	public boolean onScaleBegin(ScaleGestureDetector detector) {
    		mode = ZOOM;
    		return true;
    	}
    	
		@Override
	    public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = (float)Math.min(Math.max(.95f, detector.getScaleFactor()), 1.05);
		 	float origScale = saveScale;
	        saveScale *= mScaleFactor;
	        if (saveScale > maxScale) {
	        	saveScale = maxScale;
	        	mScaleFactor = maxScale / origScale;
	        } else if (saveScale < minScale) {
	        	saveScale = minScale;
	        	mScaleFactor = minScale / origScale;
	        }
	        
	        //Log.i(tag, "mScaleFactor : "+mScaleFactor);
	        
        	right = width * saveScale - width - (2 * redundantXSpace * saveScale);
            bottom = height * saveScale - height - (2 * redundantYSpace * saveScale);
            
            //Log.i(tag, "right : "+right);
            //Log.i(tag, "bottom : "+bottom);
            //Log.i(tag, "currrent : "+(origWidth * saveScale));
            //Log.i(tag, "currrent : "+(origWidth * saveScale));
            
        	if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
        		matrix.postScale(mScaleFactor, mScaleFactor, width / 2, height / 2);
            	if (mScaleFactor < 1) {
            		matrix.getValues(m);
            		float x = m[Matrix.MTRANS_X];
                	float y = m[Matrix.MTRANS_Y];
                	if (mScaleFactor < 1) {
        	        	if (Math.round(origWidth * saveScale) < width) {
        	        		if (y < -bottom){
            	        		//matrix.postTranslate(0, -(y + bottom));
        	        		}
        	        		else if (y > 0){
            	        		//matrix.postTranslate(0, -y);
        	        		}
        	        	} else {
	                		if (x < -right) {
	        	        		//matrix.postTranslate(-(x + right), 0);
	                		}
	                		else if (x > 0) {
	        	        		//matrix.postTranslate(-x, 0);
	                		}
        	        	}
                	}
            	}
        	} else {
            	matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
            	matrix.getValues(m);
            	float x = m[Matrix.MTRANS_X];
            	float y = m[Matrix.MTRANS_Y];
            	if (mScaleFactor < 1) {
    	        	if (x < -right) {
    	        		//matrix.postTranslate(-(x + right), 0);
    	        	}
    	        	else if (x > 0) {
    	        		//matrix.postTranslate(-x, 0);
    	        	}
    	        	if (y < -bottom){
    	        		//matrix.postTranslate(0, -(y + bottom));
    	        	}
    	        	else if (y > 0){
    	        		//matrix.postTranslate(0, -y);
    	        	}
            	}
        	}
	        return true;
	        
	    }
	}
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	Log.i(tag, "dispatch touch event .............");
    	
    	if(!isTouchDetach){
    		isTouchDetach = true;
    		return false;
    	}
    	
    	return super.dispatchTouchEvent(event);
    	//return false;
    }
    
    
    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
    {
    	//if(Constant.log)Log.i(tag, "onMeasure called.................");
    	
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        
        //Fit to screen.
        float scale;
        float scaleX =  (float)width / (float)bmWidth;
        float scaleY = (float)height / (float)bmHeight;
        scale = Math.min(scaleX, scaleY);
        //scale = 1f;
        matrix.setScale(scaleX, scaleY);
        //matrix.preScale(scale, scale);
        setImageMatrix(matrix);
        saveScale = 1f;
        
        //----------------------------------- Center the image -------------------------------------------
         
        redundantYSpace = (float)height - (scaleY * (float)bmHeight);
        redundantXSpace = (float)width - (scaleX * (float)bmWidth);
        redundantYSpace /= (float)2;
        redundantXSpace /= (float)2;
        
        //matrix.postTranslate(redundantXSpace, redundantYSpace);
        
        //------------------------- calculate original image width and height ----------------------------
        
        origWidth = width - 2 * redundantXSpace;
        origHeight = height - 2 * redundantYSpace;
        
        //------------------------- calculate right and bottom of image ----------------------------------
        
        right = width * saveScale - width - (2 * redundantXSpace * scaleX);
        bottom = height * saveScale - height - (2 * redundantYSpace * scaleY);
        
        //right = width * saveScale- (2 * redundantXSpace * saveScale);
        //bottom = height * saveScale- (2 * redundantXSpace * saveScale);
        
        //right = 320;
        //bottom = 480;
        
        //setImageMatrix(matrix);
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		
		if(Constant.log) Log.i(tag, "first touch down ...........");
		
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
		/*if(velocityX < 0){
			if(velocityX < -200){
				isFling = true;
				return true;
			}
		}else{
			if(velocityX > 200){
				isFling = true;
				return true;
			}
		}
		isFling = false;*/
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
		
		if(Constant.log) Log.i(tag, "onSingleTapUp ...........");
		isTouchDetach = false;
		
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		Log.i(tag, "Touch called .........");
		
		mScaleDetector.onTouchEvent(event);
		mGestureDetector.onTouchEvent(event);
		
		matrix.getValues(m);
		
		float x = m[Matrix.MTRANS_X];
    	float y = m[Matrix.MTRANS_Y];
    	PointF curr = new PointF(event.getX(), event.getY());
    	
    	switch (event.getAction()) {
        	case MotionEvent.ACTION_DOWN:
                last.set(event.getX(), event.getY());
                start.set(last);
                mode = DRAG;
                break;
        	case MotionEvent.ACTION_MOVE:
        		if (mode == DRAG) {
        			float deltaX = curr.x - last.x;
        			float deltaY = curr.y - last.y;
        			float scaleWidth = Math.round(origWidth * saveScale);
        			float scaleHeight = Math.round(origHeight * saveScale);
    				if (scaleWidth <= width) {
    					
    					if(deltaX < 0){
            				if(m[Matrix.MTRANS_X] <= 0){
            					deltaX = 0;
            				}
            			}else{
            				if((m[Matrix.MTRANS_X]+scaleWidth) >= getWidth()){
            					deltaX = 0;
            				}
            			}
    					if(Constant.log)Log.i(tag, "scaledwith : 1.......");
    				} 
    				if (scaleHeight <= height) {
    					
    					if(deltaY < 0){
            				if(m[Matrix.MTRANS_Y] < 0){
            					deltaY = 0;
            				}
            			}else{
            				if((m[Matrix.MTRANS_Y]+scaleHeight) >= getHeight()){
            					deltaY = 0;
            				}
            			}
    					if(Constant.log)Log.i(tag, "scaledwith : 2.......");
    				} 
    				if(scaleWidth > width){
    					if(Constant.log)Log.i(tag, "scaledwith : 3......");
        				if (x + deltaX > 0)
            				deltaX = -x;
            			else if (x + deltaX < -right)
            				deltaX = -(x + right);
    				}
    				if(scaleHeight > height){
        				if (y + deltaY > 0)
            				deltaY = -y;
            			else if (y + deltaY < -bottom)
            				deltaY = -(y + bottom);
    				}
        			
    				if(Constant.log)Log.i(tag, "deltax : "+deltaX+" deltay : "+deltaY);
    				
                	matrix.postTranslate(deltaX, deltaY);
                	last.set(curr.x, curr.y);
                	start.set(event.getX(), event.getY());
        		}
        		break;
        		
        	case MotionEvent.ACTION_UP:
        		mode = NONE;
        		int xDiff = (int) Math.abs(curr.x - start.x);
                int yDiff = (int) Math.abs(curr.y - start.y);
                if (xDiff < CLICK && yDiff < CLICK)
                    performClick();
        		break;
        		
        	case MotionEvent.ACTION_POINTER_UP:
        		mode = NONE;
        		break;
    	}
		
		setImageMatrix(matrix);
        invalidate();
        return true;
	}
	

	@Override
	public boolean onScale(ScaleGestureDetector detector) {

		float mScaleFactor = (float)Math.min(Math.max(.95f, detector.getScaleFactor()), 1.05);
	 	float origScale = saveScale;
        saveScale *= mScaleFactor;
        if (saveScale > maxScale) {
        	saveScale = maxScale;
        	mScaleFactor = maxScale / origScale;
        } else if (saveScale < minScale) {
        	saveScale = minScale;
        	mScaleFactor = minScale / origScale;
        }
        
        if(Constant.log)Log.i(tag, "mScaleFactor : "+mScaleFactor);
        right = width * saveScale - width - (2 * redundantXSpace * saveScale);
        bottom = height * saveScale - height - (2 * redundantYSpace * saveScale);
        
    	if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
    		matrix.postScale(mScaleFactor, mScaleFactor, width / 2, height / 2);
    		//if (mScaleFactor < 1) {
    		//	matrix.getValues(m);
    		//}
    	} else {
        	matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
        	//matrix.getValues(m);
    	}
    	//setImageMatrix(matrix);
        return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		mode = ZOOM;
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}
}