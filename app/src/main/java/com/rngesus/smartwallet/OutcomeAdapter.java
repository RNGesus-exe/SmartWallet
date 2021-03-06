package com.rngesus.smartwallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OutcomeAdapter extends FirebaseRecyclerAdapter<Outcome,OutcomeAdapter.OutcomeViewHolder> {

    public OutcomeAdapter(@NonNull FirebaseRecyclerOptions<Outcome> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OutcomeViewHolder holder, int position, @NonNull Outcome model) {
        holder.tvAmount.setText("Amount: "+model.getAmount());
        holder.tvDate.setText("Date: "+model.getDate());
        holder.tvSender.setText("Receiver: "+model.getReceiver());
        holder.tvTime.setText("Time: "+model.getTime());
    }

    @NonNull
    @Override
    public OutcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_view, parent, false);

        return new OutcomeViewHolder(view);
    }

    public class OutcomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvAmount, tvSender, tvTime;
        public OutcomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.proName);
            tvTime=itemView.findViewById(R.id.tv_Amount);
            tvSender=itemView.findViewById(R.id.receiver);
            tvAmount=itemView.findViewById(R.id.tv_price);
        }
    }
}
