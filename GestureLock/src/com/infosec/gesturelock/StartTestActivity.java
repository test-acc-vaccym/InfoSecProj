package com.infosec.gesturelock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.infosec.gesturedata.GestureData;

public class StartTestActivity extends Activity implements SensorEventListener {
	private GestureData userAttempt = null;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	static final float ALPHA = 0.25f;
	
	private float[] mAcceleration = null;
	
	private boolean btnDown = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_start_test);
		
//		View decorView = getWindow().getDecorView();
//		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//		                              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//		                              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//		                              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//		                              | View.SYSTEM_UI_FLAG_FULLSCREEN
//		                              | View.SYSTEM_UI_FLAG_LOW_PROFILE);
		
		this.userAttempt = new GestureData();

		this.mSensorManager = (SensorManager) getSystemService(SetPasswordActivity.SENSOR_SERVICE);
		
		if(this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
			this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		}
		
		this.mSensorManager.registerListener(this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

		findViewById(R.id.lockScreenlockView).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
//						Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
//						Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
						btnDown = false;
						checkData();
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
//		mSensorManager.unregisterListener(this);
//		
//        NavUtils.navigateUpFromSameTask(this);
//        this.finish();
//
//	    super.onBackPressed();
	}
	
	private void onLeave() {
		mSensorManager.unregisterListener(this);
		
        NavUtils.navigateUpFromSameTask(this);
        this.finish();

	    super.onBackPressed();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        int type = sensor.getType();
        
		switch(type){
			case Sensor.TYPE_LINEAR_ACCELERATION:
				mAcceleration = lowPassFilter(event.values.clone(), mAcceleration);
				
				if(this.btnDown){
					this.userAttempt.accelerometerParser(mAcceleration);
				}
				
				break;
			default:
				break;
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
	
	private void checkData() {
		if(GestureData.compResults(HomeActivity.userPassword, this.userAttempt)){
			ImageView lockView = (ImageView) findViewById(R.id.lockScreenlockView);
			lockView.setImageResource(R.drawable.unlockscreen);
			lockView.invalidate();
			Toast.makeText(this, "Unlocked.", Toast.LENGTH_SHORT).show();
			
			onLeave();
		}else{
			imposter();
		}
	}
	
	private void imposter() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("IMPOSTER!");
        alert.setInverseBackgroundForced(true);
        alert.setMessage("You have gestured the incorrect pattern. Would you like to retry?");
        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	userAttempt.data.clear();
            	
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onLeave();
            }
        });
        
        alert.show();
	}
}