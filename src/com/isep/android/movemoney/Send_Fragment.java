package com.isep.android.movemoney;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Send_Fragment extends Fragment {
	View rootview;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.send_layout, container, false);
		
		String message = "this is just a test";
		TextView tv = (TextView) rootview.findViewById(R.id.send_test);
		tv.setTextSize(40);
		tv.setText(message);
		
		
		
		return rootview;
	}
	
}
