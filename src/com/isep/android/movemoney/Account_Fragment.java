package com.isep.android.movemoney;

import java.util.ArrayList;
import java.util.HashMap;

import com.parse.ParseUser;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Account_Fragment extends Fragment {
	View rootview;
	
	TextView credit_currenttv;
	double credit_current;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootview = inflater.inflate(R.layout.account_layout, container, false);
		
		ParseUser user = ParseUser.getCurrentUser();
		credit_current = user.getDouble("credit");
		
		credit_currenttv = (TextView) rootview.findViewById(R.id.account_credit);
		
		credit_currenttv.setText(String.valueOf(credit_current)+" euros");
		
		ArrayList<HashMap<String, Object>> items = getItems(); 
		
		ListView list = (ListView)rootview.findViewById(R.id.ListView_account);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.account_single, 
                new String[] {"accountlist_username","accountlist_credit","accountlist_phonenumber","accountlist_date"}, 
                new int[] {R.id.accountlist_username, R.id.accountlist_credit, R.id.accountlist_phonenumber, R.id.accountlist_date});
		
		list.setAdapter(adapter);
		
		return rootview;
	
	}
	
	private ArrayList<HashMap<String, Object>> getItems() {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < 5; i++) {

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("accountlist_username", "PierreLI"+i);
            //username
            map.put("accountlist_credit", "10"+i+" euros");
            //credit
            map.put("accountlist_phonenumber", "066759707"+i);
            //phonenumber
            map.put("accountlist_date", "Jan 2"+i+", 2015");
            //date
            items.add(map);
        }
        return items;
    }
	
}
