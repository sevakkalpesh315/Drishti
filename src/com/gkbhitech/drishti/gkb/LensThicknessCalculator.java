package com.gkbhitech.drishti.gkb;

import com.gkbhitech.drishti.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LensThicknessCalculator extends Activity{
	
	private static final String tag = "LensThicknessCalculator";
	
	// .............. variable used in UI ................
	private Spinner spnLensType, spnReSph, spnReCyl, spnReAxis, spnReAdd, spnLeSph, spnLeCyl, spnLeAxis, spnLeAdd, diameter;
	
	private String[] arrayOfLensTypeValues = {"Single Vision", "Progressive Vision"};
	
	private Double[] arrayOfReSphValues = {-6.0,-5.75,-5.5,-5.25,-5.0,-4.75,-4.5,-4.25,-4.0,-3.75,-3.5,-3.25,
			-3.0,-2.75,-2.5,-2.25,-2.0,-1.75,-1.5,-1.25,-1.0,-0.75,-0.5,-0.25,0.0,0.25,0.5,0.75,1.0,1.25,1.5,
			1.75,2.0,2.25,2.5,2.75,3.0};
	
	private Double[] arrayOfLeSphValues = {-6.0,-5.75,-5.5,-5.25,-5.0,-4.75,-4.5,-4.25,-4.0,-3.75,-3.5,-3.25,
			-3.0,-2.75,-2.5,-2.25,-2.0,-1.75,-1.5,-1.25,-1.0,-0.75,-0.5,-0.25,0.0,0.25,0.5,0.75,1.0,1.25,1.5,
			1.75,2.0,2.25,2.5,2.75,3.0};
	
	private Double[] arrayOfReCylValues = {0.0,-0.25,-0.5,-0.75,-1.0,-1.25,-1.5,-1.75,-2.0};
	
	private Double[] arrayOfLeCylValues = {0.0,-0.25,-0.5,-0.75,-1.0,-1.25,-1.5,-1.75,-2.0};
	
	private Integer[] arrayOfReAxisValues = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,
			27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,
			59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
			91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,
			117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,
			141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,
			165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180};
	
	private Integer[] arrayOfLeAxisValues = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,
			27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,
			59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
			91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,
			117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,
			141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,
			165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180};
	
	private Integer[] arrayOfDiameterTypeValues = {40,45,50,55,60,65,70};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//registerReceiver();
		
		setContentView(R.layout.activity_thickness_calculator);
		
		spnLensType = (Spinner) findViewById(R.id.Spinner_lens_type);
		spnReSph = (Spinner) findViewById(R.id.spn_re_sph);
		spnReCyl = (Spinner) findViewById(R.id.spn_re_cyl);
		spnReAxis = (Spinner) findViewById(R.id.spn_re_axis);
		spnReAdd = (Spinner) findViewById(R.id.spn_re_add);
		
		spnLeSph = (Spinner) findViewById(R.id.spn_le_sph);
		spnLeCyl = (Spinner) findViewById(R.id.spn_le_cyl);
		spnLeAxis = (Spinner) findViewById(R.id.spn_le_axis);
		spnLeAdd = (Spinner) findViewById(R.id.spn_le_add);
		
		diameter = (Spinner) findViewById(R.id.Spinner_diameter);
				
		ArrayAdapter<String> arrayAdapterLensTypeValues = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLensTypeValues);
		spnLensType.setAdapter(arrayAdapterLensTypeValues);
		
		ArrayAdapter<Double> arrayAdapterReSphValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfReSphValues);
		spnReSph.setAdapter(arrayAdapterReSphValues);
		
		ArrayAdapter<Double> arrayAdapterLeSphValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLeSphValues);
		spnLeSph.setAdapter(arrayAdapterLeSphValues);
		
		ArrayAdapter<Double> arrayAdapterReCylValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfReCylValues);
		spnReCyl.setAdapter(arrayAdapterReCylValues);
		
		ArrayAdapter<Double> arrayAdapterLeCylValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLeCylValues);
		spnLeCyl.setAdapter(arrayAdapterLeCylValues);
		
		ArrayAdapter<Integer> arrayAdapterReAxisValues = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfReAxisValues);
		spnReAxis.setAdapter(arrayAdapterReAxisValues);
		
		ArrayAdapter<Integer> arrayAdapterLeAxisValues = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLeAxisValues);
		spnLeAxis.setAdapter(arrayAdapterLeAxisValues);
		
		ArrayAdapter<Integer> arrayAdapterDiameterTypeValues = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfDiameterTypeValues);
		diameter.setAdapter(arrayAdapterDiameterTypeValues);
	}

}
