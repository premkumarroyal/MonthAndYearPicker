package com.example.prem.firstpitch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.Calendar;

/**
 * Created by Prem on 25-Aug-16.
 */
public class MonthPickerView extends FrameLayout {

    private int _maxYear = 2100;
    private int _minYear = 1900;
    private int _minMonth = 0;
    private int _maxMonth = 12;
    private int _selectedMonth;
    private int _selectedYear;

    // private OnDaySelectedListener mOnDaySelectedListener;

    public MonthPickerView(Context context) {
        this(context, null);
    }

    public MonthPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.month_picker_view, this, true);
        Calendar today = Calendar.getInstance();
        _selectedMonth = today.get(Calendar.MONTH);
        _selectedYear = today.get(Calendar.YEAR);
    }


    protected void init(int year, int month) {

    }

    public void setMinMonthMinYear(int minMonth, int minYear) {
        _minMonth = minMonth;
        _minYear = minYear;
    }

    public void setMaxMonthMaxYear(int maxMonth, int maxYear) {
        _maxMonth = maxMonth;
        _maxYear = maxYear;
    }

    public int getMinMonth() {
        return _minMonth;
    }

    public int getMinYear() {
        return _minYear;
    }

    public int getMaxMonth() {
        return _maxMonth;
    }

    public int getMaxYear() {
        return _maxYear;
    }

    public int getSelectedMonth() {
        return _selectedMonth;
    }

    public int getSelectedYear() {
        return _selectedYear;
    }

    public void setCurrentMonthAndYear(int selectedMonth, int selectedYear) {
        _selectedYear = selectedYear;
        _selectedMonth = selectedMonth;
    }

    /**
     * Sets the listener to call when the user selects a day.
     *
     * @param b The listener to call.
     */
   /* public void setOnDaySelectedListener(OnDaySelectedListener listener) {
        mOnDaySelectedListener = listener;
    }*/

   /* public interface OnDaySelectedListener {
        void onDaySelected(DayPickerView view, Calendar day);
    }
*/
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}

