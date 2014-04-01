package com.infosec.gesturelock;

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

public class SetPassword extends Activity implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mGravity;
	private Sensor mMagnetometer;
	private Sensor mGyroscope;
	
	static final float ALPHA = 0.20f;
	
	private float[] mAcceleration = null;
	private float[] mGravitation = null;
	private float[] mGeomagnetic = null;
	  
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
		
		xAxis = (TextView) this.findViewById(R.id.axisXval);
		yAxis = (TextView) this.findViewById(R.id.axisYval);
		zAxis = (TextView) this.findViewById(R.id.axisZval);
		orient = (TextView) this.findViewById(R.id.orientationVal);
		directionAccel = (TextView) this.findViewById(R.id.directionlbl);
		
		mSensorManager = (SensorManager) getSystemService(SetPassword.SENSOR_SERVICE);
		
		if(mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
			mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		}

		if(mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
			mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		}
		
		if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
			mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		}
		
		if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
			mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		}
		
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
		
		final TextView testButton = (TextView) this.findViewById(R.id.testBtn);
		
		findViewById(R.id.testBtn).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						testButton.setText("Accessing Accelerometer");
						testButton.setBackgroundColor(Color.RED);
//						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
						testButton.setText("tacocat");
						testButton.setBackgroundColor(Color.LTGRAY);
//						btnDown = false;
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
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
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
				mAcceleration = lowPassFilter(event.values.clone(), mAcceleration);
				
				xAxis.setText(Float.toString(mAcceleration[0] * 100));
				yAxis.setText(Float.toString(mAcceleration[1] * 100));
				zAxis.setText(Float.toString(mAcceleration[2] * 100));
				
				// determine the directions
				accelerometerParser(event.values.clone());
				break;
			case Sensor.TYPE_GRAVITY:
				mGravitation = event.values.clone();
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				mGeomagnetic = event.values.clone();
				break;
			case Sensor.TYPE_GYROSCOPE:
				
				break;
			default:
				break;
		}
		
	    if (mGravitation != null && mGeomagnetic != null) {
	        float R[] = new float[9];
	        float I[] = new float[9];
	        boolean success = SensorManager.getRotationMatrix(R, I, mGravitation, mGeomagnetic);
	        if (success) {
	            float orientation[] = new float[3];
	            SensorManager.getOrientation(R, orientation);   

	            float azimuthInDegrees = ((float) Math.toDegrees(orientation[0]) + 360) % 360;

	            if(btnDown){
//	            	xAxis.setText(Float.toString(mAcceleration[0]));
//					yAxis.setText(Float.toString(mAcceleration[1]));
//					zAxis.setText(Float.toString(mAcceleration[2]));
					orient.setText(Float.toString(azimuthInDegrees));
	            }
	        }
	    }
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
//		mSensorManager.unregisterListener(this, mAccelerometer);
//		mSensorManager.unregisterListener(this, mMagnetometer);

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
	
	private float[] lowPassFilter(float[] input, float[] output) {
		if (output == null) {
			return input;     
		}
	    
	    for (int i=0; i<input.length; i++) {
	    	output[i] = output[i] + ALPHA * (input[i] - output[i]);
	    }
	    
	   	return output;
	}
}
