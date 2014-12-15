package com.gkbhitech.drishti.gkb;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.gkbhitech.drishti.common.CallbackInterface;
import com.gkbhitech.drishti.common.Constant;

public class LensThicknessMasterAsynTask extends AsyncTask<Void, Void, Integer>{

	private static final String tag = "customerAsyncTask";
	private String errorMessage = "error";
	private Context context;
	private ProgressDialog progressDialog;
	private Double sph;
	private Double cyl;
	private int axis;
	private int desiredAxis;
	private double lensPower;
	//private Double framePD;
	private double hDecentration;
	private double vDecentration;
	private int eye;
	//private Double prism11;
	//private int prism12;
	//private Double prism21;
	//private int prism22;
	private double lensMaterial;
	private double ct;
	private int lensType;
	private double width;
	private double height;
	private double hDecByPrism;
	private double vDecByPrism;
	
	private double l;
	private double t;
	private String maxThicknessAt;
	private String minThicknessAt;
	
	//private double maxThickness;
	//private double minThickness;
	//private int axisAt;
	//private int axisAtMin;
	
	private DecimalFormat df = new DecimalFormat("##.##");
	
	private CallbackInterface callbackInterface;
	
	public LensThicknessMasterAsynTask(Context context,
			Double sph, Double cyl, int axis, int desiredAxis, Double lensPower, Double lensMaterial, Double ct, 
			Double width, Double height, int eye, Double hDecentration, Double vDecentration, 
			Double hDecByPrism, Double vDecByPrism, int lensType, CallbackInterface callbackInterface){
		
		this.context = context;
		this.sph = sph;
		this.cyl = cyl;
		this.axis = axis;
		this.desiredAxis = desiredAxis;
		this.lensPower = lensPower;
		this.lensMaterial = lensMaterial;
		this.ct = ct;
		this.width = width;
		this.height = height;
		this.eye = eye;
		this.hDecentration = hDecentration;
		this.vDecentration = vDecentration;
		this.hDecByPrism = hDecByPrism;
		this.vDecByPrism = vDecByPrism;
		this.lensType = lensType;
		this.callbackInterface = callbackInterface;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Loading ...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try{
			
			LensThicknessMaster lensThicknessMaster = new LensThicknessMaster(sph, cyl, axis, lensPower,lensMaterial, ct, width, height, eye, hDecentration, vDecentration, hDecByPrism, vDecByPrism);
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
				l = length.get(desiredAxis-1);
				//txtL.setText(df.format(l).toString()+" mm");
				
				List<Double> thickness = lensThicknessMaster.getThicknessOfA();
				double maxThickness = Collections.max(thickness);
				//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At ");
				
				double minThickness = ct;
				t = maxThickness;
				int axisAt = lensThicknessMaster.getNewAxis().get(length.indexOf(Collections.max(length)));
				if(lensPower < 0){
					t = Math.abs(Math.pow(l, 2)*lensPower/(lensMaterial-1)/2000)+ct;
					minThickness = Collections.min(thickness);
					
					//double temp = thickness.indexOf(maxThickness);
					//Log.i(tag, "index of "+maxThickness+" is : "+temp);
					
					
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At "+axisAt);
					maxThicknessAt = df.format(maxThickness).toString()+" mm Axis At "+axisAt;
					
					int axisAtMin = lensThicknessMaster.getNewAxis().get(length.indexOf(Collections.min(length)));
					//txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAtMin);
					minThicknessAt = df.format(minThickness).toString()+" mm Axis At "+axisAtMin;
				}else{
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm at the Optical Center");
					maxThicknessAt = df.format(maxThickness).toString()+" mm at the Optical Center";
					
					//int axisAt = lensThicknessDataForTypeA.getNewAxis().get(length.indexOf(Collections.max(length)));
					//txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAt);
					minThicknessAt = df.format(minThickness).toString()+" mm Axis At "+axisAt;
				}
				//txtT.setText(df.format(t).toString()+" mm");
				
				
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
				
				l = length.get(desiredAxis-1);
				//txtL.setText(df.format(l).toString()+" mm");
				
				List<Double> thickness = lensThicknessMaster.getThicknessOfB();
				double maxThickness = Collections.max(thickness);
				//Log.i(tag, "maxThickness = "+maxThickness);
				//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm");
				
				double minThickness = ct;
				double t = maxThickness;
				int axisAt = lensThicknessMaster.getBNewAxis().get(length.indexOf(Collections.max(length)));
				if(lensPower < 0){
					t = Math.abs(Math.pow(l, 2)*lensPower/(lensMaterial-1)/2000)+ct;
					minThickness = Collections.min(thickness);
					
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm Axis At "+axisAt);
					maxThicknessAt = df.format(maxThickness).toString()+" mm Axis At "+axisAt;
					
					int axisAtMin = lensThicknessMaster.getBNewAxis().get(length.indexOf(Collections.min(length)));
					//txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAtMin);
					minThicknessAt = df.format(minThickness).toString()+" mm Axis At "+axisAtMin;
				}else{
					//txtMaxThickness.setText(df.format(maxThickness).toString()+" mm at the Optical Center");
					maxThicknessAt = df.format(maxThickness).toString()+" mm at the Optical Center";
					
					//int axisAt = lensThicknessDataForTypeA.getBNewAxis().get(length.indexOf(Collections.max(length)));
					//txtMinThickness.setText(df.format(minThickness).toString()+" mm Axis At "+axisAt);
					minThicknessAt = df.format(minThickness).toString()+" mm Axis At "+axisAt;
				}
				//txtT.setText(df.format(t).toString()+" mm");
				
			}
			
			return Constant.RESULT_SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result == Constant.RESULT_SUCCESS){
			callbackInterface.callback(l, t, maxThicknessAt, minThicknessAt);
		}
		progressDialog.dismiss();
	}
}
