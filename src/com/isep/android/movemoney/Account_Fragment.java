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
	double credit_current;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		rootview = inflater.inflate(R.layout.account_layout, container, false);
		
		InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		ParseUser user = ParseUser.getCurrentUser();
		credit_current = user.getDouble("credit");
		
		credit_currenttv = (TextView) rootview.findViewById(R.id.account_credit);
		
		credit_currenttv.setText(String.valueOf(credit_current)+" euros");
		
		ArrayList<HashMap<String, Object>> items = getItems();
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_account);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.account_single, 
                new String[] {
			
					"accountlist_username",
					"accountlist_credit",
					"accountlist_phonenumber",
					"accountlist_date",
					"accountlist_phonenumber_receiver",
					"alertlist_money_situation"
				
				}, 
                
				new int[] {
			
					R.id.accountlist_username, 
					R.id.accountlist_credit, 
					R.id.accountlist_phonenumber, 
					R.id.accountlist_date,
					R.id.accountlist_phonenumber_receiver,
					R.id.accountlist_money_situation
				
				}
		
		);
		
		adapter.notifyDataSetChanged();
		
		list.setAdapter(adapter);
		
		//list.invalidateViews();
		
		return rootview;
	
	}
	
	private ArrayList<HashMap<String, Object>> getItems() {
        final ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
            
        	String currentUsername;
        	currentUsername = ParseUser.getCurrentUser().getString("username");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Process");
	        //query.whereEqualTo("phonenumber2",currentUsername);
	        query.whereEqualTo("process_situation", "finish");
	        query.addDescendingOrder("updatedAt");
	        
	        query.findInBackground(new FindCallback<ParseObject>() {

	            @Override
	            public void done(List<ParseObject> objects, ParseException e) {
	                if (e == null) {
	                	
	                	for(int i = 0; i<objects.size(); i++){
	                		
	                		ParseObject test = objects.get(i);
	                		HashMap<String, Object> map = new HashMap<String, Object>();
	                		
	                		String name = test.getString("user1");
	                		double credit = test.getDouble("process_credit");
	                		String phonenumber = test.getString("phonenumber1");
	                		
	                		Date recharge_date = test.getUpdatedAt();
	                		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	                		String recharge_date_string = df.format(recharge_date);
	                		
	                		String money_situation = test.getString("money_situation");
	                		String money_situation_symbol;
	                		
	                		String name_starter = test.getString("user2");
	                		String phonenumber_receiver = test.getString("phonenumber2");
	                		
	                		if(money_situation.equals("positive")){
	                			
	                			money_situation_symbol = "+";
	                			
	                		} else {
	                			
	                			money_situation_symbol = "-";
	                			
	                		}
	                		
	                		String credit_String = String.valueOf(credit);
	                		
	                		ParseUser user = ParseUser.getCurrentUser();
	                		
	                		if(!phonenumber.equals(user.getUsername())) {
	                		
	                			map.put("accountlist_username", name);
		                		map.put("accountlist_credit", money_situation_symbol+credit_String);
		                        map.put("accountlist_phonenumber", phonenumber);
		                        map.put("accountlist_date", recharge_date_string);
		                        map.put("accountlist_phonenumber_receiver", phonenumber_receiver);
		                        map.put("accountlist_money_situation", money_situation);
		                        items.add(map);
	                        
	                		} else {
	                			
	                			map.put("accountlist_username", name_starter);
		                		map.put("accountlist_credit", money_situation_symbol+credit_String);
		                        map.put("accountlist_phonenumber", phonenumber_receiver);
		                        map.put("accountlist_date", recharge_date_string);
		                        map.put("accountlist_phonenumber_receiver", phonenumber_receiver);
		                        map.put("accountlist_money_situation", money_situation);
		                        items.add(map);
	                			
	                		}
	                		
	                	}
	               
	                } else {
	                    Log.d("App", "Error: " + e.getMessage());
	                }
	            }

				
	        });

        return items;
    }
	
}