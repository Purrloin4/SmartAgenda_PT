package com.example.smartagenda;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private boolean allDay;
    private boolean informativeTask;


    public Event(String description, LocalTime startTime,LocalTime endTime, LocalDate date, boolean allDay, boolean informativeTask) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.allDay = allDay;
        this.informativeTask = informativeTask;
    }


    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return CalendarUtils.formattedTime(startTime);
    }
    public String getEndTime() {
        return CalendarUtils.formattedTime(endTime);
    }

    public String getDate() {
        return CalendarUtils.formattedDate(date);
    }

    public boolean isAllDay() {
        return allDay;
    }

    public boolean isInformative() {
        return informativeTask;
    }
}
