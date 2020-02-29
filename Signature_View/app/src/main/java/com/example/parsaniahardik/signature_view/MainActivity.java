package com.example.parsaniahardik.signature_view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button signIn, signUp;
    public static String value;
    private static final String KEY_EMPTY = "";
    private String login_url = "http://192.168.1.135/test-android/index-login.php";
    JSONParser jsonParser = new JSONParser();
    private String errorMessage1 = "Incorrect details";
    private String errorMessage2 = "Error in registering.";
    private String errorMessage3 = "Error.";
    private String message = "Successfully logged in";
    protected Boolean status;

    SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            loadDashboard();
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dialog openDialog = new Dialog(MainActivity.this);
        openDialog.setContentView(R.layout.admin_layout);

        setTitle("Main Activity");

        signIn = findViewById(R.id.btnLogin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Login";
                Intent loginIntent = new Intent(MainActivity.this, FingerprintActivity.class);
                startActivity(loginIntent);
            }
        });

        signUp = findViewById(R.id.btnRegister);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRegisterAuthentication(view);
//                showRegisterAuthentication(MainActivity.this);

            }
        });
    }

    private void loadDashboard() {
        Intent i = new Intent(MainActivity.this, SuccessActivity.class);
        startActivity(i);
        finish();
    }

    private void loadRegisterAuthentication(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.admin_layout, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);

        final EditText editAdminPass = popupView.findViewById(R.id.editAdminPass);
        Button btnSubmit = popupView.findViewById(R.id.submitBtn);
        Button btnCancel = popupView.findViewById(R.id.cancelBtn);
        TextView tvAdminPass = popupView.findViewById(R.id.tvAdminPass);

        tvAdminPass.setText("Enter Password to Register:");

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(editAdminPass.getWindowToken(), 0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Password", Toast.LENGTH_LONG).show();
                AttemptAdminLogin attemptLogin= new AttemptAdminLogin();
                if(KEY_EMPTY.equals(editAdminPass.getText().toString())){
                    //session.setPassword(editAdminPass.getText().toString());
                    Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_LONG).show();
                }else {
                    attemptLogin.execute(editAdminPass.getText().toString(), "", "");
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
    }

    /*private void showRegisterAuthentication(MainActivity main) {
        Dialog openDialog = new Dialog(main);
        openDialog.setContentView(R.layout.admin_layout);
        openDialog.setTitle("Enter Password to Register");

        final EditText editAdminPass = openDialog.findViewById(R.id.editAdminPass);
        Button btnSubmit = openDialog.findViewById(R.id.submitBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Password", Toast.LENGTH_LONG).show();
                //AttemptLogin attemptLogin= new AttemptLogin();
                //attemptLogin.execute(editAdminPass.getText().toString(),"","");
            }
        });



    }*/

    private class AttemptAdminLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String password = args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password", password));

            JSONObject json = jsonParser.makeHttpRequest(login_url, "POST", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    if(result.getString("message").matches(errorMessage1) || result.getString("message").matches(errorMessage2) || result.getString("message").matches(errorMessage3)){
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_SHORT).show();
                    } else if(result.getString("message").matches(message)){
                        Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(loginIntent);
                    } else{
                        Toast.makeText(getApplicationContext(), "Wrong password entered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
