package com.rngesus.smartwallet;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPref;
    public static final String MY_PREFS_FILENAME = "com.rngesus.smartwallet";

    public SharedPref() {

    }

    public void SaveData(Context context,String message,String username, String password) {
        sharedPref = context.getSharedPreferences("MY_PREFS_FILENAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("msg", "true");
        editor.putString("UserName", username);
        editor.putString("Password", password);
        editor.commit();
    }

    public String LoadData(Context context) {
        sharedPref = context.getSharedPreferences("MY_PREFS_FILENAME", Context.MODE_PRIVATE);
        String fileContent =   sharedPref.getString("msg", "No msg");
                fileContent +=  "," + sharedPref.getString("UserName", "No name");
        fileContent += "," + sharedPref.getString("Password", "No password");
        return fileContent;
    }
    public void removeDataFromPref(Context context) {
        sharedPref = context.getSharedPreferences("MY_PREFS_FILENAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("UserName");
        editor.remove("msg");
        editor.remove("Password");
        editor.apply();
    }

}
