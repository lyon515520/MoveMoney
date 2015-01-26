package com.isep.android.movemoney;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") 
public class Account_Fragment extends Fragment {
	View rootview;
	
	TextView credit_currenttv;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		rootview = inflater.inflate(R.layout.account_layout, container, false);
		
		InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		ParseUser user = ParseUser.getCurrentUser();
		String phonenumber_current = user.getUsername();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
		query.whereEqualTo("username", phonenumber_current);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> userList, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null) {
					
					ParseObject userData = userList.get(0);
					double credit = userData.getDouble("credit");
					
					credit_currenttv = (TextView) rootview.findViewById(R.id.account_credit);
					
					credit_currenttv.setText(String.valueOf(credit)+" euros");
					
				} else {
					
					// to do the code here
					
				}
			}
			
		});
		
		GetItems_Account gt_account = new GetItems_Account();
		ArrayList<HashMap<String, Object>> items = gt_account.getItems_Account();
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_account);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.account_single, 
                new String[] {
			
					"accountlist_username",
					"accountlist_credit",
					"accountlist_phonenumber",
					"accountlist_date",
					"accountlist_index"
				
				}, 
                
				new int[] {
			
					R.id.accountlist_username, 
					R.id.accountlist_credit, 
					R.id.accountlist_phonenumber, 
					R.id.accountlist_date,
					R.id.accountlist_index
				
				}
		
		);
		
		adapter.notifyDataSetChanged();
		
		list.setAdapter(adapter);
		
		//list.invalidateViews();
		
		return rootview;
	
	}
	
}