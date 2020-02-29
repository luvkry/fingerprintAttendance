package com.example.parsaniahardik.signature_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OTPActivity extends AppCompatActivity {

    private static final String KEY_EMPTY = "";

    Button btnSubmit, btnCancel;
    EditText editOTP;
    TextView tvOTP;
    Integer userOTP, genOTP, setOTP;
    String userContact;

    OTP getOneTimePassword = new OTP();
    AttemptGetOTP attemptGetOTP = new AttemptGetOTP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        setTitle("OTP Activity");

        Intent intent = getIntent();
        userContact = intent.getStringExtra("message");
        //Toast.makeText(OTPActivity.this, userContact, Toast.LENGTH_SHORT).show();

        getOneTimePassword.setGeneratedOTP();
        genOTP = getOneTimePassword.getGeneratedOTP();

        editOTP = findViewById(R.id.editOTP);
        btnSubmit = findViewById(R.id.submitOBtn);
        btnCancel = findViewById(R.id.cancelOBtn);
        tvOTP = findViewById(R.id.tvOTP);

        attemptGetOTP.sendOTP(userContact);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(OTPActivity.this, LoginActivity.class);
                startActivity(backIntent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
                    userOTP = Integer.parseInt(editOTP.getText().toString());
                    attemptGetOTP.compareOTP();
                }
            }
        });


    }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(editOTP.getText().toString())){
            editOTP.setError("OTP cannot be empty");
            editOTP.requestFocus();
            return false;
        }
        return true;
    }

    class AttemptGetOTP {

        // Compare User And Generated OTP
        private void compareOTP(){
            if(userOTP.equals(genOTP)){
                Intent successIntent = new Intent(OTPActivity.this, SuccessActivity.class);
                startActivity(successIntent);
            } else{
                Toast.makeText(OTPActivity.this, "Please enter the OTP again", Toast.LENGTH_SHORT).show();
            }
        }

        // Send OTP
        private void sendOTP(String contact) {
            try {
                if(genOTP != null){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact, null, "This is your OTP : " + genOTP, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(OTPActivity.this, "OTP not generated", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS failed, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            //SmsManager smsManager = SmsManager.getDefault();

            //smsManager.sendTextMessage(contact, null, "This is your OTPActivity : " + OTPActivity, null, null);
            /** Check if generated OTPActivity is the same as entered **/

        }
    }
}

/*public class HelloWorld{

    public static void main(String []args){
        AnotherClass another = new AnotherClass();
        another.setStr("ahhy");
        another.printStr();
        String value = another.getStr();
        System.out.println(value);
    }
}
public Boolean setStatus(){
          Boolean state = false;
          if(string1 != string2){
              return state = true;
          }
          return state;
      }
*/