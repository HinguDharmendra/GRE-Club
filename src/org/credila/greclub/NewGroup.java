package org.credila.greclub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewGroup extends Activity {

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    switch (item.getItemId()) {
//	    // Respond to the action bar's Up/Home button
//	    case android.R.id.home:
//	        NavUtils.navigateUpFromSameTask(this);
//	        return true;
//	    }
//	    return super.onOptionsItemSelected(item);
//	}
	DBManager db;
	EditText meaning;
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
        setTitle("Create Group");
//        getActionBar().setDisplayHomeAsUpEnabled(true);

		meaning=(EditText) findViewById(R.id.meaningBox);
		spinner = (Spinner) findViewById(R.id.posSpinner);

		Button add=(Button)findViewById(R.id.create);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result=null;
				db = new DBManager(getApplicationContext());
				db.open();
				result=db.newGroup(meaning.getText().toString(),spinner.getSelectedItem().toString(),DisplayActivity.word);
				db.close();
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				finish();
				
			}
		});
		
		Button cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	

//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		//super.onBackPressed();
//		new AlertDialog.Builder(this)
//	    .setTitle("Exit Application")
//	    .setMessage("Are you sure you want to exit?")
//	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int which) { 
//	            // continue with delete
//
//				Intent intent = new Intent(Intent.ACTION_MAIN);
//			    intent.addCategory(Intent.CATEGORY_HOME);
//			    startActivity(intent);
//			
//	        	
//	        }
//	     })
//	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int which) { 
//	            // do nothing
//	        	
//	        }
//	     })
//	     .show();
//
//	}
	

}
