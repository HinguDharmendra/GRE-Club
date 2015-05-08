package org.credila.greclub;

import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UniversityDetail extends Activity {
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String displayText = "";
		Intent intent=getIntent();
		setContentView(R.layout.activity_university_detail);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		String out=intent.getStringExtra("University_Name");
		setTitle(out);
		out=out.replace(' ','_');
		out=out.replace('(','_');
		out=out.replace(',','_');
		out=out.replace(')','_');
		out=out.replace('-','_');
		out=out.replace('&','_');
		for(char a='A',b='a';a<='Z';a++,b++)
		out=out.replace(a,b);
		try {
		InputStream fileStream = getResources().openRawResource(
				getResources().getIdentifier("raw/"+out,"raw", getPackageName()));
		int fileLen = fileStream.available();
		// Read the entire resource into a local byte buffer.
		byte[] fileBuffer = new byte[fileLen];
		fileStream.read(fileBuffer);
		fileStream.close();
		displayText = new String(fileBuffer);
		
		TextView textView=(TextView)findViewById(R.id.textView1);
		textView.setTextSize(10);
		textView.setText(Html.fromHtml("<br>"+displayText));
		
		//textView.setMovementMethod(new ScrollingMovementMethod());
		textView.setScrollbarFadingEnabled(true);
		
		} catch (Exception e) {
		  // exception handling
			Log.v("University Detail", "index=" + e);
		}
//		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.university_detail, menu);
		return true;
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		NavUtils.navigateUpFromSameTask(this);

	}

}
