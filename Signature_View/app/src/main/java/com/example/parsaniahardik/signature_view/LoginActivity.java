package com.example.parsaniahardik.signature_view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private SessionHandler session;

    private static final String KEY_EMPTY = "";

    private EditText editStudID;
    private Button btnBack, btnRequest, btnRegister;
    private String login_url = "http://192.168.1.135/test-android/login.php";
    //private String login_url = "http://172.30.142.252/test-android/login.php";
    JSONParser jsonParser = new JSONParser();
    private String errorMessage = "No data";
    private String message = "Success";

    private Integer contactFound, userContact, success, contact;
    OTP getOneTimePassword = new OTP();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            loadDashboard();
        }*/
        setContentView(R.layout.activity_login);
        setTitle("Login Activity");

        btnBack = findViewById(R.id.btnLBack);
        btnRequest = findViewById(R.id.btnLRequest);
        btnRegister = findViewById(R.id.btnLRegister);
        editStudID = findViewById(R.id.editLStudID);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Check number from login if is from the database **/
                if (validateInputs()) {
                    GetStudentContact getStudentContact = new GetStudentContact();
                    getStudentContact.execute(editStudID.getText().toString(), "", "");
                }
            }
        });

    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(editStudID.getText().toString())) {
            editStudID.setError("Student ID cannot be empty");
            editStudID.requestFocus();
            return false;
        }
        return true;
    }


    private class GetStudentContact extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String studentID = args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("studentID", studentID));

            JSONObject json = jsonParser.makeHttpRequest(login_url, "GET", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            try {
                if (result != null) {
                    if (result.getString("message").matches(errorMessage)) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_SHORT).show();
                    } else if (result.getString("message").matches(message)) {
                        Toast.makeText(getApplicationContext(), "Here:", Toast.LENGTH_SHORT).show();
                        success = result.getInt("success");
                        if (success == 1) {
                            Intent otpIntent = new Intent(LoginActivity.this, OTPActivity.class);
                            JSONArray student = result.getJSONArray("student");
                            for (int x = 0; x < student.length(); x++) {
                                JSONObject stud = student.getJSONObject(x);
                                userContact = stud.getInt("contact");
                                String value = userContact.toString();
                                if (!value.isEmpty()) {
                                    otpIntent.putExtra("message", value);
                                    startActivity(otpIntent);
                                }
                            }


                            /*
                            for(int x = 0; x < student.length(); x++){
                                JSONObject stud = student.getJSONObject(x);
                                userContact = stud.getInt("contact");
                                String value = userContact.toString();
                                if(KEY_EMPTY.equals(userContact.toString())){
                                    Toast.makeText(getApplicationContext(), "Jere: Empty" , Toast.LENGTH_SHORT).show();
                                } else{
                                    if(!value.isEmpty()){
                                        Toast.makeText(getApplicationContext(), "NOt" + value , Toast.LENGTH_SHORT).show();
                                        Intent otpIntent = new Intent(LoginActivity.this, OTPActivity.class);
                                        otpIntent.putExtra("inputContact", value);
                                        setResult(RESULT_OK, otpIntent);
                                        finish();
                                        //startActivity(otpIntent);
                                    }
                                }
                            }*/
                        }
                        /*
                        if(success == 1){
                            if(!KEY_EMPTY.equals(userContact.toString())){
                                otpIntent.putExtra("inputContact", userContact.toString());
                                    startActivity(otpIntent);
                            }
                        }*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong studentID entered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*class OTPOperation{
        private Integer userContact;
        private Integer userOneTimePass;
        private Integer generatedOneTimePass;

        // Contact From Database
        private void setUserContact(Integer contactFound) {
            this.userContact = contactFound;
        }

        // OTP From Input
        private void setUserOTP(Integer userOTP) {
            this.userOneTimePass = userOTP;
        }

        protected void setGeneratedOTP(){
            Integer value;
            // Using numeric values
            String numbers = "0123456789";

            // Using random method
            Random rndm_method = new Random();
            char[] otp = new char[6];

            for (int i = 0; i < 6; i++)
            {
                // Use of charAt() method : to get character value
                // Use of nextInt() as it is scanning the value as int
                otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
            }
            value = Integer.parseInt(String.valueOf(otp));
            this.generatedOneTimePass = value;
        }

        // OTP From Generated
        protected Integer getUserContact(){
            return userContact;
        }

        protected Integer getUserOTP(){
            return userOneTimePass;
        }

        protected Integer getGeneratedOTP(){
            return generatedOneTimePass;
        }
        */
    /*private Integer number;
        private Integer oneTimePass;
        private Integer gOneTimePass;

        // Contact From Database
        protected void setContact(Integer contactFound) {
            this.number = contactFound;
        }

        // OTPActivity From Input
        protected void setUserOTP(Integer userOTP) {
            this.oneTimePass = userOTP;
        }

        // OTPActivity From Generated
        protected void getUserOTP(Integer userOTP){
            this.gOneTimePass = userOTP;
        }

        protected Integer getContact(){
            Integer contactt = number;
            return contactt;
        }


        // Generate OTPActivity
        protected String generateOTP(int length) {
            displayLoader();
            Random obj = new Random();
            char[] otp = new char[length];
            for (int i=0; i<length; i++)
            {
                otp[i]= (char)(obj.nextInt(10)+48);
            }
            String generated = String.valueOf(otp);
            getUserOTP(Integer.parseInt(generated));

            return generated;
        }

        // Generate OTPActivity
        protected void setOTP() {
            Integer value;
            // Using numeric values
            String numbers = "0123456789";

            // Using random method
            Random rndm_method = new Random();
            char[] otp = new char[6];

            for (int i = 0; i < 6; i++)
            {
                // Use of charAt() method : to get character value
                // Use of nextInt() as it is scanning the value as int
                otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
            }

            value = Integer.parseInt(String.valueOf(otp));
            //getUserOTP(value);
        }*/
    /*

        // Compare User And Generated OTPActivity
        */
    /*protected void compareOTP(){
            if(oneTimePass != gOneTimePass){
                Toast.makeText(LoginActivity.this, "OTPActivity not same", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(LoginActivity.this, "OTPActivity same", Toast.LENGTH_SHORT).show();
                Intent successIntent = new Intent(LoginActivity.this, SuccessActivity.class);
                startActivity(successIntent);
            }
        }*/
    /*

        // Send OTPActivity
        */
    /*private void sendOTP() {
            Integer contactFound = number;
            OTP = getOneTimePass.generateOTP(6);
            String contact = contactFound.toString();

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(contact, null, "This is your OTPActivity : " + OTP, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS failed, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            //SmsManager smsManager = SmsManager.getDefault();

            //smsManager.sendTextMessage(contact, null, "This is your OTPActivity : " + OTPActivity, null, null);
            */
    /**//** Check if generated OTPActivity is the same as entered **//**//*
            getOneTimePass.compareOTP();
        }*//*
    }*/

    /*private void checkContact(final String contactEntered) {
        try {
            //Populate the request parameters
            request.put(KEY_CONTACT, contactEntered);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        displayLoader();
        jsArrayRequest = new JsonObjectRequest(Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                try {
                    //Check if user got logged in successfully
                    if (response.getInt(KEY_STATUS) == 0) {
                        session.loginUser(contactEntered,response.getString(KEY_CONTACT));
                        displayLoader();
                        // If exist, generate OTPActivity
                        showOTPAuthentication(LoginActivity.this);

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

    private void showOTPAuthentication(LoginActivity login) {

        Dialog openDialog = new Dialog(login);
        openDialog.setContentView(R.layout.otp_layout);
        openDialog.setTitle("Enter OTPActivity");

        final Integer OTPGenerated = setOTP();
        final EditText editOTP = openDialog.findViewById(R.id.editOTP);
        Button btnSubmit = openDialog.findViewById(R.id.submitBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                OTPActivity = generateOTP(6);
                smsManager.sendTextMessage(studentID, null, "This is your OTPActivity : " + OTPActivity, null, null);
                */
    /** Check if generated OTPActivity is the same as entered **//*
                if(!OTPGenerated.equals(editOTP)){
                    Toast.makeText(LoginActivity.this, "OTPActivity not same", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "OTPActivity same", Toast.LENGTH_LONG).show();
                }
                */
    /** Get OTPActivity, convert from string to integer, store to temp file. **//*
                String getOTP = editOTP.getText().toString();
                String defaultOTP = setOTP().toString();
                if(getOTP.matches(defaultOTP)){
                    Intent successIntent = new Intent(v.getContext(), SuccessActivity.class);
                    v.getContext().startActivity(successIntent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "OTPActivity not same", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    /*
    * final Dialog openDialog = new Dialog(context);
        openDialog.setContentView(R.layout.password_layout);
        openDialog.setTitle("Enter Password");
        final EditText passwordDialog = (EditText)openDialog.findViewById(R.id.password);
        Button loginWithPasswordButton = (Button)openDialog.findViewById(R.id.login_button);
        loginWithPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String authPassword = passwordDialog.getText().toString();
                if(TextUtils.isEmpty(authPassword)){
                    Toast.makeText(view.getContext(), "Password field must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                if(mUser.getPass().equals(authPassword)){
                    Intent userIntent = new Intent(view.getContext(), ProfileActivity.class);
                    userIntent.putExtra("USER_BIO", userString);
                    view.getContext().startActivity(userIntent);
                }else{
                    Toast.makeText(view.getContext(), "Incorrect password! Try again", Toast.LENGTH_LONG).show();
                    return;
                }
                openDialog.dismiss();
            }
        });
        openDialog.show();*/

    /**
     * Launch Dashboard Activity on Successful Login
     */
    /*private void loadDashboard() {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }*/
/*    private void loadOTPAuthentication(View view) {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.otp_layout, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        Button btnSubmit, btnCancel;

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);

        final EditText editOTP = popupView.findViewById(R.id.editOTP);
        btnSubmit = popupView.findViewById(R.id.submitBtn);
        btnCancel = popupView.findViewById(R.id.cancelBtn);
        TextView tvOTP = popupView.findViewById(R.id.tvOTP);

        tvOTP.setText("Enter OTPActivity");

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(editOTP.getWindowToken(), 0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Password", Toast.LENGTH_LONG).show();
                if(KEY_EMPTY.equals(editOTP.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "OTPActivity", Toast.LENGTH_SHORT).show();
                    Integer userOTP = Integer.parseInt(editOTP.getText().toString());
                    getOneTimePass.setUserOTP(userOTP);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
                return true;
            }
        });
    }*/
}
