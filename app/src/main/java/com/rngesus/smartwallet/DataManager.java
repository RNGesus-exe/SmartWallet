package com.rngesus.smartwallet;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataManager {

    DatabaseReference firebaseDatabase;

    DataManager(){
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void createUserInFirebase(String userId, String username, View view,boolean showToast){
        firebaseDatabase = firebaseDatabase.child(userId);
        HashMap<String,String> user = new HashMap<>();
        user.put("username",username);
        firebaseDatabase.setValue(user).addOnSuccessListener(aVoid -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Account Creation Successful!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Account Creation Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addIncomeReceipt(String receiverID,String dateTime,String time,String sender,String reason,String amount,View view,boolean showToast){
        firebaseDatabase = firebaseDatabase.child(receiverID).child("Income");

        HashMap<String,String> incomeReceipt = new HashMap<>();
        incomeReceipt.put("date",dateTime);
        incomeReceipt.put("amount",amount);
        incomeReceipt.put("time",time);
        incomeReceipt.put("reason",reason);
        incomeReceipt.put("sender",sender);

        firebaseDatabase.push().setValue(incomeReceipt).addOnSuccessListener(aVoid -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Receipt Creation Successful!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Receipt Creation Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addOutcomeReceipt(String receiver,String dateTime,String time,String senderID,String reason,String amount,View view,boolean showToast){
        firebaseDatabase = firebaseDatabase.child(senderID).child("Outcome");

        HashMap<String,String> outcomeReceipt = new HashMap<>();
        outcomeReceipt.put("date",dateTime);
        outcomeReceipt.put("amount",amount);
        outcomeReceipt.put("time",time);
        outcomeReceipt.put("reason",reason);
        outcomeReceipt.put("receiver",receiver);

        firebaseDatabase.push().setValue(outcomeReceipt).addOnSuccessListener(aVoid -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Receipt Creation Successful!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            if(showToast) {
                Toast.makeText(view.getContext(), "Receipt Creation Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
