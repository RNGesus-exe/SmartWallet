package com.rngesus.smartwallet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter< WishListAdapter.MAdapter> {
    ArrayList<WishList> items;

    public WishListAdapter(ArrayList<WishList> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public  WishListAdapter.MAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
        return new  WishListAdapter.MAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MAdapter holder, int position) {
        holder.etItemName.setText(items.get(position).getTitle());
        holder.etAvalable.setText(items.get(position).getAvalable());
        holder.etPrice.setText((items.get(position).getPrice()));
        holder.etamount.setText(items.get(position).getTotalproduct());
        holder.image.setImageResource(items.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        Log.i("Hello", "getItemCount: "+items.size());
        return items.size();
    }

    public interface ItemSelected {
        void itemClicked(int index);
    }

    public class MAdapter extends RecyclerView.ViewHolder
    {
        TextView etItemName;
        TextView etAvalable;
        TextView etPrice;
        TextView etamount;
        ImageView image;
        public MAdapter(@NonNull View itemView) {
            super(itemView);
            etItemName = itemView.findViewById(R.id.proName);
            etAvalable = itemView.findViewById(R.id.receiver);
            etPrice = itemView.findViewById(R.id.tv_price);
            etamount = itemView.findViewById(R.id.tv_Amount);
            image = itemView.findViewById(R.id.imgpro);
        }

    }

}
