package com.isep.android.movemoney;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@SuppressLint("SimpleDateFormat") 
public class GetItems {
	
	public ArrayList<HashMap<String, Object>> getItems() {
		
		final ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		
		String currentUsername;
     	currentUsername = ParseUser.getCurrentUser().getString("username");
     	
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Process");
	        query.whereEqualTo("process_situation", "unfinish");
	        //query.whereEqualTo("phonenumber2",currentUsername);
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
	                		
	                		String id_process=test.getObjectId();
	                		
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
	                		
		                		map.put("alertlist_username", name);
		                		map.put("alertlist_credit", money_situation_symbol+credit_String);
		                        map.put("alertlist_phonenumber", phonenumber);
		                        map.put("alertlist_date", recharge_date_string);
		                        map.put("alertlist_id_process", id_process);
		                        map.put("alertlist_phonenumber_receiver", phonenumber_receiver);
		                        map.put("alertlist_money_situation", money_situation);
		                        items.add(map);
	                		
	                		} else {
	                			
	                			map.put("alertlist_username", name_starter);
		                		map.put("alertlist_credit", money_situation_symbol+credit_String);
		                        map.put("alertlist_phonenumber", phonenumber_receiver);
		                        map.put("alertlist_date", recharge_date_string);
		                        map.put("alertlist_id_process", id_process);
		                        map.put("alertlist_phonenumber_receiver", phonenumber_receiver);
		                        map.put("alertlist_money_situation", money_situation);
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
