package com.example.smartagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    Context context;
    List<String> groups;
    private GroupViewHolder.OnGroupListener mOnGroupListener;

    public GroupAdapter(Context context, List<String> groups, GroupViewHolder.OnGroupListener onGroupListener) {
        this.context = context;
        this.groups = groups;
        this.mOnGroupListener = onGroupListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_cell,parent,false);
        return new GroupViewHolder(view, mOnGroupListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.group.setText(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}

