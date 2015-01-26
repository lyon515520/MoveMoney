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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile_Fragment extends Fragment {
	View rootview;
	
	TextView usernametv;
	TextView phonenumbertv;
	TextView credittv;
	
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
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null) {
					
					ParseObject userData = userList.get(0);
					double credit = userData.getDouble("credit");
					String username = userData.getString("nickname");
					String phonenumber = userData.getString("username");
					
					usernametv = (TextView) rootview.findViewById(R.id.profile_username);
					phonenumbertv = (TextView) rootview.findViewById(R.id.profile_phonenumber);
					credittv = (TextView) rootview.findViewById(R.id.profile_credit);
					
					usernametv.setText(username);
					phonenumbertv.setText(phonenumber);
					credittv.setText(String.valueOf(credit)+" euros");
					
				} else {
					
					// to do the code here
					
				}
			}
			
		});
		
		return rootview;
	}
	
}
