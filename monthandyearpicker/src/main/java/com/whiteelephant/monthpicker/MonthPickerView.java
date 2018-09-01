package com.whiteelephant.monthpicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.prem.firstpitch.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

class MonthPickerView extends FrameLayout {

    YearPickerView _yearView;
    ListView _monthList;
    static int _minYear = 1900, _maxYear = Calendar.getInstance().get(Calendar.YEAR);
    MonthViewAdapter _monthViewAdapter;
    TextView _month, _year, _title;
    Context _context;
    int _headerFontColorSelected, _headerFontColorNormal;
    boolean _showMonthOnly;
    int _selectedMonth, _selectedYear;
    MonthPickerDialog.OnYearChangedListener _onYearChanged;
    MonthPickerDialog.OnMonthChangedListener _onMonthChanged;
    OnDateSet _onDateSet;
    OnCancel _onCancel;
    private String[] _monthNames;

    /*private static final int[] ATTRS_TEXT_COLOR = new int[] {
            com.android.internal.R.attr.textColor};
    private static final int[] ATTRS_DISABLED_ALPHA = new int[] {
            com.android.internal.R.attr.disabledAlpha};*/

    public MonthPickerView(Context context) {
        this(context, null);
        _context = context;
    }

    public MonthPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        _context = context;
    }

    public MonthPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.month_picker_view, this);
        _monthNames = new DateFormatSymbols(Locale.getDefault()).getShortMonths();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.monthPickerDialog,
                defStyleAttr, 0);

        // getting default values based on the user's theme.

        /*

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                headerBgColor = android.R.attr.colorAccent;
            } else {
                //Get colorAccent defined for AppCompat
                headerBgColor = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
            }
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(headerBgColor, outValue, true);
            int color = outValue.data;

        // OR
        TypedValue typedValue = new TypedValue();

        TypedArray a = mContext.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent, R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        // OR

        final TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (R.attr.colorAccent, value, true);
        int color = value.data
    */

        int headerBgColor = a.getColor(R.styleable.monthPickerDialog_headerBgColor, 0);
        _headerFontColorNormal = a.getColor(R.styleable.monthPickerDialog_headerFontColorNormal, 0);
        _headerFontColorSelected = a.getColor(R.styleable.monthPickerDialog_headerFontColorSelected, 0);
        int monthBgColor = a.getColor(R.styleable.monthPickerDialog_monthBgColor, 0);
        int monthBgSelectedColor = a.getColor(R.styleable.monthPickerDialog_monthBgSelectedColor, 0);
        int monthFontColorNormal = a.getColor(R.styleable.monthPickerDialog_monthFontColorNormal, 0);
        int monthFontColorSelected = a.getColor(R.styleable.monthPickerDialog_monthFontColorSelected, 0);
        int monthFontColorDisabled = a.getColor(R.styleable.monthPickerDialog_monthFontColorDisabled, 0);
        int headerTitleColor = a.getColor(R.styleable.monthPickerDialog_headerTitleColor, 0);
        int actionButtonColor = a.getColor(R.styleable.monthPickerDialog_dialogActionButtonColor, 0);

         if (monthFontColorNormal == 0) {

             monthFontColorNormal = getResources().getColor(R.color.fontBlackEnable);

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                monthFontColorNormal = android.R.attr.textColor;
            } else {
                monthFontColorNormal = getResources().getIdentifier("textColor", "attr", null);
            }
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(monthFontColorNormal, outValue, true);
            int color = outValue.data;
            monthFontColorNormal = color;*/


            /*monthFontColorNormal = context.getTheme().resolveAttribute(
                    android.R.attr.textColorPrimary, outValue, true) ? outValue.data : getResources().getColor(R.color.fontBlackEnable);*/

        }

        if (monthFontColorSelected == 0) {
            monthFontColorSelected = getResources().getColor(R.color.fontWhiteEnable);
        }

        if (monthFontColorDisabled == 0) {
            monthFontColorDisabled = getResources().getColor(R.color.fontBlackDisable);

        }
        if (_headerFontColorNormal == 0) {
            _headerFontColorNormal = getResources().getColor(R.color.fontWhiteDisable);
        }
        if (_headerFontColorSelected == 0) {
            _headerFontColorSelected = getResources().getColor(R.color.fontWhiteEnable);
        }
        if (headerTitleColor == 0) {
            headerTitleColor = getResources().getColor(R.color.fontWhiteEnable);
        }
        if (monthBgColor == 0) {
            monthBgColor = getResources().getColor(R.color.fontWhiteEnable);
        }

        if (headerBgColor == 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                headerBgColor = android.R.attr.colorAccent;
            } else {
                //Get colorAccent defined for AppCompat
                headerBgColor = context.getResources().getIdentifier("colorAccent",
                        "attr", context.getPackageName());
            }
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(headerBgColor, outValue, true);
            headerBgColor = outValue.data;
        }

        if (monthBgSelectedColor == 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                monthBgSelectedColor = android.R.attr.colorAccent;
            } else {
                //Get colorAccent defined for AppCompat
                monthBgSelectedColor = context.getResources().getIdentifier("colorAccent",
                        "attr", context.getPackageName());
            }
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(monthBgSelectedColor, outValue, true);
            monthBgSelectedColor = outValue.data;
        }

        HashMap<String, Integer> map = new HashMap();
        if (monthBgColor != 0)
            map.put("monthBgColor", monthBgColor);
        if (monthBgSelectedColor != 0)
            map.put("monthBgSelectedColor", monthBgSelectedColor);
        if (monthFontColorNormal != 0)
            map.put("monthFontColorNormal", monthFontColorNormal);
        if (monthFontColorSelected != 0)
            map.put("monthFontColorSelected", monthFontColorSelected);
        if (monthFontColorDisabled != 0)
            map.put("monthFontColorDisabled", monthFontColorDisabled);

        a.recycle();

        _monthList = (ListView) findViewById(R.id.listview);
        _yearView = (YearPickerView) findViewById(R.id.yearView);
        _month = (TextView) findViewById(R.id.month);
        _year = (TextView) findViewById(R.id.year);
        _title = (TextView) findViewById(R.id.title);
        RelativeLayout _pickerBg = (RelativeLayout) findViewById(R.id.picker_view);
        LinearLayout _header = (LinearLayout) findViewById(R.id.header);
        RelativeLayout _actionBtnLay = (RelativeLayout) findViewById(R.id.action_btn_lay);
        TextView ok = (TextView) findViewById(R.id.ok_action);
        TextView cancel = (TextView) findViewById(R.id.cancel_action);


        if (actionButtonColor != 0) {
            ok.setTextColor(actionButtonColor);
            cancel.setTextColor(actionButtonColor);
        } else {
            ok.setTextColor(headerBgColor);
            cancel.setTextColor(headerBgColor);
        }

        if (_headerFontColorSelected != 0)
            _month.setTextColor(_headerFontColorSelected);
        if (_headerFontColorNormal != 0)
            _year.setTextColor(_headerFontColorNormal);
        if (headerTitleColor != 0)
            _title.setTextColor(headerTitleColor);
        if (headerBgColor != 0)
            _header.setBackgroundColor(headerBgColor);
        if (monthBgColor != 0)
            _pickerBg.setBackgroundColor(monthBgColor);
        if(monthBgColor != 0)
            _actionBtnLay.setBackgroundColor(monthBgColor);

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                _onDateSet.onDateSet();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                _onCancel.onCancel();
            }
        });
        _monthViewAdapter = new MonthViewAdapter(context);
        _monthViewAdapter.setColors(map);
        _monthViewAdapter.setOnDaySelectedListener(new MonthViewAdapter.OnDaySelectedListener() {
            @Override
            public void onDaySelected(MonthViewAdapter view, int selectedMonth) {
                Log.d("----------------", "MonthPickerDialogStyle selected month = " + selectedMonth);
                MonthPickerView.this._selectedMonth = selectedMonth;
                _month.setText(_monthNames[selectedMonth]);
                if (!_showMonthOnly) {
                    _monthList.setVisibility(View.GONE);
                    _yearView.setVisibility(View.VISIBLE);
                    _month.setTextColor(_headerFontColorNormal);
                    _year.setTextColor(_headerFontColorSelected);
                }
                if (_onMonthChanged != null) {
                    _onMonthChanged.onMonthChanged(selectedMonth);
                }
            }
        });
        _monthList.setAdapter(_monthViewAdapter);

        _yearView.setRange(_minYear, _maxYear);
        _yearView.setColors(map);
        _yearView.setYear(Calendar.getInstance().get(Calendar.YEAR));
        _yearView.setOnYearSelectedListener(new YearPickerView.OnYearSelectedListener() {
            @Override
            public void onYearChanged(YearPickerView view, int selectedYear) {
                Log.d("----------------", "selected year = " + selectedYear);
                MonthPickerView.this._selectedYear = selectedYear;
                _year.setText("" + selectedYear);
                _year.setTextColor(_headerFontColorSelected);
                _month.setTextColor(_headerFontColorNormal);
                if (_onYearChanged != null) {
                    _onYearChanged.onYearChanged(selectedYear);
                }
            }
        });
        _month.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_monthList.getVisibility() == GONE) {
                    _yearView.setVisibility(GONE);
                    _monthList.setVisibility(VISIBLE);
                    _year.setTextColor(_headerFontColorNormal);
                    _month.setTextColor(_headerFontColorSelected);
                }
            }
        });
        _year.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                if (_yearView.getVisibility() == GONE) {
                    _monthList.setVisibility(GONE);
                    _yearView.setVisibility(VISIBLE);
                    _year.setTextColor(_headerFontColorSelected);
                    _month.setTextColor(_headerFontColorNormal);
                }
            }
        });
    }

    protected void init(int year, int month) {
        _selectedYear = year;
        _selectedMonth = month;
    }

    protected void setMaxMonth(int maxMonth) {
        if (maxMonth <= Calendar.DECEMBER && maxMonth >= Calendar.JANUARY) {
            _monthViewAdapter.setMaxMonth(maxMonth);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between " +
                    "Calendar.JANUARY, Calendar.DECEMBER");
        }
    }


    protected void setMinMonth(int minMonth) {
        if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
            _monthViewAdapter.setMinMonth(minMonth);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between" +
                    " Calendar.JANUARY, Calendar.DECEMBER");
        }
    }

    protected void setMinYear(int minYear) {
        _yearView.setMinYear(minYear);
    }

    protected void setMaxYear(int maxYear) {
        _yearView.setMaxYear(maxYear);
    }

    protected void showMonthOnly() {
        _showMonthOnly = true;
        _year.setVisibility(GONE);
    }

    protected void showYearOnly() {
        _monthList.setVisibility(View.GONE);
        _yearView.setVisibility(VISIBLE);

        _month.setVisibility(GONE);
        _year.setTextColor(_headerFontColorSelected);
    }

    protected void setActivatedMonth(int activatedMonth) {
        if (activatedMonth >= Calendar.JANUARY && activatedMonth <= Calendar.DECEMBER) {
            _monthViewAdapter.setActivatedMonth(activatedMonth);
           _month.setText(_monthNames[activatedMonth]);
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }

    }

    protected void setActivatedYear(int activatedYear) {
        _yearView.setActivatedYear(activatedYear);
        _year.setText(Integer.toString(activatedYear));
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
        if (dialogTitle != null && dialogTitle.trim().length() > 0) {
            _title.setText(dialogTitle);
            _title.setVisibility(VISIBLE);
        } else {
            _title.setVisibility(GONE);
        }
    }

    protected int getMonth() {
        return _selectedMonth;
    }

    protected int getYear() {
        return _selectedYear;
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

    public void setOnDateListener(OnDateSet onDateSet) {
        this._onDateSet = onDateSet;
    }

    public void setOnCancelListener(OnCancel onCancel) {
        this._onCancel = onCancel;
    }


    public interface OnDateSet{
        void onDateSet();
    }
    public interface OnCancel{
        void onCancel();
    }

    MonthPickerDialog.OnConfigChangeListener configChangeListener;

    protected void setOnConfigurationChanged(MonthPickerDialog.OnConfigChangeListener configChangeListener){
        this.configChangeListener = configChangeListener;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        configChangeListener.onConfigChange();
        super.onConfigurationChanged(newConfig);
    }
}
