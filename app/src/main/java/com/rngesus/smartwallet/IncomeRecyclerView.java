package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class IncomeRecyclerView extends AppCompatActivity{

    RecyclerView recyclerView;
    IncomeAdapter income_adapter;
    FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    String userID = firebaseAuth.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_rv);

       recyclerView=findViewById(R.id.rv_income);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       getSupportActionBar().setTitle("Income");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       FirebaseRecyclerOptions<Income> options =
               new FirebaseRecyclerOptions.Builder<Income>()
                       .setQuery(FirebaseDatabase.getInstance().getReference()
                       .child("Users")
                       .child(userID).child("Income"), Income.class)
                       .build();

       income_adapter=new IncomeAdapter(options);
       recyclerView.setAdapter(income_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        income_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        income_adapter.stopListening();
    }
    @Override
    public void onBackPressed(){
        return;
    }
}