package com.whiteelephant.monthpickersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.urbanpiper.sample.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  Jan -> 0, Feb -> 2 on select on month
         *  Need to test app completely
         *
         *
         *  Read the code line by line and understand it well.
         *  rename/delete unwanted code
         *  Remove toast msgs, unwanted logs if any.
         *  Change the package name if possible
         *
         *
         */

        findViewById(R.id.month_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(com.urbanpiper.sample.MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                        Toast.makeText(com.urbanpiper.sample.MainActivity.this, "Date setted with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, 2017, Calendar.JANUARY);

                builder.build().show();

                builder.setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        Log.d(TAG, "Selected month : " + selectedMonth);
                        Toast.makeText(com.urbanpiper.sample.MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) {
                        Log.d(TAG, "Selected year : " + selectedYear);
                        Toast.makeText(com.urbanpiper.sample.MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
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
