package com.example.parsaniahardik.signature_view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SessionHandler  {
    JSONParser jsonParser = new JSONParser();
    private String url = "http://192.168.1.135/test-android/session.php";

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_YEAR = "year";

    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";

    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private String finalPassword;

    public String setPassword(String password){
        return this.finalPassword = password;
    }

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
        String pass = setPassword(finalPassword);
        new Connection().execute(pass, "", "");
    }

    class Connection extends AsyncTask<String,String,JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String password = args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password", password));

            JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);

            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    isLoggedIn();
                } else {
                    String name, studID, password;
                    Integer contact;
                    JSONArray array = result.getJSONArray("stud");
                    JSONObject object2 = array.getJSONObject(1);
                    JSONObject object3 = array.getJSONObject(2);
                    JSONObject object4 = array.getJSONObject(3);
                    JSONObject object5 = array.getJSONObject(4);

                    name = object2.getString("studentID");
                    contact = object3.getInt("contact");
                    studID = object4.getString("studentID");
                    password = object5.getString("name");
                    getStudentDetails(name, studID, contact, password);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Logs in the user by saving user details and setting session
     *
     * @param name
     */
    public void loginUser(String name, String contactNo) {


        mEditor.putString(KEY_NAME, name);
        mEditor.putString(KEY_CONTACT, contactNo);

        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return true;
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public Student getStudentDetails(String name, String studID, Integer contact, String password) {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        student.setStudID(studID);
        student.setContact(contact);

        return student;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }
}
