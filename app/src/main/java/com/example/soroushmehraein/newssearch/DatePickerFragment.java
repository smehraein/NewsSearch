package com.example.soroushmehraein.newssearch;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.soroushmehraein.newssearch.activities.FilterActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/30/16
 */
public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = (Calendar) getArguments().getSerializable(FilterActivity.INTENT_CALENDAR);
        assert c != null;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();

        // Create a new instance of TimePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        return dialog;
    }
}
