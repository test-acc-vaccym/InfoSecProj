package com.infosec.gesturelock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
		if(existingPass()){
			Intent lockIntent = new Intent(this, StartTestActivity.class);
			this.startActivity(lockIntent);
		}else{
			noPassAlert();
		}
	}
	
	private boolean existingPass(){
		// Check for existing password
		if(HomeActivity.userPassword == null){
			return false;
		}else{
			return true;
		}
	}
	
	private void noPassAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        submitQuery.setCancelable(true);
        alert.setTitle("No Password Set");
        alert.setInverseBackgroundForced(true);
        alert.setMessage("You have not yet set a gesture password. Would you like to set a new password?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	
                dialog.dismiss();
            }
        });
        
        alert.show();
	}
}