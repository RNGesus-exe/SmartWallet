package com.rngesus.smartwallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

public class TransferActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    private DocumentReference userDocRef;
    private DocumentReference receiverDocRef;

    String ReceiverEmail=""; // needs to be changed in init()
    String email; // global var used in for each loop
    int amount;
    boolean EmailFlag = false;
    boolean AmountFlag = false;

    EditText etEmail;
    EditText etAmount;
    EditText etConfirmAmount;
    Button btnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        etEmail = findViewById(R.id.etEmail);
        etAmount = findViewById(R.id.etAmount);
        etConfirmAmount = findViewById(R.id.etConfirmAmount);
        btnTransfer = findViewById(R.id.btnTransfer);
        loadProfile();

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiverEmail = etEmail.getText().toString();
                if(checkEmail(ReceiverEmail) == true)
                {
                    if(AmountFlag==true)
                    {
                        executeTransaction();
                    }
                }
            }
        });




    }

    public boolean checkEmail(String email)
    {

        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            EmailFlag = true;
                            return;
                        } else {
                            EmailFlag = false;

                        }

                    }

                });
        return EmailFlag;
    }



    public void loadProfile()
    {


        Query query;
        query = ProfileRef.orderBy("email", Query.Direction.DESCENDING);
                query.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                String data = "";
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Profile profile = documentSnapshot.toObject(Profile.class);
                                    profile.setDocumentId(documentSnapshot.getId());

                                    email = profile.getEmail();
                                     amount = profile.getAmount();

                                    documentSnapshot.getReference();

                                    if (userEmail.equalsIgnoreCase(email)) {
                                        userDocRef = documentSnapshot.getReference();

                                        if(amount >= Integer.parseInt(etAmount.getText().toString()))
                                        {
                                            AmountFlag = true;
                                        }

                                    }

                                    if (ReceiverEmail.equalsIgnoreCase(email))
                                    {
                                        receiverDocRef = documentSnapshot.getReference();
                                    }
                                    //check  comma separated value from qr scan



                                }

                            }
                        });
    }
    private void executeTransaction() {
        db.runTransaction(new Transaction.Function<Long>() {
            @Override
            public Long apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
              //  DocumentReference userProfileRef = ProfileRef.document(userDocRef.toString());
               // DocumentReference receiverProfileRef = ProfileRef.document(receiverDocRef.toString());

                DocumentSnapshot exampleNoteSnapshot = transaction.get(userDocRef);
                long newUserAmount = exampleNoteSnapshot.getLong("Amount") - (Integer.parseInt(etAmount.getText().toString()));
                transaction.update(userDocRef, "Amount", newUserAmount);


                DocumentSnapshot exampleRSnapshot = transaction.get(userDocRef);
                long newReceiverAmount = exampleRSnapshot.getLong("Amount") + (Integer.parseInt(etAmount.getText().toString()));
                transaction.update(receiverDocRef, "Amount", newReceiverAmount);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Long>() {
            @Override
            public void onSuccess(Long result) {
                Toast.makeText(TransferActivity.this, "Transaction Successful " , Toast.LENGTH_SHORT).show();
            }
        });
    }


}