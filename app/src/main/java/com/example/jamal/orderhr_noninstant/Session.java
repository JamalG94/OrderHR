package com.example.jamal.orderhr_noninstant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jamal on 5/14/2018.
 */

public class Session {

    private static SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username) {
        prefs.edit().putString("userN", username).apply();
    }

    public void setPassword(String password) {
        prefs.edit().putString("pass", password).apply();
    }

    public static String getUsername() {
        String usename = prefs.getString("userN","");
        return usename;
    }
    
}
