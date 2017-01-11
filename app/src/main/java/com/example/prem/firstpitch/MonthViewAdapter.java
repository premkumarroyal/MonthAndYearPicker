package com.example.prem.firstpitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.Calendar;

/**
 * Created by Prem on 15-Aug-16.
 */
public class MonthViewAdapter extends BaseAdapter {

    private Calendar mSelectedDay = Calendar.getInstance();
    private final Calendar mMinDate = Calendar.getInstance();
    private final Calendar mMaxDate = Calendar.getInstance();
    private ColorStateList mCalendarTextColors = ColorStateList.valueOf(Color.BLACK);
    private OnDaySelectedListener mOnDaySelectedListener;
    private int mFirstDayOfWeek;

    private Context context;

    public MonthViewAdapter(Context context){
       this.context = context;

        Calendar min = Calendar.getInstance();
        min.set(Calendar.DATE, 1);
        min.set(Calendar.MONTH, Calendar.JANUARY);
        min.set(Calendar.YEAR, 2016);

        Calendar max = Calendar.getInstance();
        min.set(Calendar.DATE, 1);
        min.set(Calendar.MONTH, Calendar.DECEMBER);
        min.set(Calendar.YEAR, 2015);
        setRange(min, max);
    }


    @Override
    public int getCount() {
        return 12;
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
            v = new MonthView(context);
            // Set up the new view
            final AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);
            v.setClickable(true);
            v.setOnDayClickListener(mOnDayClickListener);
            if (mCalendarTextColors != null) {
                v.setTextColor(mCalendarTextColors);
            }
        }
        final int minMonth = mMinDate.get(Calendar.MONTH);
        final int minYear = mMinDate.get(Calendar.YEAR);
        final int currentMonth = position + minMonth;
        final int month = currentMonth % 12;
        final int year = currentMonth / 12 + minYear;
        final int selectedDay;
        if (isSelectedDayInMonth(year, month)) {
            selectedDay = mSelectedDay.get(Calendar.DAY_OF_MONTH);
        } else {
            selectedDay = -1;
        }
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
        }
        v.setMonthParams(selectedDay, month, year, mFirstDayOfWeek,
                enabledDayRangeStart, enabledDayRangeEnd);
        v.invalidate();
        return v;
    }

    private final MonthView.OnDayClickListener mOnDayClickListener = new MonthView.OnDayClickListener() {
        @Override
        public void onDayClick(MonthView view, Calendar day) {
            Log.d("SimpleMonthAdapter", "onDayClick " + day);
            if (day != null && isCalendarInRange(day)) {
                Log.d("SimpleMonthAdapter", "day not null && Calender in range " + day);
                setSelectedDay(day);
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onDaySelected(MonthViewAdapter.this, day);
                }
            }
        }
    };

    private boolean isCalendarInRange(Calendar value) {
        return value.compareTo(mMinDate) >= 0 && value.compareTo(mMaxDate) <= 0;
    }

    /**
     * Updates the selected day and related parameters.
     *
     * @param day The day to highlight
     */
    public void setSelectedDay(Calendar day) {
        Log.d("SimpleMonthAdapter", "setSelectedDay : " + day);
        mSelectedDay = day;
        notifyDataSetChanged();
    }

    /* set min and max date and years*/
    public void setRange(Calendar min, Calendar max) {
        mMinDate.setTimeInMillis(min.getTimeInMillis());
        mMaxDate.setTimeInMillis(max.getTimeInMillis());
        notifyDataSetInvalidated();
    }

    private boolean isSelectedDayInMonth(int year, int month) {
        return mSelectedDay.get(Calendar.YEAR) == year && mSelectedDay.get(Calendar.MONTH) == month;
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
        public void onDaySelected(MonthViewAdapter view, Calendar day);
    }
}
