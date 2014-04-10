package com.infosec.gesturelock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.infosec.gesturedata.GestureData;
import com.infosec.gesturelock.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeActivity extends Activity {

    public static GestureData userPassword;
    
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
        
        userPassword = null;
		
		try {
			FileInputStream fos = this.openFileInput("userPass");
			ObjectInputStream is = new ObjectInputStream(fos);
			userPassword = (GestureData) is.readObject();
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Password not detected", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Ex: File no here 2", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Ex: File no here 3", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Ex: File no here 4", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	public void lockDeviceBtnPress(View view) {
		Intent lockDeviceIntent = new Intent(this, LockActivity.class);
		this.startActivity(lockDeviceIntent);
	}

	public void setPassBtnPress(View view) {
		Intent setLockIntent = new Intent(this, SetPasswordActivity.class);
		this.startActivity(setLockIntent);
	}
	
	public void instructionBtnPress(View view) {
		Intent tutIntent = new Intent(this, TutActivity.class);
		this.startActivity(tutIntent);
	}
}
