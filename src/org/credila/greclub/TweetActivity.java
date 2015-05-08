package org.credila.greclub;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TweetActivity extends Activity{

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
			Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
		    startActivity(intent);
		    finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	static Twitter twitter1;
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
		
	SharedPreferences mSharedPreferences;	
	    
	static String access_token;
	static String access_token_secret;
	String score;
	
	
	
	EditText tweet_status;
	Button tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet);
	
		Intent intent=getIntent();
	score=intent.getStringExtra("SCORE");
//		getActionBar().setDisplayHomeAsUpEnabled(true);
	mSharedPreferences = getApplicationContext().getSharedPreferences(
				"MyPref", 0);
		access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
		// Access Token Secret
		access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");

		
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		    cb.setDebugEnabled(true)
		          .setOAuthConsumerKey("zqSm9RFufHW52oIZ4X1QQ")
		          .setOAuthConsumerSecret("6DDsgUrxp4yhocu2uz0yghhyQlsdNfjs2LbeUV7X4")
 			  .setOAuthAccessToken(access_token)
	          .setOAuthAccessTokenSecret(access_token_secret);
			System.out.println("success12345  "+access_token);
			    
			

		    TwitterFactory tf = new TwitterFactory(cb.build());
		    twitter1 = tf.getInstance();

		
		
		
		tweet_status=(EditText)findViewById(R.id.editText1);
		tweet=(Button)findViewById(R.id.button1);
		String msg="Wohoo! I completed word quiz in "+score+". Try GRE Club to improve your vocabulary with fun!";
		System.out.println(msg);
		tweet_status.setText(msg);
		
		tweet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String tweet_user=tweet_status.getText().toString();
				
				System.out.println(tweet_user);
				System.out.println("tweet_user");
				try {
					twitter1.updateStatus(tweet_user);
					Toast.makeText(getApplicationContext(), "Status Updated", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
				    startActivity(intent);
					
					
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
				Intent intent = new Intent(TweetActivity.this,SplashScreen.class);
			    startActivity(intent);
			    finish();
	}	
	        	

	
}
