package com.gkbhitech.drishti.gkb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gkbhitech.drishti.common.Constant;

public class LensThicknessMaster {
	
	private static final String tag = "LensThicknessDataForTypeA";
	
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
	private List<Double> lensPowerList = new ArrayList<Double>();
	private List<Double> thickness = new ArrayList<Double>();
	private double aTheta;
	private List<Double> newTheta = new ArrayList<Double>();
	private double sph;
	private double cyl;
	private double axis;
	private double lensPower;
	private double lensMaterial;
	private int eye;
	//private double prism21;
	private double hDecByPrism;
	private double HDecentration;
	private double vDecByPrism;
	private double VDecentration;
	private double et;
	
	private double bTheta;
	private List<Double> bX = new ArrayList<Double>();
	private List<Double> bY = new ArrayList<Double>();
	private List<Double> bd = new ArrayList<Double>();
	private List<Double> bNewX = new ArrayList<Double>();
	private List<Double> bNewY = new ArrayList<Double>();
	private List<Double> bNewTheta = new ArrayList<Double>();
	private List<Integer> bNewAxis = new ArrayList<Integer>(); 
	private List<Double> bLength = new ArrayList<Double>();
	private List<Double> bLensPowerList = new ArrayList<Double>();
	private List<Double> bThickness = new ArrayList<Double>();

	public LensThicknessMaster(double sph, double cyl, double axis, double lensPower, double lensMaterial, double et, double width, double height, int eye,
			double HDecentration, double VDecentration, double hDecByPrism, double vDecByPrism) {	
		this.sph = sph;
		this.cyl = cyl;
		this.axis = axis;
		this.lensPower = lensPower;
		this.lensMaterial = lensMaterial;
		this.et = et;
		this.width = width;
		this.height = height;
		this.eye = eye;
		//this.prism21 = prism21;
		this.hDecByPrism = hDecByPrism;
		this.vDecByPrism = vDecByPrism;
		this.HDecentration = HDecentration;
		this.VDecentration = VDecentration;
	}
	
	public void setAxis(){
		axiss = new ArrayList<Integer>();
		for(int i = 0; i <= Constant.threeSixty; i++){
			axiss.add(i);
		}
	}
	
	public List<Integer> getAxis(){
		return axiss;
	}
	
	public void calculateTheta(){
		aTheta = Math.atan2(height,width)*180/Math.PI;
		//if(Constant.log) Log.i(tag, "Theta : "+aTheta);
	}
	
	public double getTheta(){
		return aTheta;
	}
	
	public void addItemToX(Double item, Integer position){
		if(position != null){
			x.add(position, item);
		}else{
			x.add(item);
		}
		//if(Constant.log)Log.i(tag, "Add "+item+" at "+position+" to x");
	}
	
	public void addItemToY(Double item, Integer position){
		if(position != null){
			y.add(position, item);
		}else{
			y.add(item);
		}
		
		//if(Constant.log)Log.i(tag, "Add "+item+" at "+position+" to y");
	}
	
	public void calculateY(){
		
		while(y.size() < Constant.ninety){
			
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			
			int axis = y.size();
			
			if(axis <= aTheta){
				//Double temp0 = x.get(Constant.zero);
				//if(Constant.log) Log.i(tag, "x zero : "+temp0);
				//Double temp1 = Math.tan(Math.PI*axis/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "tan result : "+temp1);
				Double temp = (Math.tan(Math.PI*axis/Constant.oneEighty))*(x.get(Constant.zero));
				//if(Constant.log) Log.i(tag, "if Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}else{
				Double temp = height/2;
				//if(Constant.log) Log.i(tag, "else Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}
		}
		
		if(y.size() == Constant.ninety){
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			y.add(height/2);
			//if(Constant.log) Log.i(tag, "Y item : "+y.get(Constant.ninety));
			//if(Constant.log)Log.i(tag, "Add "+(height/2)+" at "+Constant.ninety+" to y");
		}
		
		Double ab180 = -1*(width/2);
		
		while(y.size() > Constant.ninety && y.size() < Constant.oneEighty){
			
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			
			int axis = y.size();
			
			if(axis <= (Constant.oneEighty-aTheta)){
				Double temp = height/2;
				//if(Constant.log) Log.i(tag, "else Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}else{
				Double temp = -1*ab180*Math.tan(Math.PI*(Constant.oneEighty-axis)/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "else Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}
		}
		
		if(y.size() == Constant.oneEighty){
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			y.add(new Double(0));
			//if(Constant.log) Log.i(tag, "Y item : "+y.get(Constant.oneEighty));
			//if(Constant.log)Log.i(tag, "Add "+(new Double(0))+" at "+Constant.oneEighty+" to y");
		}
		
		while(y.size() > 180 && y.size() < Constant.twoSeventy){
			
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			
			int axis = y.size();
			
			if(axis <= (Constant.oneEighty+aTheta)){
				Double temp = ab180*Math.tan(Math.PI*(axis-Constant.oneEighty)/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "if Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp); 
			}else{
				Double temp = -1*height/2;
				//if(Constant.log) Log.i(tag, "else Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp); 
			}
		}
		
		if(y.size() == Constant.twoSeventy){
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			y.add(-1*height/2);
			//if(Constant.log) Log.i(tag, "Y item : "+y.get(Constant.twoSeventy));
			//if(Constant.log)Log.i(tag, "Add "+(-1*height/2)+" at "+Constant.twoSeventy+" to y");
		}
		
		while(y.size() < Constant.threeSixty){
			
			//if(Constant.log) Log.i(tag, "Y size : "+y.size());
			
			int axis = y.size();
			
			if(axis <= (Constant.threeSixty-aTheta)){
				Double temp = -1*height/2;
				//if(Constant.log) Log.i(tag, "if Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}else{
				Double temp = Math.tan(Math.PI*axis/Constant.oneEighty)*x.get(Constant.zero);
				//if(Constant.log) Log.i(tag, "else Y item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to y");
				y.add(temp);
			}
		}
		
		//if(Constant.log)Log.i(tag, "Add "+new Double(0)+" at "+y.size()+" to y");
		y.add(new Double(0));
		//if(Constant.log) Log.i(tag, "Y size : "+y.size());
	}
	
	public void calculateX(){
		
		while(x.size() < Constant.ninety){
			
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			
			int axis = x.size();
			
			if(axis <= aTheta){
				Double temp = width/2;
				//if(Constant.log) Log.i(tag, "if X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}else{
				Double temp = (Math.tan(Math.PI*(Constant.ninety-axis)/Constant.oneEighty))*(y.get(92));
				//if(Constant.log) Log.i(tag, "else X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}
		}
		
		if(x.size() == Constant.ninety){
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			x.add(new Double(0));
			//if(Constant.log) Log.i(tag, "X item : "+x.get(Constant.ninety));
			//if(Constant.log)Log.i(tag, "Add "+(new Double(0))+" at "+Constant.ninety+" to y");
		}
		
		Double ac90 = height/2;
		
		while(x.size() > Constant.ninety && x.size() < Constant.oneEighty){
			
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			
			int axis = x.size();
			
			if(axis <= (Constant.oneEighty-aTheta)){
				Double temp = -1*ac90*Math.tan(Math.PI*(axis-Constant.ninety)/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "if X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}else{
				Double temp = -1*width/2;
				//if(Constant.log) Log.i(tag, "else X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}
		}
		
		if(x.size() == Constant.oneEighty){
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			x.add(-1*width/2);
			//if(Constant.log) Log.i(tag, "X item : "+x.get(Constant.oneEighty));
			//if(Constant.log) Log.i(tag, "Add "+(-1*width/2)+" at "+Constant.oneEighty+" to y");
		}
		
		while(x.size() > 180 && x.size() < Constant.twoSeventy){
			
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			
			int axis = x.size();
			
			if(axis <= (Constant.oneEighty+aTheta)){
				Double temp = -1*width/2;
				//if(Constant.log) Log.i(tag, "if X item : "+temp);
				//if(Constant.log) Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}else{
				Double temp = -1*ac90*Math.tan(Math.PI*(Constant.twoSeventy-axis)/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "else X item : "+temp);
				//if(Constant.log) Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}
		}
		
		if(x.size() == Constant.twoSeventy){
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			x.add(new Double(0.0));
			//if(Constant.log) Log.i(tag, "X item : "+x.get(Constant.twoSeventy));
			//if(Constant.log) Log.i(tag, "Add "+(new Double(0.0))+" at "+Constant.twoSeventy+" to y");
		}
		
		while(x.size() < Constant.threeSixty){
			
			//if(Constant.log) Log.i(tag, "X size : "+x.size());
			
			int axis = x.size();
			
			if(axis <= (Constant.threeSixty-aTheta)){
				Double temp = ac90*Math.tan(Math.PI*(axis-Constant.twoSeventy)/Constant.oneEighty);
				//if(Constant.log) Log.i(tag, "if X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}else{
				Double temp = width/2;
				//if(Constant.log) Log.i(tag, "if X item : "+temp);
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+axis+" to x");
				x.add(temp);
			}
		}
		
		//if(Constant.log)Log.i(tag, "Add "+(width/2)+" at "+x.size()+" to x");
		x.add(width/2);
		//if(Constant.log) Log.i(tag, "X size : "+x.size());
	}

	public void calculateAD(){
		
		Double temp = 0.0;
		for(int index : axiss){
			
			temp = Math.sqrt(Math.pow(x.get(index), 2)+Math.pow(y.get(index),2));
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to AD ");
			ad.add(temp);
		}
	}
	
	public void calculateNewX(){
		
		double temp = 0.0;
		for(int index : axiss){
			if(eye == 1){
				//if(Constant.log)Log.i(tag, x.get(index)+" "+HDecentration+" "+hDecByPrism);
				temp = x.get(index)-HDecentration-hDecByPrism;
				newX.add(temp);
			}else{
				//if(Constant.log)Log.i(tag, x.get(index)+" "+HDecentration+" "+hDecByPrism);
				temp = x.get(index)+HDecentration-hDecByPrism;
				newX.add(temp);
			}
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to new x");
		}
	}
	
	public void calculateNewY(){
		double temp = 0.0;
		for(int index : axiss){
			temp = y.get(index)-VDecentration-vDecByPrism;
			newY.add(temp);
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to new y");
		}
	}
	
	public void calculateNewTheta(){
		
		double temp = Constant.oneEighty/Math.PI*Math.atan2(newY.get(Constant.zero),newX.get(Constant.zero));
		
		newTheta.add(temp);
		
		for(int index = 1; index < axiss.size(); index++){
			//if(Constant.log)Log.i(tag, "Add "+(newTheta.get(index-1)+1)+" at "+index+" to new theta");
			newTheta.add(newTheta.get(index-1)+1);
		}
	}
	
	public void calculateNewAxis(){
		for(int index : axiss){
			
			//if(Constant.log)Log.i(tag, "Add "+((int) (newTheta.get(index)-newTheta.get(Constant.zero)))+" at "+index+" to new axis");
			newAxis.add((int) (newTheta.get(index)-newTheta.get(Constant.zero)));
		}
	}
	
	public void calculateLength(){
		
		Double temp = 0.0;
		for(int index : axiss){
			
			temp = Math.sqrt(Math.pow(newX.get(index), 2)+Math.pow(newY.get(index),2));
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to length");
			length.add(temp);
		}
	}
	
		
	public void calculateLensPower(){
		
		double temp = 0.0;
		
		for(int index : newAxis){
			temp = sph+(cyl*Math.pow((Math.sin(Math.PI*(index-axis)/180)), 2));
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to lenspower");
			lensPowerList.add(temp);
		}
	}
	
	public void calculateThickness(){
		
		for(int index : axiss){
		
			if(lensPower > 0){
				
				//double temp = Math.pow(Collections.max(length), 2);
				//if(Constant.log)Log.i(tag, "temp for thickenss : "+temp);
				
				//double maxFromLensPower = Collections.max(lensPowerList);
				//if(Constant.log)Log.i(tag, "max from lenspower : "+maxFromLensPower);
				
				double temp1 = Math.abs(Math.pow(Collections.max(length), 2)*Collections.max(lensPowerList)/(lensMaterial-1)/2000)+et;
				//if(Constant.log)Log.i(tag, "thickness : "+temp1);
				//if(Constant.log)Log.i(tag, "Add "+temp1+" at "+index+" to thickness");
				thickness.add(temp1);
			}else{
				
				double temp = Math.abs(lensPowerList.get(index)*Math.pow(length.get(index), 2)/(lensMaterial-1)/2000)+et;
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to thickness");
				thickness.add(temp);
			}
		}
	}
	
	public List<Double> getLengthOfA(){
		return length;
	}
	
	public List<Double> getThicknessOfA(){
		return thickness;
	}
	
	public List<Integer> getNewAxis(){
		return newAxis;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------
	
	public void calculateBTheta(){
		double temp = Constant.oneEighty/Math.PI*Math.atan2(y.get(32), y.get(31));
		//if(Constant.log)Log.i(tag, "Theta : "+temp+" type B");
		bTheta = temp;
	}
	
	public void calculateBX(){
		
		for(int index : axiss){
			
			double temp = width/2*height/2/(Math.sqrt(Math.pow(height/2,2)+(Math.pow(width/2,2)*Math.pow(Math.tan(index*Math.PI/Constant.oneEighty),2))));
			
			if(index > Constant.ninety && index <= Constant.twoSeventy){
				temp *= -1;
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to bx");
				bX.add(temp);
			}else{
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to bx");
				bX.add(temp);
			}
		}
	}
	
	public void calculateBY(){
		
		for(int index : axiss){
			
			double temp = Math.tan(index*Math.PI/Constant.oneEighty)*width/2*height/2/(Math.sqrt(Math.pow(height/2,2)+(Math.pow(width/2,2)*Math.pow(Math.tan(index*Math.PI/Constant.oneEighty),2))));
			
			if(index > Constant.ninety && index <= Constant.twoSeventy){
				temp *= -1;
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to by");
				bY.add(temp);
			}else{
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to by");
				bY.add(temp);
			}
		}
	}
	
	public void calculateBD() {

		Double temp = 0.0;
		for (int index : axiss) {

			temp = Math.sqrt(Math.pow(bX.get(index), 2)+ Math.pow(bY.get(index), 2));
			// if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to BD ");
			bd.add(temp);
		}
	}
	
	public void calculateBNewX() {

		double temp = 0.0;
		for (int index : axiss) {
			if (eye == 1) {
				// if(Constant.log)Log.i(tag,
				// x.get(index)+" "+HDecentration+" "+hDecByPrism);
				temp = bX.get(index) - HDecentration - hDecByPrism;
				bNewX.add(temp);
			} else {
				// if(Constant.log)Log.i(tag,
				// x.get(index)+" "+HDecentration+" "+hDecByPrism);
				temp = bX.get(index) + HDecentration - hDecByPrism;
				bNewX.add(temp);
			}
			// if(Constant.log)Log.i(tag,
			// "Add "+temp+" at "+index+" to new x of b");
		}
	}
	
	public void calculateBNewY(){
		double temp = 0.0;
		for(int index : axiss){
			temp = bY.get(index)-VDecentration-vDecByPrism;
			bNewY.add(temp);
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to new y of b");
		}
	}
	

	public void calculateBNewTheta(){
		
		double temp = Constant.oneEighty/Math.PI*Math.atan2(bNewY.get(Constant.zero),bNewX.get(Constant.zero));
		//if(Constant.log)Log.i(tag, "Add "+temp+" at 0 to new theta of b");
		bNewTheta.add(temp);
		
		for(int index = 1; index < axiss.size(); index++){
			//if(Constant.log)Log.i(tag, "Add "+(bNewTheta.get(index-1)+1)+" at "+index+" to new theta of b");
			bNewTheta.add(bNewTheta.get(index-1)+1);
		}
	}
	
	public void calculateBNewAxis(){
		for(int index : axiss){
		
			//if(Constant.log)Log.i(tag, "Add "+((int) (bNewTheta.get(index)-bNewTheta.get(Constant.zero)))+" at "+index+" to new axis of b");
			bNewAxis.add((int) (bNewTheta.get(index)-bNewTheta.get(Constant.zero)));
		}
	}
	
	public void calculateBLength(){
	
		Double temp = 0.0;
		for(int index : axiss){
		
			temp = Math.sqrt(Math.pow(bNewX.get(index), 2)+Math.pow(bNewY.get(index),2));
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to length of b");
			bLength.add(temp);
		}
	}

	public void calculateBLensPower(){
		
		double temp = 0.0;
		
		for(int index : bNewAxis){
			temp = sph+(cyl*Math.pow((Math.sin(Math.PI*(index-axis)/180)), 2));
			//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to lenspower");
			bLensPowerList.add(temp);
		}
	}
	
	public void calculateBThickness(){
		
		for(int index : axiss){
		
			if(lensPower > 0){
				
				double temp1 = Math.abs(Math.pow(Collections.max(bLength), 2)*Collections.max(bLensPowerList)/(lensMaterial-1)/2000)+et;
				//if(Constant.log)Log.i(tag, "Add "+temp1+" at "+index+" to thickness");
				bThickness.add(temp1);
			}else{
				
				double temp = Math.abs(bLensPowerList.get(index)*Math.pow(bLength.get(index), 2)/(lensMaterial-1)/2000)+et;
				//if(Constant.log)Log.i(tag, "Add "+temp+" at "+index+" to thickness");
				bThickness.add(temp);
			}
		}
	}
	
	public List<Double> getLengthOfB(){
		return bLength;
	}
	
	public List<Double> getThicknessOfB(){
		return bThickness;
	}
	
	public List<Integer> getBNewAxis(){
		return bNewAxis;
	}
}
