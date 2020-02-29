package com.example.parsaniahardik.signature_view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

//    private String[] modString = new String[] {"DB3","NS3","ALGO3","IS3","OS3", "PL3"};
//    private String[] yearString = new String[] {"FIRST","SECOND","THIRD"};

    protected Button btnNext, btnCancel;
    protected EditText editName, editContact, editStudentID;
    private static final String KEY_EMPTY = "";

    private ProgressDialog pDialog;
    private String register_url = "http://192.168.1.135/test-android/register.php";
    //private String register_url = "http://172.30.142.252/test-android/registration.php";

    private SessionHandler session;

    JSONParser jsonParser = new JSONParser();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register Activity");

        editName = findViewById(R.id.editName);
        editContact = findViewById(R.id.editContact);
        editStudentID = findViewById(R.id.editStudID);
        btnNext = findViewById(R.id.btnNext);
        btnCancel = findViewById(R.id.btnCancel);

        /* Back to Home Page */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /** Register **/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    RegisterStudent registerStudent = new RegisterStudent();
                    registerStudent.execute(editName.getText().toString(), editContact.getText().toString(), editStudentID.getText().toString());
                }
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Registering Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /*private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_NAME, name);
            request.put(KEY_PASS, password);
            request.put(KEY_CONTACT, contactNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginUser(name,contactNo);
                                loadDashboard();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existing
                                Toast.makeText(getApplicationContext(),
                                        "Student already registered", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "Not added, error", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        editName.setText(error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }*/
    /*
    * private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                String name = args[0];
                String studID = args[1];
                String password = args[2];
                String contact = args[3];

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("studentID", studID));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("contact", contact));

                JSONObject json = jsonParser.makeHttpRequest(register_url, "POST", params);

                return json;
            }

            protected void onPostExecute(JSONObject result) {

                // dismiss the dialog once product deleted
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

                try {
                    if (result != null) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

    private class RegisterStudent extends AsyncTask<String,String,JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String contact = args[2];
            String studentID = args[1];
            String name = args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("studentID", studentID));
            params.add(new BasicNameValuePair("contact", contact));

            JSONObject json = jsonParser.makeHttpRequest(register_url, "POST", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    displayLoader();
                    Intent register = new Intent(RegisterActivity.this, SuccessActivity.class);
                    startActivity(register);
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(editName.getText().toString())){
            editName.setError("Name cannot be empty");
            editName.requestFocus();
            return false;
        }

        if(KEY_EMPTY.equals(editContact.getText().toString())){
            editContact.setError("Contact cannot be empty");
            editContact.requestFocus();
            return false;
        }

        if(KEY_EMPTY.equals(editStudentID.getText().toString())){
            editStudentID.setError("Student ID cannot be empty");
            editStudentID.requestFocus();
            return false;
        }
        return true;
    }

    /*public void RegisterStudentFunction(final String name, final String studID, final String password, final String contactNo) {


        RegisterStudent registerStudent = new RegisterStudent();
        registerStudent.execute(name,studID,password,contactNo);

    }*/
}
