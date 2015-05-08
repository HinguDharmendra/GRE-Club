package org.credila.greclub;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GroupDisplay extends Activity {
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    switch (item.getItemId()) {
//	    // Respond to the action bar's Up/Home button
//	    case android.R.id.home:
//	    	Intent intent=new Intent(GroupDisplay.this,MainActivity.class);
//	        startActivity(intent);
//	        return true;
//	    }
//	    return super.onOptionsItemSelected(item);
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_display);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Group");

		Intent i=getIntent();
		String sid=i.getStringExtra("sid");
		String gloss=i.getStringExtra("gloss");
		
		DBManager db=new DBManager(this);
		db.open();
		
		List<String> words=db.getGroupWords(sid);
		
		db.close();
		
		TextView tv=(TextView)findViewById(R.id.Meaning);
		tv.setText(gloss.replace("; "+'\"',";\n"+'\"'));
		ListView lv=(ListView)findViewById(R.id.group_word_list);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, words));
	}

//
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
