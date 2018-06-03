package com.example.jamal.orderhr_noninstant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jamal.orderhr_noninstant.Datastructures.SuperUser;

/**
 * Created by jamal on 5/14/2018.
 */

public class Session {

    private static SharedPreferences prefs;

    public Session(Context cntx) {
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

    public void setUser(SuperUser user){
        prefs.edit().putString("User", user.TypeOfUser()).apply();
    }

    public static String getUserType(){
        String user = prefs.getString("User", "");
        return user;
    }
    
}
