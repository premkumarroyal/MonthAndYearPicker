package com.whiteelephant.monthpicker;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.example.prem.firstpitch.R;

import java.util.Calendar;
import java.util.HashMap;


class MonthViewAdapter extends BaseAdapter {

    private int _minMonth, _maxMonth, _activatedMonth;
    private Context _context;
    private HashMap<String, Integer> _colors;
    private OnDaySelectedListener mOnDaySelectedListener;

    public MonthViewAdapter(Context context) {
        this._context = context;
        setRange();
    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final MonthView v;
        if (convertView != null) {
            v = (MonthView) convertView;
        } else {
            v = new MonthView(_context);
            v.setColors(_colors);

            // Set up the new view
            final AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);
            v.setClickable(true);
            v.setOnMonthClickListener(mOnDayClickListener);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            v.setBackgroundDrawable(_context.getDrawable(R.drawable.month_ripplr));
        }
        v.setMonthParams(_activatedMonth, _minMonth, _maxMonth);
        v.reuse();
        v.invalidate();
        return v;
    }

    private final MonthView.OnMonthClickListener mOnDayClickListener = new MonthView.OnMonthClickListener() {
        @Override
        public void onMonthClick(MonthView view, int day) {
            Log.d("MonthViewAdapter", "onDayClick " + day);
            if (isCalendarInRange(day)) {
                Log.d("MonthViewAdapter", "day not null && Calender in range " + day);
                setSelectedMonth(day);
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onDaySelected(MonthViewAdapter.this, day);
                }
            }
        }
    };

    boolean isCalendarInRange(int value) {
        return value >= _minMonth && value <= _maxMonth;
    }

    /**
     * Updates the selected day and related parameters.
     *
     * @param month The day to highlight
     */
    public void setSelectedMonth(int month) {
        Log.d("MonthViewAdapter", "setSelectedMonth : " + month);
        _activatedMonth = month;
        notifyDataSetChanged();
    }

    /* set min and max date and years*/
    public void setRange() {

        _minMonth = Calendar.JANUARY;
        _maxMonth = Calendar.DECEMBER;
        _activatedMonth = Calendar.AUGUST;
        notifyDataSetInvalidated();
    }

    /**
     * Sets the listener to call when the user selects a day.
     *
     * @param listener The listener to call.
     */
    public void setOnDaySelectedListener(OnDaySelectedListener listener) {
        mOnDaySelectedListener = listener;
    }

    public interface OnDaySelectedListener {
        void onDaySelected(MonthViewAdapter view, int month);
    }

    void setMaxMonth(int maxMonth) {
        if (maxMonth <= Calendar.DECEMBER && maxMonth >= Calendar.JANUARY) {
            _maxMonth = maxMonth;
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }
    }


    void setMinMonth(int minMonth) {
        if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
            _minMonth = minMonth;
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }
    }

    void setActivatedMonth(int activatedMonth) {
        if (activatedMonth >= Calendar.JANUARY && activatedMonth <= Calendar.DECEMBER) {
            _activatedMonth = activatedMonth;
        } else {
            throw new IllegalArgumentException("Month out of range please send months between Calendar.JANUARY, Calendar.DECEMBER");
        }
    }

    void setColors(HashMap map) {
        _colors = map;
    }
}
