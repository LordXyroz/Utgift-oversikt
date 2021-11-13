package com.utgiftoversikt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class LogRecyclerView  extends RecyclerView.Adapter<LogRecyclerView.ViewHolder> {
    private ArrayList<LogInfo> mData;

    public LogRecyclerView(ArrayList<LogInfo> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrollviewentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogInfo info = mData.get(position);

        holder.personText.setText(info.person.toString());
        holder.transactionTypeField.setText(info.transactionType.toString());
        holder.transactionAmountField.setText(String.format(Locale.ENGLISH,"%.2f", info.transactionAmount) + " kr");
        holder.description.setText(info.description);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView personText;
        private final TextView transactionTypeField;
        private final TextView transactionAmountField;
        private final TextView description;

        public ViewHolder(View view) {
            super(view);

            personText = view.findViewById(R.id.personText);
            transactionTypeField = view.findViewById(R.id.transactionTypeField);
            transactionAmountField = view.findViewById(R.id.transactionAmountField);
            description = view.findViewById(R.id.comment);
        }
    }
}
