package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class out_come extends AppCompatActivity implements OutAdapter.ItemSelected{
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Outcome_data> arrayList;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_come);
        recyclerView=findViewById(R.id.recycle2);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dialog=new Dialog(this);
        arrayList=new ArrayList<Outcome_data>();
        arrayList.add(new Outcome_data("5","5000","king","why i tell you","monday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","tuesday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","Wednesday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","Thursday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","Friday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","Saturday"));
        arrayList.add(new Outcome_data("5","500","king","why i tell you","Sunday"));

        myAdapter=new OutAdapter( arrayList,this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void Select(int index) {
        dialog.setContentView(R.layout.test2);
        TextView date,day,sender,income,reason,cross;
        cross=dialog.findViewById(R.id.txtclose);
        date=dialog.findViewById(R.id.cdata);
        day=dialog.findViewById(R.id.Cday);
        sender=dialog.findViewById(R.id.sender);
        income=dialog.findViewById(R.id.cIncome);
        reason=dialog.findViewById(R.id.Creason);
        date.setText(arrayList.get(index).getDate());
        day.setText(arrayList.get(index).getDay());
        sender.setText(arrayList.get(index).getReceiver());
        income.setText(arrayList.get(index).getOutcome());
        reason.setText(arrayList.get(index).getReason());


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    }

