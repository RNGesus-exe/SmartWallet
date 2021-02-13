package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    FirebaseFirestore firebaseFirestore;
    TextView txt;
    int Amountofuser;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        btn = findViewById(R.id.purchase);
        if (ShopActivity.wishListArray == null) {

            Toast.makeText(this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
        } else {

            txt = findViewById(R.id.txt7);
            // This value store total price
            int val = getTotalprice();
            txt.setText("TOtal amount:" + String.valueOf(val));

            // Recyclerview
            recyclerView = findViewById(R.id.mycart);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            myAdapter = new WishListAdapter(ShopActivity.wishListArray);
            recyclerView.setAdapter(myAdapter);
        }


        // Button to confirm Purchase
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loadUserBalance(new MyCallback() {
                   @Override
                   public void onCallback(int pin_list) {
                       if(pin_list>getTotalprice())
                       {
                           executeTransaction(pin_list);
                           Date currentTime = Calendar.getInstance().getTime();
                           String Str = currentTime.toString();
                           String[] allParts = Str.split("\\s+");
                           String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
                           String time = allParts[3];
                           DataManager dataManager = new DataManager();
                           dataManager.addOutcomeReceipt("Shop", date, time,
                                   firebaseAuth.getCurrentUser().getUid(), " You bought stuff from shop ",
                                   String.valueOf(getTotalprice()), v, false);
                           Intent intent = new Intent(Mycart.this, NavigationActivity.class);
                           startActivity(intent);

                       }
                       else
                       {
                           Toast.makeText(Mycart.this, "You don't have enough balance", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });


    }
    public interface MyCallback {
        void onCallback(int pin_list);
    }

    private int getTotalprice() {
        int addprice = 0;
        for (int i = 0; i < ShopActivity.wishListArray.size(); i++) {
            String price = ShopActivity.wishListArray.get(i).getPrice();
            addprice += Integer.parseInt(price);
        }
        return addprice;
    }

    private void  loadUserBalance( MyCallback callback)
    {

        String user = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("USERS").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Amountofuser=documentSnapshot.getLong("Amount").intValue();
                callback.onCallback(Amountofuser);

            }


        });
    }

    private void executeTransaction(int value) {

        int val = value - getTotalprice();
        Toast.makeText(this, "Total value is"+val, Toast.LENGTH_SHORT).show();
        String user = firebaseAuth.getCurrentUser().getUid();

       final DocumentReference documentReference=FirebaseFirestore.getInstance().collection("USERS").document(user);
        Map<String,Object>map=new HashMap<>();
        map.put("Amount",val);
        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Mycart.this, "FINALLY ADDED", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Mycart.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}