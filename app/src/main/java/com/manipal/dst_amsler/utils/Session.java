package com.manipal.dst_amsler.utils;

/**
 * Created by HP_400_G2 on 18-04-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by HP_400_G2 on 18-04-2017.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub

         prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserName(String user_id) {
        prefs.edit().putString("u_id", user_id).commit();
    }

    public String getUserName() {
        String user_id = prefs.getString("user_id","");
        return user_id;
    }
}