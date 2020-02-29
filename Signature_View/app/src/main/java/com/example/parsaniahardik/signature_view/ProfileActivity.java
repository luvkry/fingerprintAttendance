package com.example.parsaniahardik.signature_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {

    TextView getKeyValue;
    Button getkeyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile Page");


        getkeyBtn = (Button)findViewById(R.id.getValue);

        String userBio = getIntent().getExtras().getString("USER_BIO");
        Gson gson = ((CustomApplication)getApplication()).getGsonObject();

        UserObject mUserObject = gson.fromJson(userBio, UserObject.class);
        String bio = mUserObject.getName() + "\n" +
                mUserObject.getPass() + "\n" +
                mUserObject.getCfmPass();

        TextView userTextValue = (TextView)findViewById(R.id.user_bio);
        userTextValue.setText(bio);
        getkeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            }
        });
    }
}
