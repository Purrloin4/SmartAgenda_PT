package com.example.smartagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    Context context;
    List<String> members;

    public MemberAdapter(Context context, List<String> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_cell,parent,false);
        return new MemberViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.member.setText(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }


}
