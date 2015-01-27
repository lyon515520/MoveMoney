package com.isep.android.movemoney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.isep.android.movemoney.R.color;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

public class Alert_Fragment extends Fragment {
	View rootview;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.alert_layout, container, false);
		
		InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_alert);
		
		GetItems_Alert gt_alert = new GetItems_Alert();
		
	    ArrayList<HashMap<String, Object>> items = gt_alert.getItems_Alert();
	       
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.alert_single, 
                new String[] {  
        			
        			"alertlist_username",
        			"alertlist_credit",
        			"alertlist_phonenumber",
        			"alertlist_date",
        			"alertlist_id_process",
        			"alertlist_credit_absolute",
        			"alertlist_index"
        		
        		}, 
                
                new int[] {
        			
        			R.id.alertlist_username,
        			R.id.alertlist_credit, 
        			R.id.alertlist_phonenumber, 
        			R.id.alertlist_date, 
        			R.id.alertlist_id_process,
        			R.id.alertlist_credit_absolute,
        			R.id.alertlist_index
        		
        		}
        
        );
	    
        //adapter.notifyDataSetChanged();
        
        //Adapter for ListView
        
        
	    list.setAdapter(adapter);
		
		list.setItemsCanFocus(true);
        
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        	
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {  
            	//TextView process_receiver = (TextView) view.findViewById(R.id.alertlist_phonenumber_receiver);
            	//String process_receivertxt = process_receiver.getText().toString();
            	TextView phonenumberView = (TextView) view.findViewById(R.id.alertlist_phonenumber);
            	String phonenumber = phonenumberView.getText().toString();//phonenumber on the Screen 
            	
            	final ParseUser user = ParseUser.getCurrentUser();
            	
            	String phonenumber_current = user.getUsername();//phonenumber of current user 
            	
            	TextView indexView = (TextView) view.findViewById(R.id.alertlist_index);
            	String index = indexView.getText().toString();//the index, "1" represent Currentuser is User1, "2" represent Currentuser is User2
            	
            	if(index.equals("2")) {
            		//this represent CurrentUser is the User2, when to open the pop-out window, it shows two button
	            	new AlertDialog.Builder(getActivity())
	                .setTitle("Request")
	                .setMessage(
	        		"Amount:  "+"\u20ac"+
	                ((TextView)view.findViewById(R.id.alertlist_credit_absolute)).getText()+
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
										
										final double credit_transfer = processData.getDouble("process_credit");
										//double credit_old = user.getDouble("credit");
										String money_situation = processData.getString("money_situation");
										
										String phonenumber_current = user.getUsername();
										
										String phonenumber_actor = processData.getString("phonenumber1");
										
										if(money_situation.equals("positive")) {
											
											//double credit_new = credit_old - credit_transfer;
											//user.put("credit", credit_new);
											ParseQuery<ParseObject> query1 = ParseQuery.getQuery("User_copy");
											query1.whereEqualTo("username", phonenumber_current);
											query1.findInBackground(new FindCallback<ParseObject>() {
												
												@Override
												public void done(List<ParseObject> userList, ParseException e) {
													// TODO Auto-generated method stub
													if(e == null) {
														
														ParseObject userData = userList.get(0);
														double credit = userData.getDouble("credit");
														double credit_new = credit - credit_transfer;
														userData.put("credit", credit_new);
														userData.saveInBackground();
														
													} else {
														
														Log.d("App", "Error: " + e.getMessage());
														
													}
												}
												
											});
											
											/*this is the activity of the Actor(who starts the activity)*/
											ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User_copy");
											query2.whereEqualTo("username", phonenumber_actor);
											query2.findInBackground(new FindCallback<ParseObject>() {
												
												@Override
												public void done(List<ParseObject> userList, ParseException e) {
													// TODO Auto-generated method stub
													if(e == null) {
														
														ParseObject userData = userList.get(0);
														double credit = userData.getDouble("credit");
														double credit_new = credit + credit_transfer;
														userData.put("credit", credit_new);
														userData.saveInBackground();
														
													} else {
														
														Log.d("App", "Error: " + e.getMessage());
														
													}
												}
												
											});
											
											
										} else {
											
											//double credit_new = credit_old + credit_transfer;
											//user.put("credit", credit_new);
											ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
											query.whereEqualTo("username", phonenumber_current);
											query.findInBackground(new FindCallback<ParseObject>() {
												
												@Override
												public void done(List<ParseObject> userList, ParseException e) {
													// TODO Auto-generated method stub
													if(e == null) {
														
														ParseObject userData = userList.get(0);
														double credit = userData.getDouble("credit");
														double credit_new = credit + credit_transfer;
														userData.put("credit", credit_new);
														userData.saveInBackground();
														
													} else {
														
														Log.d("App", "Error: " + e.getMessage());
														
													}
												}
												
											});
											
											/*this is the activity of the Actor(who starts the activity)*/
											ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User_copy");
											query2.whereEqualTo("username", phonenumber_actor);
											query2.findInBackground(new FindCallback<ParseObject>() {
												
												@Override
												public void done(List<ParseObject> userList, ParseException e) {
													// TODO Auto-generated method stub
													if(e == null) {
														
														ParseObject userData = userList.get(0);
														double credit = userData.getDouble("credit");
														double credit_new = credit - credit_transfer;
														userData.put("credit", credit_new);
														userData.saveInBackground();
														
													} else {
														
														Log.d("App", "Error: " + e.getMessage());
														
													}
												}
												
											});
											
										}
										
										processData.put("process_situation", "finish");
										
										processData.saveInBackground();
										
										
										
									} else {
										
										Log.d("App", "Error: " + ee.getMessage());
										
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
	            	
	            	//view.setBackgroundColor(color.grey_clair);
            	 
            } else {
            	
            	new AlertDialog.Builder(getActivity())
                .setTitle("Request")
                .setMessage(
        		"Amount:  "+"\u20ac"+
                ((TextView)view.findViewById(R.id.alertlist_credit_absolute)).getText()+
                '\n'+
                "Receiver:  "+
                ((TextView)view.findViewById(R.id.alertlist_username)).getText()+
                '\n'+
                "Mobile:  "+
                ((TextView)view.findViewById(R.id.alertlist_phonenumber)).getText()+
                '\n'+
                "Date:  "+
                ((TextView)view.findViewById(R.id.alertlist_date)).getText()
                +'\n'
                
                )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            	
            	//view.setBackgroundColor(color.white);
            	
            }
            	
            }

         });
        
		return rootview;
	 }
	 
}