package org.credila.greclub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class Quiz extends Activity implements Animation.AnimationListener{
	Animation fadeout;
	Animation fadewrong;
	Animation fadein;
	private GridviewAdapter mAdapter;
	String clicked;
	boolean state = false, word;
	List<String> words = new ArrayList<String>();
	List<String> meaning = new ArrayList<String>();
	List<String> data;
	GridView gv;
	Intent scoreIntent;
	int quizDuration = 0;
	View preView;
	int loc = 0, index;
	private long startTime = 0L;
	private Handler customHandler = new android.os.Handler();
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
		       final long start = startTime;
		       long millis = System.currentTimeMillis() - start;
		       int seconds = (int) (millis / 1000);
		       int minutes = seconds / 60;
		       seconds     = seconds % 60;
		       String time=((minutes==0)?"":minutes+":")+((seconds==0)?"":seconds+":")+(millis-(seconds*1000))/100;
		          setTitle(time);            
		       customHandler.postDelayed(mUpdateTimeTask, 100);
		   }
		};
//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_row);
		setContentView(R.layout.main);
		  fadeout= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
		  fadewrong= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_wrong);
		  fadein= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
	     
		Intent intent = getIntent();
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		String boxlist[] = { "HF", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		if (startTime == 0L) {
            startTime = System.currentTimeMillis();
            customHandler.postDelayed(mUpdateTimeTask, 100);
       }

		try {
			DBManager db = new DBManager(this);
			db.open();
			data = db.read_random(boxlist[0]);
			System.out.println(data);

			for (loc = 0; loc < data.size(); loc++, loc++) {
				words.add(data.get(loc));
				meaning.add(data.get(loc + 1));
			}
			Collections.shuffle(data);
			mAdapter = new GridviewAdapter(this, data);
			gv = (GridView) findViewById(R.id.gridView1);
			gv.setBackgroundColor(Color.BLACK);
			gv.setAdapter(mAdapter);
//
//			TextView t1=(TextView)findViewById(R.id.textView1);
//			TextView t2=(TextView)findViewById(R.id.textView2);
//			TextView t3=(TextView)findViewById(R.id.textView3);
//			TextView t4=(TextView)findViewById(R.id.textView4);
//			TextView t5=(TextView)findViewById(R.id.textView5);
//			TextView t6=(TextView)findViewById(R.id.textView6);
//			TextView t7=(TextView)findViewById(R.id.textView7);
//			TextView t8=(TextView)findViewById(R.id.textView8);
//			if(t1!=null)t1.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t2!=null)t2.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t3!=null)t3.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t4!=null)t4.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t5!=null)t5.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t6!=null)t6.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t7!=null)t7.setBackgroundColor(getResources().getColor(R.color.dark));
//			if(t8!=null)t8.setBackgroundColor(getResources().getColor(R.color.dark));
			gv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, final View v,
						int position, long id) {

					clicked = ((TextView) v.findViewById(R.id.textView1))
							.getText().toString();

					if (state == true) {
						if (word == true) {
							if (clicked.compareToIgnoreCase(meaning.get(index)) == 0) {
//								Toast.makeText(getApplicationContext(), "True",
//										Toast.LENGTH_SHORT).show();
								preView.setBackgroundColor(getResources()
										.getColor(R.color.green));
								v.setBackgroundColor(getResources()
										.getColor(R.color.green));
										v.startAnimation(fadeout);
								 preView.startAnimation(fadeout);
							v.setVisibility(View.INVISIBLE);
								preView.setVisibility(View.INVISIBLE);
								quizDuration++;
							} else {
								preView.setBackgroundColor(getResources()
										.getColor(R.color.dark));
								
								v.setBackgroundColor(getResources()
										.getColor(R.color.dark));
							}

						} else if (word == false) {
							if (clicked.compareToIgnoreCase(words.get(index)) == 0) {
//								Toast.makeText(getApplicationContext(), "True",
//										Toast.LENGTH_SHORT).show();
								preView.setBackgroundColor(getResources()
										.getColor(R.color.green));
								
								v.setBackgroundColor(getResources()
										.getColor(R.color.green));
								 v.startAnimation(fadeout);
								 preView.startAnimation(fadeout);
								
								v.setVisibility(View.INVISIBLE);
								preView.setVisibility(View.INVISIBLE);
								quizDuration++;
							} else {

								preView.setBackgroundColor(getResources()
										.getColor(R.color.dark));
								
								v.setBackgroundColor(getResources()
										.getColor(R.color.dark));
							}
						}
						state = false;
					} else if (state == false) {
						v.setBackgroundColor(getResources().getColor(
								R.color.blue));
						
						preView = v;
						for (int i = 0; i < words.size(); i++) {
							if (clicked.compareToIgnoreCase(words.get(i)) == 0) {
								index = i;
								word = true;
								System.out.println(word + " It is a word " + i
										+ " " + state);
								break;
							}
							if (clicked.compareToIgnoreCase(meaning.get(i)) == 0) {
								index = i;
								word = false;
								System.out.println(word + " It is meaning" + i
										+ " " + state);
								break;
							}
						}
						state = true;
					}
					if (quizDuration == 5) {
						
						final long start = startTime;
						long millis = System.currentTimeMillis() - start;
					    int seconds = (int) (millis / 1000);
					    int minutes = seconds / 60;
					    seconds     = seconds % 60;
					    String time=((minutes==0)?"":minutes+":")+((seconds==0)?"":seconds+":")+(millis-(seconds*1000))/10;
						customHandler.removeCallbacks(mUpdateTimeTask);
						Intent intent=new Intent(Quiz.this,PlayAgainActivity.class);
						intent.putExtra("SCORE", time);
						startActivity(intent);
						finish();
					}
					
				}

			});

			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        Intent intent=new Intent(Quiz.this,MainActivity.class);
	        startActivity(intent);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	@Override
    public void onAnimationStart(Animation animation) {
 
    }
 
    @Override
    public void onAnimationEnd(Animation animation) {

    }
 
    @Override
    public void onAnimationRepeat(Animation animation) {
 
    }
    

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		 Intent intent=new Intent(Quiz.this,MainActivity.class);
	        startActivity(intent);
	        finish();
	        
	}

}
