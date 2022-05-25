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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    String[] groupNames;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        description = findViewById(R.id.descriptionIn);


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



        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/groups_per_user/"+username+"/"+username;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                groupNames= new String[response.length()];
                for (int i=0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try
                    {
                        o = response.getJSONObject(i);
                        groupNames[i]=o.get("team_name").toString();

                        if (i==response.length()-1)
                        {
                            groupSp= findViewById(R.id.groupSpinner);
                            groupSp.setVisibility(View.INVISIBLE);
                            ArrayAdapter adapter = new ArrayAdapter
                                    (NewTaskActivity.this, android.R.layout.simple_spinner_item, groupNames);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            groupSp.setAdapter(adapter);
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

        requestQueue.add(submitRequest);


        TipOfDayFragment dialogFragment = new TipOfDayFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest3);
    }

    public void writeToDataBaseGroup(String newDescription, String newStartTime, String newEndTime, String newDate, String user, String group)
    {
        String requestURL7 = "https://studev.groept.be/api/a21pt308/add_group_task/"+newDescription+"/"+newStartTime+"/"+newEndTime+"/"+newDate+"/"+user+"/"+group;
        JsonArrayRequest submitRequest7 = new JsonArrayRequest(Request.Method.GET,requestURL7,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest7);
    }

    public void onScheduleTaskBtn_Clicked (View caller)
    {
        if (!description.getText().toString().matches("")
                    && !durationTV.getText().toString().matches("select")
                    && !deadlineTV.getText().toString().matches("select"))
            {
                if (LocalDate.now().isBefore(deadlineLD.minusDays(1)))
                {
                    final boolean[] taskScheduled = {false};
                    for(final int[] j = {1}; j[0] < DAYS.between(LocalDate.now(), deadlineLD); j[0]++)
                    {
                        requestQueue = Volley.newRequestQueue(this);


                        String dateAttempt = deadlineLD.minusDays(j[0]).toString();

                        String requestURL2 = "https://studev.groept.be/api/a21pt308/eventsOfDayInChronologicalOrder/"+username+"/"+dateAttempt;


                        JsonArrayRequest submitRequest2 = new JsonArrayRequest(Request.Method.GET,requestURL2,null,new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
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
                                                if (isON==false)
                                                {
                                                    //personal task
                                                    writeToDataBase(description.getText().toString(), newStartTime.toString(), newEndTime.toString(), dateAttempt);
                                                    taskScheduled[0] =true;
                                                    Event newEvent = new Event(description.getText().toString(), newStartTime, newEndTime, LocalDate.parse(dateAttempt), false, false);
                                                    Event.eventsList.add(newEvent);
                                                    Toast.makeText(NewTaskActivity.this, "Your task was successfully scheduled.", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    //group task

                                                    String requestURL = "https://studev.groept.be/api/a21pt308/members_per_group/"+groupSp.getSelectedItem().toString();
                                                    LocalTime finalNewStartTime = newStartTime;
                                                    LocalTime finalNewEndTime = newEndTime;
                                                    LocalTime finalNewStartTime1 = newStartTime;
                                                    LocalTime finalNewEndTime1 = newEndTime;
                                                    JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                                                        @Override
                                                        public void onResponse(JSONArray response) {
                                                            JSONObject o = null;
                                                            try {

                                                                o = response.getJSONObject(0);
                                                                String[] members = o.get("members").toString().split(", ");
                                                                final int[] counter = {0};

                                                                for (String member : members)
                                                                {
                                                                    final int[] count2 = {0};

                                                                    String requestURL6 = "https://studev.groept.be/api/a21pt308/eventsOfDayInChronologicalOrder/"+member+"/"+dateAttempt;
                                                                    JsonArrayRequest submitRequest6 = new JsonArrayRequest(Request.Method.GET,requestURL6,null,new Response.Listener<JSONArray>() {
                                                                        @Override
                                                                        public void onResponse(JSONArray response) {

                                                                            for (int i=0; i<response.length(); ++i) {
                                                                                JSONObject o = null;
                                                                                JSONObject o2 = null;
                                                                                try
                                                                                {
                                                                                    o = response.getJSONObject(i);

                                                                                    if ((response.length()==1 && o.get("startTime").toString().equals("00:00")
                                                                                            && o.get("endTime").toString().equals("23:59"))
                                                                                            ||(LocalTime.parse(o.get("startTime").toString()).compareTo(finalNewStartTime1)<=0
                                                                                            && LocalTime.parse(o.get("endTime").toString()).compareTo(finalNewStartTime)>=0 && LocalTime.parse(o.get("endTime").toString()).compareTo(finalNewEndTime1)<0)
                                                                                            ||(LocalTime.parse(o.get("startTime").toString()).compareTo(finalNewStartTime1)>=0
                                                                                            && LocalTime.parse(o.get("endTime").toString()).compareTo(finalNewStartTime1)<=0)
                                                                                            ||(LocalTime.parse(o.get("startTime").toString()).compareTo(finalNewStartTime1)>0 && LocalTime.parse(o.get("startTime").toString()).compareTo(finalNewEndTime1)<0
                                                                                            && LocalTime.parse(o.get("endTime").toString()).compareTo(finalNewStartTime1)<0))
                                                                                    {
                                                                                        count2[0]++;
                                                                                        //i=response.length();

                                                                                    }

                                                                                    if (i==response.length()-1 && count2[0]==0) {
                                                                                        counter[0]++;

                                                                                        if ( member.equals(members[members.length-1]) && counter[0] == members.length)
                                                                                        {
                                                                                            Event newEvent = new Event(description.getText().toString(), finalNewStartTime, finalNewEndTime, LocalDate.parse(dateAttempt), false, false);
                                                                                            Event.eventsList.add(newEvent);

                                                                                            for (String member : members)
                                                                                            {
                                                                                                writeToDataBaseGroup(description.getText().toString(), finalNewStartTime.toString(), finalNewEndTime.toString(), dateAttempt, member, groupSp.getSelectedItem().toString());
                                                                                                taskScheduled[0] =true;
                                                                                                Toast.makeText(NewTaskActivity.this, "Your group task was successfully scheduled.", Toast.LENGTH_SHORT).show();
                                                                                            }
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

                                                                    requestQueue.add(submitRequest6);
                                                                }


                                                            }
                                                            catch (JSONException e)
                                                            {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(NewTaskActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    requestQueue.add(submitRequest);

                                                }

                                            }

                                            /*
                                            else
                                            {
                                                if (taskScheduled[0]==false && j[0]==DAYS.between(LocalDate.now(), deadlineLD)-1 && i==response.length()-1)
                                                {
                                                    Toast.makeText(NewTaskActivity.this, "This task doesn't fit in your schedule.", Toast.LENGTH_LONG).show();
                                                    //Intent intent = new Intent(NewTaskActivity.this, AgendaScreenActivity.class);
                                                    //startActivity(intent);
                                                }
                                            }*/
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


                    if (taskScheduled[0]==false)
                    {
                        Toast.makeText(NewTaskActivity.this, "This task doesn't fit in your schedule.", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(this, AgendaScreenActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(this, "Deadline can't be in the past or tomorrow.", Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                Toast.makeText(this, "Fill in all fields before saving", Toast.LENGTH_SHORT).show();
            }
        }
}