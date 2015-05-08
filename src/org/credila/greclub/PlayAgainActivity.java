package org.credila.greclub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayAgainActivity extends Activity {

	Button home;
	Button share;
	String score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_again);
		Intent intent=getIntent();
		score=intent.getStringExtra("SCORE").toString();
		TextView textView=(TextView)findViewById(R.id.textView1);
		textView.setText("Your score: "+score);
		Button button=(Button)findViewById(R.id.button1);
		home=(Button)findViewById(R.id.button2);
		share=(Button)findViewById(R.id.button3);
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PlayAgainActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PlayAgainActivity.this,TwitterActivity.class);
				intent.putExtra("SCORE", score);
				startActivity(intent);
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PlayAgainActivity.this,Quiz.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_again, menu);
		return true;
	}

	public void startQuiz(View view)
	{
		Intent intent=new Intent(this,Quiz.class);
		startActivity(intent);
		
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent intent= new Intent(PlayAgainActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	
}
