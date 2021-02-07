package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class OutcomeRecyclerView extends AppCompatActivity{
    RecyclerView recycler_view;
    OutcomeAdapter outcome_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_rv);

        recycler_view=findViewById(R.id.rv_outcome);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Outcome> options =
                new FirebaseRecyclerOptions.Builder<Outcome>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child("123")
                                .child("Outcome"), Outcome.class).build();

        outcome_adapter = new OutcomeAdapter(options);
        recycler_view.setAdapter(outcome_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        outcome_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        outcome_adapter.stopListening();
    }
}

