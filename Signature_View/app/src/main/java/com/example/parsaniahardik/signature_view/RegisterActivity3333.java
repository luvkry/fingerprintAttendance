package com.example.parsaniahardik.signature_view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity3333 extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";

    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_YEAR = "year";

    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";

    private String[] modString = new String[] {"DB3","NS3","ALGO3","IS3","OS3", "PL3"};
    private String[] yearString = new String[] {"FIRST","SECOND","THIRD"};

    protected Button btnRegister, btnCancel;
    protected EditText editName, editPass, editCfmPass,editContact;
    protected Spinner spinMod,spinYear;
    protected String userrole,branch, name, password, cfmPassword, year, contactNo, OTP;

    private ProgressDialog pDialog;
    private String register_url = "http://192.168.1.135/www/fyp/register.php";
    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        setTitle("Register2 Activity");

        spinMod = findViewById(R.id.spinMod);
        spinYear = findViewById(R.id.spinYear);
        editName = findViewById(R.id.editName);
        editContact = findViewById(R.id.editContact);
        editPass = findViewById(R.id.editPass);
        editCfmPass = findViewById(R.id.editCfmPass);
        btnRegister = findViewById(R.id.btnRegister);
        /* Back to Home Page */
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity3333.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /** Register2 **/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                name = editName.getText().toString().toLowerCase().trim();
                password = editPass.getText().toString().trim();
                cfmPassword = editPass.getText().toString().trim();
                contactNo = editContact.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

        /* Spinner */
        spinMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                branch =(String) spinMod.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMod.setAdapter(adapter_branch);

        ///......................spinner2

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                year =(String) spinYear.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearString);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinYear.setAdapter(adapter_year);

    }

    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity3333.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_NAME, name);
            request.put(KEY_PASS, password);
            request.put(KEY_CONTACT, contactNo);
            request.put(KEY_YEAR, year);
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
                                editContact.setError("Student already registered");
                                editContact.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }



    private boolean validateInputs() {
        if (KEY_EMPTY.equals(name)) {
            editName.setError("Name cannot be empty");
            editName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(contactNo)) {
            editContact.setError("Contact number cannot be empty");
            editContact.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            editPass.setError("Password cannot be empty");
            editPass.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(cfmPassword)) {
            editCfmPass.setError("Confirm Password cannot be empty");
            editCfmPass.requestFocus();
            return false;
        }
        if (!password.equals(cfmPassword)) {
            editCfmPass.setError("Password and Confirm Password does not match");
            editCfmPass.requestFocus();
            return false;
        }

        return true;
    }
}
