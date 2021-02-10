package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShopActivity extends AppCompatActivity implements ItemAdapter.ItemSelected {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Dialog dialog;
    ArrayList<Item> ItemList;
    Button btn;
   public static ArrayList<Whislist_data>whislist_dataArray;
    static int i=0;






    public int[] images = {R.drawable.pic1 ,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic14};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn=findViewById(R.id.cartactivity);
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
        whislist_dataArray=new ArrayList<Whislist_data>();

        recyclerView = findViewById(R.id.ShopRecyclerView);
        layoutManager = new GridLayoutManager(ShopActivity.this,2);
        recyclerView.setLayoutManager( layoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapter = new ItemAdapter(ItemList,this,images);
        recyclerView.setAdapter(myAdapter);


btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ShopActivity.this,Mycart.class);
        startActivity(intent);
    }
});




    }

    @Override
    public void itemClicked(int index) {
        dialog=new Dialog(ShopActivity.this);
        dialog.setContentView(R.layout.dialogue);
        ImageView img =dialog.findViewById(R.id.product);
        ImageView back =dialog.findViewById(R.id.imageView);
        ImageView plus =dialog.findViewById(R.id.increment);
        ImageView minus =dialog.findViewById(R.id.decrement);
        TextView cancel=dialog.findViewById(R.id.cancle_price);
        TextView total=dialog.findViewById(R.id.totalproduct);
        TextView productive=dialog.findViewById(R.id.Available);
        TextView DPrice=dialog.findViewById(R.id.DiscountPrice);
        Button addtocart=dialog.findViewById(R.id.Addtocart);
        TextView productN=dialog.findViewById(R.id.Name);
        img.setImageResource(images[index]);

        cancel.setText(ItemList.get(index).getOriginalPrice());
        productive.setText(ItemList.get(index).getItemStatus());
        DPrice.setText(ItemList.get(index).getDiscountPrice());
        productN.setText(ItemList.get(index).getItemName());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pro=Integer.parseInt((String) total.getText())+1;
                total.setText(String.valueOf(pro));
               String val=ItemList.get(index).getDiscountPrice();
               int val2=Integer.parseInt(DPrice.getText().toString())+Integer.parseInt(val);
               DPrice.setText(String.valueOf(val2));

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pro=Integer.parseInt((String) total.getText())-1;
                if(pro>=0) {
                    total.setText(String.valueOf(pro));
                    String val = ItemList.get(index).getDiscountPrice();
                    int val2 = Integer.parseInt(DPrice.getText().toString()) - Integer.parseInt(val);
                    DPrice.setText(String.valueOf(val2));
                }
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ShopActivity.this, "Happy shopping", Toast.LENGTH_SHORT).show();
                int img=images[index];
                whislist_dataArray.add(new Whislist_data(img,ItemList.get(index).getItemName(),ItemList.get(index).getItemStatus(), (String) DPrice.getText(), (String) total.getText()));
                dialog.dismiss();
            }
        });
       dialog.show();

    }
    @Override
    public void onBackPressed(){
        return;
    }
}