package com.gkbhitech.drishti.gkb;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.common.CallbackInterface;
import com.gkbhitech.drishti.common.Constant;

public class LensThicknessCalculationFormActivity extends Activity {

	private static final String tag = "LensThicknessCalculationFormActivity";

	// .............. variable used in UI ................
	private ImageView imvBack;
	private ImageView imvHome;
	private Spinner spnSph;
	private Spinner spnCyl;
	private Spinner spnAxis;
	private Spinner spnPrism11;
	private Spinner spnPrism12;
	private Spinner spnPrism21;
	private Spinner spnPrism22;
	private Spinner spnDesiredAxis;
	private Button btnCalculateLensPower;
	private Spinner spnLensType;
	private Spinner spnEye;
	private Spinner spnLensMaterial;
	private TextView txtLensPower;
	private EditText edtLensWidth;
	private EditText edtLensHeight;
	private EditText edtDBL;
	private EditText edtMpnoPD;
	private EditText edtOCHeight;
	private EditText edtET;
	private Button btnCalculate;
	private TextView txtFramePD;
	private TextView txtHDecentration;
	private TextView txtVDecentration;
	private TextView txtL;
	private TextView txtT;
	private TextView txtMaxThickness;
	private TextView txtMinThickness;

	private Integer[] arrayOfAxisValues = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,
			27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,
			59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
			91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,
			117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,
			141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,
			165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180};

	private Double[] arrayOfPrismValues = {0.00,0.25,0.50,0.75,1.00,1.25,1.50,1.75,2.00,2.25,2.50,2.75,3.00,3.25,3.50,3.75,
			4.00,4.25,4.50,4.75,5.00,5.25,5.50,5.75,6.00,6.25,6.50,6.75,7.00,7.25,7.50,7.75,8.00,8.25,8.50,8.75,
			9.00,9.25,9.50,9.75,10.00};
	
	private String[] arrayOfPrism12Values = {"Up","Down"};
	private String[] arrayOfPrism22Values = {"In","Out"};
	
	private String[] arrayOfLensTypeValues = {"Type A","Type B"};
	private String[] arrayOfEyeValues = {"OD(R)","OS(L)"};
	private String[] arrayOfLensMaterialKey = {"CR39","Crown Glass","Trivex","Sola Spectralite",
			"AO Alphalite","1.56 Mid - Index","Polycarbonate","1.60 High - Index","1.67 High - Index",
			"1.70 High - Index","1.74 High - Index","1.80 High - Index","1.90 High - Index"};
	private Double[] arrayOfLensMaterialValues = {1.498,1.523,1.532,1.537,1.582,1.56,1.587,1.6,1.67,1.7,1.74,1.8,1.9}; 
	
	private Double sph;
	private Double cyl;
	private int axis;
	private int desiredAxis;
	private Double lensPower;
	private Double framePD;
	private Double hDecentration;
	private Double vDecentration;
	private int eye;
	private Double prism11;
	private int prism12;
	private Double prism21;
	private int prism22;
	private double lensMaterial;
	private double et;
	private int lensType;
	
	private DecimalFormat df = new DecimalFormat("##.##");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		registerReceiver();
		
		setContentView(R.layout.activity_lens_thickness_calculation_form);
		
		imvBack = (ImageView) findViewById(R.id.imv_back);
		imvHome = (ImageView) findViewById(R.id.imv_exit);
		spnSph = (Spinner) findViewById(R.id.spn_sph);
		spnCyl = (Spinner) findViewById(R.id.spn_cyl);
		spnAxis = (Spinner) findViewById(R.id.spn_axis);
		spnPrism11 = (Spinner) findViewById(R.id.spn_prism11);
		spnPrism12 = (Spinner) findViewById(R.id.spn_prism12);
		spnPrism21 = (Spinner) findViewById(R.id.spn_prism21);
		spnPrism22 = (Spinner) findViewById(R.id.spn_prism22);
		spnDesiredAxis = (Spinner) findViewById(R.id.spn_desired_axis);
		btnCalculateLensPower = (Button) findViewById(R.id.btn_lens_power);
		txtLensPower = (TextView) findViewById(R.id.txt_lens_power);
		spnLensType = (Spinner) findViewById(R.id.spn_lens_type);
		spnEye = (Spinner) findViewById(R.id.spn_eye);
		spnLensMaterial = (Spinner) findViewById(R.id.spn_lens_material);
		edtLensWidth = (EditText) findViewById(R.id.edt_lens_width);
		edtLensHeight= (EditText) findViewById(R.id.edt_lens_height);
		edtDBL = (EditText) findViewById(R.id.edt_dbl);
		edtMpnoPD = (EditText) findViewById(R.id.edt_mono_pd);
		edtOCHeight = (EditText) findViewById(R.id.edt_oc_height);
		edtET = (EditText) findViewById(R.id.edt_et);
		btnCalculate = (Button) findViewById(R.id.btn_calculate);
		txtFramePD = (TextView) findViewById(R.id.txt_actual_frame_pd);
		txtHDecentration = (TextView) findViewById(R.id.txt_actual_h_decentration);
		txtVDecentration = (TextView) findViewById(R.id.txt_actual_v_decentration);
		txtL = (TextView) findViewById(R.id.txt_actual_l);
		txtT = (TextView) findViewById(R.id.txt_actual_t);
		txtMaxThickness = (TextView) findViewById(R.id.txt_actual_max_thickenss);
		txtMinThickness = (TextView) findViewById(R.id.txt_actual_min_thickenss);
		
		ArrayAdapter<Double> arrayAdapterSphAndCylValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, Constant.sph);
		spnSph.setAdapter(arrayAdapterSphAndCylValues);
		spnCyl.setAdapter(arrayAdapterSphAndCylValues);
		
		ArrayAdapter<Integer> arrayAdapterAxisValues = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfAxisValues);
		spnAxis.setAdapter(arrayAdapterAxisValues);
		spnDesiredAxis.setAdapter(arrayAdapterAxisValues);
		
		ArrayAdapter<Double> arrayAdapterPrismValues = new ArrayAdapter<Double>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfPrismValues);
		spnPrism11.setAdapter(arrayAdapterPrismValues);
		spnPrism21.setAdapter(arrayAdapterPrismValues);
		
		ArrayAdapter<String> arrayAdapterLensTypeValues = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLensTypeValues);
		spnLensType.setAdapter(arrayAdapterLensTypeValues);
		
		ArrayAdapter<String> arrayAdapterEyeValues = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfEyeValues);
		spnEye.setAdapter(arrayAdapterEyeValues);
		
		ArrayAdapter<String> arrayAdapterLensMaterialKey = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfLensMaterialKey);
		spnLensMaterial.setAdapter(arrayAdapterLensMaterialKey);
		
		ArrayAdapter<String> arrayAdapterPrism12Values = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfPrism12Values);
		spnPrism12.setAdapter(arrayAdapterPrism12Values);
		ArrayAdapter<String> arrayAdapterPrism22Values = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item_1, arrayOfPrism22Values);
		spnPrism22.setAdapter(arrayAdapterPrism22Values);
		
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
		spnSph.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				sph = Constant.sph[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnCyl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				cyl = Constant.sph[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnAxis.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				axis = arrayOfAxisValues[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spnDesiredAxis.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				desiredAxis = arrayOfAxisValues[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		btnCalculateLensPower.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					lensPower = sph+(cyl*Math.pow((Math.sin(Math.PI*(desiredAxis-axis)/180)), 2));
					txtLensPower.setText(df.format(lensPower).toString());
			}
		});
		
		spnEye.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				eye = position+1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnPrism11.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				prism11 = arrayOfPrismValues[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnPrism12.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				prism12 = position+1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnPrism21.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				prism21 = arrayOfPrismValues[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnPrism22.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				prism22 = position+1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnLensMaterial.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				lensMaterial = arrayOfLensMaterialValues[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spnLensType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				lensType = position+1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnCalculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double width;
				double height;
				double dbl;
				double monoPD;
				double OCHeight;
				double et;
				try{
					width = Double.parseDouble(edtLensWidth.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter width value", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					height = Double.parseDouble(edtLensHeight.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter height value", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					dbl = Double.parseDouble(edtDBL.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter DBL value", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					monoPD = Double.parseDouble(edtMpnoPD.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter Mono PD value", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					OCHeight = Double.parseDouble(edtOCHeight.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter OC Height value", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					et = Double.parseDouble(edtET.getText().toString());
				}catch (NumberFormatException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Please enter ET value", Toast.LENGTH_SHORT).show();
					return;
				}
				
				double framePD = width + dbl;
				txtFramePD.setText(" "+framePD+" mm");
				hDecentration = (width+dbl)/2-monoPD;
				txtHDecentration.setText(" "+hDecentration+" mm");
				vDecentration = OCHeight-(height/2);
				txtVDecentration.setText(" "+vDecentration+" mm");
				
				double hDecByPrism = 0.0;
				double lensPowerAt180 = sph+(cyl*Math.pow((Math.sin(Math.PI*(180-axis)/180)), 2));
				
				if(prism21 != 0.0){
					if(prism22 == 1){
						double temp = prism21/lensPowerAt180*10;
						hDecByPrism = temp;
					}else{
						double temp = -1*prism21/lensPowerAt180*10;
						hDecByPrism = temp;
					}
				}
				
				double vDecByPrism = 0.0;
				double lensPowerAt90 = sph+(cyl*Math.pow((Math.sin(Math.PI*(90-axis)/180)), 2));
				
				if(prism11 != 0.0){
					//if(prism12 == 1){
						double temp = prism11/lensPowerAt90*10;
						vDecByPrism = temp;
					//}else{
					//	double temp = -1*prism11/lensPowerAt90*10;
					//	vDecByPrism = temp;
					//}
				}
				
				CallbackInterface callbackInterface = new CallbackInterface() {
					
					@Override
					public void callback(Double l, Double t, String maxThicknessAt,
							String minThicknessAt) {
						// TODO Auto-generated method stub
						txtL.setText(df.format(l).toString()+" mm");
						txtT.setText(df.format(t).toString()+" mm");
						txtMaxThickness.setText(maxThicknessAt);
						txtMinThickness.setText(minThicknessAt);
					}

					@Override
					public void callback() {
						// TODO Auto-generated method stub
						
					}
					
				};
				
				
				LensThicknessMasterAsynTask lensThicknessMasterAsynTask = new LensThicknessMasterAsynTask(LensThicknessCalculationFormActivity.this,
						sph, cyl, axis, desiredAxis, lensPower, lensMaterial, et, width, height, eye, hDecentration, vDecentration, hDecByPrism,
						vDecByPrism, lensType, callbackInterface);
				lensThicknessMasterAsynTask.execute();
				
				
				
				/*LensThicknessMaster lensThicknessMaster = new LensThicknessMaster(sph, cyl, axis, lensPower,lensMaterial, et, width, height, eye, HDecentration, VDecentration, hDecByPrism, vDecByPrism);
				lensThicknessMaster.setAxis();
				
				
				if(lensType == 1){
					lensThicknessMaster.addItemToX(width/2, null);
					lensThicknessMaster.addItemToY(new Double(0), null);
					lensThicknessMaster.calculateTheta();
					lensThicknessMaster.calculateY();
					lensThicknessMaster.calculateX();
					lensThicknessMaster.calculateAD();
					lensThicknessMaster.calculateNewX();
					lensThicknessMaster.calculateNewY();
					lensThicknessMaster.calculateNewTheta();
					lensThicknessMaster.calculateNewAxis();
					lensThicknessMaster.calculateLength();
					lensThicknessMaster.calculateLensPower();
					lensThicknessMaster.calculateThickness();
					
					List<Double> length = lensThicknessMaster.getLengthOfA();
					double l = length.get(desiredAxis-1);
					txtL.setText(df.format(l).toString()+" mm");
					
					List<Double> thickness = lensThicknessMaster.getThicknessOfA();
					double maxThickness = Collections.max(thickness);
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At ");
					
					double minThickness = et;
					double t = maxThickness;
					int axisAt = lensThicknessMaster.getNewAxis().get(length.indexOf(Collections.max(length)));
					if(lensPower < 0){
						t = Math.abs(Math.pow(l, 2)*lensPower/(lensMaterial-1)/2000)+et;
						minThickness = Collections.min(thickness);
						
						//double temp = thickness.indexOf(maxThickness);
						//Log.i(tag, "index of "+maxThickness+" is : "+temp);
						
						
						txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At "+axisAt);
						
						int axisAtMin = lensThicknessMaster.getNewAxis().get(length.indexOf(Collections.min(length)));
						txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAtMin);
					}else{
						txtMaxThickness.setText(df.format(maxThickness).toString()+" mm at the Optical Center");
						
						//int axisAt = lensThicknessDataForTypeA.getNewAxis().get(length.indexOf(Collections.max(length)));
						txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAt);
					}
					txtT.setText(df.format(t).toString()+" mm");
					
					
				}else{
					//lensThicknessDataForTypeA.calculateBTheta();
					lensThicknessMaster.calculateBX();
					lensThicknessMaster.calculateBY();
					lensThicknessMaster.calculateBD();
					lensThicknessMaster.calculateBNewX();
					lensThicknessMaster.calculateBNewY();
					lensThicknessMaster.calculateBNewTheta();
					lensThicknessMaster.calculateBNewAxis();
					lensThicknessMaster.calculateBLength();
					lensThicknessMaster.calculateBLensPower();
					lensThicknessMaster.calculateBThickness();
					
					List<Double> length = lensThicknessMaster.getLengthOfB();
					
					double l = length.get(desiredAxis-1);
					txtL.setText(df.format(l).toString()+" mm");
					
					List<Double> thickness = lensThicknessMaster.getThicknessOfB();
					double maxThickness = Collections.max(thickness);
					Log.i(tag, "maxThickness = "+maxThickness);
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm");
					
					double minThickness = et;
					double t = maxThickness;
					int axisAt = lensThicknessMaster.getBNewAxis().get(length.indexOf(Collections.max(length)));
					if(lensPower < 0){
						t = Math.abs(Math.pow(l, 2)*lensPower/(lensMaterial-1)/2000)+et;
						minThickness = Collections.min(thickness);
						
						txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At "+axisAt);
						
						int axisAtMin = lensThicknessMaster.getBNewAxis().get(length.indexOf(Collections.min(length)));
						txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAtMin);
					}else{
						txtMaxThickness.setText(df.format(maxThickness).toString()+" mm at the Optical Center");
						
						//int axisAt = lensThicknessDataForTypeA.getBNewAxis().get(length.indexOf(Collections.max(length)));
						txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAt);
					}
					txtT.setText(df.format(t).toString()+" mm");
					
				}*/
			}
		});
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
}



//=IF(C23>0,IF($C$32=1,INDEX($AH$3:$AH$363,MATCH(MAX($AI$3:$AI$363),$AI$3:$AI$363,0)),INDEX($BH$3:$BH$363,MATCH(MAX($BI$3:$BI$363),$BI$3:$BI$363,0))),IF($C$32=1,INDEX($AH$3:$AH$363,MATCH(MIN($AI$3:$AI$363),$AI$3:$AI$363,0)),INDEX($BH$3:$BH$363,MATCH(MIN($BI$3:$BI$363),$BI$3:$BI$363,0))))















