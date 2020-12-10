package com.widas.demo;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static SharedPreference mSharedPreference;
    private SharedPreferences sharedPreferences;

    public static SharedPreference getInstance(Context context) {
        if (mSharedPreference == null) {
            mSharedPreference = new SharedPreference(context);
        }
        return mSharedPreference;
    }

    private SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("androidsdkwidaas",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key,"");
        }
        return "";
    }
}
