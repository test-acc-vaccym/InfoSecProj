package com.infosec.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import com.infosec.gesturelock.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // get time interval shared preference
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        if (sharedPref.contains("FirstRun")) {
        	Log.d("settings", "CONTAINS");
    		setContentView(R.layout.activity_home);
        } else {
        	Log.d("settings", "NOT CONTAINS");
        	
        	prefEditor.putBoolean("FirstRun", false);
        	prefEditor.commit();

            Intent tutIntent = new Intent(this, TutActivity.class);
            this.startActivity(tutIntent);
            this.finish();
        }
	}
}
