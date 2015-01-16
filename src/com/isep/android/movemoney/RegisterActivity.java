package com.isep.android.movemoney;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	Button btnregister;
	
	EditText phone;
	EditText username;
	EditText pwd;
	EditText confirmationpwd;
	EditText invitationcode;
	
	String phonetxt;
	String usernametxt;
	String pwdtxt;
	String confirmationpwdtxt;
	String invitationcodetxt;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.password);
        confirmationpwd = (EditText) findViewById(R.id.confirmationpwd);
        invitationcode = (EditText) findViewById(R.id.invitationcode);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
        
        //setting access to activity_main.xml
        TextView mainScreen = (TextView) findViewById(R.id.btnRegister);
        
        //Listening to register button
        mainScreen.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View arg0) {
            	//update user data to Parse
            	phonetxt = phone.getText().toString();
            	usernametxt = username.getText().toString();
            	pwdtxt = pwd.getText().toString();
            	confirmationpwdtxt = confirmationpwd.getText().toString();
            	invitationcodetxt = invitationcode.getText().toString();
            	
            	// Force user to fill up the form
				if (phonetxt.equals("") && usernametxt.equals("") && pwdtxt.equals("") && confirmationpwdtxt.equals("")) {
					
					Toast.makeText(getApplicationContext(),
							"Please complete the forms with the star",
							Toast.LENGTH_LONG).show();
				
				} else if(!pwdtxt.equals(confirmationpwdtxt)) {
					
					Toast.makeText(getApplicationContext(),
							"Please confirm your password",
							Toast.LENGTH_LONG).show();
				
				} else if(phonetxt.length()!= 10 || !(phonetxt.startsWith("06") || phonetxt.startsWith("07"))) {
					
					Toast.makeText(getApplicationContext(),
							"Please confirm your phone number",
							Toast.LENGTH_LONG).show();
					
				} else if(pwd.length()<7) {
					
					Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters",
							Toast.LENGTH_LONG).show();	
					
				} else if(usernametxt.contains(" ")) {
					
					Toast.makeText(getApplicationContext(),
							"Space is not allowed in Username",
							Toast.LENGTH_LONG).show();	
					
				}else {
					
					ParseUser user = new ParseUser();
					user.put("nickname", usernametxt);
					user.setUsername(phonetxt); /*set the phone number as the username, because in Parse the username is unique and is defaultly used in Login function*/
					user.setPassword(pwdtxt);
					user.put("invitation_code", invitationcodetxt);
					
					user.signUpInBackground(new SignUpCallback() {
						
						public void done(ParseException e) {
							
							if (e == null) {
								
								// Show a simple Toast message upon successful registration
								Toast.makeText(getApplicationContext(),
										"Successfully Signed up!",
										Toast.LENGTH_LONG).show();
								
								// Switching to Main screen
								Intent i = new Intent(getApplicationContext(), MainActivity.class);
				                startActivity(i);
								
							} else {
								
								Toast.makeText(getApplicationContext(),
										"Sign up Error", Toast.LENGTH_LONG)
										.show();
								
							}
							
						}
						
					});
					
				}
            }
        });
    }
	
}
