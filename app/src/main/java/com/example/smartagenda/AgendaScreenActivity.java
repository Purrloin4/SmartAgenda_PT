package com.example.smartagenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.smartagenda.CalendarUtils.daysInWeekArray;
import static com.example.smartagenda.CalendarUtils.monthYearFromDate;

public class AgendaScreenActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    Button button;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button addTask;

    //Button addEvent;
    //Button addTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
    }

    private void initWidgets()
    {
        button = (Button) findViewById(R.id.popupBtn);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearText);
        eventListView = findViewById(R.id.eventListView);
        //addEvent = (Button) findViewById(R.id.eventAddBtn);
        addTask = (Button) findViewById(R.id.taskAddBtn);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }


    public void onPlusBtn_Clicked(View caller)
    {
        PopupMenu popupMenu = new PopupMenu(AgendaScreenActivity.this, button);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.show();
    }

    public void onAccountBtn3_Clicked(View caller)
    {
        AccountOverviewFragment dialogFragment = new AccountOverviewFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");
    }

    /*
    public void onTaskAddBtn_Clicked(MenuItem item)
    {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void scheduleTask_Clicked(MenuItem item) {
        Intent intent = new Intent(this, ScheduleNewTaskActivity.class);
        startActivity(intent);
    }*/

    public void onScheduleTaskBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, LogOutActivity.class);
        startActivity(intent);
    }



}

