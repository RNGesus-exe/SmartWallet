package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;


public class ShopActivity extends AppCompatActivity implements ItemAdapter.ItemSelected {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Dialog dialog;
    ArrayList<Item> ItemList;





    public int[] images = {R.drawable.pic1 ,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic14};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ItemList = new ArrayList<Item>();
        ItemList.add(new Item("HP Envy 15 Laptop","Available","30000","25000"));
        ItemList.add(new Item("ASUS LTX08 Laptop","Sold out","55000","38999"));
        ItemList.add(new Item("HP Black Projector","Sold out","6000","4500"));
        ItemList.add(new Item("Arduino Uno 2.0 Mini","Available","800","599"));
        ItemList.add(new Item("Sony Bravia UHD TV ","Available","30000","25000"));
        ItemList.add(new Item("Samsung 3D OLED TV","Available","99000","85999"));
        ItemList.add(new Item("BeatsAudio Headphone","Available","2000","999"));
        ItemList.add(new Item("HP AirPods edition 3","Available","3000","2500"));
        ItemList.add(new Item(" Blue earphone edition","Sold out","200","149"));
        ItemList.add(new Item("HP Pavilion Laptop","Available","40000","36499"));
        ItemList.add(new Item("Dell Desktop","Sold out","30000","25000"));
        ItemList.add(new Item("Phone A","Available","50000","40000"));
        ItemList.add(new Item("TouchPad A","Sold out","700","650"));
        ItemList.add(new Item("DVD Player A","Sold out","5000","4500"));

        recyclerView = findViewById(R.id.ShopRecyclerView);
        layoutManager = new GridLayoutManager(ShopActivity.this,2);
        recyclerView.setLayoutManager( layoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapter = new ItemAdapter(ItemList,this,images);
        recyclerView.setAdapter(myAdapter);






    }

    @Override
    public void itemClicked(int index) {

    }
}