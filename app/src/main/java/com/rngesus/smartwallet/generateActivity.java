package com.rngesus.smartwallet;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class generateActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    ImageView qrcode;
    EditText etValue;
    Button btnGetCode;
    String userEmail = firebaseAuth.getCurrentUser().getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        init();
       btnGetCode.setOnClickListener(v -> {
            if( !etValue.getText().toString().isEmpty())
            {   String encodedString;
                String qrAmount = etValue.getText().toString().trim();
                encodedString = (userEmail+","+qrAmount+","+firebaseAuth.getCurrentUser().getUid());
                QRGEncoder qrgEncoder = new QRGEncoder(encodedString,null, QRGContents.Type.TEXT,1000);
                Bitmap bitmap = qrgEncoder.getBitmap();
                qrcode.setImageBitmap(bitmap);
            }
            else
            {
                Toast.makeText(generateActivity.this,"No value entered!", Toast.LENGTH_SHORT).show();
            }
       });
    }

    public void init()
    {

        etValue = findViewById(R.id.etValue);
        btnGetCode = findViewById(R.id.btnGetCode);
        qrcode = findViewById(R.id.qrcode);

    }

}