package com.gkbhitech.drishti.gkb;

import java.util.List;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.model.Video;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;

public class VideoActivity extends Activity {

	private static final String tag = "VideoActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private LinearLayout llVideoList;

	// .............. variable used to access Application...........
	private DrishtiApplication mApp;

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;

	private List<Video> videos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_home);

		llVideoList = (LinearLayout) findViewById(R.id.ll_video_list);

		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();

		videos = dataBaseAdapter.getVideoList();

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,160); // Width , height
		params.setMargins(10, 10, 10, 10);

		if(videos != null && videos.size() > 0){
			for (int i = 0; i < videos.size(); i++) { Video video = videos.get(i);
	
				/*
				 * android:id="@+id/txt_activate_pplp"
				 * android:layout_width="match_parent"
				 * android:layout_height="match_parent" android:layout_margin="10dp"
				 * android:text="@string/activate_pplp"
				 * android:textColor="@color/black" />
				 */
	
				final Button txtVideoTitle = new Button(getApplicationContext());
				txtVideoTitle.setLayoutParams(params);
				txtVideoTitle.setBackgroundResource(R.drawable.video4);
				txtVideoTitle.setTypeface(null, Typeface.BOLD);
				txtVideoTitle.setText(video.getVideo_description());
				txtVideoTitle.setTextColor(Color.BLACK);
	
				final String[] fileName = video.getUrl().split("/");
				txtVideoTitle.setOnClickListener(new OnClickListener() {
	
					@Override
					public void onClick(View v) {
	
						// TODO Auto-generated method stub\
						Intent i = new Intent(VideoActivity.this,
								VideoViewDemo.class);
						i.putExtra("videoFileName", fileName[fileName.length - 1]);
						startActivity(i);
					}
				});
	
				llVideoList.addView(txtVideoTitle);
				if (i != (videos.size() - 1)) {
					View view = new View(getApplicationContext());
					/*
					 * view.setLayoutParams(new
					 * LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1));
					 * view.setBackgroundColor(Color.LTGRAY);
					 */
					llVideoList.addView(view);
				}
			}
		}
	}
}
