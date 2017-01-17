package com.example.prem.firstpitch;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView monthList;
    YearPickerView yearView;
    TextView monthPicker, datePicker;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthList = (ListView) findViewById(R.id.listview);
        monthPicker = (TextView) findViewById(R.id.month_picker);
        datePicker = (TextView) findViewById(R.id.date_picker);

        // monthList = (MyMonthView) findViewById(R.id.monthView);

        yearView = (YearPickerView) findViewById(R.id.yearView);

       /* MyMonthViewAdapter monthViewAdapter = new MyMonthViewAdapter(this, null);
        monthViewAdapter.setOnDaySelectedListener(new MyMonthViewAdapter.OnMonthClickListener() {
            @Override
            public void onDaySelected(MyMonthViewAdapter view, int day) {
                Log.d("----------------", "selected month = " + day);
                monthList.setVisibility(View.GONE);
                yearView.setVisibility(View.VISIBLE);

            }
        });
        monthList.setAdapter(monthViewAdapter); */

       /* Calendar min = Calendar.getInstance();
        min.set(Calendar.DATE, 1);
        min.set(Calendar.MONTH, Calendar.FEBRUARY);
        min.set(Calendar.YEAR, 1980);

        Calendar max = Calendar.getInstance();
        max.set(Calendar.DATE, 1);
        max.set(Calendar.MONTH, Calendar.OCTOBER);
        max.set(Calendar.YEAR, 2050);*/


        /**
         *  @TODO
         *
         *  All are hardCoded now make them efficient -> Calender.min/max etc
         *  apply ripple effect -> both year and month view -> month view u need a animation u can't apply directly ripple.
         *  Scroll selected year view to middle
         *  need to provide a doc for methods in only MonthPickerDialog.Builder because that the only the user facing class. if u need interanlly just give the comments
         *
         *
         *  Optional :
         *  Reduce the dialog size like date picker android.
         *  if can provide font changing also : http://stackoverflow.com/questions/12128331/how-to-change-fontfamily-of-textview-in-android
         *  apply animations for dialog open and close : http://stackoverflow.com/questions/4817014/animate-a-custom-dialog
         *                                               http://stackoverflow.com/questions/32908279/change-dialogfragment-enter-exit-transition-at-just-before-dismissing
         *  Portrait View/LandScape View -> LOW Priority and not easy, need to write listview completly
         *
         *  Set cancel button for dialog -> DONE on 15 Dec 2016 1:06AM
         *  Hardcode colors internally if colors not setted use default, else take from theme..  -> DONE on 15 Dec 2016 1:02AM
         *  Picker.setHeaderTitle :  it can be setVisible visible/gone. If user set Title then automatically it will show else it will hide... -> DONE
         *  showMonthOnly, setYearOnly -> DONE
         *  need to implement and their listeners accordingly... DONE ->
         *  Need to set listeners onMonthChanged, onYearChanged -> DONE
         *  Change font color for not selectable months. -> similar to DatePicker setMaxDate()  - DONE
         *
         * showMonthOnly(),setYearOnly() :
         * 1. No params provided.
         * 2. Can give other value as null/0
         * 3. on Date set alternative value can be given as null/0
         * 4. if required value not sent then exception will be raised.
         * 5. Need to test well, if user setMonthAndYear at once need to igone the alternatives
         *
         *
         *
         *  Everything should be in builder patten and these methods should be inside MonthPickerDialog
         *
         *  setMaxMonth( int month )
         *  setMinMonth( int month )
         *  setMonthRange( int min, int max )
         *  setActivatedMonth(int selectMonth)
         *  updateMonth(int month); - cant update view once u show dialog - Can't be done after showing
         *
         *  setMaxYear( int minYear )
         *  setMinYear( int maxYear )
         *  setYearRange( int min, int max )
         *  setActivatedYear(int selectyear)
         *  updateYear(year);  - cant update view once u show dialog
         *
         *  // this is to set month and year at a time.
         *  setDate(Calender min, Calender max) - no need
         *  setMonthYearRange(int minMonth, int maxMonth, int minYear, int maxYear)
         *
         *  // all below colors need to accept from xml file else accept the theme..
         *  setTheme();
         *  setBackGroundColor();
         *  setDefaultFontcolor();
         *  setTodayFontColor();
         *  setSelectedfontColor();
         *
         *  setHeaderColor();
         *  setHeaderFontColor();
         *
         *  onMonthChangedListner(); - no need - can be use in DateSetListner - implemented this may needed
         *  onYearChangedListner(); - no need - can be use in DateSetListner - implemented this may needed
         *  onDateSetListner(); - no need - can be use in DateSetListner
         *  onDismissListner(); - no need - can be use in DateSetListner
         *
         */

        yearView.setRange(1980, 2100);
        yearView.setYear(Calendar.getInstance().get(Calendar.YEAR));
        yearView.setOnYearSelectedListener(new YearPickerView.OnYearSelectedListener() {
            @Override
            public void onYearChanged(YearPickerView view, int year) {
                Log.d("----------------", "selected year = " + year);
                yearView.setVisibility(View.GONE);
                monthList.setVisibility(View.VISIBLE);
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                Log.d("datePickerDailog..", "yuuuuuuuuuuuuup");
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                //@TODO check what are methods are theere for DatePicker May need to give
                                // almost all methods to picker view

                            }
                        }, 2016, 7, 19);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });


        monthPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /**
                 *  All methods are implemented.. but need to test it well
                 *  > min and max month background color...
                 *  > selected month background and text color and text size
                 *  1. App crashing if user setting activated value first then setting min max or range. This type of errors may occur frequrently
                 *  so need to build builder class...
                 *
                 *  > Builder class need to build to chain all these classes. If u use builder class then u can get rid of all above crashes
                 *  because all method calling will be in my hand
                 *  > Theme need to prepare
                 *  > remove hardcoding of all sizes and allow overriding if possible using styles or somehow..
                 *
                 */


                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(TestView view, int selectedMonth, int selectedYear) {
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
