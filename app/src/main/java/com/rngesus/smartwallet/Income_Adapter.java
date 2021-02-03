package com.rngesus.smartwallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Income_Adapter extends RecyclerView.Adapter< Income_Adapter.MyAdapter> {

    ArrayList<Income_data>Income;
    ItemSelected activity;

    public Income_Adapter(ArrayList<Income_data> income, Context context) {
        this.Income = income;
        this.activity = (ItemSelected)context;
    }
    public interface  ItemSelected
    {
        void Select(int index);
    }

    @NonNull
    @Override
    public Income_Adapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.income_designe,parent,false);
        return new MyAdapter(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Income_Adapter.MyAdapter holder, int position) {
        holder.itemView.setTag(Income.get(position));
        holder.date.setText("DATE:"+Income.get(position).getDate());
        holder.day.setText("Day:"+Income.get(position).getDay());
        holder.sender.setText("Sender:"+Income.get(position).getSender());
        holder.income.setText("Income:"+Income.get(position).getIncome());

    }

    @Override
    public int getItemCount() {
        return Income.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView date,income,sender,day;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            day=itemView.findViewById(R.id.day);
            sender=itemView.findViewById(R.id.sender);
            income=itemView.findViewById(R.id.income);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.Select(Income.indexOf((Income_data) itemView.getTag()));

                }
            });

        }
    }
}
