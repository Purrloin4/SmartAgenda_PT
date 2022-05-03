package com.example.smartagenda;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NewEventFragment extends DialogFragment {
    Button startTimeBtn;
    int startHour, startMin;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        startTimeBtn = (Button) getView().findViewById(R.id.popupBtn);
        return inflater.inflate(R.layout.fragment_new_event, container, false);
    }
    /*

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selStartHour, int selStartMin)
            {

                startHour = selStartHour;
                startMin = selStartMin;
                startTimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMin));

            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog (this, onTimeSetListener, startHour, startMin, true);
        timePickerDialog.show();

    }

     */

}