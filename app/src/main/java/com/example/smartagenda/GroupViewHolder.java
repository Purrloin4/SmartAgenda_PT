package com.example.smartagenda;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    TextView group;
    RecyclerView members;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        group = itemView.findViewById(R.id.groupNameTV);
    }
}
