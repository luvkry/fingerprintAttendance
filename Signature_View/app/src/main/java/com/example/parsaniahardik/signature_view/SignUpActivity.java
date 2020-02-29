package com.example.parsaniahardik.signature_view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {

    protected TextView displayError;
    protected EditText editName, editPass, editCfmPass;
    protected RadioGroup radioGroup;
    protected Button nextBtn, cancelBtn;
    protected boolean loginOption;
    protected Spinner spinMod,spinYear;
    protected String userrole,branch,year;

    private String[] modString = new String[] {"DB3","NS3","ALGO3","IS3","OS3", "PL3"};
    private String[] yearString = new String[] {"FIRST","SECOND","THIRD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("Android Fingerprint Registration");

        editName = (EditText)findViewById(R.id.editName);
        editPass = (EditText)findViewById(R.id.editPass);
        editCfmPass = (EditText)findViewById(R.id.editCfmPass);
        spinMod = (Spinner)findViewById(R.id.spinMod);
        spinYear = (Spinner)findViewById(R.id.spinYear);

        /* Spinner */
        spinMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch =(String) spinMod.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> mod_branch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, modString);
        mod_branch
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMod.setAdapter(mod_branch);

        ///......................spinner2

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                year =(String) spinYear.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> year_branch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearString);
        year_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinYear.setAdapter(year_branch);
        cancelBtn = (Button)findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        nextBtn = (Button)findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue, passValue, cfmPassValue;
                nameValue = editName.getText().toString();
                passValue = editPass.getText().toString();
                cfmPassValue = editCfmPass.getText().toString();

                if(TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(passValue)|| TextUtils.isEmpty(cfmPassValue)){
                    Toast.makeText(SignUpActivity.this, "All input fields must be filled", Toast.LENGTH_LONG).show();
                }else if(!passValue.equals(cfmPassValue)&& !cfmPassValue.equals(passValue)){
                    Toast.makeText(SignUpActivity.this, "Login option must be selected", Toast.LENGTH_LONG).show();
                }else{
                    /*Gson gson = ((CustomApplication)getApplication()).getGsonObject();

                    String userDataString = gson.toJson(userData);
                    CustomSharedPreference pref = ((CustomApplication)getApplication()).getShared();
                    pref.setUserData(userDataString);

                    editName.setText("");
                    editPass.setText("");
                    editCfmPass.setText("");

                    Intent loginIntent = new Intent(SignUpActivity.this, FingerprintActivity.class);
                    startActivity(loginIntent);*/
                }
            }
        });
    }
}
