package com.isep.android.movemoney;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile_Fragment extends Fragment {
	View rootview;
	
	TextView usernametv;
	TextView phonenumbertv;
	TextView credittv;
	
	Button btn_profile_username;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.profile_layout, container, false);
		
		ParseUser user = ParseUser.getCurrentUser();
		//String username = user.getString("nickname");
		String phonenumber_current = user.getUsername();
		//double credit = user.getDouble("credit");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
		query.whereEqualTo("username", phonenumber_current);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject userData, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null) {
					
					double credit = userData.getDouble("credit");
					String username = userData.getString("nickname");
					String phonenumber = userData.getString("username");
					
					usernametv = (TextView) rootview.findViewById(R.id.profile_username);
					phonenumbertv = (TextView) rootview.findViewById(R.id.profile_phonenumber);
					credittv = (TextView) rootview.findViewById(R.id.profile_credit);
					btn_profile_username = (Button) rootview.findViewById(R.id.btn_profile_username);
					
					usernametv.setText(username);
					phonenumbertv.setText(phonenumber);
					credittv.setText(String.valueOf(credit)+" euros");
					
					btn_profile_username.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							
							LayoutInflater factory=LayoutInflater.from(getActivity());
							final View view = factory.inflate(R.layout.modify_alertdialog_username,null);
							
							new AlertDialog.Builder(getActivity())
								.setTitle("Info")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(view)
								.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										EditText username_new;
										final String username_newtxt;
										
										username_new = (EditText) view.findViewById(R.id.new_username);
										username_newtxt = username_new.getText().toString();
										
										if(!username_newtxt.equals("")) {
											
											ParseUser user = ParseUser.getCurrentUser();
											String phonenumber_current = user.getUsername();
											
											ParseQuery<ParseObject> query1 = ParseQuery.getQuery("User_copy");
											query1.whereEqualTo("username", phonenumber_current);
											query1.getFirstInBackground(new GetCallback<ParseObject>() {
												
												@Override
												 public void done(ParseObject userData, ParseException e) {
													
													userData.put("nickname", username_newtxt);
													userData.saveInBackground();
													
												}
												
											});//this means to change the table User_copy
											
											user.put("nickname", username_newtxt);
											user.saveInBackground();   //this means to change the table User
											
										} else if(username_newtxt.length()>10) {
											
											Toast.makeText(getActivity().getApplicationContext(),
													"Username must not longer than 10 characters",
													Toast.LENGTH_LONG).show();	
										} else {
											
											Toast.makeText(getActivity().getApplicationContext(),
													"Username connot be empty!",
													Toast.LENGTH_LONG).show();	
											
										}
										
									}
									
									
									
								})
								.setNegativeButton("No", null).show();
							
						}
						
						
						
					});
					
				} else {
					
					Log.d("App", "Error: " + e.getMessage());
					
				}
			}
			
		});
		
		return rootview;
	}
	
}
