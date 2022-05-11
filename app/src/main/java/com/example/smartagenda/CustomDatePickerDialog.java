package com.example.smartagenda;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.time.LocalDate;

public class CustomDatePickerDialog extends DatePickerDialog {

    public static interface CustomOnDateSetListener {
        void onDateSet(DatePicker view, LocalDate date);
    }

    public CustomDatePickerDialog(Context context,
                                  CustomOnDateSetListener listener, LocalDate date) {
        super(context, adaptListener(listener),
                date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
    }

    private static OnDateSetListener adaptListener(CustomOnDateSetListener customListener) {
        return new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                customListener.onDateSet(view, LocalDate.of(year, month + 1, dayOfMonth));
            }
        };
    }

}
