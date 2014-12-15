package com.gkbhitech.drishti.gkb;

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

import java.io.File;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyDialog;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.Toast;


public class MediaPlayerDemo_Video extends Activity implements
        OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener, OnVideoSizeChangedListener, OnTouchListener, MediaPlayerControl,SurfaceHolder.Callback {

    private static final String TAG = "MediaPlayerDemo";
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mMediaPlayer;
    private MediaController mcontroller;
    private Handler handler = new Handler();

    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    //private Bundle extras;
    private static final String MEDIA = "media";
    //private static final int LOCAL_AUDIO = 1;
    //private static final int STREAM_AUDIO = 2;
    //private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;

    /**
     * 
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.mediaplayer_2);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //extras = getIntent().getExtras();
        mPreview.setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * the MediaController will hide after 3 seconds - tap the screen to
         * make it appear again
         */
        mcontroller.show();
        return false;
    }

    private void playVideo(Integer Media) {
        doCleanUp();
        try {

            switch (Media) {
                case LOCAL_VIDEO:
                    /*
                     * TODO: Set the path variable to a local media file path.
                     */
                	
                	//File sdcard = Environment.getExternalStorageDirectory();
                    //path = sdcard.getAbsolutePath()+"/.drishti/why_this.3gp";
                	path = Constant.DRISHTI_PATH_ON_SD_CARD+"/why_this.3gp";
                    
                    //path = "";
                    if (path == "") {
                        // Tell the user to provide a media file URL.
                        Toast
                                .makeText(
                                        MediaPlayerDemo_Video.this,
                                        "Please edit MediaPlayerDemo_Video Activity, "
                                                + "and set the path variable to your media file path."
                                                + " Your media file must be stored on sdcard.",
                                        Toast.LENGTH_LONG).show();

                    }
                    break;
                case STREAM_VIDEO:
                    /*
                     * TODO: Set path variable to progressive streamable mp4 or
                     * 3gpp format URL. Http protocol should be used.
                     * Mediaplayer can only play "progressive streamable
                     * contents" which basically means: 1. the movie atom has to
                     * precede all the media data atoms. 2. The clip has to be
                     * reasonably interleaved.
                     * 
                     */
                    //path = "http://dl.dropbox.com/u/51533277/CAT.mp4";
                    path = "rtsp://v8.cache1.c.youtube.com/CjgLENy73wIaLwn5nbjrs3LpfRMYESARFEIJbXYtZ29vZ2xlSARSB3JlbGF0ZWRg6o-x1YSSrahPDA==/0/0/0/video.3gp";
                    if (path == "") {
                        // Tell the user to provide a media file URL.
                        Toast
                                .makeText(
                                        MediaPlayerDemo_Video.this,
                                        "Please edit MediaPlayerDemo_Video Activity,"
                                                + " and set the path variable to your media file URL.",
                                        Toast.LENGTH_LONG).show();

                    }

                    break;


            }

            // Create a new media player and set the listeners
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepare();
            
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mcontroller = new MediaController(this);

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
            Log.i(TAG, "......."+e.getMessage()+"..........");
            MyDialog myDialog = new MyDialog(getApplicationContext());
            myDialog.displayDialog("Error Message", e.getMessage());
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.i(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
        mcontroller.setMediaPlayer(this);
        mcontroller.setAnchorView(findViewById(R.id.surface));
        handler.post(new Runnable() {

            public void run() {
                mcontroller.setEnabled(true);
                mcontroller.show();
            }
        });

    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
        //mMediaPlayer.pause();
    }


    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        playVideo(4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //releaseMediaPlayer();
        //doCleanUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //releaseMediaPlayer();
        //doCleanUp();
    }
    

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    private void startVideoPlayback() {
        Log.i(TAG, "startVideoPlayback............");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return mMediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return mMediaPlayer.isPlaying(); 
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mMediaPlayer.pause();
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		mMediaPlayer.seekTo(pos);

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		mMediaPlayer.start();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}

