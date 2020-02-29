package com.example.parsaniahardik.signature_view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SignatureLayout extends AppCompatActivity {

    Bitmap bitmap;
    Button clear,save, back;
    SignatureView signatureView;
    String path, value;
    // Student student = new Student();
    private static final String IMAGE_DIRECTORY = "/sign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature_layout);

        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        back = (Button) findViewById(R.id.back);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
                if(!path.isEmpty()){
                    Toast.makeText(SignatureLayout.this, "File exist", Toast.LENGTH_LONG).show();
                    Toast.makeText(SignatureLayout.this, path, Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignatureLayout.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /** Check if signature is the same from the db, if same proceed to success page else display wrong signature  **/

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fo);
            fo.write(bytes.toByteArray());
            byte[] bArray = bytes.toByteArray();
            String signbit = bArray.toString();
            MediaScannerConnection.scanFile(SignatureLayout.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SignatureLayout.this,  "Save file error!", Toast.LENGTH_LONG).show();
        }
        return "";
    }

    private String getSignString() {
        String signbit = "";

        return signbit;
    }


    /*
    *  public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/ //);
    // have the object build the directory structure, if needed.
        /*if (!wallpaperDirectory.exists()) {
        wallpaperDirectory.mkdirs();
        Log.d("hhhhh",wallpaperDirectory.toString());
    }

        try {
        File f = new File(wallpaperDirectory, Calendar.getInstance()
                .getTimeInMillis() + ".jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        byte[] bArray = bytes.toByteArray();
        fo.write(bytes.toByteArray());
        String signbit = bArray.toString();
        Student student = new Student();
        student.setSignString(signbit);
        signString = getSignString();
        MediaScannerConnection.scanFile(SignatureLayout.this,
                new String[]{f.getPath()},
                new String[]{"image/jpeg"}, null);
        fo.close();
        Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

        //return f.getAbsolutePath();
        return signString;
    } catch (IOException e1) {
        e1.printStackTrace();
    }
        return "";
}*/
}