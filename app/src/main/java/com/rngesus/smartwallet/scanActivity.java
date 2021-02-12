package com.rngesus.smartwallet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.type.DateTime;
import com.google.zxing.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

public class scanActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail =  firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    private DocumentReference userDocRef;
    private DocumentReference receiverDocRef;
    DataManager dataManager = new DataManager();
    boolean EmailFlag = true;
    boolean AmountFlag = false;
    String ReceiverEmail; // needs to be changed in init()
    String email; // global var used in for each loop
    int amount;
    private CodeScanner mCodeScanner;
    Dialog dialog;
    ImageButton BtnCancel;
//    ImageButton btnConfirm;
    EditText etTransferAmount;
    String TransferAmount;
    String REmail;
    String RUid;
    String RName;
    Firebase fb=new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        dialog = new Dialog(this);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   Toast.makeText(scanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        String scannerOutput = result.getText();
                        StringTokenizer tokens = new StringTokenizer(scannerOutput, ",");
                        REmail = tokens.nextToken();
                        RName = tokens.nextToken();
                        TransferAmount = tokens.nextToken();
                        RUid = tokens.nextToken();
                     //   Toast.makeText(scanActivity.this, "Name"+RName, Toast.LENGTH_SHORT).show();
                        confirmation();



                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    public void confirmation(){

        dialog.setContentView(R.layout.confirm_view);
     //   btnConfirm = dialog.findViewById(R.id.btnConfirm);
        BtnCancel = dialog.findViewById(R.id.btnCancel);
        etTransferAmount = dialog.findViewById(R.id.etTransferAmount);
        etTransferAmount.setText(TransferAmount);
        loadData();

     //   btnConfirm.setOnClickListener(v -> {
      //      loadData();
      //      executeTransaction();

       // });

        BtnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }
    public void executeTransaction()
    {
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentSnapshot senderSnapshot = transaction.get(userDocRef);
            Long newUserAmount = senderSnapshot.getLong("Amount") - (Integer.parseInt(TransferAmount));
            DocumentSnapshot RSnapshot = transaction.get(receiverDocRef);
            Long newRAmount = RSnapshot.getLong("Amount") + (Integer.parseInt(TransferAmount));
            transaction.update(userDocRef, "Amount",newUserAmount);
            transaction.update(receiverDocRef, "Amount", newRAmount);
            return null;
        }).addOnCompleteListener(task -> Toast.makeText(scanActivity.this,"Complete",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(scanActivity.this,"failed transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());

    }

    public void loadData()
    {
        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ReceiverEmail = REmail;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Profile profile = documentSnapshot.toObject(Profile.class);

                        email = profile.getEmail();

                        amount = profile.getAmount();

                        if (userEmail.equalsIgnoreCase(email)) {
                            userDocRef = documentSnapshot.getReference();

                            if(amount >= Integer.parseInt(TransferAmount))
                            {
                                AmountFlag = true;
                            }
                            else
                            {
                                AmountFlag = false;
                            }

                        }
                        if (ReceiverEmail.equalsIgnoreCase(email))
                        {
                            receiverDocRef = documentSnapshot.getReference();
                        }

                    }

                }).addOnFailureListener(e -> Toast.makeText(scanActivity.this,"failed to get query results",Toast.LENGTH_SHORT).show());
    }
    public void btnClicked(View view)
    {

        if(fb.loadReceiverDocRef(ReceiverEmail, view.getContext()) != null) {
            loadData();
            executeTransaction();
            //--------ADD RECEIPT FUNCTIONS HERE
            Date currentTime = Calendar.getInstance().getTime();
            String Str = currentTime.toString();
            String[] allParts = Str.split("\\s+");
            String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
            String time = allParts[3];
            StringTokenizer tokens = new StringTokenizer(ReceiverEmail, "@");
            String receiver = tokens.nextToken();
            DataManager dataManager = new DataManager();
            dataManager.addOutcomeReceipt(receiver, date, time,
                    firebaseAuth.getCurrentUser().getUid(), " QR Transfer ", TransferAmount, view, false);
            dataManager = new DataManager();
            tokens = new StringTokenizer(firebaseAuth.getCurrentUser().getEmail(), "@");
            String sender = tokens.nextToken();
            dataManager.addIncomeReceipt(RUid,
                    date, time, sender,
                    "QR Transfer", TransferAmount, view, false);
        }
        else{
            Toast.makeText(this, "An error occurred please try again!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
