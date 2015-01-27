package com.isep.android.movemoney;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	EditText invitor;
	
	String phonetxt;
	String usernametxt;
	String pwdtxt;
	String confirmationpwdtxt;
	String invitortxt;
	
	double credit_defalt;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.password);
        confirmationpwd = (EditText) findViewById(R.id.confirmationpwd);
        invitor = (EditText) findViewById(R.id.invitor);
 
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
            	invitortxt = invitor.getText().toString();
            	
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
					
				} else if(pwdtxt.length()<6) {
					
					Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters",
							Toast.LENGTH_LONG).show();	
					
				} else if(pwdtxt.matches("[0-9]+") || pwdtxt.matches("[a-zA-Z]+")) {
					
					Toast.makeText(getApplicationContext(),
							"Password must contain numbers and letters",
							Toast.LENGTH_LONG).show();
					
				} else if(usernametxt.contains(" ")) {
					
					Toast.makeText(getApplicationContext(),
							"Space is not allowed in Username",
							Toast.LENGTH_LONG).show();	
					
				} else if(usernametxt.length()>10) {
					
					Toast.makeText(getApplicationContext(),
							"Username must not longer than 10 characters",
							Toast.LENGTH_LONG).show();	
					
				}else {
					
					ParseUser user = new ParseUser();
					user.put("nickname", usernametxt);
					user.setUsername(phonetxt); /*set the phone number as the username, because in Parse the username is unique and is defaultly used in Login function*/
					user.setPassword(pwdtxt);
					
					final ParseObject user_copy = new ParseObject("User_copy");
					final ParseACL acl = new ParseACL();
					acl.setPublicReadAccess(true);
					acl.setPublicWriteAccess(true);
					
					if(!invitortxt.equals("")){
					
						ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
						query.whereEqualTo("username", invitortxt);
						query.getFirstInBackground(new GetCallback<ParseObject>() {
							
							@Override
							 public void done(ParseObject userData, ParseException e) {
								// TODO Auto-generated method stub
								if(userData != null) {
									
									double credit = userData.getDouble("credit");
									double credit_new = credit + 50;
									userData.put("credit", credit_new);
									userData.saveInBackground();
									user_copy.put("invitation_code", invitortxt);
									user_copy.put("credit", credit_defalt + 50);
									user_copy.put("nickname", usernametxt);
									user_copy.put("username", phonetxt);
									user_copy.setACL(acl);
									user_copy.saveInBackground();
									
								} else {
									
									Log.d("App", "Error: " + e.getMessage());
									Toast.makeText(getApplicationContext(),
											"There is no such an invitor!!!",
											Toast.LENGTH_LONG).show();
									
								}
							}
							
						});
					
					} else {
						
						user_copy.put("invitation_code", "");
						user_copy.put("credit", credit_defalt);
						user_copy.put("nickname", usernametxt);
						user_copy.put("username", phonetxt);
						user_copy.setACL(acl);
						user_copy.saveInBackground();
						
					}
					
					
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
