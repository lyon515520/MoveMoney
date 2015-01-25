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
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") 
public class Alert_Fragment extends Fragment {
	View rootview;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.alert_layout, container, false);
		ListView list = (ListView)rootview.findViewById(R.id.ListView_alert);
		 
	    ArrayList<HashMap<String, Object>> items = getItems();
	       
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.alert_single, 
                new String[] {"alertlist_username","alertlist_credit","alertlist_phonenumber","alertlist_date"}, 
                new int[] {R.id.alertlist_username, R.id.alertlist_credit, R.id.alertlist_phonenumber, R.id.alertlist_date});
	    
        adapter.notifyDataSetChanged();
        
        //Adapter for ListView
	    list.setAdapter(adapter);
		
		list.setItemsCanFocus(true);
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        	
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {  
              
            	new AlertDialog.Builder(getActivity())
                .setTitle(((TextView)view.findViewById(R.id.alertlist_phonenumber)).getText())
                .setMessage("This message should change according to the alert:"+
                '\n'+'\n'+
                "Sender: "+
                ((TextView)view.findViewById(R.id.alertlist_username)).getText()+
                '\n'+
                "date: "+
                ((TextView)view.findViewById(R.id.alertlist_date)).getText()
                )
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // continue with delete
                    }
                 })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // do nothing
                    }
                 })
                .setIcon(android.R.drawable.ic_dialog_info)
                 .show();
            }

         });
        
		return rootview;
	 }
	
	 private ArrayList<HashMap<String, Object>> getItems() {
		final ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
         
     	String currentUsername;
     	currentUsername = ParseUser.getCurrentUser().getString("username");
         ParseQuery<ParseObject> query = ParseQuery.getQuery("Process");
	        query.whereEqualTo("phonenumber2",currentUsername);
	        query.whereEqualTo("process_situation", "unfinish");
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
	                		
	                		if(money_situation.equals("positive")){
	                			
	                			money_situation_symbol = "+";
	                			
	                		} else {
	                			
	                			money_situation_symbol = "-";
	                			
	                		}
	                		
	                		String credit_String = String.valueOf(credit);
	                		
	                		
	                		map.put("alertlist_username", name);
	                		map.put("alertlist_credit", money_situation_symbol+credit_String);
	                        map.put("alertlist_phonenumber", phonenumber);
	                        map.put("alertlist_date", recharge_date_string);
	                        items.add(map);
	                	}
	               
	                } else {
	                    Log.d("App", "Error: " + e.getMessage());
	                }
	            }

				
	        });

     return items;
	    }
	 
	 public void alertWindow(View view) {
		 new AlertDialog.Builder(getActivity())
         .setTitle("Detail of Alert")
         .setMessage("This message should change according to the alert:")
         .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) { 
                 // continue with delete
             }
          })
         .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) { 
                 // do nothing
             }
          })
         .setIcon(android.R.drawable.ic_dialog_info)
          .show();
	       // TextView textview = (TextView)findViewById(R.id.textView1);
	       // textview.setText("Äãµã»÷ÁËButton");        
	    }
}
