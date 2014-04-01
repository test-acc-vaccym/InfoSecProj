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
import android.widget.TextView;

import com.infosec.gesturedata.AccelEvent;
import com.infosec.gesturedata.GestureData;

public class SetPasswordActivity extends Activity implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mGravity;
	private Sensor mMagnetometer;
	private Sensor mGyroscope;
	
	
	private float[] mAcceleration = null;
//	private float[] mGravitation = null;
//	private float[] mGeomagnetic = null;
	private ArrayList<AccelEvent> accelData = null;
	  
	private TextView xAxis = null;
	private TextView yAxis = null;
	private TextView zAxis = null;
	private TextView orient = null;
	private TextView directionAccel = null;
	
	float prevX = 0.0f;
	float prevY = 0.0f;
	float prevZ = 0.0f;
	
	boolean btnDown = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setpassword);
		
		this.accelData = new ArrayList<AccelEvent>();
		
		this.xAxis = (TextView) this.findViewById(R.id.axisXval);
		this.yAxis = (TextView) this.findViewById(R.id.axisYval);
		this.zAxis = (TextView) this.findViewById(R.id.axisZval);
		this.orient = (TextView) this.findViewById(R.id.orientationVal);
		this.directionAccel = (TextView) this.findViewById(R.id.directionlbl);
		
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
		
		final TextView testButton = (TextView) this.findViewById(R.id.testBtn);
		
		findViewById(R.id.testBtn).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						testButton.setText("Accessing Accelerometer");
						testButton.setBackgroundColor(Color.RED);
						accelData.clear();
						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
						testButton.setText("tacocat");
						testButton.setBackgroundColor(Color.LTGRAY);
						orient.setText(Integer.toString(accelData.size()));
						HomeActivity.gestureData = new GestureData(accelData);
						btnDown = false;
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
				this.mAcceleration = lowPassFilter(event.values.clone(), this.mAcceleration);
				
				xAxis.setText(Float.toString(this.mAcceleration[0] * 100));
				yAxis.setText(Float.toString(this.mAcceleration[1] * 100));
				zAxis.setText(Float.toString(this.mAcceleration[2] * 100));
				
				if(this.btnDown){
					AccelEvent dataPoint = new AccelEvent(event.values[0], event.values[1], event.values[2]);
					this.accelData.add(dataPoint);
				}
				
				// determine the directions
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
	
	// TODO: Figure out how to get only initial acceleration.
	private void accelerometerParser(float[] input){
		if(input[0]*100 >= 15.0f){
//			Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
			directionAccel.setText("Right");
		}else if(input[0]*100 <= -15.0f){
//			Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
			directionAccel.setText("Left");
		}
		
		if(input[1]*100 >= 13.0f){
//			Toast.makeText(this, "Forward", Toast.LENGTH_SHORT).show();
			directionAccel.setText("Forward");
		}else if(input[1]*100 <= -13.0f){
//			Toast.makeText(this, "Backward", Toast.LENGTH_SHORT).show();
			directionAccel.setText("Backward");
		}
		
		if(input[2]*100 >= 15.0f){
			directionAccel.setText("Zenith (Up)");
		}else if(input[2]*100 <= -15.0f){
			directionAccel.setText("Nadir (Down)");
		}
	}
	
}
