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

import java.util.List;

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
                    dataManager.addOutcomeReceipt("Recharge", DateTime.getDefaultInstance().toString(),Timestamp.now().toString(),"NA","Account Top up",fb.CardAmount+"",view,false);
                    RechargeCode.setText("");

                }
                else{
                    Toast.makeText(this, "Loading Failed please try again ", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    private void emailAuthentication(String strUserName) {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {

                            final Dialog dialog = new Dialog(RechargeAccount.this); // Context, this, etc.
                            dialog.setContentView(R.layout.recharge_layout);
                            dialog.show();

                        } else {
                            Toast.makeText(RechargeAccount.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void readDocument(int val)
    {
             firebaseFirestore.collection("Cards").whereEqualTo("recharge",val).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                 @Override
                 public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                     List<DocumentSnapshot>snapshotList=queryDocumentSnapshots.getDocuments();
                     for(DocumentSnapshot snapshot:snapshotList)
                     {
                         card=snapshot.getId().toString();
                         rechargeamount=snapshot.getData().toString();
                         dialoge();


                     }

                 }

             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(RechargeAccount.this, "Failure", Toast.LENGTH_SHORT).show();

                 }
             });
    }
public void dialoge()
{
    // This will create dialog and set id of Documnet
    final Dialog dialog = new Dialog(RechargeAccount.this); // Context, this, etc.
    dialog.setContentView(R.layout.rechargedialogue2);
    TextView key=dialog.findViewById(R.id.key);
    Button btn=dialog.findViewById(R.id.dialogebtn);
    EditText rechargekey=dialog.findViewById(R.id.rechrgekey);
    key.setText(card);

    //This loop will only get in integer part from {recharge=integer}

    for(int i=0; i<rechargeamount.length(); i++)
    {
        if(Character.isDigit(rechargeamount.charAt(i)))
        {
            str=str+rechargeamount.charAt(i);
        }
    }
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String RK=rechargekey.getText().toString();
          String RK2=key.getText().toString();
          if(!RK.isEmpty()) {
              Toast.makeText(RechargeAccount.this, ""+RK2+" "+RK, Toast.LENGTH_LONG).show();
              if (RK
                      .equals(RK2)) {
                  String homevalue = (String) HomeFragment.balance.getText();
                  int finalamount = Integer.parseInt(str) + Integer.parseInt(homevalue);
                  HomeFragment.balance.setText(String.valueOf(finalamount));
              }
              else
              {
                  Toast.makeText(RechargeAccount.this, "Key Mismatch", Toast.LENGTH_SHORT).show();
              }
          }
           else
           {
               Toast.makeText(RechargeAccount.this, "Please write the key", Toast.LENGTH_SHORT).show();
           }




        }
    });

    dialog.show();
}

}