package com.example.smartagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.List;

public class EventAdapter extends ArrayAdapter<com.example.smartagenda.Event> {

    public EventAdapter(@NonNull Context context, List<com.example.smartagenda.Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        com.example.smartagenda.Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String formattedTime;
        if (event.isAllDay()){
            formattedTime = "all day";
        }
        else{
            formattedTime = event.getStartTime() +"-"+ event.getEndTime();
        }
        String eventTitle = event.getDescription() +" \u2022 "+ formattedTime;
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
