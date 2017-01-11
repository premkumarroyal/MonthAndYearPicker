package com.example.prem.firstpitch;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;


/**
 * Displays a selectable list of years.
 */
public class YearPickerView extends ListView {
    private final YearAdapter mAdapter;
    private final int mViewSize;
    private final int mChildSize;
    private OnYearSelectedListener mOnYearSelectedListener;
    private Context context;
    HashMap<String,Integer> colors;

    public YearPickerView(Context context){
        this(context,null);
    }
    public YearPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.AppTheme);
    }

    public YearPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        final LayoutParams frame = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(frame);
        final Resources res = context.getResources();
        mViewSize = res.getDimensionPixelOffset(R.dimen.datepicker_view_animator_height);
        mChildSize = res.getDimensionPixelOffset(R.dimen.datepicker_year_label_height);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int year = mAdapter.getYearForPosition(position);
                mAdapter.setSelection(year);
                if (mOnYearSelectedListener != null) {
                    mOnYearSelectedListener.onYearChanged(YearPickerView.this, year);
                }
            }
        });
        mAdapter = new YearAdapter(getContext());
        setAdapter(mAdapter);
    }

    public void setOnYearSelectedListener(OnYearSelectedListener listener) {
        mOnYearSelectedListener = listener;
    }

    /**
     * Sets the currently selected year. Jumps immediately to the new year.
     *
     * @param year the target year
     */
    public void setYear(final int year) {
        mAdapter.setSelection(year);
        post(new Runnable() {
            @Override
            public void run() {
                final int position = mAdapter.getPositionForYear(year);
                if (position >= 0 /*&& position < getCount()*/) {
                    setSelectionCentered(position);
                }
            }
        });
    }

    public void setSelectionCentered(int position) {
        final int offset = mViewSize / 2 - mChildSize / 2;
        setSelectionFromTop(position, offset);
    }

    public void setRange(int min, int max) {
        mAdapter.setRange(min, max);
    }

    public void setColors(HashMap<String,Integer> colors) {
        this.colors = colors;
    }

    private class YearAdapter extends BaseAdapter {
        private final int ITEM_LAYOUT = R.layout.year_label_text_view;
        private final LayoutInflater mInflater;
        private int mActivatedYear;
        private int mMinYear, mMaxYear, mCount;

        public YearAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public void setRange(int min, int max) {
            int count = max - min + 1;
            if (mMinYear != min ||mMaxYear != max || mCount != count ) {
                mMinYear = min;
                mMaxYear = max;
                mCount = count;
                notifyDataSetInvalidated();
            }
        }

        public boolean setSelection(int year) {
            if (mActivatedYear != year) {
                mActivatedYear = year;
                notifyDataSetChanged();
                return true;
            }
            return false;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Integer getItem(int position) {
            return getYearForPosition(position);
        }

        @Override
        public long getItemId(int position) {
            return getYearForPosition(position);
        }

        public int getPositionForYear(int year) {
            return year - mMinYear;
        }

        public int getYearForPosition(int position) {
            return mMinYear + position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView v;
            final boolean hasNewView = convertView == null;
            if (hasNewView) {
                v = (TextView) mInflater.inflate(ITEM_LAYOUT, parent, false);
            } else {
                v = (TextView) convertView;
            }
            final int year = getYearForPosition(position);
            final boolean activated = mActivatedYear == year;

            // if (hasNewView || v.isActivated() != activated) {
            if (hasNewView || v.getTag() != null || v.getTag().equals(activated)) {
                if (activated) {
                    v.setTextColor(colors.get("monthBgSelectedColor"));
                    v.setTextSize(25);
                } else {
                    v.setTextColor(colors.get("monthFontColorNormal"));
                    v.setTextSize(20);
                }
                // v.setActivated(activated);
                    v.setTag(activated);

            }
            v.setText(Integer.toString(year));
            return v;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        protected void setMaxYear(int maxYear) {
            Log.d("YearView---","maxYear " +maxYear);
            mMaxYear = maxYear;
            mCount = mMaxYear - mMinYear + 1;
          //  setYear(mActivatedYear);
            notifyDataSetInvalidated();
        }

        protected void setMinYear(int minYear) {
            Log.d("YearView---","minYear " +minYear);
            mMinYear = minYear;
            mCount = mMaxYear - mMinYear + 1;
          //  setYear(mActivatedYear);
            notifyDataSetInvalidated();
        }

        protected void setActivatedYear(int activatedYear) {
            if(activatedYear >= mMinYear && activatedYear <= mMaxYear) {
                Log.d("YearView---", "current year " + activatedYear);
                mActivatedYear = activatedYear;
                setYear(activatedYear);
            }else{
                throw new IllegalArgumentException("activated date is not in range");
            }
        }

    }

    public int getFirstPositionOffset() {
        final View firstChild = getChildAt(0);
        if (firstChild == null) {
            return 0;
        }
        return firstChild.getTop();
    }

    /**
     * The callback used to indicate the user changed the year.
     */
    public interface OnYearSelectedListener {
        /**
         * Called upon a year change.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         */
        void onYearChanged(YearPickerView view, int year);
    }

    protected void setMinYear(int minYear){
        mAdapter.setMinYear(minYear);
    }

    protected void setMaxYear(int maxYear){
        mAdapter.setMaxYear(maxYear);
    }

    protected void setActivatedYear(int activatedYear){ mAdapter.setActivatedYear(activatedYear); }
}

