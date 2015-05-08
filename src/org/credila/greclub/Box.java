package org.credila.greclub;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Box extends Activity {
	List<String> data;
	String count;
	int min;
	int max;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	Intent intent=new Intent(Box.this,MainActivity.class);
	        startActivity(intent);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_box);
    	
//    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	Intent intent=getIntent();
		final String box=intent.getStringExtra("box");
		final ListView listview = (ListView) findViewById(R.id.list);
		setTitle(box);
	try{		
		DBManager db = new DBManager(this);
        
    	db.open();
    	 count=db.getCount(box);
    	 min=Integer.parseInt(count.split("@")[0]);
    	 max=Integer.parseInt(count.split("@")[1]);
    	data=db.read(box);
		listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data));
        listview.setFastScrollEnabled(true);
		db.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position,
				long id) {
			String item=parent.getItemAtPosition(position).toString();
			try{
				
				Intent ourIntent=new Intent("org.credila.greclub.DISPLAYACTIVITY");
				ourIntent.putExtra("ID",item);
				ourIntent.putExtra("minwid",min);
				ourIntent.putExtra("maxwid",max);
				startActivity(ourIntent);
				
			}
			catch(Exception e)
			{e.printStackTrace();}
		}
		
	});
	final EditText et=(EditText)findViewById(R.id.editText41);
	et.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(s.length()>0){
			try
			{
				DBManager db=new DBManager(Box.this);
				db.open();
				List<String> searchdata=db.search(s.toString().trim(),box);
				listview.setAdapter(new ArrayAdapter<String>(Box.this,android.R.layout.simple_list_item_1,searchdata));
				db.close();
			}
		catch(Exception e){e.printStackTrace();}
				}
			else listview.setAdapter(new ArrayAdapter<String>(Box.this,android.R.layout.simple_list_item_1, data));
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	});


    }


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent intent=new Intent(Box.this,MainActivity.class);
        startActivity(intent);
        finish();

	}


}
