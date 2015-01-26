package com.isep.android.movemoney;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
		
		InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_alert);
		 
	    ArrayList<HashMap<String, Object>> items = getItems();
	       
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.alert_single, 
                new String[] {"alertlist_username","alertlist_credit","alertlist_phonenumber","alertlist_date","alertlist_id_process"}, 
                new int[] {R.id.alertlist_username, R.id.alertlist_credit, R.id.alertlist_phonenumber, R.id.alertlist_date, R.id.alertlist_id_process});
	    
        //adapter.notifyDataSetChanged();
        
        //Adapter for ListView
	    list.setAdapter(adapter);
		
		list.setItemsCanFocus(true);
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        	
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {  
              
            	new AlertDialog.Builder(getActivity())
                .setTitle("Request")
                .setMessage(
        		"Amount:  "+
                ((TextView)view.findViewById(R.id.alertlist_credit)).getText()+"\u20ac"+
                '\n'+
                "Demander:  "+
                ((TextView)view.findViewById(R.id.alertlist_username)).getText()+
                '\n'+
                "Mobile:  "+
                ((TextView)view.findViewById(R.id.alertlist_phonenumber)).getText()+
                '\n'+
                "Date:  "+
                ((TextView)view.findViewById(R.id.alertlist_date)).getText()
                +'\n'
                
                )
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                	
                    public void onClick(DialogInterface dialog, int which) { 
                    	
                    	TextView id_process = (TextView) view.findViewById(R.id.alertlist_id_process);
                    	String id_processtxt = id_process.getText().toString();
                    	
                    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Process");
                    	
                    	query.getInBackground(id_processtxt, new GetCallback<ParseObject>() {

							public void done(ParseObject processData,ParseException ee) {
								if(ee == null){
									
									ParseUser user = ParseUser.getCurrentUser();
									double credit_transfer = processData.getDouble("process_credit");
									double credit_old = user.getDouble("credit");
									String money_situation = processData.getString("money_situation");
									
									if(money_situation.equals("positive")) {
										
										double credit_new = credit_old + credit_transfer;
										user.put("credit", credit_new);
										
									} else {
										
										double credit_new = credit_old - credit_transfer;
										user.put("credit", credit_new);
										
									}
									
									user.saveInBackground();
									
									processData.put("process_situation", "finish");
									processData.saveInBackground();
								
								}
								
							}
							
							
                    	});
                    	
                    }
                    
                 })
                .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                    	
                    	TextView id_process = (TextView) view.findViewById(R.id.alertlist_id_process);
                    	String id_processtxt = id_process.getText().toString();
                    	
                    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Process");
                    	query.getInBackground(id_processtxt, new GetCallback<ParseObject>() {

							public void done(ParseObject processData,ParseException ee) {
								if(ee == null){
									
									processData.deleteInBackground();
									processData.saveInBackground();
								
								}
								
							}
							
							
                    	});
                    	
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
	                		
	                		String id_process=test.getObjectId();
	                		
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
	                        map.put("alertlist_id_process", id_process);
	                        items.add(map);
	                	}
	               
	                } else {
	                    Log.d("App", "Error: " + e.getMessage());
	                }
	            }

				
	        });

     return items;
	    }
	 
}