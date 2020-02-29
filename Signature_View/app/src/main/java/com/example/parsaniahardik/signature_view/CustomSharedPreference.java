package com.example.parsaniahardik.signature_view;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreference {

    private SharedPreferences sharedPref;

    public CustomSharedPreference(Context context) {
        sharedPref = context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
    }

    public SharedPreferences getInstanceOfSharedPreference(){
        return sharedPref;
    }

    //Save user information
    public void setStudentData(String studentData){
        sharedPref.edit().putString("STUDENT", studentData).apply();
    }

    public String getStudentData(){
        return sharedPref.getString("STUDENT", "");
    }
}