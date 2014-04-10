package com.infosec.gesturelock;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class StartTestActivity extends Activity {

	private boolean btnDown = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Log.d("asdf", "MOO");
		
		setContentView(R.layout.activity_start_test);
		//tacocatBtn = (Button) this.findViewById(R.id.lockScreenlockView);

		
		/*
		findViewById(R.id.lockScreenlockView).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
						btnDown = true;
						break;
					case MotionEvent.ACTION_UP:
						Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
						btnDown = false;
						break;
					default:
						break;
				}
				return true;
			}
		}); */
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	 
	@Override
	protected void onPause() {
			super.onPause();
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
        NavUtils.navigateUpFromSameTask(this);
        this.finish();

	    super.onBackPressed();
	}

	public void getData(View view) {
//		Intent lockDeviceIntent = new Intent(this, LockActivity.class);
//		this.startActivity(lockDeviceIntent);
	}
}