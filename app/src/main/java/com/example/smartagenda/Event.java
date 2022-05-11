package com.example.smartagenda;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(CalendarUtils.formattedDate(date).toString()))
                events.add(event);
        }

        return events;
    }


    private String description;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private TextView date;


    public Event(String description, int startHour, int startMin, int endHour, int endMin, TextView date) {
        this.description = description;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public String getDate() {
        return date.getText().toString();
    }
}
