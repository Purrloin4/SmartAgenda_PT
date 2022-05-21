package com.example.smartagenda;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberViewHolder extends RecyclerView.ViewHolder {
    TextView member;

    public MemberViewHolder(@NonNull View itemView) {
        super(itemView);
        member = itemView.findViewById(R.id.memberCellTV);
    }
}
