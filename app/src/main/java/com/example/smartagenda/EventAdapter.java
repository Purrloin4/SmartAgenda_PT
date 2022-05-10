package com.example.smartagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<com.example.smartagenda.Event>
{
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

        String formattedStartHour = "" +event.getStartHour();
        String formattedStartMin = ""+ event.getStartMin();
        String formattedEndHour = "" +event.getEndHour();
        String formattedEndMin = ""+ event.getEndMin();
        if (event.getStartHour() < 10){
        formattedStartHour = "0" + event.getStartHour();
        }
        if (event.getStartMin() < 10){
            formattedStartMin = "0" + event.getStartMin();
        }
        if (event.getEndHour() < 10){
            formattedEndHour = "0" + event.getEndHour();
        }
        if (event.getEndMin() < 10){
            formattedEndMin = "0" + event.getEndMin();
        }

        String eventTitle = event.getDescription() +" "+ formattedStartHour + ":" + formattedStartMin + "-" + formattedEndHour + ":" + formattedEndMin;
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
