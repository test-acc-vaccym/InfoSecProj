package com.infosec.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;

import com.infosec.gesturedata.GestureData;
import com.infosec.gesturelock.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeActivity extends Activity {
	public static GestureData gestureData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_home);
        
        // Check for first run
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        if (!sharedPref.contains("FirstRun")) {
        	prefEditor.putBoolean("FirstRun", false);
        	prefEditor.commit();

            Intent tutIntent = new Intent(this, TutActivity.class);
            this.startActivity(tutIntent);
        }
	}
	
	public void lockDeviceBtnPress(View view) {
		
	}
	
	public void unlockDeviceBtnPress(View view) {
		
	}

	public void setPassBtnPress(View view) {
		Intent setLockIntent = new Intent(this, SetPassword.class);
		this.startActivity(setLockIntent);
	}
	
	public void instructionBtnPress(View view) {
		Intent tutIntent = new Intent(this, TutActivity.class);
		this.startActivity(tutIntent);
	}
}
