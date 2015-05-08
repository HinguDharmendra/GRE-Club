package org.credila.greclub;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class TwitterActivity extends Activity {	
	
	SharedPreferences Prefs;
	SharedPreferences.Editor prefsEditor;
	
	static String TWITTER_CONSUMER_KEY ="zqSm9RFufHW52oIZ4X1QQ";
    
	static String TWITTER_CONSUMER_SECRET ="6DDsgUrxp4yhocu2uz0yghhyQlsdNfjs2LbeUV7X4";

	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

	Button btnLoginTwitter;

	// Twitter
	private static Twitter twitter;
	private static RequestToken requestToken;
	
	// Shared Preferences
	private static SharedPreferences mSharedPreferences;
	
	// Internet Connection detector
//	private ConnectionDetector cd;
	
	// Alert Dialog Manager
//	AlertDialogManager alert = new AlertDialogManager();

	String verifier;

	
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
	String score;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_twitter);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Prefs = this.getSharedPreferences("Prefs",MODE_WORLD_READABLE);
        prefsEditor = Prefs.edit();
		
		Intent intent=getIntent();
		score=intent.getStringExtra("SCORE");
        if(score!=null)
		prefsEditor.putString("timer",score);
        prefsEditor.commit();

		//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		
		// Check if twitter keys are set
		if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
			// Internet Connection is not present
			//alert.showAlertDialog(MainActiv\ity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
			// stop executing code by return
			return;
		}

		// All UI elements
		btnLoginTwitter = (Button) findViewById(R.id.twitter_login);
				
	  
		// Shared Preferences
		mSharedPreferences = getApplicationContext().getSharedPreferences(
				"MyPref", 0);
		boolean t = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN,false);
		
		if( t == true )
		{
			String prefName = Prefs.getString("timer","0");
			Intent i=new Intent(TwitterActivity.this,TweetActivity.class);
			i.putExtra("SCORE", prefName);
			startActivity(i);
			}
				
		btnLoginTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loginToTwitter();
			}
		});

		
		if (!isTwitterLoggedInAlready()) {
			Log.d("Checking1","Checking") ; 
			Uri uri = getIntent().getData();
			if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
				// oAuth verifier
				String verifier = uri
						.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

				try {
					// Get the access token
					AccessToken accessToken = twitter.getOAuthAccessToken(
							requestToken, verifier);
					Log.d("Checking2","Checking") ; 
					// Shared Preferences
					Editor e = mSharedPreferences.edit();

					// After getting access token, access token secret
					// store them in application preferences
					e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
					e.putString(PREF_KEY_OAUTH_SECRET,
							accessToken.getTokenSecret());

					
					// Store login status - true
					e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
					e.commit(); // save changes

					Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

					// Hide login button
					btnLoginTwitter.setVisibility(View.GONE);
					Log.d("Checking3","Checking") ; 
					
					String prefName = Prefs.getString("timer","0");
					
					Intent i=new Intent(this,TweetActivity.class);
					i.putExtra("SCORE", prefName);
					startActivity(i);
					
					
					// Show Update Twitter
					//lblUpdate.setVisibility(View.VISIBLE);
					
					//followers.setVisibility(View.VISIBLE);
					//getfriends.setVisibility(View.VISIBLE);
					//btnLogoutTwitter.setVisibility(View.VISIBLE);
					
					// Getting user details from twitter
					// For now i am getting his name only
					//userID = accessToken.getUserId();
					//User user = twitter.showUser(userID);
					//String username = user.getName();
					
					// Displaying in xml ui
					//lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
				} catch (Exception e) {
					// Check log for login errors
					Log.e("Twitter Login Error", "> " + e.getMessage());
				}
			}
		}

	}

	/**
	 * Function to login twitter
	 * */
	private void loginToTwitter() {
		// Check if already logged in
		if (!isTwitterLoggedInAlready()) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			Configuration configuration = builder.build();
			
			TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter
						.getOAuthRequestToken(TWITTER_CALLBACK_URL);
				this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(requestToken.getAuthenticationURL())));
		
				} 
			catch (Exception e) {
											// Check log for login errors
											Log.e("Twitter Login Error", "> " + e.getMessage());
										}
							    	}
							     								
					else 
					{
			 //user already logged into twitter
			Toast.makeText(getApplicationContext(),
					"Already Logged into twitter", Toast.LENGTH_LONG).show();
		}
		
	}

	
	/**
	 * Function to logout from twitter
	 * It will just clear the application shared preferences
	 * */
	
	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
				Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
			    startActivity(intent);
			    finish();
	}	
	        	
}


