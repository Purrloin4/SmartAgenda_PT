package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewRecurringEventActivity extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;
    DatePickerDialog.OnDateSetListener setListener;
    DatePickerDialog.OnDateSetListener setListener2;
    private EditText eventDescriptionET;
    private Spinner daysSp2;

    private TextView startTime, endTime;
    private int startHour, startMin, endHour, endMin;

    private Button addEvent;

    private boolean coherentTime;
    private boolean coherentDate;

    private TextView title1;
    private TextView title2;
    private Switch allDay;
    private boolean isON;


    private int startDay, startMonth, startYear, endDay, endMonth, endYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recurring_event);

        title1 = findViewById(R.id.startTimeTxt);
        title2 = findViewById(R.id.endTimeTxt);
        allDay = findViewById(R.id.allDaySw);
        daysSp2 = findViewById(R.id.daysSp2);
        isON = false;

        coherentTime = false;
        coherentDate = false;
        addEvent=findViewById(R.id.addRecurringEventBtn);
        eventDescriptionET = findViewById(R.id.descriptionIn2);

        startDate = findViewById(R.id.startDateIn);
        endDate = findViewById(R.id.endDateIn);

        endTime = findViewById(R.id.endTimeIn);
        startTime = findViewById(R.id.startTimeIn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecurringEventActivity.this,android.R.style.Theme_Holo_Light_Dialog, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet (DatePicker view, int year, int month, int dayOfMonth) {  //this part doest do anything because we show "select" instead i think
                month = month + 1;
                startMonth = month;
                startYear = year;
                startDay = dayOfMonth;
                String date = year + "-" + month  + "-" + dayOfMonth;
                startDate.setText(date);
            }

        };


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecurringEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view , int year, int month, int day) {
                        month = month+1;
                        String startMonth = "" + month;
                        String startDay = "" + day;
                        if (month<10)
                            startMonth= "0" +month;
                        if (day<10)
                            startDay= "0" +day;
                        String date = startDay + "/"  + startMonth + "/" + year;
                        startDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecurringEventActivity.this,android.R.style.Theme_Holo_Light_Dialog, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet (DatePicker viw, int year, int month, int dayOfMonth) { // same here
                month = month + 1;
                endMonth = month;
                endYear = year;
                endDay = dayOfMonth;
                String date = dayOfMonth + "/" + month + "/" + year;
                endDate.setText(date);
            }

        };


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecurringEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view , int year, int month, int day) {
                        month = month+1;
                        String startMonth = "" + month;
                        String startDay = "" + day;
                        if (month<10)
                            startMonth= "0" +month;
                        if (day<10)
                            startDay= "0" +day;
                        String date = startDay + "/"  + startMonth + "/" + year;
                        endDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public ArrayList<String> getDates(TextView startDate, TextView endDate)
    {
        ArrayList<String> dates = new ArrayList<String>();
        if (startYear <= endYear){
            if (startMonth <= endMonth){
                if(startDay <= endDay){

                }
            }
        }
        return dates;
    }

    public void onSelectStartTime_Clicked(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selStartHour, int selStartMin) {
                startHour= selStartHour;
                startMin = selStartMin;
                startTime.setText(String.format(Locale.getDefault(),"%02d:%02d", startHour, startMin));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, startHour, startMin, true);
        timePickerDialog.show();

    }

    public void onSelectEndTime_Clicked(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selEndHour, int selEndMin) {
                endHour= selEndHour;
                endMin = selEndMin;
                endTime.setText(String.format(Locale.getDefault(),"%02d:%02d", endHour, endMin));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, endHour, endMin, true);
        timePickerDialog.show();

    }

    public void onAddEventBtn_Clicked(View caller)
    {

        if (startHour==endHour)
        {
            if (startMin<endMin)
            {
                coherentTime = true;
            }
            else
            {
                Toast.makeText(this, "Time slot is incoherent.", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            if (startHour<endHour)
            {
                coherentTime = true;
            }
            else
            {
                Toast.makeText(this, "Time slot is incoherent", Toast.LENGTH_SHORT).show();
            }
        }

        if (startYear==endYear)
        {
            if (startMonth<endMonth)
            {
                coherentDate=true;
            }
            else
            {
                if (startMonth==endMonth)
                {
                    if (startDay<=endDay)
                    {
                        coherentDate=true;
                    }
                    else
                    {
                        Toast.makeText(this, "Time slot is incoherent", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Time slot is incoherent", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else
        {
            if (startYear<endYear)
            {
                coherentDate=true;
            }
            else
            {
                Toast.makeText(this, "Time slot is incoherent", Toast.LENGTH_SHORT).show();
            }
        }


        if (coherentDate && coherentTime)
        {
            String eventDescription = eventDescriptionET.getText().toString();
            Event newEvent = new Event(eventDescription, startHour, startMin, endHour, endMin, startDate); //Somehow make this a loop for every day from startDate until endDate
            Event.eventsList.add(newEvent);
            finish();

            Intent intent = new Intent(this, AgendaScreenActivity.class);
            startActivity(intent);
        }
    }

    public void onAllDaySp_Clicked(View caller)
    {
        if (isON==false)
        {
            title1.setVisibility(caller.INVISIBLE);
            startTime.setVisibility(caller.INVISIBLE);
            title2.setVisibility(caller.INVISIBLE);
            endTime.setVisibility(caller.INVISIBLE);
            isON=true;
        }
        else
        {
            title1.setVisibility(caller.VISIBLE);
            startTime.setVisibility(caller.VISIBLE);
            title2.setVisibility(caller.VISIBLE);
            endTime.setVisibility(caller.VISIBLE);
            isON=false;
        }
    }





}