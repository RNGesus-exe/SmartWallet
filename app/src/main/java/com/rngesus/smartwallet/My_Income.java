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
       myAdapter=new Income_Adapter(MyActivity.arrayList,this);
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
        date.setText(MyActivity.arrayList.get(index).getDate());
        day.setText(MyActivity.arrayList.get(index).getDay());
        sender.setText(MyActivity.arrayList.get(index).getSender());
        income.setText(MyActivity.arrayList.get(index).getIncome());
        reason.setText(MyActivity.arrayList.get(index).getReason());


        cross.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });
        dialog.show();
    }
}