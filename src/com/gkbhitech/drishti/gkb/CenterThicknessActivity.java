package com.gkbhitech.drishti.gkb;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkbhitech.drishti.HomeActivity;
import com.gkbhitech.drishti.R;
import com.gkbhitech.drishti.app.DrishtiApplication;
import com.gkbhitech.drishti.common.Constant;
import com.gkbhitech.drishti.common.MyToast;
import com.gkbhitech.drishti.database.DataBaseAdapter;
import com.gkbhitech.drishti.httpclient.NetworkUnavailableException;
import com.gkbhitech.drishti.httpclient.UnauthorizedException;
import com.gkbhitech.drishti.httpclient.WebServiceObjectClient;
import com.gkbhitech.drishti.services.methodresponse.MethodResponseCalBaseCurve;

public class CenterThicknessActivity extends Activity {

	private Context context;
	private String tag = "CenterThicknessActivity";

	// .............. variable used to access database .............
	private DataBaseAdapter dataBaseAdapter;
	private DrishtiApplication mApp;
	private WebServiceObjectClient webServiceObjectClient;

	private RadioGroup rgType;
	private RadioButton rbProgressive, rbSingleVision;
	private Spinner spnSph, spnAxis, spnCyl, spnIndex;
	private EditText edtDiameter, edtBoxWidthA, edtBoxWidthB, edtPdHeight;
	private TextView txtThicknessOpt, txtCenterThickness, txtBaseCurve,
			txtDecentration, txtEffectiveDiameter, txtMinEdge, txtMaxEdge,
			txtDbl;
	private Button btnThicknessOpt, btnCenterThickness;

	private ArrayList<Float> sphCyl = new ArrayList<Float>();
	private ArrayList<Float> cylMaster = new ArrayList<Float>();
	private ArrayList<Integer> axis = new ArrayList<Integer>();
	private Float[] index = { 1.50f, 1.55f, 1.56f, 1.59f, 1.60f, 1.67f, 1.74f };
	private float centerThickness = 0;
	private int type = 1;
	private boolean isOptimazation = false;
	private float selectedSph = 0;
	private int selectedSAxis = 0;
	private float cyl = 0;
	private float dec = 2.50f;
	private float diameter = 0f;
	private float opticalDiameter = 0;
	private float backcurve = 0;
	private float backcurveCyl = 0;
	private float backRadiusCyl = 0;
	private float backRadius = 0;
	private float backSag = 0;
	private float frontRadius = 0;
	private float frontSag = 0;
	private float boxWidthA = 0;
	private float boxHeightB = 0;
	private float dbl = 0;
	private float pdHeight = 0;
	private float pd = 36.50f;

	private DecimalFormat df = new DecimalFormat("#.##");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center_thickness);

		mApp = (DrishtiApplication) getApplication();
		dataBaseAdapter = mApp.getDataBaseAdapter();
		webServiceObjectClient = mApp.getWebserviceObjectClient();

		context = CenterThicknessActivity.this;
		initView();
		setUpSpinner();

	}

	// ============================================ Init Views
	// ================================

	private void initView() {

		rgType = (RadioGroup) findViewById(R.id.rgType);
		rbProgressive = (RadioButton) findViewById(R.id.rgProgressive);
		rbSingleVision = (RadioButton) findViewById(R.id.rgSingleVion);

		spnSph = (Spinner) findViewById(R.id.spn_sph);
		spnCyl = (Spinner) findViewById(R.id.spn_cyl);
		spnIndex = (Spinner) findViewById(R.id.spn_index);
		spnAxis = (Spinner) findViewById(R.id.spn_axis);

		edtDiameter = (EditText) findViewById(R.id.edt_diameter);
		edtDiameter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String s1 = edtDiameter.getText().toString();
				if (!s1.equalsIgnoreCase("") && s1.length() >= 2) {
					float f = Float.parseFloat(s1);
					if (f > 100 || f < 50) {
						edtDiameter.setText("");
					}
				}
			}
		});

		edtBoxWidthA = (EditText) findViewById(R.id.edt_boxwidth_a);
		edtBoxWidthB = (EditText) findViewById(R.id.edt_boxwidth_b);
		txtDbl = (TextView) findViewById(R.id.edt_dbl);
		edtPdHeight = (EditText) findViewById(R.id.edt_pdheight);

		txtThicknessOpt = (TextView) findViewById(R.id.txt_thickness_opt);
		txtCenterThickness = (TextView) findViewById(R.id.txt_centerthickness);
		/* txtEt = (TextView) findViewById(R.id.txt_et1); */
		txtBaseCurve = (TextView) findViewById(R.id.txt_basecurve);
		txtDecentration = (TextView) findViewById(R.id.txtDecentration);
		txtEffectiveDiameter = (TextView) findViewById(R.id.txtEffectiveDiameter);
		txtMinEdge = (TextView) findViewById(R.id.txtMinEdge);
		txtMaxEdge = (TextView) findViewById(R.id.txtMaxEdge);

		btnCenterThickness = (Button) findViewById(R.id.btn_centerthickness);
		btnCenterThickness.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				isOptimazation = false;
				cyl = (Float) spnCyl.getSelectedItem();
				selectedSph = (Float) spnSph.getSelectedItem();
				selectedSAxis = (Integer) spnAxis.getSelectedItem();

				if (cyl > 0) {
					if (selectedSph == 0) {
						selectedSph = 0;
					}
					selectedSph = selectedSph + cyl;
					cyl = cyl * -1;
					if (selectedSAxis > 90) {
						selectedSAxis = selectedSAxis - 90;
					} else {
						selectedSAxis = selectedSAxis + 90;
					}
				}

				try {
					/* centerThickness= */
					calculateCenterthickness(
							(Float) spnIndex.getSelectedItem(), selectedSph);

					// txtCenterThickness.setText(centerThickness+"");
				} catch (Exception e) {
					Toast.makeText(context, " Enter Diameter",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});

		btnThicknessOpt = (Button) findViewById(R.id.btn_thickness_opt);
		btnThicknessOpt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * try{ Float. }catch (Exception e) { // TODO: handle exception
				 * }
				 */

				isOptimazation = true;
				selectedSph = (Float) spnSph.getSelectedItem();
				selectedSAxis = (Integer) spnAxis.getSelectedItem();

				if (cyl > 0) {
					if (selectedSph == 0) {
						selectedSph = 0;
					}
					selectedSph = selectedSph + cyl;
					cyl = cyl * -1;
					if (selectedSAxis > 90) {
						selectedSAxis = selectedSAxis - 90;
					} else {
						selectedSAxis = selectedSAxis + 90;
					}
				}

				try {
					/* centerThickness= */
					calculateCenterthickness(
							(Float) spnIndex.getSelectedItem(), selectedSph);

					/*
					 * if (sph>=2&&cyl<=-0.5){ if(axis>=0&&axis<=90){ String
					 * x=(centerThickness
					 * )-(centerThickness*(((axis/90)*30)/100))+"";
					 * txtThicknessOpt.setText(x); } else if
					 * (axis>90&&axis<=180){ String
					 * x=(centerThickness)-(centerThickness
					 * *(((180-axis)/90)*30)/100)+"";
					 * txtThicknessOpt.setText(x);
					 * 
					 * }
					 * 
					 * }else { txtThicknessOpt.setText("No Optimzation"); }
					 */
				} catch (Exception e) {
					Toast.makeText(context, "Please Enter all values",
							Toast.LENGTH_LONG).show();

					e.printStackTrace();
				}

			}
		});

	}

	public void setUpSpinner() {
		for (float i = 20; i >= -20; i -= 0.25) {
			sphCyl.add(i);
		}

		for (float i = 6; i >= -6; i -= 0.25) {
			cylMaster.add(i);
		}

		for (int i = 0; i < 180; i++) {
			axis.add((i + 1));
		}

		ArrayAdapter<Float> spnSphCylAdapter = new ArrayAdapter<Float>(context,
				R.layout.spinner_simple_item_1, sphCyl);
		ArrayAdapter<Float> spnCylAdapter = new ArrayAdapter<Float>(context,
				R.layout.spinner_simple_item_1, cylMaster);
		ArrayAdapter<Integer> spnAxisAdapter = new ArrayAdapter<Integer>(
				context, R.layout.spinner_simple_item_1, axis);
		ArrayAdapter<Float> spnIndexAdapter = new ArrayAdapter<Float>(context,
				R.layout.spinner_simple_item_1, index);

		spnSphCylAdapter
				.setDropDownViewResource(R.layout.spinner_simple_item_1);
		spnAxisAdapter.setDropDownViewResource(R.layout.spinner_simple_item_1);
		spnIndexAdapter.setDropDownViewResource(R.layout.spinner_simple_item_1);

		spnSph.setAdapter(spnSphCylAdapter);
		spnCyl.setAdapter(spnCylAdapter);
		spnAxis.setAdapter(spnAxisAdapter);
		spnIndex.setAdapter(spnIndexAdapter);

		spnSph.setSelection(spnSphCylAdapter.getPosition((float) 0));
		spnCyl.setSelection(spnCylAdapter.getPosition((float) 0));
		spnAxis.setSelection(spnAxisAdapter.getPosition(90));
	}

	// ===================================== On Click
	// =========================================

	public void back(View v) {
		finish();
	}

	public void home(View v) {

		Intent i = new Intent(getApplicationContext(), HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);

	}

	public void calculateCenterthickness(float index, float sph)
			throws Exception {
		if (rgType.getCheckedRadioButtonId() == R.id.rgProgressive) {
			Log.v(tag, "Type1");
			type = 1;
		} else {
			Log.v(tag, "Type2");
			type = 2;
		}

		CalBaseCurveAsyncTask calBaseCurveAsyncTask = new CalBaseCurveAsyncTask(
				CenterThicknessActivity.this, sph, index);
		calBaseCurveAsyncTask.execute();

	}

	private class CalBaseCurveAsyncTask extends AsyncTask<Void, Void, Integer> {

		private Context context;
		private MethodResponseCalBaseCurve methodResponseCalBaseCurve;
		private String errorMessage;
		private ProgressDialog progressDialog;
		private float sph;
		private float index;

		public CalBaseCurveAsyncTask(Context context, float sph, float index) {
			this.context = context;
			this.sph = sph;
			this.index = index;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Calculate basecurve...");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			try {

				methodResponseCalBaseCurve = webServiceObjectClient
						.getCalculatedBaseCurve(sph, cyl, index, type);
				if (methodResponseCalBaseCurve != null) {
					if (methodResponseCalBaseCurve.getResponseCode() == 0) {

						return Constant.RESULT_SUCCESS;
					} else {
						errorMessage = methodResponseCalBaseCurve
								.getResponseMessage();
						return methodResponseCalBaseCurve.getResponseCode();
					}
				} else {
					errorMessage = Constant.MESSAGE_NULL_RESPONSE;
					return Constant.RESULT_NULL_RESPONSE;
				}
			} catch (NetworkUnavailableException e) {
				e.printStackTrace();
				return Constant.RESULT_NETWORK_UNAVAILABLE;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return Constant.RESULT_AUTHENTICATION_FAILURE;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = e.getMessage();
				return -1;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result == Constant.RESULT_SUCCESS) {

				try {
					float basecurve = methodResponseCalBaseCurve.getData()
							.getBaseCurve();
					txtBaseCurve.setText(df.format(basecurve));
					float minEt = 0;
					opticalDiameter = calculateDiameter();
					backcurve = (float) Math.abs((sph - basecurve)
							* ((1.498 - 1) / (index - 1)));
					backRadius = (float) (0.498 * 1000 / Math
							.abs((backcurve)));
					backSag = (float) (backRadius - Math
							.sqrt((backRadius * backRadius)
									- ((opticalDiameter / 2) * (opticalDiameter / 2))));
					frontRadius = ((index - 1) * 1000) / basecurve;
					frontSag = (float) (frontRadius - Math
							.sqrt((frontRadius * frontRadius)
									- ((opticalDiameter / 2) * (opticalDiameter / 2))));
					
					if (sph >= 2.25) {
						minEt = 0.6f;

					}
					if (sph >= 2.25) {
						Log.v(tag, "Sph Greater than 2.5");
						Log.v(tag, "Optical diamter " + opticalDiameter);
						
						centerThickness = (frontSag - backSag) + minEt;

					} else {
						Float.parseFloat(edtDiameter.getText().toString());
						centerThickness = calculateThickness(index, sph);
					}

					float decentration = 0;
					float effectiveDiameter = 0;
					float minEdge = 0;
					float maxEdge = 0;

					if (dec > 0) {
						decentration = 2.50f;
					} else {
						decentration = (boxWidthA / 2) - (pd - dbl / 2);
					}

					backcurveCyl = (float) (Math.abs(backcurve) + Math.abs(cyl
							* (1.498 - 1) / (index - 1)));
					backRadiusCyl = (float) (0.498 * 1000 / Math
							.abs(backcurveCyl));

					effectiveDiameter = opticalDiameter - decentration * 2;
					minEdge = (backSag + centerThickness) - frontSag;
					double x = (backRadiusCyl * backRadiusCyl)
							- ((opticalDiameter / 2) * (opticalDiameter / 2));
					maxEdge = (float) ((backRadiusCyl - Math.sqrt(x)) + centerThickness)
							- frontSag;

					txtCenterThickness.setText(df.format(centerThickness));
					txtDecentration.setText(df.format(decentration));
					txtEffectiveDiameter.setText(df.format(effectiveDiameter));
					txtMinEdge.setText(df.format(minEdge));
					txtMaxEdge.setText(df.format(maxEdge));

					if (isOptimazation) {

						try {
							Float.parseFloat(edtBoxWidthA.getText().toString());
							Float.parseFloat(edtBoxWidthB.getText().toString());
							Float.parseFloat(txtDbl.getText().toString());
							Float.parseFloat(edtPdHeight.getText().toString());
						} catch (Exception e) {
							Toast.makeText(context, "Enter all values",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}

						if (selectedSph >= 2f && cyl <= -0.5f) {
							if (selectedSAxis >= 0 && selectedSAxis <= 90) {
								float xx = (centerThickness)
										- (float) (centerThickness * (((selectedSAxis / 90f) * 30f) / 100f));
								txtThicknessOpt.setText(df.format(xx));
							} else if (selectedSAxis > 90f
									&& selectedSAxis <= 180f) {
								float xx = (centerThickness)
										- (centerThickness
												* (((180f - selectedSAxis) / 90f) * 30f) / 100f);
								txtThicknessOpt.setText(df.format(xx));

							} else {
								txtThicknessOpt.setText("No Optimzation");

							}

						} else {
							txtThicknessOpt.setText("No Optimzation");
						}
						if (Constant.log)
							Log.i(tag, "Opt : " + txtThicknessOpt.getText());
						isOptimazation = false;
					}

				} catch (Exception e) {

					Toast.makeText(context, "Enter Diameter", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			} else if (result == Constant.RESULT_RECORD_NOT_FOUND) {
				txtBaseCurve.setText("Out of range");
				txtCenterThickness.setText("");
			} else {
				MyToast.show(context, result, errorMessage);
			}

			progressDialog.cancel();
		}
	}

	public float calculateThickness(float index, float sph) {
		float x = 0f;
		Log.v(tag, "centerThickness//: " + x+" sph//"+sph+" Index//"+index);

		if (index == 1.56f) {
			if (sph <= -1.25f) {
				x = 1.8f;
			}
			if (sph == -1f) {
				x = 2;
			}
			if (sph == -0.75f) {
				x = 2.2f;
			}
			if (sph == -0.5f) {
				x = 2.3f;
			}
			if (sph <= 0f&&sph > -0.5f) {
				x = 2.4f;
			}
			if (sph == 0.25f) {
				x = 2.4f;
			}
			if (sph == 0.5f) {
				x = 2.5f;
			}
			if (sph == 0.75f) {
				x = 2.6f;
			}
			if (sph == 1f) {
				x = 2.9f;
			}
			if (sph == 1.25f) {
				x = 3.1f;
			}
			if (sph == 1.5f) {
				x = 3.3f;
			}
			if (sph == 1.75f) {
				x = 3.5f;
			}
			if (sph == 2f) {
				x = 3.7f;
			}
			if (sph == 50f) {
				x = 3.5f;
			} 
		} else if (index == 1.5f) {

			if (sph <= -1f) {
				x = 1.8f;
			}
			if (sph <= -0.75f&&sph >-1f) {
				x = 1.9f;
			}
			if (sph <= -0.5f&&sph >-0.75f) {
				x = 2;
			}
			if (sph <= -0.25f&&sph >-0.5f) {
				x = 2.1f;
			}
			if (sph <= 0f&&sph >-0.25f) {
				x = 2.2f;
			}
			if (sph <= 0.25f&&sph >0f) {
				x = 2.4f;
			}
			if (sph <= 0.5f&&sph >0.25f) {
				x = 2.5f;
			}
			if (sph <= 0.75f&&sph >0.5f) {
				x = 2.6f;
			}
			if (sph <= 1f&&sph >0.75f) {
				x = 2.9f;
			}
			if (sph <= 1.25f&&sph >1f) {
				x = 3.1f;
			}
			if (sph == 1.5f) {
				x = 3.3f;
			}
			if (sph == 1.75f) {
				x = 3.5f;
			}
			if (sph <= 2f&&sph >1.75f) {
				x = 3.7f;
			} 
			if (sph <= 50f&&sph >2f) {
				x = 3.5f;
			}
		} else if (index == 1.55f) {

			if (sph <= -1.25f) {
				x = 1.8f;
			}
			if (sph == -1f) {
				x = 2f;
			}
			if (sph == -0.75f) {
				x = 2.2f;
			}
			if (sph == -0.5f) {
				x = 2.3f;
			}
			if (sph <= 0f&&sph>-0.5f) {
				x = 2.4f;
			}
			if (sph == 0.25f) {
				x = 2.4f;
			}
			if (sph == 0.5f) {
				x = 2.5f;
			}
			if (sph == 0.75f) {
				x = 2.6f;
			}
			if (sph == 1f) {
				x = 2.9f;
			}
			if (sph == 1.25f) {
				x = 3.1f;
			}
			if (sph == 1.5f) {
				x = 3.3f;
			}
			if (sph == 1.75f) {
				x = 3.5f;
			}
			if (sph == 2f) {
				x = 3.7f;
			} 
			if (sph <= 50&&sph>2f) {
				x = 3.5f;
			}
		} else if (index == 1.59f) {

			if (sph <= -1.25f) {
				x = 1.7f;
			}
			if (sph == -1f) {
				x = 1.8f;
			}
			if (sph == -0.75f) {
				x = 1.9f;
			}
			if (sph == -0.5f) {
				x = 2f;
			}
			if (sph == -0.25f) {
				x = 2.1f;
			}
			if (sph == 0f) {
				x = 2.2f;
			}
			if (sph == 0.25f) {
				x = 2.3f;
			}
			if (sph == 0.5f) {
				x = 2.4f;
			}
			if (sph == 0.75f) {
				x = 2.6f;
			}
			if (sph == 1f) {
				x = 2.9f;
			}
			if (sph == 1.25f) {
				x = 3.1f;
			}
			if (sph == 1.5f) {
				x = 3.3f;
			}
			if (sph == 1.75f) {
				x = 3.5f;
			}
			if (sph == 2f) {
				x = 3.7f;
			} if (sph <= 50f&&sph>2f) {
				x = 3.5f;
			}
		} else if (index == 1.6f) {

			if (sph <= -2.5f) {
				x = 1.3f;
			}
			if (sph <= -1.75f&&sph>-2.5f) {
				x = 1.4f;
			}
			if (sph == -1.5f) {
				x = 1.5f;
			}
			if (sph == -1.25f) {
				x = 1.6f;
			}
			if (sph == -1f) {
				x = 1.6f;
			}
			if (sph == -0.75f) {
				x = 1.7f;
			}
			if (sph == -0.5f) {
				x = 1.8f;
			}
			if (sph == -0.25f) {
				x = 1.9f;
			}
			if (sph == 0f) {
				x = 2f;
			}
			if (sph == 0.25f) {
				x = 2.3f;
			}
			if (sph == 0.5f) {
				x = 2.4f;
			}
			if (sph == 0.75f) {
				x = 2.5f;
			}
			if (sph == 1f) {
				x = 2.6f;
			}
			if (sph == 1.25f) {
				x = 2.7f;
			}
			if (sph == 1.5f) {
				x = 2.9f;
			}
			if (sph == 1.75f) {
				x = 3.1f;
			}
			if (sph == 2f) {
				x = 3.3f;
			}
			if (sph <= 50f&&sph>2f) {
				x = 3.5f;
			}
		} else if (index == 1.67f) {

			if (sph <= -1.75f) {
				x = 1.3f;
			}
			if (sph == -1.5f) {
				x = 1.4f;
			}
			if (sph == -1.25f) {
				x = 1.5f;
			}
			if (sph == -1f) {
				x = 1.6f;
			}
			if (sph == -0.75f) {
				x = 1.7f;
			}
			if (sph == -0.5f) {
				x = 1.8f;
			}
			if (sph == -0.25f) {
				x = 1.9f;
			}
			if (sph == 0f) {
				x = 2f;
			}
			if (sph == 0.25f) {
				x = 2.2f;
			}
			if (sph == 0.5f) {
				x = 2.3f;
			}
			if (sph == 0.75f) {
				x = 2.4f;
			}
			if (sph == 1f) {
				x = 2.5f;
			}
			if (sph == 1.25f) {
				x = 2.6f;
			}
			if (sph == 1.5f) {
				x = 2.8f;
			}
			if (sph == 1.75f) {
				x = 2.9f;
			}
			if (sph == 2f) {
				x = 3.1f;
			} if (sph <= 50f&&sph>2f) {
				x = 3.5f;
			}

		} else if (index == 1.74f) {

			if (sph <= -1.5f) {
				x = 1.1f;
			}
			if (sph == -1.25f) {
				x = 1.3f;
			}
			if (sph == -1f) {
				x = 1.4f;
			}
			if (sph == -0.75f) {
				x = 1.5f;
			}
			if (sph == -0.5f) {
				x = 1.6f;
			}
			if (sph == -0.25f) {
				x = 1.7f;
			}
			if (sph == 0f) {
				x = 1.8f;
			}
			if (sph == 0.25f) {
				x = 2f;
			}
			if (sph == 0.5f) {
				x = 2.2f;
			}
			if (sph == 0.75f) {
				x = 2.3f;
			}
			if (sph == 1f) {
				x = 2.4f;
			}
			if (sph == 1.25f) {
				x = 2.5f;
			}
			if (sph == 1.5f) {
				x = 2.6f;
			}
			if (sph == 1.75f) {
				x = 2.8f;
			}
			if (sph == 2f) {
				x = 2.9f;
			} 
			if (sph <= 50f&&sph>2f) {
				x = 3.5f;
			}
		}
		Log.v(tag, "centerThickness//: " + x+" sph//"+sph);
		return x;
	}

	public float calculateDiameter() {
		Log.v(tag, "Calculate diameter");

		diameter = Float.parseFloat(edtDiameter.getText().toString());
		if (isOptimazation) {
			boxWidthA = Float.parseFloat(edtBoxWidthA.getText().toString());
			boxHeightB = Float.parseFloat(edtBoxWidthB.getText().toString());
			dbl = Float.parseFloat(txtDbl.getText().toString());
			pdHeight = Float.parseFloat(edtPdHeight.getText().toString());

		}

		if (diameter >= 50) {
			return diameter;
		} else if (type == 2) {
			Log.v(tag, "Type2 " + boxWidthA);
			if (pdHeight >= (boxHeightB / 2)) {
				float x = (boxWidthA - (pd - dbl / 2));
				float y = pdHeight;
				float z = (x * x + y * y);
				z = (float) Math.sqrt(Double.parseDouble(z + "")) * 2;
				return z;

			} else {
				float x = (boxWidthA - (pd - dbl / 2));
				float y = (boxHeightB - pdHeight);
				float z = (x * x + y * y);
				z = (float) Math.sqrt(Double.parseDouble(z + "")) * 2;
				return z;
			}
		} else if (type == 1) {
			Log.v(tag, "Type1 " + boxWidthA);
			if (pdHeight >= (boxHeightB / 2)) {
				float x = (boxWidthA - (pd - dbl / 2));
				float y = pdHeight - 3;
				float z = (x * x + y * y);
				z = (float) Math.sqrt(Double.parseDouble(z + "")) * 2;
				return z;

			} else {
				float x = (boxWidthA - (pd - dbl / 2));
				float y = (boxHeightB - (pdHeight - 3));
				float z = (x * x + y * y);
				z = (float) Math.sqrt(Double.parseDouble(z + "")) * 2;
				return z;
			}
		}

		return 0f;
	}

}