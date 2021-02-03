package com.rngesus.smartwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class My_Income extends AppCompatActivity implements Income_Adapter.ItemSelected {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Income_data>arrayList;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__income);

       recyclerView=findViewById(R.id.recycle);
       recyclerView.setHasFixedSize(true);
       layoutManager=new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       dialog=new Dialog(this);

       arrayList=new ArrayList<Income_data>();
        arrayList.add(new Income_data("5","5000","king","why i tell you","monday"));
        arrayList.add(new Income_data("10","500000","king","why i tell you","tuesday"));
        arrayList.add(new Income_data("15","50000000","king","why i tell you","Wednesday"));
        arrayList.add(new Income_data("20","50000000000","king","why i tell you","Thursday"));
        arrayList.add(new Income_data("25","500000000000000","king","why i tell you","Friday"));
        arrayList.add(new Income_data("25","50000000000000000","king","why i tell you","maturday"));
        arrayList.add(new Income_data("30","50000000000000000000","king","why i tell you","Sunday"));



       myAdapter=new Income_Adapter( arrayList,this);
       recyclerView.setAdapter(myAdapter);

    }


    @Override
    public void Select(int index) {
       dialog.setContentView(R.layout.test);
        TextView date,day,sender,income,reason,cross;
        cross=dialog.findViewById(R.id.txtclose);
        date=dialog.findViewById(R.id.cdata);
        day=dialog.findViewById(R.id.Cday);
        sender=dialog.findViewById(R.id.sender);
        income=dialog.findViewById(R.id.cIncome);
        reason=dialog.findViewById(R.id.Creason);
        date.setText(arrayList.get(index).getDate());
        day.setText(arrayList.get(index).getDay());
        sender.setText(arrayList.get(index).getSender());
        income.setText(arrayList.get(index).getIncome());
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