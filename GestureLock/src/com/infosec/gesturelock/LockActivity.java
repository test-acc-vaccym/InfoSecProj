package com.infosec.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class LockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_test_lock);
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

	public void startTestBtn(View view) {
		Intent lockIntent = new Intent(this, StartTestActivity.class);
		this.startActivity(lockIntent);
		Log.d("asdf", "MOO");
	}
	
	private boolean existingPass(){
		// Check for existing password		
		return false;
	}
}