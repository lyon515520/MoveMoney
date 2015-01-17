package com.isep.android.movemoney;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	SharedPreferences pref;
	Editor editor;
	Context _context;
	
	int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "mmadmin";
	
	public static final String KEY_NAME = "session";
	
	public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
	
	public void createUserSession(String user_session){
		
        editor.putString(KEY_NAME, user_session);
        editor.commit();
    
	}  

}
