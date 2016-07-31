package com.example.soroushmehraein.newssearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.soroushmehraein.newssearch.DatePickerFragment;
import com.example.soroushmehraein.newssearch.R;
import com.example.soroushmehraein.newssearch.models.SearchFilters;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView tvStartDate;
    Spinner spSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(SearchFilters.getInstance().getStartDateHumanString());

        spSort = (Spinner) findViewById(R.id.spSort);
        assert spSort != null;
        spSort.setOnItemSelectedListener(this);
        setSpinnerToValue(spSort, SearchFilters.getInstance().getSort());

        createCheckboxes();
    }

    private void createCheckboxes() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.llCheckbox);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < SearchFilters.NEWS.values().length; i++) {
            SearchFilters.NEWS newsDesk = SearchFilters.NEWS.values()[i];
            CheckBox checkbox = new CheckBox(this);
            checkbox.setLayoutParams(layoutParams);
            checkbox.setText(newsDesk.name());
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String newsDesk = (String) buttonView.getText();
                    SearchFilters.NEWS newsDeskEnum = SearchFilters.NEWS.valueOf(newsDesk);
                    SearchFilters.getInstance().setNewsDesk(newsDeskEnum, isChecked);
                }
            });
            Boolean checked = SearchFilters.getInstance().checkNewsDesk(newsDesk);
            checkbox.setChecked(checked);
            assert layout != null;
            layout.addView(checkbox);
        }
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

    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sort = (String) parent.getItemAtPosition(position);
        SearchFilters.getInstance().setSort(sort);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
