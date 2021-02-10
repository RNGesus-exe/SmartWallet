package com.rngesus.smartwallet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;

public class TransferActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail =  firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    private DocumentReference userDocRef;
    private DocumentReference receiverDocRef;

    String ReceiverEmail; // needs to be changed in init()
    String email; // global var used in for each loop
    int amount;
    boolean EmailFlag = true;
    boolean AmountFlag = false;

    EditText etEmail;
    EditText etAmount;
    EditText etConfirmAmount;
    Button btnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        firebaseAuth.getCurrentUser().getUid();
        etEmail = findViewById(R.id.etEmail);
        etAmount = findViewById(R.id.etAmount);
        etConfirmAmount = findViewById(R.id.etConfirmAmount);
        btnTransfer = findViewById(R.id.btnTransfer);

        getSupportActionBar().setTitle("Transfer Cash");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTransfer.setOnClickListener(v -> {
            ReceiverEmail = etEmail.getText().toString();
            loadProfile();
            if(checkEmail(ReceiverEmail))
            {
                if(AmountFlag)
                {
                    loadProfile();
                    executeTransaction();
                }else if(!AmountFlag)
                {
                    Toast.makeText(TransferActivity.this," Insufficient funds",Toast.LENGTH_LONG).show();
                }
            }
            else
                {
                       Toast.makeText(TransferActivity.this,"Incorrect Receiver Email",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed(){
        return;
    }
    public boolean checkEmail(String email)
    {

        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                    if(isNewUser) {
                        EmailFlag = false;

                    } else {
                        EmailFlag = true;
                    }
                });
        return EmailFlag;
    }


    public void loadProfile()
    {
        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ReceiverEmail = etEmail.getText().toString();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Profile profile = documentSnapshot.toObject(Profile.class);

                        email = profile.getEmail();

                        amount = profile.getAmount();

                        if (userEmail.equalsIgnoreCase(email)) {
                            userDocRef = documentSnapshot.getReference();

                            if(amount >= Integer.parseInt(etAmount.getText().toString()))
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
                        //check  comma separated value from qr scan
                    }

                }).addOnFailureListener(e -> Toast.makeText(TransferActivity.this,"failed to get query results",Toast.LENGTH_SHORT).show());
    }


    private void executeTransaction() {
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentSnapshot senderSnapshot = transaction.get(userDocRef);
            Long newUserAmount = senderSnapshot.getLong("Amount") - (Integer.parseInt(etAmount.getText().toString()));
            DocumentSnapshot RSnapshot = transaction.get(receiverDocRef);
            Long newRAmount = RSnapshot.getLong("Amount") + (Integer.parseInt(etAmount.getText().toString()));
            transaction.update(userDocRef, "Amount",newUserAmount);
            transaction.update(receiverDocRef, "Amount", newRAmount);
            return null;
        }).addOnCompleteListener(task -> Toast.makeText(TransferActivity.this,"Complete",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(TransferActivity.this,"failed transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());

    }
}