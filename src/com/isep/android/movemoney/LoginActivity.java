package com.isep.android.movemoney;

import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	Button btnlogin;
	
	TextView btntoregister;
	
	EditText loginphone;
	EditText loginpwd;
	
	String loginphonetxt;
	String loginpwdtxt;
	
	SessionManager session;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
 
        btntoregister = (TextView) findViewById(R.id.link_to_register);
 
        // Listening to register new account link
        btntoregister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        
        //setting access to activity_main.xml
        btnlogin = (Button) findViewById(R.id.btnLogin);
        loginphone = (EditText) findViewById(R.id.loginphone);
		loginpwd = (EditText) findViewById(R.id.loginpwd);
        
        //Listening to login button
        btnlogin.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View v) {
            	
            	loginphonetxt = loginphone.getText().toString();
            	loginpwdtxt = loginpwd.getText().toString();

            	session = new SessionManager(getApplicationContext());

            	ParseUser.logInInBackground(loginphonetxt, loginpwdtxt,
            			new LogInCallback() {
            		
            		public void done(ParseUser user, ParseException e) {
            			
            			if (user != null) {
							// If user exist and authenticated, send user to MainActivity.class
            				session.createUserSession(loginpwdtxt);
							Intent i = new Intent(
									LoginActivity.this,
									MainActivity.class);
							startActivity(i);
							Toast.makeText(getApplicationContext(),
									"Successfully Logged in",
									Toast.LENGTH_LONG).show();
							finish();
						} else {
							Toast.makeText(
									getApplicationContext(),
									"No such user exist, please signup",
									Toast.LENGTH_LONG).show();
						}
            			
            		}
            		
            	});
            }
        });
    }

}
