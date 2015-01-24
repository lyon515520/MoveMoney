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

public class Account_Fragment extends Fragment {
	View rootview;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.account_layout, container, false);
		
		ArrayList<HashMap<String, Object>> items = getItems(); 
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_account);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.account_single, 
                new String[] {"title_account","sender_account","date_account"}, 
                new int[] {R.id.title_account, R.id.sender_account, R.id.date_account});
		
		list.setAdapter(adapter);
		
		return rootview;
	
	}
	
	private ArrayList<HashMap<String, Object>> getItems() {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < 5; i++) {

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("title_account", "Title"+i);
            //title
            map.put("sender_account", "sender"+i);
            //sender
            map.put("date_account", "date"+i);
            //date
            items.add(map);
        }
        return items;
    }
	
}
