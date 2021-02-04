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
       


        myAdapter=new OutAdapter( OutcomeApplication.arrayList2,this);
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
        date.setText(OutcomeApplication.arrayList2.get(index).getDate());
        day.setText(OutcomeApplication.arrayList2.get(index).getDay());
        sender.setText(OutcomeApplication.arrayList2.get(index).getReceiver());
        income.setText(OutcomeApplication.arrayList2.get(index).getOutcome());
        reason.setText(OutcomeApplication.arrayList2.get(index).getReason());


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    }

