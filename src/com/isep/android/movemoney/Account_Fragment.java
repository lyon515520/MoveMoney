package com.isep.android.movemoney;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Account_Fragment extends Fragment {
	View rootview;
	TextView test;
	
	/*public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		String message = "this is just a test";
		TextView tv = (TextView) getView().findViewById(R.id.account_test);
		tv.setTextSize(40);
		tv.setText(message);
		setContentView(tv);
		
	}
	*/
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		/*
		String message = "this is just a test";
		TextView tv = (TextView) getView().findViewById(R.id.account_test);
		tv.setTextSize(40);
		tv.setText(message);
		setContentView(tv);
		*/
		
		rootview = inflater.inflate(R.layout.account_layout, container, false);
		return rootview;
	
	}
	
}
