package com.rngesus.smartwallet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class generateActivity extends AppCompatActivity {
    ImageView qrcode;
    EditText etValue;
    Button btnGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        etValue = findViewById(R.id.etValue);
        btnGetCode = findViewById(R.id.btnGetCode);
        qrcode = findViewById(R.id.qrcode);

       btnGetCode.setOnClickListener(v -> {
            if( !etValue.getText().toString().isEmpty())
            {
                String value = etValue.getText().toString().trim();
                QRGEncoder qrgEncoder = new QRGEncoder(value,null, QRGContents.Type.TEXT,1000);

                Bitmap bitmap = qrgEncoder.getBitmap();

                qrcode.setImageBitmap(bitmap);
             //   qrcode.setBackgroundColor(Color.BLUE);
            }
            else
            {
                Toast.makeText(generateActivity.this,"No value entered!", Toast.LENGTH_SHORT).show();
            }
       });
    }
    public void removeBackground( Bitmap bmp)
    {

    }

}