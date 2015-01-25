package com.isep.android.movemoney;

import java.util.List;

import com.parse.FindCallback;
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

public class Send_Fragment extends Fragment {
	View rootview;
	
	EditText send_number;
	EditText send_amount;
	
	String send_numbertxt;
	double send_amounttxt;
	
	Button btn_send;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.send_layout, container, false);
		
		send_number = (EditText) rootview.findViewById(R.id.send_target);
		send_amount = (EditText) rootview.findViewById(R.id.send_amount);
		
		btn_send = (Button) rootview.findViewById(R.id.send_button);
		
		btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				send_numbertxt = send_number.getText().toString();
				send_amounttxt = Double.parseDouble(send_amount.getText().toString());
				
				if(send_amounttxt > 0) {
					
					final ParseObject process = new ParseObject("Process");
					
					process.put("money_situation", "positive");
					process.put("process_situation", "unfinish");
					process.put("type", "send");
					process.put("process_credit", send_amounttxt);
					
					ParseUser user = ParseUser.getCurrentUser();
					 
					process.put("user1", user.getString("nickname"));
					process.put("phonenumber1", user.getString("username"));  //this part is to store the data of Sender in Parse
					//process.put("parent1", user);                           
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
					query.whereEqualTo("username", send_numbertxt);
					query.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> userList,ParseException e) {
							// TODO Auto-generated method stub
							if(userList.size()!= 0) {
								
								ParseObject userData = userList.get(0);
								//process.put("parent2", userData.getObjectId());
								process.put("user2", userData.getString("nickname"));
								process.put("phonenumber2", send_numbertxt);
								//process.put("user2", "test");
								process.saveInBackground();
								
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
