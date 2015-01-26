package com.isep.android.movemoney;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@SuppressLint("SimpleDateFormat") 
public class GetItems_Alert {
	
	public ArrayList<HashMap<String, Object>> getItems_Alert() {
		
		final ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		
		final String currentUsername;
     	currentUsername = ParseUser.getCurrentUser().getString("username");

		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Process");
        query1.whereEqualTo("process_situation", "unfinish");
        query1.addDescendingOrder("updatedAt");
        query1.findInBackground(new FindCallback<ParseObject>() {
        	
        	@Override 
            public void done(List<ParseObject> objects, ParseException e) {
        		
        		if (e == null) {
                	
                	for(int i = 0; i<objects.size(); i++){
                		
                		ParseObject userData = objects.get(i);
                		HashMap<String, Object> map = new HashMap<String, Object>();
                		
                		String user1 = userData.getString("user1");
                		String phonenumber1 = userData.getString("phonenumber1");//Information of user1(the starter of activity)
                		
                		String user2 = userData.getString("user2");
                		String phonenumber2 = userData.getString("phonenumber2");//Information of user2
                		
                		double credit_process = userData.getDouble("process_credit");//the process credit
                		
                		Date recharge_date = userData.getUpdatedAt();
                		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                		String recharge_date_string = df.format(recharge_date);//the update date
                		
                		String money_situation = userData.getString("money_situation");//the money situation
                		String money_situation_symbol;
                		
                		String id_process=userData.getObjectId();//the object ID
                		
                		String credit_String = String.valueOf(credit_process);
                		
                		if(currentUsername.equals(phonenumber1)) {
                			
                			if(money_situation.equals("negative")){
                    			
                    			money_situation_symbol = "-";
                    			
                    		} else {
                    			
                    			money_situation_symbol = "+";
                    			
                    		}
	                		
                			map.put("alertlist_username", user2);
	                		map.put("alertlist_credit", money_situation_symbol+credit_String);     //below is the part of display on the device of First user(the activity starter)
	                        map.put("alertlist_phonenumber", phonenumber2);
	                        map.put("alertlist_date", recharge_date_string);
	                        map.put("alertlist_id_process", id_process);
	                        //map.put("alertlist_phonenumber_receiver", phonenumber_receiver);
	                        //map.put("alertlist_money_situation", money_situation);
	                        map.put("alertlist_credit_absolute", credit_String);
	                        map.put("alertlist_index", "1");
	                        items.add(map);
	                        
                		} else {
                			
                			if(!money_situation.equals("negative")){
                    			
                    			money_situation_symbol = "-";
                    			
                    		} else {
                    			
                    			money_situation_symbol = "+";
                    			
                    		}
                			
                			map.put("alertlist_username", user1);
                    		map.put("alertlist_credit", money_situation_symbol+credit_String);     //below is the part of display on the device of Second user
                            map.put("alertlist_phonenumber", phonenumber1);
                            map.put("alertlist_date", recharge_date_string);
                            map.put("alertlist_id_process", id_process);
                            //map.put("alertlist_phonenumber_receiver", phonenumber_receiver);
                            //map.put("alertlist_money_situation", money_situation);
                            map.put("alertlist_credit_absolute", credit_String);
                            map.put("alertlist_index", "2");
                            items.add(map);
                			
                		} 
                		
                	} 
                	
        		} else {}
        		
        	}
        	
        });
		
		return items;
		
	}

}
