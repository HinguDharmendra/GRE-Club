package org.credila.greclub;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputForm extends Activity{

	SharedPreferences myPrefs;
	SharedPreferences.Editor prefsEditor;
	
	EditText name,age,email,contact, city, course, country;
	Button sub;
	String Sname,Sage,Semail,Scontact,Scity,Scourse,Scountry;
	Button splash;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			   StrictMode.setThreadPolicy(policy);
			}
		splash=(Button)findViewById(R.id.button2);
		splash.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(InputForm.this,SplashScreen.class);
				startActivity(intent);
			}
		});
		myPrefs = this.getSharedPreferences("myPrefs",MODE_WORLD_READABLE);
        prefsEditor = myPrefs.edit();
        //prefsEditor.putString("info", "false");
        //prefsEditor.commit();
        String prefName = myPrefs.getString("info", "false");

        if(prefName=="true")
        {
        	Intent intent=new Intent(this,SplashScreen.class);
    		startActivity(intent);
        	
        }
        else
        {
        
		name=(EditText)findViewById(R.id.editText1);
		age=(EditText)findViewById(R.id.editText2);
		email=(EditText)findViewById(R.id.editText4);
		contact=(EditText)findViewById(R.id.editText3);
		city=(EditText)findViewById(R.id.editText5);
		course=(EditText)findViewById(R.id.editText6);
		country=(EditText)findViewById(R.id.editText7);
		
		sub=(Button)findViewById(R.id.button1);
		
		sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if((name.getText().toString().trim().equals("")) 
						|| (age.getText().toString().trim().equals("")) 
						|| (contact.getText().toString().trim().equals("")) ||
						(email.getText().toString().trim().equals("")) || (city.getText().toString().trim().equals("")) ||
						(course.getText().toString().trim().equals("")) || (country.getText().toString().trim().equals("")))
				{
				    Toast.makeText(getApplicationContext(), "Please fill the remaining fields", Toast.LENGTH_SHORT).show();
			
					
				}
				else{

					Sname = name.getText().toString();
					Sage = age.getText().toString();
					Scontact = contact.getText().toString();
					Semail = email.getText().toString();
					Scity = city.getText().toString();
					Scourse = course.getText().toString();
					Scountry = country.getText().toString();
					System.out.println(Sname);
					System.out.println(Sage);
					System.out.println(Scontact);
					System.out.println(Semail);
					System.out.println(Scity);
					System.out.println(Scountry);
					System.out.println(Scourse);
					
					
					HttpClient httpclient = new DefaultHttpClient();
					String data=""+"http://messi14.byethost7.com/info.php?name="+Sname+"&age="+Sage+"&contact="+Scontact+"&email="+Semail+"&city="+Scity+"&course="+Scourse+"&country="+Scountry+"";
					String data1="http://messi14.byethost7.com/info.php?name=pawan&age=34&contact=9821350649&email=singh.maharshi@gmail.com&city=Mumbai&course=MS&country=US";
					System.out.println(data);
					System.out.println(data1);
					HttpGet httpget = new HttpGet(data);
					try {
					    HttpResponse response = httpclient.execute(httpget);
					    System.out.println("Sent data");
					    if(response != null) {
					       // String line = "";
				
					    	//InputStream inputstream = response.getEntity().getContent();
					        //line = convertStreamToString(inputstream);
					        Toast.makeText(getApplicationContext(),"Info Updated", Toast.LENGTH_SHORT).show();

					        prefsEditor.putString("info", "true");
					        prefsEditor.commit();
					        
					        
					        Intent i=new Intent(getApplicationContext(),SplashScreen.class);
					        startActivity(i);
					    } else {
					        Toast.makeText(getApplicationContext(), "Unable to complete your request", Toast.LENGTH_LONG).show();
					        Intent i=new Intent(getApplicationContext(),SplashScreen.class);
					        startActivity(i);
					    }
					} catch (ClientProtocolException e) {
					    Toast.makeText(getApplicationContext(), "Caught ClientProtocolException", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
					    Toast.makeText(getApplicationContext(), "Caught IOException", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
					    Toast.makeText(getApplicationContext(), "Caught Exception", Toast.LENGTH_SHORT).show();
					    e.printStackTrace();
					}
		

				}
				
						
			}
		});
		
	
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
