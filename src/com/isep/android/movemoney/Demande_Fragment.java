package com.isep.android.movemoney;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Demande_Fragment extends Fragment {
	View rootview;
	
	EditText demande_number;
	EditText demande_amount;
	
	String demande_demanderNo;
	String demande_demanderName;
	
	String demande_numbertxt;
	double demande_amounttxt;
	
	Button btn_demande;
	Button btn_contact;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.demande_layout, container, false);
		
		demande_number = (EditText) rootview.findViewById(R.id.demande_target);
		demande_amount = (EditText) rootview.findViewById(R.id.demande_amount);
		
		btn_demande = (Button) rootview.findViewById(R.id.demande_button);
		btn_contact = (Button) rootview.findViewById(R.id.contact_list_demande);
		
		btn_contact.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Uri uri = Uri.parse("content://com.android.contacts/contacts"); 
				
		        //get a ContentResolver
		        ContentResolver reslover = getActivity()
		        		.getApplicationContext()
		        		.getContentResolver();  
		        
		        //use content://com.android.contacts/contacts
		        Cursor cursor = reslover.query(uri, null, null, null, null);  
		        
		        List<String> id_list = new ArrayList<String>();
		        List<String> name_list = new ArrayList<String>();
		        //List<String> number_list = new ArrayList<String>();
		        while(cursor.moveToNext()){  
		        	
		            //get ID  
		            String id = cursor.getString(
		            		cursor.getColumnIndex(
		            				android.provider
		            				.ContactsContract.Contacts._ID));    
		            id_list.add(id);
		            
		            //get name  
		            String name = cursor.getString(
		            		cursor.getColumnIndex(
		            				android.provider
		            				.ContactsContract.Contacts.DISPLAY_NAME));  
		            name_list.add(name);   
		        }
		        
		        final String[] id_array = id_list.toArray(new String[id_list.size()]);
		        String[] name_array = name_list.toArray(new String[name_list.size()]);
		        
		        //start the AlertDialog
		        AlertDialog.Builder builder = new Builder(getActivity());
		        
		    	builder.setTitle("Contact List");
		    	builder.setIcon(android.R.drawable.ic_dialog_info)
		    			.setSingleChoiceItems(
	    				name_array, 0,
		    			null)
		    			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		    				public void onClick(DialogInterface dialog, int whichButton) {
		    					int selectedPosition =  ((AlertDialog)dialog)
		    							.getListView()
		    							.getCheckedItemPosition();
		    					
		    					
		    					ContentResolver reslover_search = getActivity()
		    							.getApplicationContext()
		    							.getContentResolver();  
		    					
		    			       
		    					//get phone number
		    			        Cursor phone_search =   reslover_search.query(
		    			        		ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,   
		    		                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID 
		    		                    + "=" + id_array[selectedPosition], null, null);
		    			        
		    			        List<String> number_list = new ArrayList<String>();
		    			        
		    			        while(phone_search.moveToNext()){ //get phone number(in case of several numbers)  
		    		                int phoneFieldColumnIndex = phone_search.
		    		                		getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);  
		    		                String phoneNumber = phone_search.
		    		                		getString(phoneFieldColumnIndex);  
		    		                number_list.add(phoneNumber);  
		    		            }
		    			        
		    			        final String[] number_array = number_list.toArray(new String[number_list.size()]);
		    			        
		    			        //deal with the number that we get
		    			        
		    			        //if we do not get phone number, give a toast
		    			        if(number_list.size()==0) 
		    			        	{
		    			        	demande_number.setText("");
		    			        	
		    			        	Toast.makeText(getActivity().getApplicationContext(),
		    			        		"No phone Number!", Toast.LENGTH_LONG)
										.show();
		    			        	}
		    			        
		    			        //if we get only one number
		    			        else if(number_list.size()==1)
		    			        	{
		    			        	String display = number_array[0];
		    			        	
		    			        	//deal with the beginning of the number
		    			    		display = display.replace("+33", "0");
		    			    		display = display.replace("0033", "0");
		    			    		
		    			    		//delete the possible space between the numbers
		    			    		display = display.replaceAll("[^\\d]", "");	
		    			        	
		    			    		//check the number that we've got
		    			    		if (display.length()==10){
		    			    			
		    			    			//check if the number is mobile
	    			        			if(display.startsWith("06")||display.startsWith("07"))
	    			        				demande_number.setText(display);
	    			        			else Toast.makeText(getActivity().getApplicationContext(),
	    		    			        		"Please use a mobile phone!", Toast.LENGTH_LONG)
	    										.show();
		    			    			}
		    			        		else{
		    			        			Toast.makeText(getActivity().getApplicationContext(),
		    		    			        		"Please use a French mobile!", Toast.LENGTH_LONG)
		    										.show();
		    			        		}
		    			        	
		    			        	}
		    			        
		    			        else if(number_list.size()>1)
		    			        	{
		    			        	AlertDialog.Builder builder_number = new Builder(getActivity());
		    			        	builder_number.setTitle("Contact List");
		    			        	builder_number.setIcon(android.R.drawable.ic_dialog_info)
		    				    			.setSingleChoiceItems(
		    			    				number_array, 0,
		    				    			null)
		    				    			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		    				    				public void onClick(DialogInterface dialog, int whichButton) {
		    				    					int selectedNumber = ((AlertDialog)dialog).getListView()
		    				    							.getCheckedItemPosition();
		    				    					
		    				    					String display=number_array[selectedNumber];
		    				    					
		    				    					display = display.replace("+33", "0");
		    				    					display = display.replace("0033", "0");
		    			    			    		display = display.replaceAll("[^\\d]", "");
		    			    			    		
		    		    			        		if (display.length()==10){
		    		    			        			if(display.startsWith("06")||display.startsWith("07"))
		    		    			        				demande_number.setText(display);
		    		    			        			else Toast.makeText(getActivity().getApplicationContext(),
		    		    		    			        		"Please use a mobile phone!", Toast.LENGTH_LONG)
		    		    										.show();
		    		    			        		}
		    		    			        		else{
		    		    			        			Toast.makeText(getActivity().getApplicationContext(),
		    		    		    			        		"Please use a French mobile!", Toast.LENGTH_LONG)
		    		    										.show();
		    		    			        		}
		    				    				}
		    				    			})
		    				    			.setNegativeButton("Cancel", null).show();
		    			        	
		    			        	}
		    			        else Toast.makeText(getActivity().getApplicationContext(),
		    			        		"Error!!!", Toast.LENGTH_LONG)
										.show();	
		    				}
		    			})
		    			.setNegativeButton("Cancel", null).show();
			}	
		});
		
		btn_demande.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				demande_numbertxt = demande_number.getText().toString();

												
				if((demande_numbertxt.startsWith("06")
						||demande_numbertxt.startsWith("07"))
						&&(demande_numbertxt.length()==10))
						{
							String amount = demande_amount.getText().toString();
							if(amount.startsWith("0")
									||amount.startsWith("1")
									||amount.startsWith("2")
									||amount.startsWith("3")
									||amount.startsWith("4")
									||amount.startsWith("5")
									||amount.startsWith("6")
									||amount.startsWith("7")
									||amount.startsWith("8")
									||amount.startsWith("9")
									||amount.startsWith("."))
								
									{
								
								demande_amounttxt = Double.parseDouble(demande_amount.getText().toString());

									if(demande_amounttxt>0){
								
									final ParseObject process = new ParseObject("Process");
									
									process.put("money_situation", "positive");
									process.put("process_situation", "unfinish");
									process.put("type", "demande");
									process.put("process_credit", demande_amounttxt);
									
									
									ParseUser user = ParseUser.getCurrentUser();
									//
									demande_demanderNo = user.getUsername();
									demande_demanderName = user.getString("nickname");
									 
									process.put("user1", user.getString("nickname"));
									process.put("phonenumber1", demande_demanderNo);  //this part is to store the data of Sender in Parse
									//process.put("parent", user);                           
									
									ParseQuery<ParseObject> query = ParseQuery.getQuery("User_copy");
									query.whereEqualTo("username", demande_numbertxt);
									query.findInBackground(new FindCallback<ParseObject>() {
										
										@Override
										public void done(List<ParseObject> userList,ParseException e) {
											// TODO Auto-generated method stub
											if(e == null) {
												
												try {
													
													ParseObject userData = userList.get(0);
													//process.put("parent2", userData.getObjectId());
													process.put("user2", userData.getString("nickname"));
													process.put("phonenumber2", demande_numbertxt);
													//process.put("user2", "test");
													
													ParseACL acl = new ParseACL();
													acl.setPublicReadAccess(true);
													acl.setPublicWriteAccess(true);
													
													process.setACL(acl);
													
													process.saveInBackground();
													
													sms message = new sms();
													message.sendSMS("demande_success", demande_numbertxt,demande_demanderNo,demande_demanderName);
													Toast.makeText(getActivity().getApplicationContext(),
															"Your request has been sent", Toast.LENGTH_LONG)
															.show();
													
												} catch(IndexOutOfBoundsException r) {
													AlertDialog.Builder builder_confirm = new Builder(getActivity());
													builder_confirm.setTitle("Comfirmation");
													builder_confirm.setIcon(android.R.drawable.ic_dialog_info)
						    				    			.setMessage("Your request doesn't succeed. Because the receiver doesn't have an account.\nInvite him/her?")
						    				    			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	// TODO Auto-generated method stub
																	sms message = new sms();
																	message.sendSMS("demande_fail", demande_numbertxt,demande_demanderNo,demande_demanderName);
																	Toast.makeText(getActivity().getApplicationContext(),
																			"An invitation SMS has been sent", Toast.LENGTH_LONG)
																			.show();	
																}})
						    				    			.setNegativeButton("Cancel", null)
						    				    			.show();
												
												}
												
											} else {
												
												Log.d("App", "Error: " + e.getMessage());
												
											}
										}
									});
									/*
									Toast.makeText(getActivity().getApplicationContext(),
											"Transfere successfully", Toast.LENGTH_LONG)
											.show();
									
								*/
								//erase the EditText
									demande_number.setText("");
									demande_amount.setText("");
								
								//end of if
								}
									else{
										Toast.makeText(getActivity().getApplicationContext(),
												"Please enter a right amount", Toast.LENGTH_LONG)
												.show();
									}
									}
							
								else{
									Toast.makeText(getActivity().getApplicationContext(),
											"Please enter an amount", Toast.LENGTH_LONG)
											.show();
									}
						}
				
				else{

					Toast.makeText(getActivity().getApplicationContext(),
							"Please enter a French mobile number", Toast.LENGTH_LONG)
							.show();
					
				}
				
			}
			
			
		});
		
		
		
		return rootview;
	}
	
}
