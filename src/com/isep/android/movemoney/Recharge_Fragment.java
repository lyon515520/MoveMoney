package com.isep.android.movemoney;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recharge_Fragment extends Fragment {
	View rootview;
	EditText recharge;
	
	double credit;
	double credit_add;
	double credit_new;
	
	Button btn_recharge;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.recharge_layout, container, false);
		
		recharge = (EditText) rootview.findViewById(R.id.recharge_editText1);
		
		btn_recharge = (Button) rootview.findViewById(R.id.recharge_button);	
		
		btn_recharge.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				credit_add = Double.parseDouble(recharge.getText().toString());
				
				if(credit_add > 0) {
						
					ParseUser user = ParseUser.getCurrentUser();
					credit = user.getDouble("credit");
					credit_new = credit + credit_add; 
					user.put("credit", credit_new);
					
					user.saveInBackground();//update the data in Parse 
					
					ParseObject process = new ParseObject("Process");
					process.put("process_credit", credit_add);
					String name1 = user.getString("nickname");
					String phonenumber1 = user.getString("username");
					process.put("phonenumber1", phonenumber1);
					process.put("phonenumber2", phonenumber1);
					process.put("user1", name1);
					process.put("user2", name1);
					//process.put("parent1", user);
					//process.put("parent2", user);
					process.put("process_situation", "finish");
					process.put("money_situation", "positive");
					process.put("type", "recharge"); //it's means user1 send money to user2, type is recharge
					process.saveInBackground();
					
					Toast.makeText(getActivity().getApplicationContext(),
							"Recharge successfully", Toast.LENGTH_LONG)
							.show();
					
				} else {
					
					Toast.makeText(getActivity().getApplicationContext(),
							"Please enter a correct number", Toast.LENGTH_LONG)
							.show();
					
				}
				
				
			}
			
			
			
		});
		
		
		return rootview;
	}
	
}
