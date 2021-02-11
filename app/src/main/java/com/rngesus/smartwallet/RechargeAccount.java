package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.rngesus.smartwallet.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class RechargeAccount extends AppCompatActivity {
    EditText RechargeCode;
    Button btn;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private String card;
    String str ="";
    private String rechargeamount;
    private Firebase fb = new Firebase();
    private DataManager dataManager = new DataManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_account);
        init();
    }

    private void init() {
        getSupportActionBar().setTitle("Recharge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RechargeCode = findViewById(R.id.etRechargeCode);
        btn = findViewById(R.id.recharge);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void Recharge(View view) {
        DocumentReference docRef;
        String pin = RechargeCode.getText().toString();
        fb.loadCardsData(pin,this);
        docRef =  fb.loadReceiverDocRef(mAuth.getCurrentUser().getEmail(),this);


        if (pin.equals("")) {
            Toast.makeText(this, "Please Enter Recharge Pin ", Toast.LENGTH_SHORT).show();
        }else
        {
            if(fb.pinFlag) {
                fb.loadCardsData(pin, this);
                docRef =  fb.loadReceiverDocRef(mAuth.getCurrentUser().getEmail(),this);
                if(docRef!=null) {
                    fb.executeCardTransaction(this, docRef);
                    Date currentTime = Calendar.getInstance().getTime();
                    String Str = currentTime.toString();
                    String[] allParts = Str.split("\\s+");
                    String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
                    String time = allParts[3];
                    DataManager dataManager = new DataManager();
                    dataManager.addIncomeReceipt(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            date, time, "Recharge Retailer",
                            "QR Transfer",String.valueOf(fb.recharge_amount), view, false);
                    dataManager.addOutcomeReceipt("Recharge", DateTime.getDefaultInstance().toString(),Timestamp.now().toString(),"NA","Account Top up",fb.CardAmount+"",view,false);
                    RechargeCode.setText("");

                }
                else{
                    Toast.makeText(this, "Loading Failed please try again ", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

}