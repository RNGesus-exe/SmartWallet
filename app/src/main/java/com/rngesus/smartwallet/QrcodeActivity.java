package com.rngesus.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class QrcodeActivity extends AppCompatActivity {

 Button btnGenerate;
 Button btnScan;
 ImageView ivQR;
 public static final int  scan = 1;
 public static final int  generate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().setTitle("QrCode");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       init();

        btnGenerate.setOnClickListener(v -> {
            Intent intent = new Intent(QrcodeActivity.this, generateActivity.class);
            startActivityForResult(intent,generate);


        });

        btnScan.setOnClickListener(v -> {
            Intent intent = new Intent(QrcodeActivity.this, scanActivity.class);
            startActivityForResult(intent,scan);




        });





    }
    public void init()
    {
        btnGenerate = findViewById(R.id.btnGenerate);
        btnScan = findViewById(R.id.btnScan);
        ivQR = findViewById(R.id.ivQR);
    }
    @Override
    public void onBackPressed(){
        return;
    }


}