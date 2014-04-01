package com.infosec.gesturelock;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.infosec.gesturedata.AccelEvent;
import com.infosec.gesturedata.GestureData;
import com.infosec.gesturedata.Point;

public class SetPasswordActivity extends Activity implements SensorEventListener{
	private GestureData gestureDataSampleOne = null;
	private GestureData gestureDataSampleTwo = null;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
//	private Sensor mGravity;
//	private Sensor mMagnetometer;
//	private Sensor mGyroscope;
	
	
	static final float ALPHA = 0.20f;
	
	private float[] mAcceleration = null;
//	private float[] mGravitation = null;
//	private float[] mGeomagnetic = null;
	private ArrayList<AccelEvent> accelData = null;
	
	private TextView setPassInstr = null;
	private TextView directionAccel = null;
	private TextView posOne = null;
	private TextView posTwo = null;
	private Button tacocatBtn = null;
	
	float prevX = 0.0f;
	float prevY = 0.0f;
	float prevZ = 0.0f;
	
	boolean btnDown = true;
	boolean firstSampleCompleted = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setpassword);
		
		this.accelData = new ArrayList<AccelEvent>();
		
		this.setPassInstr = (TextView) this.findViewById(R.id.setPassInstruction);
		this.directionAccel = (TextView) this.findViewById(R.id.directionlbl);
		
		this.posOne = (TextView) this.findViewById(R.id.posOneVal);
		this.posTwo = (TextView) this.findViewById(R.id.posTwoVal);
		
		if(existingPass()){
			this.setPassInstr.setText("Enter your existing password");
		}else{
			this.setPassInstr.setText("Set new gesture password. Press and hold the tacocat button to record your new gesture password.");
		}
		
		this.mSensorManager = (SensorManager) getSystemService(SetPasswordActivity.SENSOR_SERVICE);
		
		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
			this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		}

//		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
//			this.mGravity = this.mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
//		}
//		
//		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
//			this.mMagnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//		}
//		
//		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
//			this.mGyroscope = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//		}
		
		this.mSensorManager.registerListener(this, this.mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
//		this.mSensorManager.registerListener(this, this.mGravity, SensorManager.SENSOR_DELAY_NORMAL);
//		this.mSensorManager.registerListener(this, this.mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
//		this.mSensorManager.registerListener(this, this.mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
		
		tacocatBtn = (Button) this.findViewById(R.id.testBtn);
		
		findViewById(R.id.testBtn).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						tacocatBtn.setText("Accessing Accelerometer");
						tacocatBtn.setBackgroundColor(Color.RED);
						accelData.clear();
						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
						tacocatBtn.setText("tacocat");
						tacocatBtn.setBackgroundColor(Color.LTGRAY);
						onTacocatRelease();
						break;
					default:
						break;
				}
				return true;
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
//		mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
//		mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
//		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
	}
	 
	@Override
	protected void onPause() {
			super.onPause();
			mSensorManager.unregisterListener(this);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        int type = sensor.getType();
        
		switch(type){
			case Sensor.TYPE_LINEAR_ACCELERATION:
				if(this.btnDown){
					AccelEvent dataPoint = new AccelEvent(event.values[0], event.values[1], event.values[2]);
					this.accelData.add(dataPoint);
				}
				
//				accelerometerParser(mAcceleration);
				break;
//			case Sensor.TYPE_GRAVITY:
////				mGravitation = event.values.clone();
//				break;
//			case Sensor.TYPE_MAGNETIC_FIELD:
////				mGeomagnetic = event.values.clone();
//				break;
			default:
				break;
		}
		
//	    if (mGravitation != null && mGeomagnetic != null) {
//	        float R[] = new float[9];
//	        float I[] = new float[9];
//	        boolean success = SensorManager.getRotationMatrix(R, I, mGravitation, mGeomagnetic);
//	        if (success) {
//	            float orientation[] = new float[3];
//	            SensorManager.getOrientation(R, orientation);   
//
//	            float azimuthInDegrees = ((float) Math.toDegrees(orientation[0]) + 360) % 360;
//
//				orient.setText(Float.toString(azimuthInDegrees));
//	            if(btnDown){
//	            	
//	            }
//	        }
//	    }
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wii_gee, menu);
		return true;
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		mSensorManager.unregisterListener(this);
		
        NavUtils.navigateUpFromSameTask(this);
        this.finish();

	    super.onBackPressed();
	}

	private boolean existingPass(){
		// Check for existing password		
		return false;
	}
	
	private void onTacocatRelease(){
		//RUN AWAY!!!!!
		if(!firstSampleCompleted){
			gestureDataSampleOne = new GestureData(accelData);
			setPassInstr.setText("Confirm gesture password. Press and hold the tacocat button to confirm your new gesture password.");
			firstSampleCompleted = true;
			btnDown = false;
		}else{
			gestureDataSampleTwo = new GestureData(accelData);
			btnDown = false;
			if(gestureDataSampleOne == null || gestureDataSampleTwo == null){
				setPassInstr.setText("Error: Please reenter your password");
				firstSampleCompleted = false;
			}else{
				tacocatBtn.setEnabled(false);
				setPassInstr.setText("Thinking...");
				Point gestureOne = gestureDataSampleOne.getPosition();
				Point gestureTwo = gestureDataSampleTwo.getPosition();
				
				posOne.setText("(" + Float.toString(gestureOne.x * 100.0f) + ", " + Float.toString(gestureOne.y * 100.0f) + ", " + Float.toString(gestureOne.z * 100.0f) + ")");
				posTwo.setText("(" + Float.toString(gestureTwo.x * 100.0f) + ", " + Float.toString(gestureTwo.y * 100.0f) + ", " + Float.toString(gestureTwo.z * 100.0f) + ")");
			}
		}
	}
	
	/*
	private void accelerometerParser(float[] input){
		if(input[0]*100 >= 15.0f){
			directionAccel.setText("Right");
		}else if(input[0]*100 <= -15.0f){
			directionAccel.setText("Left");
		}
		
		if(input[1]*100 >= 13.0f){
			directionAccel.setText("Forward");
		}else if(input[1]*100 <= -13.0f){
			directionAccel.setText("Backward");
		}
		
		if(input[2]*100 >= 15.0f){
			directionAccel.setText("Zenith (Up)");
		}else if(input[2]*100 <= -15.0f){
			directionAccel.setText("Nadir (Down)");
		}
	} */
}
