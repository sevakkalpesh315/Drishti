/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gkbhitech.drishti.gkb;

import java.io.File;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewDemo extends Activity {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media
     * file path.
     */
    //private String path = "http://dl.dropbox.com/u/51533277/why_this.3gp";
	private String path = "rtsp://v8.cache1.c.youtube.com/CjgLENy73wIaLwn5nbjrs3LpfRMYESARFEIJbXYtZ29vZ2xlSARSB3JlbGF0ZWRg6o-x1YSSrahPDA==/0/0/0/video.3gp";
    //private String path = "http://www.pocketjourney.com/downloads/pj/video/famous.3gp";
    //private String path = "rtsp://v1.cache2.c.youtube.com/CjYLENy73wIaLQmIelRSnpkgkxMYESARFEIJbXYtZ29vZ2xlSARSBWluZGV4YOqPsdWEkq2oTww=/0/0/0/video.3gp";
    private VideoView mVideoView;
    private String tag = "VideoViewDemo";
    private MyDialog myDialog;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.videoview);
        
        if(Constant.log)Log.i(tag, "Videp view activity ......");
        
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        
        Bundle bundle = getIntent().getExtras();
        String fileName = bundle.getString("videoFileName");

        if(fileName != null){
	        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	        
		    if(isSDPresent){
		    	
		    	if(Constant.log)Log.i(tag, isSDPresent+" = isSDPresent ......");
		        
		        File dir = new File (Constant.DRISHTI_PATH_ON_SD_CARD+"/"+fileName);
		        
		        if(dir.exists()){
		        	
		        	if(Constant.log)Log.i(tag, "is file present : "+dir.exists());
		        	
			        path = Constant.DRISHTI_PATH_ON_SD_CARD+"/"+fileName;
			        
			        //File sdcard = Environment.getExternalStorageDirectory();
			        //path = sdcard.getAbsolutePath()+"/why_this.3gp";
			        try{
				        if (path == "") {
				            // Tell the user to provide a media file URL/path.
				            Toast.makeText(
				                    VideoViewDemo.this,
				                    "Please edit VideoViewDemo Activity, and set path"
				                            + " variable to your media file URL/path",
				                    Toast.LENGTH_LONG).show();
				
				        } else {
				
				            /*
				             * Alternatively,for streaming media you can use
				             * mVideoView.setVideoURI(Uri.parse(URLstring));
				             */
				        	if(Constant.log)Log.i(tag, "path : "+path);
				            mVideoView.setVideoPath(path);
				        	//Log.i(tag, "........."+Uri.parse(path).toString()+".............");
				        	//mVideoView.setVideoURI(Uri.parse(path));
				            mVideoView.setMediaController(new MediaController(this));
				            mVideoView.requestFocus();
				            mVideoView.start();
				
				        }
			        }catch (Exception e) {
						// TODO: handle exception
			        	e.printStackTrace();
			        	Log.i(tag, e.getMessage()+"...........");
			        	myDialog = new MyDialog(getApplicationContext());
			        	myDialog.displayDialog("Error Message", e.getMessage());
					}
		        }else{
		        	myDialog = new MyDialog(getApplicationContext());
		        	myDialog.displayDialog("Error Message", "Video not exist");
		        }
		    }else{
		    	myDialog = new MyDialog(this);
	        	myDialog.displayDialog("Error Message", "Please insert sd card");
		    }
        }
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    }
    
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
    							onBackPressed();
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
}
