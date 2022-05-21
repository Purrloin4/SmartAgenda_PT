package com.example.smartagenda;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity
{

    private TextView deadlineTV, durationTV;
    DatePickerDialog.OnDateSetListener setListener;

    private int hour, min;
    private boolean isON;

    private Spinner groupSp;
    private TextView groupTV;
    private EditText description;
    private RequestQueue requestQueue;

    private LocalTime durationLT;
    private LocalDate deadlineLD;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        description = findViewById(R.id.descriptionIn);

        groupSp= findViewById(R.id.groupSpinner);
        groupSp.setVisibility(View.INVISIBLE);
        isON=false;
        groupTV=findViewById(R.id.groupNameTxt);
        groupTV.setVisibility(View.INVISIBLE);

        durationTV = findViewById(R.id.durationIn);

        deadlineTV = findViewById(R.id.deadlineIn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");

        ArrayList<String> groupNames = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/groups_per_user/"+username+"/"+username;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        groupNames.add(o.get("team_name").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, groupNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.groupSpinner);
        sItems.setAdapter(adapter);


        deadlineTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this,android.R.style.Theme_Holo_Light_Dialog, setListener, year, month, day);
               datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet (DatePicker viw, int year, int month, int dayOfMonth) {
                month = month + 1;
                deadlineLD = LocalDate.of(year, month, day);
                String date = CalendarUtils.formattedDate(deadlineLD);
                deadlineTV.setText(date);
            }

        };


        deadlineTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view , int year, int month, int day) {
                        month = month+1;
                        deadlineLD = LocalDate.of(year, month, day);
                        String date = CalendarUtils.formattedDate(deadlineLD);
                        deadlineTV.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    public void onSelectDuration_Clicked(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selHour, int selMin) {
                hour= selHour;
                min = selMin;
                //duration.setText(String.format(Locale.getDefault(),"%02d:%02d", hour, min));
                durationLT = LocalTime.of(hour,min);
                String duration = CalendarUtils.formattedTime(durationLT);
                durationTV.setText(duration);

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, min, true);
        timePickerDialog.show();

    }

    public void onGroupSwitch_Clicked(View caller)
    {

        if (isON == true)
        {
            groupSp.setVisibility(caller.INVISIBLE);
            groupTV.setVisibility(View.INVISIBLE);
            isON = false;
        }
        else
        {
            groupSp.setVisibility(caller.VISIBLE);
            groupTV.setVisibility(View.VISIBLE);
            isON = true;
        }
    }


    public void writeToDataBase(String newDescription, String newStartTime, String newEndTime, String newDate)
    {

        String requestURL3 = "https://studev.groept.be/api/a21pt308/add_task/"+newDescription+"/"+newStartTime+"/"+newEndTime+"/"+newDate+"/"+username;
        JsonArrayRequest submitRequest3 = new JsonArrayRequest(Request.Method.GET,requestURL3,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest3);
    }



    public void onScheduleTaskBtn_Clicked (View caller)
    {
        if (isON==false)
        {
            if (!description.getText().toString().matches("")
                    && !durationTV.getText().toString().matches("select")
                    && !deadlineTV.getText().toString().matches("select"))
            {


                //scheduling algorithm for personal task
                final boolean[] taskScheduled = {false};
                for(final int[] j = {1}; j[0] < DAYS.between(LocalDate.now(), deadlineLD); j[0]++)
                {
                    requestQueue = Volley.newRequestQueue(this);


                    String dateAttempt = deadlineLD.minusDays(j[0]).toString();

                    String requestURL2 = "https://studev.groept.be/api/a21pt308/eventsOfDayInChronologicalOrder/"+username+"/"+dateAttempt;



                    JsonArrayRequest submitRequest2 = new JsonArrayRequest(Request.Method.GET,requestURL2,null,new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            String info = "";

                            for (int i=0; i<response.length(); ++i) {
                                JSONObject o = null;
                                JSONObject o2 = null;
                                try
                                {
                                    int durationInMin=-1;
                                    int emptySlotInMin=-1;
                                    LocalTime newStartTime = null;
                                    LocalTime newEndTime = null;
                                    o = response.getJSONObject(i);

                                    if (response.length()==1 && o.get("startTime").toString().equals("00:00") && o.get("endTime").toString().equals("23:59"))
                                    {
                                        j[0]++;
                                    }
                                    else
                                    {
                                        durationInMin = durationLT.getMinute() + durationLT.getHour()*60;

                                        if (i==0 && !o.get("startTime").toString().equals("00:00"))
                                        {
                                            emptySlotInMin = (int) MINUTES.between(LocalTime.parse("00:00"), LocalTime.parse(o.get("startTime").toString()));
                                            newEndTime=LocalTime.parse(o.get("startTime").toString()).minusMinutes(15);
                                            newStartTime=newEndTime.minusHours(durationLT.getHour());
                                            newStartTime=newStartTime.minusMinutes(durationLT.getMinute());
                                        }
                                        else
                                        {
                                            if (i==response.length()-1 && !o.get("endTime").toString().equals("23:59"))
                                            {
                                                emptySlotInMin = (int) MINUTES.between(LocalTime.parse(o.get("endTime").toString()), LocalTime.parse("23:59"));
                                            }
                                            else
                                            {
                                                o2 =response.getJSONObject(i+1);
                                                emptySlotInMin = (int) MINUTES.between(LocalTime.parse(o.get("endTime").toString()), LocalTime.parse(o2.get("startTime").toString()));

                                            }

                                            LocalTime endTime = LocalTime.parse(o.get("endTime").toString());
                                            newStartTime = endTime.plusMinutes(15);
                                            newEndTime = newStartTime.plusHours(durationLT.getHour());
                                            newEndTime=newEndTime.plusMinutes(durationLT.getMinute());

                                        }

                                        if (emptySlotInMin
                                                >= durationInMin+30 && taskScheduled[0] ==false)
                                        {
                                            writeToDataBase(description.getText().toString(), newStartTime.toString(), newEndTime.toString(), dateAttempt);
                                            taskScheduled[0] =true;
                                            Event newEvent = new Event(description.getText().toString(), newStartTime, newEndTime, LocalDate.parse(dateAttempt), false);
                                            Event.eventsList.add(newEvent);
                                        }
                                    }
                                }

                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(submitRequest2);
                }



                Intent intent = new Intent(this, AgendaScreenActivity.class);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(this, "Fill in all fields before saving", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if (!description.getText().toString().matches("")
                    && !durationTV.getText().toString().matches("select")
                    && !deadlineTV.getText().toString().matches("select")
            && groupSp.getSelectedItem().toString().matches("" ))
            {
                Intent intent = new Intent(this, AgendaScreenActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Fill in all fields before saving", Toast.LENGTH_SHORT).show();
            }
        }
    }


}