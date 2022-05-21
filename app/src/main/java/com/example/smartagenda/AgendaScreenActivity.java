package com.example.smartagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.example.smartagenda.CalendarUtils.daysInWeekArray;
import static com.example.smartagenda.CalendarUtils.monthYearFromDate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AgendaScreenActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    Button button;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button addTask;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        if (Event.eventsList.isEmpty()){
            loadEvents();
        }
        setWeekView();
    }

    private void loadEvents() {
        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        String username = login.getString("username", "");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/events_per_user/"+username;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                            LocalTime startTimeLT = LocalTime.parse(o.get("startTime").toString());
                            LocalTime endTimeLT = LocalTime.parse(o.get("endTime").toString());
                            LocalDate date = LocalDate.parse(o.get("date").toString());

                        boolean allDayON;
                        if(o.get("allDayON").toString().equals("0")){
                                allDayON = false;
                            }
                        else{
                            allDayON = true;
                        }

                        Event newEvent = new Event(o.get("description").toString(), startTimeLT, endTimeLT, date, allDayON);
                        Event.eventsList.add(newEvent);

                        if(i == response.length()-1){
                            setWeekView();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgendaScreenActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }

    private void initWidgets()
    {
        button = findViewById(R.id.popupBtn);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearText);
        eventListView = findViewById(R.id.eventListView);
        addTask = findViewById(R.id.addPersonalTaskBtn);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
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
        setEventAdapter();
    }

    private void setEventAdapter()
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
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }


    public void onAddPersonalTaskBtn_Clicked(MenuItem item)
    {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void onAddRecurringEvent_Clicked(MenuItem item)
    {
        Intent intent = new Intent(this, NewRecurringEventActivity.class);
        startActivity(intent);
    }

    public void onGroupBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

}

