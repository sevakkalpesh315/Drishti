package com.gkbhitech.drishti.gkb;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.gkbhitech.drishti.common.Constant;

public class LensThicknessDataForTypeB {

	private static final String tag = "LensThicknessDataForTypeB";
	
	private double width;
	private double height;
	private List<Integer> axiss;
	private List<Integer> newAxis = new ArrayList<Integer>(); 
	private List<Double> x = new ArrayList<Double>();
	private List<Double> y = new ArrayList<Double>();
	private List<Double> ad = new ArrayList<Double>();
	private List<Double> newX = new ArrayList<Double>();
	private List<Double> newY = new ArrayList<Double>();
	private List<Double> length = new ArrayList<Double>();
	private double theta;
	private List<Double> newTheta = new ArrayList<Double>();
	
	public void setAxis(){
		axiss = new ArrayList<Integer>();
		for(int i = 0; i <= Constant.threeSixty; i++){
			axiss.add(i);
		}
	}
	
	public void calculateTheta(){
		theta = Math.atan2(height,width)*180/Math.PI;
		if(Constant.log) Log.i(tag, "Theta : "+theta);
	}
	
	
}
