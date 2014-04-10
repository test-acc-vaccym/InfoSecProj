package com.infosec.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ImageView;

public class LockSuccessScreen extends Activity {
	private boolean isTaco = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock_success);
		
	}

	@Override
	public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        this.finish();
        
	    super.onBackPressed();
	}
	public void returnToLock(View view) {
		Intent lockIntent = new Intent(this, StartTestActivity.class);
		this.startActivity(lockIntent);
		
		this.finish();
	}

	public void goHome(View view) {
        NavUtils.navigateUpFromSameTask(this);
        this.finish();
	}
	
	public void tacocatRelease(View view){
		ImageView lockView = (ImageView) findViewById(R.id.imageDisplayView);
		if(!isTaco){
			lockView.setImageResource(R.drawable.tacocat);
			isTaco = true;
		}else{
			lockView.setImageResource(R.drawable.welldone);
			isTaco = false;
		}
	}
}
