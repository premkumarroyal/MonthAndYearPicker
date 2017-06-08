package com.whiteelephant.monthpickersample;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNormalPicker();

         // goto styles.xml and change the monthPickerStyles for below three layouts
        
        //setBottleView();
        //chooseMonthOnly();
        //chooseYearOnly();

    }


    private void setNormalPicker(){
        setContentView(R.layout.activity_main);
        final Calendar today = Calendar.getInstance();
        findViewById(R.id.month_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                        Toast.makeText(MainActivity.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        Log.d(TAG, "Selected month : " + selectedMonth);
                        // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) {
                        Log.d(TAG, "Selected year : " + selectedYear);
                        // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                });
               /* builder.setMinYear(1980);
                builder.setMaxYear(2030);

                builder.build().show();*/

                builder.setActivatedMonth(Calendar.JULY)
                        // .setMaxMonth(Calendar.OCTOBER)
                        .setMinYear(1990)
                        .setActivatedYear(2017)
                        .setMinMonth(Calendar.FEBRUARY)
                        .setMaxYear(2030)
                        .setTitle("Select trading month")
                        .setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .build()
                        .show();
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

    private void chooseMonthOnly() {
        setContentView(R.layout.activity_choose_month);

        findViewById(R.id.choose_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {

                    }
                }, /* activated number in year */ 3, 5);

                builder.showMonthOnly()
                        .build()
                        .show();
            }
        });
    }

    int choosenYear = 2017;
    private void chooseYearOnly() {
        setContentView(R.layout.activity_choose_year);

        final TextView year = (TextView) findViewById(R.id.year);
        findViewById(R.id.choose_year).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        year.setText(Integer.toString(selectedYear));
                        choosenYear = selectedYear;
                    }
                }, choosenYear, 0);

                builder.showYearOnly()
                        .setYearRange(1990, 2030)
                        .build()
                        .show();
            }
        });
    }

    private void setBottleView() {

        setContentView(R.layout.activity_bottle);

        LinearLayout chooseQty = (LinearLayout) findViewById(R.id.select_quantity);
        final TextView qty = (TextView) findViewById(R.id.qty);


        chooseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        qty.setText(Integer.toString(selectedYear));
                    }
                }, /* activated number in year */ 3, 0);

                builder.setActivatedMonth(Calendar.JULY)
                        // .setMaxMonth(Calendar.OCTOBER)
                        //.setMinYear(1990)
                        //.setActivatedYear(3)
                        //.setMinMonth(Calendar.FEBRUARY)
                        //.setMaxYear(2030)
                        .setTitle("Select Quantity")
                        //.setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
                        .setYearRange(1, 15)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        .showYearOnly()
                        .build()
                        .show();
            }
        });

    }

}
