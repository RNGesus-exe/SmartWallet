package com.rngesus.smartwallet;

import android.graphics.Bitmap;
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
   private String FullName;
   private String RFullName=" n  ";
    String REmail;
    String userEmail = firebaseAuth.getCurrentUser().getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        etValue = findViewById(R.id.etValue);
        btnGetCode = findViewById(R.id.btnGetCode);
        qrcode = findViewById(R.id.qrcode);

       btnGetCode.setOnClickListener(v -> {
            if( !etValue.getText().toString().isEmpty())
            {   String encodedString;
                //String REmail = firebaseAuth.getCurrentUser().getEmail();
                //String RName = firebaseAuth.getCurrentUser().getDisplayName();

                loadProfile();
                Toast.makeText(generateActivity.this, "name  found"+RFullName, Toast.LENGTH_SHORT).show();
                loadProfile();
                Toast.makeText(generateActivity.this, "name  found"+RFullName, Toast.LENGTH_SHORT).show();

                String qrAmount = etValue.getText().toString().trim();
                encodedString = (userEmail+","+RFullName+","+qrAmount);

                QRGEncoder qrgEncoder = new QRGEncoder(encodedString,null, QRGContents.Type.TEXT,1000);


                Bitmap bitmap = qrgEncoder.getBitmap();

                qrcode.setImageBitmap(bitmap);
               // qrcode.setBackgroundColor(Color.BLUE);
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

    public void loadProfile()
    {

        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Profile profile = documentSnapshot.toObject(Profile.class);
                        REmail = profile.getEmail();
                        FullName = profile.getFullname();

                        if (REmail.equalsIgnoreCase(userEmail)) {
                            RFullName = FullName;
                            Toast.makeText(generateActivity.this, "name  found"+RFullName, Toast.LENGTH_SHORT).show();
                        }

                    }

                }).addOnFailureListener(e -> Toast.makeText(generateActivity.this,"failed to get query results",Toast.LENGTH_SHORT).show());
    }


}