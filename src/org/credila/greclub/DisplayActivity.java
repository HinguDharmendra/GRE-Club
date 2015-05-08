package org.credila.greclub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

;
public class DisplayActivity extends Activity implements
		TextToSpeech.OnInitListener {
	public static String word;
	public int id;
	TextView wordview;
	TextView greview;
	Button btnSpeak;
	int max;
	int min;
	private TextToSpeech tts;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        Intent intent=new Intent(DisplayActivity.this,Box.class);
	        intent.putExtra("box", ""+word.charAt(0));
	        startActivity(intent);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		word = intent.getStringExtra("ID");
		max = intent.getIntExtra("maxwid", -1);
		min = intent.getIntExtra("minwid", -1);
		setContentView(R.layout.activity_display);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		wordview = (TextView) findViewById(R.id.word);
		greview = (TextView) findViewById(R.id.gremeaning);
		tts = new TextToSpeech(this, this);
		final DBManager db = new DBManager(this);
		db.open();
		id = Integer.parseInt(db.getID(word));
		db.close();
		setMeaning(word);
		Button b = (Button) findViewById(R.id.bview);
		Button b1 = (Button) findViewById(R.id.next1);
		Button b2 = (Button) findViewById(R.id.prev1);
		btnSpeak = (Button) findViewById(R.id.tts);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent("org.credila.greclub.VIEWWORDLIST");
				// i.putExtra("word",word);
				startActivity(i);
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("NEXT", "NEXT");
				if (id == max)
					id = min;
				else
					id = id + 1;
				db.open();
				word = db.getwd(String.valueOf(id));
				setMeaning(word);
				db.close();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("PREV", "PREV");
				if (id == min)
					id = max;
				else
					id = id - 1;
				db.open();
				word = db.getwd(String.valueOf(id));
				setMeaning(word);
				db.close();
			}
		});
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation fadewrong= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_wrong);
				btnSpeak.startAnimation(fadewrong);
				speakOut();
				Animation fadein= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
				btnSpeak.startAnimation(fadein);
			}

		});

	}

	public void setMeaning(String word) {
		final ListView listview = (ListView) findViewById(R.id.meanings);
		DBManager db = new DBManager(this);
		db.open();
		setTitle(word);
		wordview.setText(word);
		String gremeaning = db.getGREMeaning(word);
		greview.setText(gremeaning);

		List<String[]> data = db.getWord(word);
		db.close();

		List<String> meaning = new ArrayList<String>();
		Iterator<String[]> itr = data.iterator();
		String[] item;

		while (itr.hasNext()) {
			item = itr.next();
			item[1] = item[1].replace("; " + '\"', ";\n" + '\"');
			meaning.add(item[0] + "  " + item[1]);

		}

		listview.setAdapter(new ArrayAdapter<String>(DisplayActivity.this,
				android.R.layout.simple_list_item_1, meaning));

	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.v("TTS", "This Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	private void speakOut() {
		// TODO Auto-generated method stub

		String text = wordview.getText().toString();

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		   Intent intent=new Intent(DisplayActivity.this,Box.class);
	        intent.putExtra("box", ""+word.charAt(0));
	        startActivity(intent);
	        finish();

	}
}
