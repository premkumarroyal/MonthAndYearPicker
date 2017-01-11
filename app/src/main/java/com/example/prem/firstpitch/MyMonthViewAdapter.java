package com.example.prem.firstpitch;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Prem on 15-Aug-16.
 */
public class MyMonthViewAdapter extends BaseAdapter {

    private OnDaySelectedListener mOnDaySelectedListener;
    private int _minMonth, _maxMonth, _activatedMonth;
    private Context context;
    private HashMap<String,Integer> colors;

    public MyMonthViewAdapter(Context context) {
        this.context = context;
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
        final MyMonthView v;
        if (convertView != null) {
            v = (MyMonthView) convertView;
        } else {
            v = new MyMonthView(context);
            Log.d("TESTVIEW-SetAdapter", "map is here -> " + colors.size());
            v.setColors(colors);

            // Set up the new view
            final AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);
            v.setClickable(true);
            v.setOnDayClickListener(mOnDayClickListener);
        }
        /*final int minMonth = mMinDate.get(Calendar.MONTH);
        final int minYear = mMinDate.get(Calendar.YEAR);
        final int currentMonth = position + minMonth;
        final int month = currentMonth % 12;
        final int year = currentMonth / 12 + minYear;
        // Invokes requestLayout() to ensure that the recycled view is set with the appropriate
        // height/number of weeks before being displayed.
        v.reuse();
        final int enabledDayRangeStart;
        if (minMonth == month && minYear == year) {
            enabledDayRangeStart = mMinDate.get(Calendar.DAY_OF_MONTH);
        } else {
            enabledDayRangeStart = 1;
        }
        final int enabledDayRangeEnd;
        if (mMaxDate.get(Calendar.MONTH) == month && mMaxDate.get(Calendar.YEAR) == year) {
            enabledDayRangeEnd = mMaxDate.get(Calendar.DAY_OF_MONTH);
        } else {
            enabledDayRangeEnd = 31;
        }*/
        //  v.setMonthParams(selectedDay, month, year, mFirstDayOfWeek, enabledDayRangeStart, enabledDayRangeEnd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            v.setBackgroundDrawable(context.getDrawable(R.drawable.month_ripplr));
        }
        v.setMonthParams(_activatedMonth, _minMonth, _maxMonth);
        v.reuse();
        v.invalidate();
        return v;
    }

    private final MyMonthView.OnDayClickListener mOnDayClickListener = new MyMonthView.OnDayClickListener() {
        @Override
        public void onDayClick(MyMonthView view, int day) {
            Log.d("MyMonthViewAdapter", "onDayClick " + day);
            if (isCalendarInRange(day)) {
                Log.d("MyMonthViewAdapter", "day not null && Calender in range " + day);
                setSelectedDay(day);
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onDaySelected(MyMonthViewAdapter.this, day);
                }
            }
        }
    };

    private boolean isCalendarInRange(int value) {
        return value > _minMonth && value <= _maxMonth + 1;
    }

    /**
     * Updates the selected day and related parameters.
     *
     * @param day The day to highlight
     */
    public void setSelectedDay(int day) {
        Log.d("MyMonthViewAdapter", "setSelectedDay : " + day);
        _activatedMonth = day - 1;
        notifyDataSetChanged();
    }

    /* set min and max date and years*/
    public void setRange() {

        _minMonth = Calendar.JANUARY;
        _maxMonth = Calendar.DECEMBER + 1;
        _activatedMonth = Calendar.AUGUST;
        notifyDataSetInvalidated();
    }

    /*private boolean isSelectedDayInMonth(int year, int month) {
        return mSelectedDay.get(Calendar.YEAR) == year && mSelectedDay.get(Calendar.MONTH) == month;
    }*/

    /**
     * Sets the listener to call when the user selects a day.
     *
     * @param listener The listener to call.
     */
    public void setOnDaySelectedListener(OnDaySelectedListener listener) {
        mOnDaySelectedListener = listener;
    }

    public interface OnDaySelectedListener {
        void onDaySelected(MyMonthViewAdapter view, int day);
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
        colors = map;
        Log.d("TESTVIEW", "map is here -> " + map.size());
    }
}
