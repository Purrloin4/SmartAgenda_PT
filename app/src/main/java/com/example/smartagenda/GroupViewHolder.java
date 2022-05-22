package com.example.smartagenda;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupViewHolder extends RecyclerView.ViewHolder{

    TextView group;
    LinearLayout parentLayout;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        group = itemView.findViewById(R.id.groupNameTV);
        parentLayout = itemView.findViewById(R.id.parentLayout);
    }


}
