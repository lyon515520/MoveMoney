package com.isep.android.movemoney;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		 ListView list = (ListView)rootview.findViewById(R.id.MyListView);
	        //data to be shown in ListView
	        ArrayList<HashMap<String, Object>> items = getItems();                                               
	        //* items are data                                      
	        //* R.layout.alert_single is a file for ListView
	        //* forth value is the value from map                               
	        //* fifth is the id in xml
	       
	        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.alert_single, 
	                new String[] {"title_alert","sender_alert","date_alert"}, 
	                new int[] {R.id.title_alert, R.id.sender_alert, R.id.date_alert});
	        //Adapter for ListView
	        list.setAdapter(adapter);
	        

		
		list.setItemsCanFocus(true);
        
        
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        	
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {  
              
            	new AlertDialog.Builder(getActivity())
                .setTitle(((TextView)view.findViewById(R.id.title_alert)).getText())
                .setMessage("This message should change according to the alert:"+
                '\n'+'\n'+
                "Sender: "+
                ((TextView)view.findViewById(R.id.sender_alert)).getText()+
                '\n'+
                "date: "+
                ((TextView)view.findViewById(R.id.date_alert)).getText()
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
	        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
	        for(int i = 0; i < 5; i++) {

	            HashMap<String, Object> map = new HashMap<String, Object>();

	            map.put("title_alert", "Title"+i);
	            //title
	            map.put("sender_alert", "sender"+i);
	            //sender
	            map.put("date_alert", "date"+i);
	            //date
	            items.add(map);
	        }
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
