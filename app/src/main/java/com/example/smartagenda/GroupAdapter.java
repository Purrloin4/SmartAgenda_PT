package com.example.smartagenda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    Context context;
    List<String> groups;

    public GroupAdapter(Context context, List<String> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_cell,parent,false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.group.setText(groups.get(position));

        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, OnGroupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("groupPosition", groups.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

}

