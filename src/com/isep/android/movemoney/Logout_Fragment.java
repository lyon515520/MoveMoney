package com.isep.android.movemoney;

import com.parse.ParseUser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Logout_Fragment extends Fragment {
	
	View rootview;
	

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.login, container, false);
		ParseUser.logOut();
		//to the login page
		Intent i = new Intent(getActivity(), LoginActivity.class);
		startActivity(i);
	    //stop the current activity
	    getActivity().finish();
	    //alert
		
		
		
	    Toast.makeText(getActivity(),
				"Successfully Logged out",
				Toast.LENGTH_LONG).show();
		return rootview;
	}

}
