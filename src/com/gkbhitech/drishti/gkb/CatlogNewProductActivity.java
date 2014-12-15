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

public class CatlogNewProductActivity extends Activity {
	private static final String tag = "CatlogNewProductActivity";

	private ImageView selectedImageView2;
	private ImageView leftArrowImageView2;
	private ImageView rightArrowImageView2;
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
			setContentView(R.layout.catlog_new_product);

		getDrawablesList();
		setupUI();
	}

	private void setupUI() {

		selectedImageView2 = (ImageView) findViewById(R.id.selected_imageview2);
		leftArrowImageView2 = (ImageView) findViewById(R.id.left_arrow_imageview2);
		rightArrowImageView2 = (ImageView) findViewById(R.id.right_arrow_imageview2);
		gallery = (Gallery) findViewById(R.id.NewProductgallery);

		leftArrowImageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView2.setOnClickListener(new OnClickListener() {

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

					leftArrowImageView2.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
					rightArrowImageView2.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));

				} else if (selectedImagePosition == 0) {

					leftArrowImageView2.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));

				} else if (selectedImagePosition == drawables.size() - 1) {

					rightArrowImageView2.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
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

			rightArrowImageView2.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
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
		drawables.add(getResources().getDrawable(R.drawable.new_prod__mr_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_ashahi_lite_v_reality_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_kdk_precise_pb_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_kodak_nxt_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_photo_speed_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_signet_navigator_poster));
		drawables.add(getResources().getDrawable(R.drawable.new_prod_trivex_poster));
	}

	private void setSelectedImage(int selectedImagePosition) {

		BitmapDrawable bd = (BitmapDrawable) drawables.get(selectedImagePosition);
		Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
		selectedImageView2.setImageBitmap(b);
		selectedImageView2.setScaleType(ScaleType.FIT_XY);

	}
}