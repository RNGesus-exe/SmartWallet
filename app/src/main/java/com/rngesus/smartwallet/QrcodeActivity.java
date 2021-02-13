package com.rngesus.smartwallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class QrcodeActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    Button btnGenerate;
 Button btnScan;
 ImageView ivQR;
 public static final int  scan = 1;
 public static final int  generate = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().setTitle("QrCode");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       init();


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        btnGenerate.setOnClickListener(v -> {
            Intent intent = new Intent(QrcodeActivity.this, generateActivity.class);
            startActivityForResult(intent,generate);


        });

        btnScan.setOnClickListener(v -> {
            Intent intent = new Intent(QrcodeActivity.this, ScanActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


}