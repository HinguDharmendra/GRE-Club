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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AllAboutTOEFL extends Activity {
	int numQuestions=0;
	ArrayAdapter<String> adapter;
	boolean arr[];
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
		setContentView(R.layout.activity_all_about_gre);
		setTitle("All About TOEFL");
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		ListView ls=(ListView)findViewById(R.id.questionlist);
		try
		{
			InputStream fileStream=getResources().openRawResource(getResources().getIdentifier("raw/tquestions", "raw", getPackageName()));
			int len=fileStream.available();
			byte fileBuffer[]=new byte[len];
			fileStream.read(fileBuffer);
			fileStream.close();
			String text=new String(fileBuffer);
			String classes[]=text.split("\n");
			numQuestions=classes.length;
			arr=new boolean[classes.length];
			for(int i=0;i<numQuestions;i++)
				arr[i]=false;
			adapter=new ArrayAdapter<String>(this, R.layout.questionanswer,R.id.question, classes);
			ls.setAdapter(adapter);
			ls.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					try
					{
						
					for(int i=((arg0.getBaseline()==-1)?0:arg0.getBaseline());i<arg0.getChildCount();i++)
					{
						View v=arg0.getChildAt(i);
						TextView t1=null;
						if(v!=null)
							t1=(TextView)v.findViewById(R.id.answer);
						if(t1!=null)
							t1.setVisibility(View.GONE);
						System.out.println(i+" "+numQuestions);
					}
					for(int i=0;i<numQuestions;i++)
					{ if(i!=arg2)arr[i]=false;}
					arg2++;
					arr[arg2-1]=!arr[arg2-1];
					InputStream fileStream=getResources().openRawResource(getResources().getIdentifier("raw/ta"+arg2, "raw", getPackageName()));
					int len=fileStream.available();
					byte fileBuffer[]=new byte[len];
					fileStream.read(fileBuffer);
					fileStream.close();
					String text=new String(fileBuffer);
					TextView t1=(TextView)arg1.findViewById(R.id.answer);
					t1.setVisibility(((!arr[arg2-1])? View.GONE : View.VISIBLE));
					t1.setText(Html.fromHtml(text));
					}
					catch(Exception e){Log.v("All About TOEFL OnClick","Exception Occured OnItemClick: ");e.printStackTrace(System.out);}
				}
			});
		}
		catch(Exception e)
		{
			Log.v("All About TOEFL","Exception Occured: "+e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_about_gre, menu);
		return true;
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		 NavUtils.navigateUpFromSameTask(this);
	}

}
