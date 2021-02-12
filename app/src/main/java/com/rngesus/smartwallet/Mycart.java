package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.StringTokenizer;

public class Mycart extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail = firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    private DocumentReference userDocRef;
    String email;
    int amount;
    int UserAmount = 0;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        btn = findViewById(R.id.purchase);
        if (ShopActivity.whislist_dataArray == null) {

            Toast.makeText(this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
        }
        else {
            txt = findViewById(R.id.txt7);
            // This value store total price
            int val = getTotalprice();
            txt.setText("TOtal amount:" + String.valueOf(val));

            // Recyclerview
            recyclerView = findViewById(R.id.mycart);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            myAdapter = new WhislistAdapter(ShopActivity.whislist_dataArray);
            recyclerView.setAdapter(myAdapter);
        }


        // Button to confirm Purchase
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (loadUserBalance() >= getTotalprice()) {

                     {
                         View view = null;
                         executeTransaction();
                         Date currentTime = Calendar.getInstance().getTime();
                         String Str = currentTime.toString();
                         String []allParts = Str.split("\\s+");
                         String date = allParts[0]+", "+ allParts[1]+", "+ allParts[2];
                         String time = allParts[3];
                         DataManager dataManager = new DataManager();
                         dataManager.addOutcomeReceipt("Shop", date, time, firebaseAuth.getCurrentUser().getUid(),"Checkout ",getTotalprice()+"",view,false);
                         Intent intent = new Intent(Mycart.this,NavigationActivity.class);
                         startActivity(intent);


                    }
                }
                else {
                    Toast.makeText(Mycart.this, "Try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private int getTotalprice() {
        int addprice = 0;
        for (int i = 0; i < ShopActivity.whislist_dataArray.size(); i++) {
            String price = ShopActivity.whislist_dataArray.get(i).getPrice();
            addprice += Integer.parseInt(price);
        }
        return addprice;
    }

    private int loadUserBalance() {

        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {


                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Profile profile = documentSnapshot.toObject(Profile.class);
                        email = profile.getEmail();
                        amount = profile.getAmount();
                        if (userEmail.equalsIgnoreCase(email)) {
                            UserAmount = amount;
                            userDocRef = documentSnapshot.getReference();
                        }
                    }
                }).addOnFailureListener(e -> Toast.makeText(this, "failed to get query results", Toast.LENGTH_SHORT).show());
        return UserAmount;
    }

    private void executeTransaction() {
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentSnapshot senderSnapshot = transaction.get(userDocRef);
            Long newUserAmount = senderSnapshot.getLong("Amount") - (getTotalprice());
            transaction.update(userDocRef, "Amount", newUserAmount);
            return null;
        }).addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {
                Toast.makeText(Mycart.this, "Complete", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(e -> Toast.makeText(this, "failed transfer" + e.getMessage(), Toast.LENGTH_SHORT).show());

    }
}