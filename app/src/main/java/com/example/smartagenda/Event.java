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
            if(event.getDate().equals(CalendarUtils.formattedDate(date)))
                events.add(event);
        }

        return events;
    }


    private String description;
    private TextView startTime;;
    private TextView endTime;
    private LocalDate date;
    private boolean allDay;


    public Event(String description, TextView startTime, TextView endTime, LocalDate date, boolean allDay) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.allDay = allDay;
    }


    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime.getText().toString();
    }

    public String getEndTime() { return endTime.getText().toString(); }

    public String getDate() {
        return CalendarUtils.formattedDate(date);
    }

    public boolean isAllDay() {
        return allDay;
    }
}
