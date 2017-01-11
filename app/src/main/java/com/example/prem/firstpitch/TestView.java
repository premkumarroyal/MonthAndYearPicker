package com.example.prem.firstpitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Prem on 26-Aug-16.
 */
class TestView extends FrameLayout implements View.OnClickListener {
    private YearPickerView yearView;
    private ListView monthList;
    private int _activatedYear;
    private int _activatedMonth;
    private int _minYear, _maxYear;
    private MyMonthViewAdapter monthViewAdapter;
    private TextView month, year, title;
    private Context _context;
    private String _headerTitle;
    private int headerFontColorSelected, headerFontColorNormal;
    private boolean showMonthOnly, showYearOnly;
    private int selectedMonth, selectedYear;
    private MonthPickerDialog.OnYearChangedListener _onYearChanged;
    private MonthPickerDialog.OnMonthChangedListener _onMonthChanged;
    HashMap<String, Integer> map;


    public TestView(Context context) {
        this(context, null);
        _context = context;
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        _context = context;
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.month_picker_view, this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.monthPickerDialog, defStyleAttr, 0);

        int headerBgColor = a.getColor(R.styleable.monthPickerDialog_headerBgColor, 0);
        headerFontColorNormal = a.getColor(R.styleable.monthPickerDialog_headerFontColorNormal, 0);
        headerFontColorSelected = a.getColor(R.styleable.monthPickerDialog_headerFontColorSelected, 0);
        int monthBgColor = a.getColor(R.styleable.monthPickerDialog_monthBgColor, 0);
        int monthBgSelectedColor = a.getColor(R.styleable.monthPickerDialog_monthBgSelectedColor, 0);
        int monthFontColorNormal = a.getColor(R.styleable.monthPickerDialog_monthFontColorNormal, 0);
        int monthFontColorSelected = a.getColor(R.styleable.monthPickerDialog_monthFontColorSelected, 0);
        int monthFontColorDisabled = a.getColor(R.styleable.monthPickerDialog_monthFontColorDisabled, 0);
        int headerTitleColor = a.getColor(R.styleable.monthPickerDialog_headerTitleColor, 0);

        if (monthFontColorNormal == 0) {
            monthFontColorNormal = getResources().getColor(R.color.fontBlackEnable);
        }
        if (monthFontColorSelected == 0) {
            monthFontColorSelected = getResources().getColor(R.color.fontWhiteEnable);
        }
        if (monthFontColorDisabled == 0) {
            monthFontColorDisabled = getResources().getColor(R.color.fontBlackDisable);
        }
        if (headerFontColorNormal == 0) {
            headerFontColorNormal = getResources().getColor(R.color.fontWhiteDisable);
        }
        if (headerFontColorSelected == 0) {
            headerFontColorSelected = getResources().getColor(R.color.fontWhiteEnable);
        }
        if (headerTitleColor == 0) {
            headerTitleColor = getResources().getColor(R.color.fontWhiteEnable);
        }
        if (monthBgColor == 0) {
            monthBgColor = getResources().getColor(R.color.fontWhiteEnable);
        }

        if (headerBgColor == 0) {
            int checkExistence = context.getResources().getIdentifier("colorAccent", "color", context.getPackageName());
            if (checkExistence != 0) {
                headerBgColor = context.getResources().getColor(R.color.colorAccent);
            } else {
                headerBgColor = getResources().getColor(R.color.colorAccent);
            }
        }

        if (monthBgSelectedColor == 0) {
            int checkExistence = context.getResources().getIdentifier("colorAccent", "color", context.getPackageName());
            if (checkExistence != 0) {
                monthBgSelectedColor = context.getResources().getColor(R.color.colorAccent);
            } else {
                monthBgSelectedColor = getResources().getColor(R.color.colorAccent);
            }
        }


        HashMap<String, Integer> map = new HashMap();
        map.put("monthBgColor", monthBgColor);
        map.put("monthBgSelectedColor", monthBgSelectedColor);
        map.put("monthFontColorNormal", monthFontColorNormal);
        map.put("monthFontColorSelected", monthFontColorSelected);
        map.put("monthFontColorDisabled", monthFontColorDisabled);
        Log.d("----------------", " headerBgColor" + headerBgColor + " headerFontColorNormal" + headerFontColorNormal + " headerFontColorSelected : "
                + headerFontColorSelected + " headerTitleColor : " + headerTitleColor + " monthBgColor:  " + monthBgColor + " monthBgSelectedColor:  "
                + monthBgSelectedColor + " monthFontColorDisabled : " + monthFontColorDisabled + " monthFontColorNormal : "
                + monthFontColorNormal + " monthFontColorSelected: " + monthFontColorSelected);

        a.recycle();

        monthList = (ListView) findViewById(R.id.listview);
        yearView = (YearPickerView) findViewById(R.id.yearView);
        month = (TextView) findViewById(R.id.month);
        year = (TextView) findViewById(R.id.year);
        title = (TextView) findViewById(R.id.title);
        RelativeLayout _pickerBg = (RelativeLayout) findViewById(R.id.picker_view);
        LinearLayout _header = (LinearLayout) findViewById(R.id.header);

        month.setTextColor(headerFontColorSelected);
        year.setTextColor(headerFontColorNormal);
        title.setTextColor(headerTitleColor);
        _header.setBackgroundColor(headerBgColor);
        _pickerBg.setBackgroundColor(monthBgColor);

        monthViewAdapter = new MyMonthViewAdapter(context);
        monthViewAdapter.setColors(map);
        monthViewAdapter.setOnDaySelectedListener(new MyMonthViewAdapter.OnDaySelectedListener() {
            @Override
            public void onDaySelected(MyMonthViewAdapter view, int selectedMonth) {
                Log.d("----------------", "TestView selected month = " + selectedMonth);
                if (!showMonthOnly) {
                    TestView.this.selectedMonth = selectedMonth;
                    monthList.setVisibility(View.GONE);
                    yearView.setVisibility(View.VISIBLE);
                    month.setTextColor(headerFontColorNormal);
                    year.setTextColor(headerFontColorSelected);
                    if (_onMonthChanged != null) {
                        _onMonthChanged.onMonthChanged(selectedMonth);
                    }
                }
                month.setText(_context.getResources().getStringArray(R.array.months)[selectedMonth - 1]);
            }
        });
        monthList.setAdapter(monthViewAdapter);

        yearView.setRange(_minYear, _maxYear);
        yearView.setColors(map);
        yearView.setYear(Calendar.getInstance().get(Calendar.YEAR));
        yearView.setOnYearSelectedListener(new YearPickerView.OnYearSelectedListener() {
            @Override
            public void onYearChanged(YearPickerView view, int selectedYear) {
                Log.d("----------------", "selected year = " + selectedYear);
                TestView.this.selectedYear = selectedYear;
                year.setText("" + selectedYear);
                year.setTextColor(headerFontColorSelected);
                month.setTextColor(headerFontColorNormal);
                if (_onYearChanged != null) {
                    _onYearChanged.onYearChanged(selectedYear);
                }
            }
        });
        month.setOnClickListener(this);
        year.setOnClickListener(this);
    }

    protected void init(int year, int monthOfYear) {
        _activatedYear = year;
        _activatedMonth = monthOfYear;
    }

    protected void setMaxMonth(int maxMonth) {
        if (maxMonth <= Calendar.DECEMBER && maxMonth >= Calendar.JANUARY) {
            monthViewAdapter.setMaxMonth(maxMonth);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }
    }


    protected void setMinMonth(int minMonth) {
        if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
            monthViewAdapter.setMinMonth(minMonth);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }
    }

    protected void setMinYear(int minYear) {
        yearView.setMinYear(minYear);
    }

    protected void setMaxYear(int maxYear) {
        yearView.setMaxYear(maxYear);
    }

    protected void showMonthOnly() {
        showMonthOnly = true;
        year.setVisibility(GONE);
    }

    protected void showYearOnly() {
        showYearOnly = true;
        monthList.setVisibility(View.GONE);
        yearView.setVisibility(VISIBLE);

        month.setVisibility(GONE);
        year.setTextColor(headerFontColorSelected);
    }

    protected void setActivatedMonth(int activatedMonth) {
        if (activatedMonth >= Calendar.JANUARY && activatedMonth <= Calendar.DECEMBER) {
            monthViewAdapter.setActivatedMonth(activatedMonth);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }

    }

    protected void setActivatedYear(int activatedYear) {
        yearView.setActivatedYear(activatedYear);
    }

    protected void setMonthRange(int minMonth, int maxMonth) {
        if (minMonth < maxMonth) {
            setMinMonth(minMonth);
            setMaxYear(maxMonth);
        } else {
            throw new IllegalArgumentException("maximum month is less then minimum month");
        }
    }

    protected void setYearRange(int minYear, int maxYear) {
        if (minYear < maxYear) {
            setMinYear(minYear);
            setMaxYear(maxYear);
        } else {
            throw new IllegalArgumentException("maximum year is less then minimum year");
        }
    }

    protected void setMonthYearRange(int minMonth, int maxMonth, int minYear, int maxYear) {
        setMonthRange(minMonth, maxMonth);
        setYearRange(minYear, maxYear);
    }

    protected void setTitle(String dialogTitle) {
        if (dialogTitle != null) {
            this._headerTitle = dialogTitle;
            title.setVisibility(VISIBLE);
        } else {
            title.setVisibility(GONE);
        }
    }

    protected int getMonth() {
        return selectedMonth;
    }

    protected int getYear() {
        return selectedYear;
    }

    protected void setOnMonthChangedListener(MonthPickerDialog.OnMonthChangedListener onMonthChangedListener) {
        if (onMonthChangedListener != null) {
            this._onMonthChanged = onMonthChangedListener;
        }
    }

    protected void setOnYearChangedListener(MonthPickerDialog.OnYearChangedListener onYearChangedListener) {
        if (onYearChangedListener != null) {
            this._onYearChanged = onYearChangedListener;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month:
                if (monthList.getVisibility() == GONE) {
                    yearView.setVisibility(GONE);
                    monthList.setVisibility(VISIBLE);
                    year.setTextColor(headerFontColorNormal);
                    month.setTextColor(headerFontColorSelected);
                }
                break;
            case R.id.year:
                if (yearView.getVisibility() == GONE) {
                    monthList.setVisibility(GONE);
                    yearView.setVisibility(VISIBLE);
                    year.setTextColor(headerFontColorSelected);
                    month.setTextColor(headerFontColorNormal);
                }
        }
    }
}
