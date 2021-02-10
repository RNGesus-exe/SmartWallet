package com.rngesus.smartwallet;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MAdapter> {
    ArrayList<Item> items;
    ItemAdapter.ItemSelected activityContext;
    int[] images;

    public ItemAdapter(ArrayList<Item> items, Context context, int[] images) {
        this.items = items;
        this.activityContext = (ItemAdapter.ItemSelected) context;
        this.images = images;
    }

    @NonNull
    @Override
    public ItemAdapter.MAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_view,parent,false);
        return new ItemAdapter.MAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MAdapter holder, int position) {
        int image_id = images[position];
        holder.itemView.setTag(items.get(position));
       /* holder.etItemName.setText(items.get(position).getItemName());
        holder.etOriginalPrice.setText(items.get(position).getOriginalPrice());
        holder.etOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.etPrice.setText((items.get(position).getDiscountPrice()));
        holder.etStatus.setText(items.get(position).getItemStatus());*/
        holder.album.setImageResource(image_id);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface ItemSelected {
        void itemClicked(int index);
    }

    public class MAdapter extends RecyclerView.ViewHolder {
        TextView etItemName;
        TextView etOriginalPrice;
        TextView etPrice;
        TextView etStatus;
        ImageView album;
        ImageView ivCart;
        public MAdapter(@NonNull View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityContext.itemClicked(items.indexOf((Item) itemView.getTag()));
                }
            });

        }

    }

}
