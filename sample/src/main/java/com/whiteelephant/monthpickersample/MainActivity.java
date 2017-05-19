package com.whiteelephant.monthpickersample;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.month_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                        Toast.makeText(MainActivity.this, "Date setted with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, 2020, Calendar.JUNE);

                builder.setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        Log.d(TAG, "Selected month : " + selectedMonth);
                        Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) {
                        Log.d(TAG, "Selected year : " + selectedYear);
                        Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setMinYear(1980);
                builder.setMinYear(2000);

                builder.build().show();

                /*builder.setActivatedMonth(Calendar.OCTOBER)
                        .setMaxMonth(Calendar.OCTOBER)
                        .setMinYear(1990)
                        .setActivatedYear(2005)
                        .setMinMonth(Calendar.JULY)
                        .setMaxYear(2015)
                        .setTitle("Select sales month")
                        .setMonthRange(Calendar.FEBRUARY, Calendar.OCTOBER)
                        .setYearRange(1890, 1890)
                        .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        *//*.showMonthOnly()
                        .showYearOnly()*//*
                        .build()
                        .show();*/
            }
        });

        findViewById(R.id.date_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, null, 2017,
                        cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
                dialog.show();
            }
        });
    }
}
