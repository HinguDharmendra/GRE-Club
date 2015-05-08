package org.credila.greclub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String boxlist[] = {"HF","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static final String[] from = new String[] { "title" };
    public static final int[] to = new int[] { R.id.title };
	private Intent boxlistIntent;
	private Intent quizIntent;
	private Intent infoIntent;
    ListView listview;
    ListView listview2;
	public static List<ListItem> list=new ArrayList<ListItem>();
	public static List<ListItem> savlist=new ArrayList<ListItem>();
	static List<HashMap<String, Object>> fillMaps;
    Button boxListButton;
    Button groupListButton;
    Button quizButton;
    Button info;
    View progressbar;
    private Boolean isBoxList = true;
    private Boolean isLoaded = false;
    
    private DBManager db;
    private static ProgressDialog progDailog;
    Thread t;
    private Handler progressHandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progDailog=new ProgressDialog(MainActivity.this);
		progDailog.setTitle("Please Wait!!");
		progDailog.setMessage("Wait!!");
		progDailog.setCancelable(false);
		progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		setContentView(R.layout.gridview_row);
		setContentView(R.layout.activity_main);
		
		boxListButton = (Button) findViewById(R.id.boxlistButton);
		groupListButton = (Button) findViewById(R.id.grouplistButton);
		quizButton = (Button) findViewById(R.id.quizButton);
		info =(Button) findViewById(R.id.info);
		listview = (ListView) findViewById(R.id.combined_list);
		listview.setFastScrollEnabled(true);
		
		toBox();

		info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(isBoxList)
				{
					info.setBackgroundColor(getResources().getColor(R.color.dark));
					quizButton.setBackgroundColor(getResources().getColor(R.color.light));
					boxListButton.setBackgroundColor(getResources().getColor(R.color.light));
					groupListButton.setBackgroundColor(getResources().getColor(R.color.light));
					info.setTextColor(Color.WHITE);
					quizButton.setTextColor(Color.BLACK);
					boxListButton.setTextColor(Color.BLACK);
					groupListButton.setTextColor(Color.BLACK);
					toInfo();
				}
				
			}

			private void toInfo() {
				// TODO Auto-generated method stub
				infoIntent=new Intent(getApplicationContext(), Info.class);
				infoIntent.putExtra("Title","FAQs");
				startActivity(infoIntent);
				finish();
			}
		});
		quizButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(isBoxList)
				{
					info.setBackgroundColor(getResources().getColor(R.color.light));
					info.setTextColor(Color.BLACK);
					quizButton.setBackgroundColor(getResources().getColor(R.color.dark));
					boxListButton.setBackgroundColor(getResources().getColor(R.color.light));
					groupListButton.setBackgroundColor(getResources().getColor(R.color.light));
					quizButton.setTextColor(Color.WHITE);
					boxListButton.setTextColor(Color.BLACK);
					groupListButton.setTextColor(Color.BLACK);
					toQuiz();
				}
				
			}
		});
		groupListButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if(isBoxList)
		    	{
		    		info.setBackgroundColor(getResources().getColor(R.color.light));
					info.setTextColor(Color.BLACK);
		    		boxListButton.setBackgroundColor(getResources().getColor(R.color.light));
		    		groupListButton.setBackgroundColor(getResources().getColor(R.color.dark));
		    		quizButton.setBackgroundColor(getResources().getColor(R.color.light));
		    		boxListButton.setTextColor(Color.BLACK);
		    		groupListButton.setTextColor(Color.WHITE);
		    		quizButton.setTextColor(Color.BLACK);
		    		toGroup();
		    	}
		    }
		});

		boxListButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if(!isBoxList)
		    	{
		    		info.setBackgroundColor(getResources().getColor(R.color.light));
					info.setTextColor(Color.BLACK);
		    		groupListButton.setBackgroundColor(getResources().getColor(R.color.light));
		    		boxListButton.setBackgroundColor(getResources().getColor(R.color.dark));
		    		quizButton.setBackgroundColor(getResources().getColor(R.color.light));
		    		boxListButton.setTextColor(Color.WHITE);
		    		groupListButton.setTextColor(Color.BLACK);
		    		quizButton.setTextColor(Color.BLACK);
		    		toBox();
		    	}
		    }
		});

		
		progressHandler = new Handler() 
		 {

		     public void handleMessage(Message msg1) 
		     {
		    	 if(progDailog.isShowing())
		         {
		    		 progDailog.dismiss();
		    		 toGroup();
		         }
		         	
		         //toGroup();
		         super.handleMessage(msg1);
		     }
		 };
		if(!isLoaded)
			{
			t=new Thread ( new Runnable()
		{
			public void run() {
				
			 	
			    db=new DBManager(getApplicationContext());
				db.open();
				
				list = db.getGroupList();
				savlist=list;
				//isLoaded=true;
				db.close();
				isLoaded=true;   
				//if(progDailog.isShowing())
					//progDailog.dismiss();
				progressHandler.sendEmptyMessage(0);
				//Log.d("DATA","data loaded");
				//toGroup();
					
		    }
		});
		t.start();
			}
			
		}
    private void toQuiz()
    {
		quizIntent=new Intent(getApplicationContext(), Quiz.class);
		quizIntent.putExtra("Title","Quiz");
		startActivity(quizIntent);
		finish();
    }
    
	
	private void toBox()
	{
		listview.setOnItemClickListener(new OnItemClickListener() {
	  @Override
	  public void onItemClick(AdapterView<?> parent, View view,
	    int position, long id) {
			boxlistIntent=new Intent(getApplicationContext(), Box.class);
			boxlistIntent.putExtra("box",boxlist[position]);
			startActivity(boxlistIntent);
		  }
		}); 

		try{		
			listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, boxlist));
			isBoxList=true;
			}
		
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();				
		}
	}
				
	private void toGroup()
	{
		if(!isLoaded){
		progDailog.show();


		}
		
		else
		{
			fillMaps = new ArrayList<HashMap<String, Object>>();
			for(ListItem li:list){
				HashMap<String, Object> map = new HashMap<String, Object>();
				String data=li.getGloss();
				String mean[]=data.split(";");
			    map.put("title",mean[0]);
	
			    fillMaps.add(map);	
			}
			SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.row, from, to);
		    listview.setAdapter(adapter);
		}
		
		
	    
		isBoxList=false;
	
	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long id) {
				
				ListItem li=list.get(position);
				
				try{
					
					Intent ourIntent=new Intent("org.credila.greclub.GROUPDISPLAY");
					ourIntent.putExtra("sid",li.getSID());
					ourIntent.putExtra("gloss",li.getGloss());
					startActivity(ourIntent);
					
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			
		});
				
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.action_about:
			Intent ourIntent=new Intent(this,About.class);
			startActivity(ourIntent);
            return true;
  
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		new AlertDialog.Builder(this)
	    .setTitle("Exit Application")
	    .setMessage("Are you sure you want to exit?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete

				Intent intent = new Intent(Intent.ACTION_MAIN);
			    intent.addCategory(Intent.CATEGORY_HOME);
			    startActivity(intent);
			
	        	
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        	
	        }
	     })
	     .show();

	}

}
