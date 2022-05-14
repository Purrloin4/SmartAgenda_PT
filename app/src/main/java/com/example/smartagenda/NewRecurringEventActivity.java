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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewRecurringEventActivity extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;
    private LocalDate startDateLD;
    private LocalDate endDateLD;

    DatePickerDialog.OnDateSetListener setListener;
    DatePickerDialog.OnDateSetListener setListener2;
    DatePickerDialog.OnDateSetListener setListener3;

    private EditText eventDescriptionET;
    private Spinner daysSp2;

    private TextView startTime, endTime;
    private int startHour, startMin, endHour, endMin;
    private LocalTime startTimeLT, endTimeLT;

    private Button addEvent;

    private boolean coherentTime;
    private boolean coherentDate;

    private TextView startTimeTV;
    private TextView endTimeTV;
    private TextView startDateTV;
    private TextView endDateTV;
    private TextView oneDateTV;
    private TextView oneDate;
    private TextView dayTxt;

    private Switch allDay;
    private Switch oneTime;

    private boolean allDayON;
    private boolean oneTimeON;


    private int startDay, startMonth, startYear, endDay, endMonth, endYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recurring_event);

        startTimeTV = findViewById(R.id.startTimeTxt);
        endTimeTV = findViewById(R.id.endTimeTxt);
        startDateTV = findViewById(R.id.startDateTxt);
        endDateTV = findViewById(R.id.endDateTxt);
        oneDateTV = findViewById(R.id.oneDateTxt);
        dayTxt = findViewById(R.id.dayTxt);

        allDay = findViewById(R.id.allDaySw);
        daysSp2 = findViewById(R.id.daysSp2);
        allDayON = false;

        oneTime = findViewById(R.id.oneTimeSw);
        oneTimeON = false;

        coherentTime = false;
        coherentDate = false;
        addEvent=findViewById(R.id.addRecurringEventBtn);
        eventDescriptionET = findViewById(R.id.descriptionIn2);

        startDate = findViewById(R.id.startDateIn);
        endDate = findViewById(R.id.endDateIn);
        oneDate = findViewById(R.id.DateIn);

        endTime = findViewById(R.id.endTimeIn);
        startTime = findViewById(R.id.startTimeIn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        oneDateTV.setVisibility(View.INVISIBLE);
        oneDate.setVisibility(View.INVISIBLE);




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
            public void onDateSet (DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startDateLD = LocalDate.of(year, month, day);
                String date = CalendarUtils.formattedDate(startDateLD);
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
                        startDateLD = LocalDate.of(year, month, day);
                        String date = CalendarUtils.formattedDate(startDateLD);
                        startDate.setText(date);
                        spinnerRemoveChecker();
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
                endDateLD = LocalDate.of(year, month, day);
                String date = CalendarUtils.formattedDate(endDateLD);
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
                        endDateLD = LocalDate.of(year, month, day);
                        String date = CalendarUtils.formattedDate(endDateLD);
                        endDate.setText(date);
                        spinnerRemoveChecker();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        oneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecurringEventActivity.this,android.R.style.Theme_Holo_Light_Dialog, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener3 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet (DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startMonth = month;
                startYear = year;
                startDay = dayOfMonth;
                String date = year + "-" + month  + "-" + dayOfMonth;
                oneDate.setText(date);
            }

        };


        oneDate.setOnClickListener(new View.OnClickListener() {
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
                        oneDate.setText(date);
                        startDateLD = LocalDate.of(year, month, day);
                        endDateLD = LocalDate.of(year, month, day);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public ArrayList<LocalDate> getDates(LocalDate startDateLD, LocalDate endDateLD)
    {
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        int j = endDateLD.getDayOfYear();
        String spinnerText = daysSp2.getSelectedItem().toString().toUpperCase(Locale.ROOT);

        if(startDateLD.getDayOfYear() == endDateLD.getDayOfYear()){
            dates.add(startDateLD);
            return dates;
        }

        for (int i = startDateLD.getDayOfYear(); i <= j; i++){ // make lambda
            String dayOfWeek = startDateLD.getDayOfWeek().toString();
            if (dayOfWeek.equals(spinnerText)){
                dates.add(startDateLD);
            }
            startDateLD = startDateLD.plusDays(1);
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
                startTimeLT = LocalTime.of(selStartHour,selStartMin);
                String time = CalendarUtils.formattedTime(startTimeLT);
                startTime.setText(time);

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
                endTimeLT = LocalTime.of(selEndHour,selEndMin);
                String time = CalendarUtils.formattedTime(endTimeLT);
                endTime.setText(time);

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, endHour, endMin, true);
        timePickerDialog.show();

    }

    public void onAddEventBtn_Clicked(View caller)
    {
        if (allDayON){
            startHour = 0;
            startMin = 0;
            endHour= 24;
            endMin = 0;

            startTimeLT = LocalTime.of(0,0);
            endTimeLT = LocalTime.of(23,59);
        }
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

        if (startDateLD.getDayOfYear() > endDateLD.getDayOfYear()){
            Toast.makeText(this, "Date is incoherent", Toast.LENGTH_SHORT).show();
        }
        else {
            coherentDate = true;
        }


        if (coherentDate && coherentTime)
        {

            String eventDescription = eventDescriptionET.getText().toString();
            for (LocalDate date:getDates(startDateLD,endDateLD)) {
                Event newEvent = new Event(eventDescription, startTimeLT, endTimeLT, date, allDayON);
                Event.eventsList.add(newEvent);
            }

            finish();

            Intent intent = new Intent(this, AgendaScreenActivity.class);
            startActivity(intent);
        }
    }

    public void onAllDaySp_Clicked(View caller)
    {
        if (allDayON ==false)
        {
            startTimeTV.setVisibility(caller.INVISIBLE);
            startTime.setVisibility(caller.INVISIBLE);
            endTimeTV.setVisibility(caller.INVISIBLE);
            endTime.setVisibility(caller.INVISIBLE);
            allDayON =true;
        }
        else
        {
            startTimeTV.setVisibility(caller.VISIBLE);
            startTime.setVisibility(caller.VISIBLE);
            endTimeTV.setVisibility(caller.VISIBLE);
            endTime.setVisibility(caller.VISIBLE);
            allDayON =false;
        }


    }
    public void onOneTimeSp_Clicked(View caller) {
        if (oneTimeON == false) {
            startDateTV.setVisibility(caller.INVISIBLE);
            startDate.setVisibility(caller.INVISIBLE);
            endDateTV.setVisibility(caller.INVISIBLE);
            endDate.setVisibility(caller.INVISIBLE);
            oneDateTV.setVisibility(caller.VISIBLE);
            oneDate.setVisibility(caller.VISIBLE);
            oneTimeON = true;
            daysSp2.setVisibility(caller.INVISIBLE);
            dayTxt.setVisibility(caller.INVISIBLE);
        } else {
            startDateTV.setVisibility(caller.VISIBLE);
            startDate.setVisibility(caller.VISIBLE);
            endDateTV.setVisibility(caller.VISIBLE);
            endDate.setVisibility(caller.VISIBLE);
            oneDateTV.setVisibility(caller.INVISIBLE);
            oneDate.setVisibility(caller.INVISIBLE);
            oneTimeON = false;
        }

    }
    public void spinnerRemoveChecker(){
        if (startDateLD != null && endDateLD != null) {
            if(startDateLD.getDayOfYear() == endDateLD.getDayOfYear()){
                daysSp2.setVisibility(View.INVISIBLE);
                dayTxt.setVisibility(View.INVISIBLE);
            }
            else{
                daysSp2.setVisibility(View.VISIBLE);
                dayTxt.setVisibility(View.VISIBLE);
            }
        }

    }




}