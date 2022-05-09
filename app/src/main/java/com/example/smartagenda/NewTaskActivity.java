package com.example.smartagenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity
{
    /*

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;*/

    private TextView deadlineTV, duration;
    DatePickerDialog.OnDateSetListener setListener;

    private int hour, min;
    private boolean isON;

    private Spinner groupSp;
    private TextView groupTV;
    private EditText description;


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

        duration = findViewById(R.id.durationIn);

        deadlineTV = findViewById(R.id.deadlineIn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


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
                String date = day + "/" + month + "/" + year;
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
                        String date = day + "/" + month + "/" + year;
                        deadlineTV.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //initWidgets();
        //time = LocalTime.now();
        //eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    public void onSelectDuration_Clicked(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selHour, int selMin) {
                hour= selHour;
                min = selMin;
                duration.setText(String.format(Locale.getDefault(),"%02d:%02d", hour, min));

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

    public void onScheduleTaskBtn_Clicked (View caller)
    {
        if (isON==false)
        {
            if (!description.getText().toString().matches("")
                    && !duration.getText().toString().matches("select")
                    && !deadlineTV.getText().toString().matches("select"))
            {
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
                    && !duration.getText().toString().matches("select")
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

    /*
    private void initWidgets()
    {
        eventNameET = findViewById(R.id.descriptionIn);
    }*/

    /*
    public void saveTaskAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }*/
}