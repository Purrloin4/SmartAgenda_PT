package com.example.smartagenda;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    TextView group;
    OnGroupListener onGroupListener;

    public GroupViewHolder(@NonNull View itemView, OnGroupListener onGroupListener) {
        super(itemView);
        group = itemView.findViewById(R.id.groupNameTV);
        this.onGroupListener = onGroupListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onGroupListener.onGroupClick(getBindingAdapterPosition());
    }

    public interface OnGroupListener{
        void onGroupClick(int position);
    }
}
