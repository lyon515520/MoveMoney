package com.isep.android.movemoney;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Alert_Fragment extends Fragment {
	View rootview;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.alert_layout, container, false);
		 ListView lvwCustom = (ListView)rootview.findViewById(R.id.MyListView);
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
	        lvwCustom.setAdapter(adapter);
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
	
}
