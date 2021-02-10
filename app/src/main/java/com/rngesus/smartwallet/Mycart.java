package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Mycart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        btn=findViewById(R.id.purchase);
        if(ShopActivity.whislist_dataArray==null)
        {

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
                //
                Toast.makeText(Mycart.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getTotalprice() {
        int addprice=0;
        for(int i=0; i<ShopActivity.whislist_dataArray.size(); i++)
        {
            String price=ShopActivity.whislist_dataArray.get(i).getPrice();
            addprice+=Integer.parseInt(price);
        }
        return addprice;
    }
}