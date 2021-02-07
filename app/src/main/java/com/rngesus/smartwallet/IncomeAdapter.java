package com.rngesus.smartwallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class IncomeAdapter extends FirebaseRecyclerAdapter<Income, IncomeAdapter.IncomeViewHolder> {

    public IncomeAdapter(@NonNull FirebaseRecyclerOptions<Income> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IncomeViewHolder holder, int position, @NonNull Income model) {
            holder.tvAmount.setText("Amount: "+model.getAmount());
            holder.tvDate.setText("Date: "+model.getDate());
            holder.tvSender.setText("Sender: "+model.getSender());
            holder.tvTime.setText("Time: "+model.getTime());
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_view, parent, false);

        return new IncomeViewHolder(view);
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvAmount, tvSender, tvTime;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvTime=itemView.findViewById(R.id.tv_time);
            tvSender=itemView.findViewById(R.id.tv_sender);
            tvAmount=itemView.findViewById(R.id.tv_amount);
        }
    }
}
