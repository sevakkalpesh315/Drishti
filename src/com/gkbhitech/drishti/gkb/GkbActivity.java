package com.gkbhitech.drishti.gkb;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.FtpFileDownloader;

public class GkbActivity extends Activity implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
	OnVideoSizeChangedListener{

	private static final String tag = "GkbActivity";
	
	//.............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private TextView txtCatalog;
	//private TextView txtVideos;
	private TextView txtThicknessCalculator;
	private TextView txtComputeQuote;
	private TextView txtNewProd;
	
	//private Button Department;
	
	private MediaPlayer mMediaPlayer;
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(Constant.log) Log.i(tag, tag+" start");
		
		registerReceiver();
		
		setContentView(R.layout.activity_gkb_home);
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		txtCatalog = (TextView) findViewById(R.id.txt_catalog);
		//txtVideos = (TextView) findViewById(R.id.txt_video);
		txtComputeQuote = (TextView) findViewById(R.id.txt_compute_quote);
		txtThicknessCalculator = (TextView) findViewById(R.id.txt_thickness_calculator);
		txtNewProd = (TextView) findViewById(R.id.txt_new_products);
		//Department = (Button) findViewById(R.id.btDEPARTMENT);
		
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
			}
		});
		txtCatalog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), GKBCatalogActivity.class);
				startActivity(i);
			}
		});
		/*txtVideos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//File sdcard = Environment.getExternalStorageDirectory();
                //String path = sdcard.getAbsolutePath()+"/.drishti/why_this.3gp";
				//FileDownloaderAsyc fileDownloaderAsyc = new FileDownloaderAsyc();
				//fileDownloaderAsyc.execute();
				
				//Downloader.DownloadFile("http://dl.dropbox.com/u/51533277/why_this.3gp", "why_this.3gp");
				
                //Intent i = new Intent(getApplicationContext(), MediaPlayerDemo_Video.class);
                //Intent i = new Intent(getApplicationContext(), VideoViewDemo.class);
                Intent i = new Intent(getApplicationContext(), VideoActivity.class);
				startActivity(i);
			}
		});*/
		txtThicknessCalculator.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), CenterThicknessActivity.class);
				startActivity(i);
			}
		});
		txtComputeQuote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ComputeQuoteFormActivity.class);
				startActivity(i);
			}
		});
txtNewProd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
				startActivity(i);
			}
		});
		/*Department.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ComputeQuoteFormActivity.class);
				startActivity(i);
			}
		});*/
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}
	
/*public class FileDownloaderAsyc extends AsyncTask<Void, Void, Boolean>{

		
		public FileDownloaderAsyc() {
			// TODO Auto-generated constructor stub
			pd = new ProgressDialog(GkbActivity.this);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setMessage("Files are loading...");
			pd.show();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			Downloader.DownloadFile("http://dl.dropbox.com/u/51533277/why_this.3gp", "why_this.3gp");
			//http://dl.dropbox.com/u/51533277/Bedardi_Raja.mp3
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.cancel();
		}
	}*/
}
