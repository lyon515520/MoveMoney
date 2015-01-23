package com.isep.android.movemoney;

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
		String username = user.getString("nickname");
		String phonenumber = user.getUsername();
		float credit = user.getInt("credit");
		
		usernametv = (TextView) rootview.findViewById(R.id.profile_username);
		phonenumbertv = (TextView) rootview.findViewById(R.id.profile_phonenumber);
		credittv = (TextView) rootview.findViewById(R.id.profile_credit);
		
		usernametv.setText(username);
		phonenumbertv.setText(phonenumber);
		credittv.setText(String.valueOf(credit)+" euros");
		
		return rootview;
	}
	
}
