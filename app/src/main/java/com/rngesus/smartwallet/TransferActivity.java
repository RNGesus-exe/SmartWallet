package com.rngesus.smartwallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

public class TransferActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etAmount;
    private EditText etConfirmAmount;
    private Button btnTransfer;
    private FirebaseFirestore db;
    private boolean amount_flag = false;
    private DocumentReference receiver_ref = null;
    private DocumentReference user_ref = null;


    public interface TransferCallBack {
        void amount_get_callback(boolean amount_and_receiver_flag,DocumentReference receiver_ref,
                                 DocumentReference current_ref);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        init();

        btnTransfer.setOnClickListener(v -> {
            if(etEmail.getText().toString().trim().isEmpty()){
                Toast.makeText(TransferActivity.this, "Enter the email of the receiver", Toast.LENGTH_LONG).show();
            }
            else if(etAmount.getText().toString().trim().isEmpty()){
                Toast.makeText(TransferActivity.this, "Enter the Amount to send", Toast.LENGTH_LONG).show();
            }
            else if (etConfirmAmount.getText().toString().trim().isEmpty()){
                Toast.makeText(TransferActivity.this, "Enter the Amount again in confirm amount text field", Toast.LENGTH_LONG).show();
            }
            else if(!etAmount.getText().toString().trim().equals(etConfirmAmount.getText().toString().trim())){
                Toast.makeText(TransferActivity.this, "Amount and Confirm Amount do not match!!", Toast.LENGTH_SHORT).show();
            }
            else{
                checkReceiverAndAmount(Integer.parseInt(etAmount.getText().toString()),
                        etEmail.getText().toString(), (amount_flag, receiver_ref,user_ref) -> {
                            if(!amount_flag){
                                Toast.makeText(TransferActivity.this, "You don't have enough balance!!", Toast.LENGTH_SHORT).show();
                            }
                            else if(receiver_ref == null) {
                                Toast.makeText(TransferActivity.this, "The receiver does not exist!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                db.runTransaction((Transaction.Function<Integer>) transaction -> {
                                    DocumentSnapshot senderSnapshot = transaction.get(user_ref);
                                    Long newUserAmount = senderSnapshot.getLong("Amount") - (Integer.parseInt(etAmount.getText().toString()));
                                    DocumentSnapshot RSnapshot = transaction.get(receiver_ref);
                                    Long newRAmount = RSnapshot.getLong("Amount") + (Integer.parseInt(etAmount.getText().toString()));
                                    transaction.update(user_ref, "Amount",newUserAmount);
                                    transaction.update(receiver_ref, "Amount", newRAmount);

                                    Date currentTime = Calendar.getInstance().getTime();
                                    String Str = currentTime.toString();
                                    String[] allParts = Str.split("\\s+");
                                    String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
                                    String time = allParts[3];
                                    StringTokenizer tokens = new StringTokenizer(etEmail.getText().toString().trim(), "@");
                                    String receiver = tokens.nextToken();

                                    DataManager dataManager = new DataManager();
                                    dataManager.addOutcomeReceipt(receiver, date, time,
                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                            " Transfer Cash ", etAmount.getText().toString(), v, false);
                                    dataManager = new DataManager();

                                    tokens = new StringTokenizer(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "@");
                                    String sender = tokens.nextToken();

                                    dataManager.addIncomeReceipt(receiver_ref.getId(),
                                            date, time, sender,
                                            "Transfer Cash", etAmount.getText().toString(), v, false);

                                    etAmount.setText("");
                                    etConfirmAmount.setText("");
                                    etEmail.setText("");

                                    return null;
                                })
                                        .addOnCompleteListener(task -> Toast.makeText(TransferActivity.this,"Transaction Complete",Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(TransferActivity.this,"Failed Transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());
                            }
                        });
            }
        });


    }

    private void checkReceiverAndAmount(int amount, String receiver,TransferCallBack transferCallBack) {
        db.collection("USERS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            System.out.println(document);
                            if(document.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    && document.getLong("Amount")>=amount){
                                amount_flag = true;
                                user_ref = document.getReference();
                            }
                            if(document.getString("email").equals(receiver)){
                                receiver_ref = document.getReference();
                            }
                        }
                        transferCallBack.amount_get_callback(amount_flag, receiver_ref,user_ref);
                    } else {
                        Toast.makeText(this, "An error occurred while Loading!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etAmount = findViewById(R.id.etAmount);
        etConfirmAmount = findViewById(R.id.etConfirmAmount);
        btnTransfer = findViewById(R.id.btnTransfer);
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Transfer Cash");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed(){
        return;
    }
}