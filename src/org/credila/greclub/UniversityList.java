
package org.credila.greclub;

import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UniversityList extends Activity {
	
	ArrayAdapter<String> content;
	String classes[];
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
						
		setContentView(R.layout.activity_university_list);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		ListView ls=(ListView) findViewById(R.id.listView1);
		EditText et=(EditText) findViewById(R.id.search_university);
		try
		{
		String displayText="";
		
		InputStream fileStream = getResources().openRawResource(
				getResources().getIdentifier("raw/universityname","raw", getPackageName()));
		int fileLen = fileStream.available();
		// Read the entire resource into a local byte buffer.
		byte[] fileBuffer = new byte[fileLen];
		fileStream.read(fileBuffer);
		fileStream.close();
		displayText = new String(fileBuffer);
		classes=displayText.split("\n");
		content = new ArrayAdapter<String>(this,R.layout.univlist_main,R.id.univ, classes);
		ls.setAdapter(content);

		ls.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				String univ=((TextView)(arg1.findViewById(R.id.univ))).getText().toString();
				Intent intent=new Intent(getApplicationContext(),UniversityDetail.class);
				intent.putExtra("University_Name", univ);
				startActivity(intent);
			}
		});
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				UniversityList.this.content.getFilter().filter(cs);	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub							
			}
		});
		}
		catch(Exception e)
		{
			Log.v("University List", "Exception: index="+e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.university_list, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	      NavUtils.navigateUpFromSameTask(this);

	}

}
