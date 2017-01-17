package com.urbanpiper.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.prem.firstpitch.MonthPickerDialog;

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
                        Log.d("MAIN ACTIVITY", "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                        Toast.makeText(MainActivity.this, "Date setted with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, 2017, Calendar.JANUARY);

                builder.build().show();

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

                /*builder.setActivatedMonth(Calendar.OCTOBER)
                        .setMaxMonth(Calendar.NOVEMBER)
                        .setMinYear(1990)
                        .setActivatedYear(2016)
                        .setMinMonth(Calendar.MARCH)
                        .setMaxYear(2050)
                        .setTitle("Select sales month")
                        .showMonthOnly()
                        .showYearOnly()
                        .build()
                        .show();*/
            }
        });
    }
}
