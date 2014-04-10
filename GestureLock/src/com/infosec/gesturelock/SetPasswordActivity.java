package com.infosec.gesturelock;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.infosec.gesturedata.GestureData;

public class SetPasswordActivity extends Activity implements SensorEventListener{
	private GestureData gestureDataSampleOne = null;
	private GestureData gestureDataSampleTwo = null;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	static final float ALPHA = 0.25f;
	
	private float[] mAcceleration = null;
	
	private TextView setPassInstr = null;
	private TextView posOne = null;
	private TextView posTwo = null;
	private Button tacocatBtn = null;
	
	float prevX = 0.0f;
	float prevY = 0.0f;
	float prevZ = 0.0f;
	
	boolean btnDown = true;
	boolean firstSampleCompleted = false;
	boolean passExists = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setpassword);
		
		this.gestureDataSampleOne = new GestureData();
		this.gestureDataSampleTwo = new GestureData();
		
		this.setPassInstr = (TextView) this.findViewById(R.id.setPassInstruction);
		
		this.posOne = (TextView) this.findViewById(R.id.posOneVal);
		this.posTwo = (TextView) this.findViewById(R.id.posTwoVal);
		
		if(existingPass()){
			this.setPassInstr.setText("Enter your existing password");
		}else{
			this.setPassInstr.setText("Set new gesture password. Press and hold the tacocat button to record your new gesture password.");
//			this.setPassInstr.setText("Please hold the button below to create your motion lock and let go when you have finished the pattern you desire.");
		}
		
		this.mSensorManager = (SensorManager) getSystemService(SetPasswordActivity.SENSOR_SERVICE);
		
		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
			this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		}
		
		this.mSensorManager.registerListener(this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		tacocatBtn = (Button) this.findViewById(R.id.testBtn);
		
		findViewById(R.id.testBtn).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						tacocatBtn.setText("Accessing Accelerometer");
						tacocatBtn.setBackgroundColor(Color.RED);
						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
						tacocatBtn.setText("Record Password");
						tacocatBtn.setBackgroundColor(Color.LTGRAY);
						btnDown = false;
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
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
				
				if(this.btnDown){

					if(!firstSampleCompleted){
						this.gestureDataSampleOne.accelerometerParser(mAcceleration);
					}else{
						this.gestureDataSampleTwo.accelerometerParser(mAcceleration);						
					}
				}
				
				break;
			default:
				break;
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
		mSensorManager.unregisterListener(this);
		
        NavUtils.navigateUpFromSameTask(this);
        this.finish();

	    super.onBackPressed();
	}

	private boolean existingPass(){
		// Check for existing password
		if(HomeActivity.userPassword == null){
			return false;
		}else{
			return true;
		}
	}
	
	private void onTacocatRelease(){
		if(passExists){
			
		}else{
			if(!firstSampleCompleted){
				posOne.setText(this.gestureDataSampleOne.data.toString());
				setPassInstr.setText("Confirm gesture password. Press and hold the tacocat button to confirm your new gesture password.");
				firstSampleCompleted = true;
			}else{
				posTwo.setText(this.gestureDataSampleTwo.data.toString());
				setPassInstr.setText("");
				tacocatBtn.setEnabled(false);
				if(GestureData.compResults(this.gestureDataSampleOne, this.gestureDataSampleTwo)){
					try {
						FileOutputStream fos = this.openFileOutput("userPass", Context.MODE_PRIVATE);
						ObjectOutputStream os = new ObjectOutputStream(fos);
						os.writeObject(this.gestureDataSampleOne);
						os.close();
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						Toast.makeText(this, "Ex: 1", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Toast.makeText(this, "Ex: 2", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}

				}
			}
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
