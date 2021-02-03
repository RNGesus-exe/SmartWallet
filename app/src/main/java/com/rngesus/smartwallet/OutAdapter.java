package com.rngesus.smartwallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OutAdapter extends  RecyclerView.Adapter< OutAdapter.MAdapter> {
    ArrayList<Outcome_data> Income;
    OutAdapter.ItemSelected activity;

    public OutAdapter(ArrayList<Outcome_data> income, Context context) {
        this.Income = income;
        this.activity = (OutAdapter.ItemSelected)context;
    }
    public interface  ItemSelected
    {
        void Select(int index);
    }

    @NonNull
    @Override
    public OutAdapter.MAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.income_designe,parent,false);
        return new MAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MAdapter holder, int position) {
        holder.itemView.setTag(Income.get(position));
        holder.date.setText("DATE:"+Income.get(position).getDate());
        holder.day.setText("Day:"+Income.get(position).getDay());
        holder.receiver.setText("Receiver:"+Income.get(position).getReceiver());
        holder.outcome.setText("Outcome:"+Income.get(position).getOutcome());
    }



    @Override
    public int getItemCount() {
        return Income.size();
    }

    public class MAdapter extends RecyclerView.ViewHolder {
        TextView date,outcome,receiver,day;
        public MAdapter(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            day=itemView.findViewById(R.id.day);
            receiver=itemView.findViewById(R.id.sender);
            outcome=itemView.findViewById(R.id.income);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.Select(Income.indexOf((Outcome_data) itemView.getTag()));

                }
            });

        }
    }
}
