package com.isep.android.movemoney;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Demande_Fragment extends Fragment {
	View rootview;
	
	EditText demande_number;
	EditText demande_amount;
	
	String demande_numbertxt;
	double demande_amounttxt;
	
	Button btn_demande;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.demande_layout, container, false);
		
		demande_number = (EditText) rootview.findViewById(R.id.demande_target);
		demande_amount = (EditText) rootview.findViewById(R.id.demande_amount);
		
		btn_demande = (Button) rootview.findViewById(R.id.demande_button);
		
		btn_demande.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				demande_numbertxt = demande_number.getText().toString();
				demande_amounttxt = Double.parseDouble(demande_amount.getText().toString());
				
				if(demande_amounttxt > 0) {
					
					final ParseObject process = new ParseObject("Process");
					
					process.put("money_situation", "positive");
					process.put("process_situation", "unfinish");
					process.put("type", "demande");
					process.put("process_credit", demande_amounttxt);
					
					ParseUser user = ParseUser.getCurrentUser();
					 
					process.put("user1", user.getString("nickname"));
					process.put("phonenumber1", user.getString("username"));  //this part is to store the data of Demander in Parse
					//process.put("parent1", user);                           
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
					query.whereEqualTo("username", demande_numbertxt);
					query.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> userList,ParseException e) {
							// TODO Auto-generated method stub
							if(e == null) {
								
								try {
									
									ParseObject userData = userList.get(0);
									//process.put("parent2", userData.getObjectId());
									process.put("user2", userData.getString("nickname"));
									process.put("phonenumber2", demande_numbertxt);
									//process.put("user2", "test");
									
									ParseACL acl = new ParseACL();
									acl.setPublicReadAccess(true);
									acl.setPublicWriteAccess(true);
									
									process.setACL(acl);
									
									process.saveInBackground();
									
								} catch(IndexOutOfBoundsException r) {
									
									Toast.makeText(getActivity().getApplicationContext(),
											"Enter a correct mobile number!", Toast.LENGTH_LONG)
											.show();
									
								}
								
							} else {
								
								Log.d("App", "Error: " + e.getMessage());
								
							}
						}
					});
					
					
					
					Toast.makeText(getActivity().getApplicationContext(),
							"Transfere successfully", Toast.LENGTH_LONG)
							.show();
					
				} else {
					
					Toast.makeText(getActivity().getApplicationContext(),
							"Please enter a correct number", Toast.LENGTH_LONG)
							.show();
					
				}
				
				
			}
			
			
			
		});
		
		
		
		return rootview;
	}
	
}
