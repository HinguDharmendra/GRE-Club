package org.credila.greclub;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent intent=new Intent(About.this,MainActivity.class);
        startActivity(intent);
        finish();

	}

}
