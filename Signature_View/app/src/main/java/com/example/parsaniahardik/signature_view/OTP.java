package com.example.parsaniahardik.signature_view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Random;

public class OTP {

    protected Integer userContact;
    protected Integer userOneTimePass;
    protected Integer generatedOneTimePass;

    // Contact From Database
    protected void setUserContact(Integer contactFound) {
        this.userContact = contactFound;
    }

    // OTP From Input
    protected void setUserOTP(Integer userOTP) {
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


    /*protected void getUserOTP(Integer userOTP){
        this.gOneTimePass = userOTP;
    }*/

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

    // Generate OTP
    /*protected String generateOTP(int length) {
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
    public void setOTP() {
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
}
