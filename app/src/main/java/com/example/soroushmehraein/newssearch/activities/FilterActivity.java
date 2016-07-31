package com.example.soroushmehraein.newssearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.soroushmehraein.newssearch.DatePickerFragment;
import com.example.soroushmehraein.newssearch.R;
import com.example.soroushmehraein.newssearch.models.SearchFilters;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(SearchFilters.getInstance().getStartDateHumanString());
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SearchFilters.getInstance().setStartDate(c);
        tvStartDate.setText(SearchFilters.getInstance().getStartDateHumanString());
    }
}
