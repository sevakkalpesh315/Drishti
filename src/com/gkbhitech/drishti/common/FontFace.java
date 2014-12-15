package com.gkbhitech.drishti.common;

import android.app.Activity;
import android.graphics.Typeface;

public class FontFace extends Activity{
	
	public static Typeface getFontFaceRupee(Activity context){
		Typeface fontFace = Typeface.createFromAsset(context.getAssets(), "font/Rupee.ttf");
		return fontFace;
	}
	
}
