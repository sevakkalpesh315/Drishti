package com.gkbhitech.drishti.gkb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;

import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.database.DataBaseAdapter;

public class CatlogAsahiliteActivity extends Activity {
	private static final String tag = "CatlogAsahiliteActivity";

	private ImageView selectedImageView1;
	private ImageView leftArrowImageView1;
	private ImageView rightArrowImageView1;
	private Gallery gallery;

	private int selectedImagePosition = 0;
	private List<Drawable> drawables;
	private CatlogKodakAdapter galImageAdapter;

	//.............. variable used to access database .............
		private DataBaseAdapter dataBaseAdapter;
		
		//.............. variable used to access Application...........
		private DrishtiApplication mApp;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
					
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);		
			if(Constant.log) Log.i(tag, tag+" start");		
			setContentView(R.layout.catlog_asahilite);

		getDrawablesList();
		setupUI();
	}

	private void setupUI() {

		selectedImageView1 = (ImageView) findViewById(R.id.selected_imageview1);
		leftArrowImageView1 = (ImageView) findViewById(R.id.left_arrow_imageview1);
		rightArrowImageView1 = (ImageView) findViewById(R.id.right_arrow_imageview1);
		gallery = (Gallery) findViewById(R.id.Asahilitegallery);

		leftArrowImageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition < drawables.size() - 1) {
					++selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);

			}
		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				selectedImagePosition = pos;

				if (selectedImagePosition > 0 && selectedImagePosition < drawables.size() - 1) {

					leftArrowImageView1.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
					rightArrowImageView1.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));

				} else if (selectedImagePosition == 0) {

					leftArrowImageView1.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));

				} else if (selectedImagePosition == drawables.size() - 1) {

					rightArrowImageView1.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
				}

				changeBorderForSelectedImage(selectedImagePosition);
				setSelectedImage(selectedImagePosition);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		galImageAdapter = new CatlogKodakAdapter(this, drawables);

		gallery.setAdapter(galImageAdapter);

		if (drawables.size() > 0) {

			gallery.setSelection(selectedImagePosition, false);

		}

		if (drawables.size() == 1) {

			rightArrowImageView1.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
		}

	}

	private void changeBorderForSelectedImage(int selectedItemPos) {

		int count = gallery.getChildCount();

		for (int i = 0; i < count; i++) {

			ImageView imageView = (ImageView) gallery.getChildAt(i);
			imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
			imageView.setPadding(3, 3, 3, 3);

		}

		ImageView imageView = (ImageView) gallery.getSelectedView();
		imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
		imageView.setPadding(3, 3, 3, 3);
	}

	private void getDrawablesList() {

		drawables = new ArrayList<Drawable>();
		drawables.add(getResources().getDrawable(R.drawable.al_1));
		drawables.add(getResources().getDrawable(R.drawable.al_2));
		drawables.add(getResources().getDrawable(R.drawable.al_3));
		drawables.add(getResources().getDrawable(R.drawable.al_4));
		drawables.add(getResources().getDrawable(R.drawable.al_5));
		drawables.add(getResources().getDrawable(R.drawable.al_6));
		drawables.add(getResources().getDrawable(R.drawable.al_7));
		drawables.add(getResources().getDrawable(R.drawable.al_8));
		drawables.add(getResources().getDrawable(R.drawable.al_9));
		drawables.add(getResources().getDrawable(R.drawable.al_10));
		drawables.add(getResources().getDrawable(R.drawable.al_11));
		drawables.add(getResources().getDrawable(R.drawable.al_12));
		drawables.add(getResources().getDrawable(R.drawable.al_13));
		drawables.add(getResources().getDrawable(R.drawable.al_14));
		drawables.add(getResources().getDrawable(R.drawable.al_15));
	}

	private void setSelectedImage(int selectedImagePosition) {

		BitmapDrawable bd = (BitmapDrawable) drawables.get(selectedImagePosition);
		Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
		selectedImageView1.setImageBitmap(b);
		selectedImageView1.setScaleType(ScaleType.FIT_XY);

	}
}