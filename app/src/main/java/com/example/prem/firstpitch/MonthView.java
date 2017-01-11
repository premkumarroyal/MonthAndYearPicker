package com.example.prem.firstpitch;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Prem on 15-Aug-16.
 */
public class MonthView extends View {

    private static final int DEFAULT_HEIGHT = 100;
    private static final int MIN_HEIGHT = 10;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = Calendar.SUNDAY;
    private static final int DEFAULT_NUM_DAYS = 4;
    private static final int DEFAULT_NUM_ROWS = 3;
    private static final int MAX_NUM_ROWS = 3;
    private static final int DAY_SEPARATOR_WIDTH = 1;
    private  Formatter mFormatter;
    private  StringBuilder mStringBuilder;
    private  int mMiniDayNumberTextSize;
    private  int mMonthLabelTextSize;
    private  int mMonthDayLabelTextSize;
    private  int mMonthHeaderSize;
    private  int mDaySelectedCircleSize;
    /** Single-letter (when available) formatter for the day of week label. */
    private SimpleDateFormat mDayFormatter = new SimpleDateFormat("EEEEE", Locale.getDefault());
    // affects the padding on the sides of this view
    private int mPadding = 20;
    // private String mDayOfWeekTypeface;
    // private String mMonthTitleTypeface;
    private Paint mDayNumberPaint;
    private Paint mDayNumberDisabledPaint;
    private Paint mDayNumberSelectedPaint;
    private Paint mMonthTitlePaint;
    private Paint mMonthDayLabelPaint;
    private int mMonth;
    private int mYear;
    // Quick reference to the width of this view, matches parent
    private int mWidth;
    // The height this view should draw at in pixels, set by height param
    private int mRowHeight = DEFAULT_HEIGHT;
    // If this view contains the today
    private boolean mHasToday = false;
    // Which day is selected [0-6] or -1 if no day is selected
    private int mSelectedDay = -1;
    // Which day is today [0-6] or -1 if no day is today
    private int mToday = DEFAULT_SELECTED_DAY;
    // Which day of the week to start on [0-6]
    private int mWeekStart = DEFAULT_WEEK_START;
    // How many days to display
    private int mNumDays = DEFAULT_NUM_DAYS;
    // The number of days + a spot for week number if it is displayed
    private int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    // First enabled day
    private int mEnabledDayStart = 1;
    // Last enabled day
    private int mEnabledDayEnd = 31;
    private final Calendar mCalendar = Calendar.getInstance();
    private final Calendar mDayLabelCalendar = Calendar.getInstance();
   // private  MonthViewTouchHelper mTouchHelper;
    private int mNumRows = DEFAULT_NUM_ROWS;
    // Optional listener for handling day click actions
    private OnDayClickListener mOnDayClickListener;
    // Whether to prevent setting the accessibility delegate
    private int mNormalTextColor;
    private int mDisabledTextColor;
    private int mSelectedDayColor;

    public MonthView(Context context) {
        super(context);
        mStringBuilder = new StringBuilder(50);
        mMonthDayLabelTextSize = 15;
        mMonthHeaderSize = 80;
        mDaySelectedCircleSize = 70;
        mRowHeight = ( 800 - mMonthHeaderSize) / MAX_NUM_ROWS;

        Log.d("---------------------","----------------------------------------------------");
        // Sets up any standard paints that will be used
        initView();
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.AppTheme);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final Resources res = context.getResources();
        //   mDayOfWeekTypeface = res.getString(R.string.day_of_week_label_typeface);
        //   mMonthTitleTypeface = res.getString(R.string.sans_serif);
        mStringBuilder = new StringBuilder(50);
        mFormatter = new Formatter(mStringBuilder, Locale.getDefault());
        mMiniDayNumberTextSize = 60;
        mMonthLabelTextSize = 60;
        mMonthDayLabelTextSize = 20;
        mMonthHeaderSize = 80;
        mDaySelectedCircleSize = 130;
        mRowHeight = ( 800 - mMonthHeaderSize) / MAX_NUM_ROWS;
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);

        Log.d("---------------------","----------------------------------------------------");
        // Sets up any standard paints that will be used
        initView();
    }
    /**
     * Sets up the text and style properties for painting.
     */
    private void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setColor(mNormalTextColor);
        mMonthTitlePaint.setTextSize(mMonthLabelTextSize);
        //  mMonthTitlePaint.setTypeface(Typeface.create(mMonthTitleTypeface, Typeface.BOLD));
        mMonthTitlePaint.setTextAlign(Paint.Align.CENTER);
        mMonthTitlePaint.setStyle(Paint.Style.FILL);
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setColor(mNormalTextColor);
        mMonthDayLabelPaint.setTextSize(mMonthDayLabelTextSize);
        // mMonthDayLabelPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setTextAlign(Paint.Align.CENTER);
        mMonthDayLabelPaint.setStyle(Paint.Style.FILL);
        mMonthDayLabelPaint.setFakeBoldText(true);
        mDayNumberSelectedPaint = new Paint();
        mDayNumberSelectedPaint.setAntiAlias(true);
        mDayNumberSelectedPaint.setColor(mSelectedDayColor);
        //mDayNumberSelectedPaint.setAlpha(SELECTED_CIRCLE_ALPHA);
        mDayNumberSelectedPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberSelectedPaint.setStyle(Paint.Style.FILL);
        mDayNumberSelectedPaint.setFakeBoldText(true);
        mDayNumberPaint = new Paint();
        mDayNumberPaint.setAntiAlias(true);
        mDayNumberPaint.setTextSize(mMiniDayNumberTextSize);
        mDayNumberPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberPaint.setStyle(Paint.Style.FILL);
        mDayNumberPaint.setFakeBoldText(false);
        mDayNumberDisabledPaint = new Paint();
        mDayNumberDisabledPaint.setAntiAlias(true);
        mDayNumberDisabledPaint.setColor(mDisabledTextColor);
        mDayNumberDisabledPaint.setTextSize(mMiniDayNumberTextSize);
        mDayNumberDisabledPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberDisabledPaint.setStyle(Paint.Style.FILL);
        mDayNumberDisabledPaint.setFakeBoldText(false);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawDays(canvas);
    }

    /**
     * Draws the month days.
     */
    private void drawDays(Canvas canvas) {
        int y = (((mRowHeight + mMiniDayNumberTextSize) / 2) - DAY_SEPARATOR_WIDTH)
                + mMonthHeaderSize;
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);
        int j = findDayOffset();
        for (int day = 1; day <= mNumCells; day++) {
            int x = (2 * j + 1) * dayWidthHalf + mPadding;
            if (mSelectedDay == day) {
                canvas.drawCircle(x, y - (mMiniDayNumberTextSize / 3), mDaySelectedCircleSize,
                        mDayNumberSelectedPaint);
            }
            if (mHasToday && mToday == day) {
                mDayNumberPaint.setColor(mSelectedDayColor);
            } else {
                mDayNumberPaint.setColor(mNormalTextColor);
            }
            final Paint paint = (day < mEnabledDayStart || day > mEnabledDayEnd) ?
                    mDayNumberDisabledPaint : mDayNumberPaint;
            canvas.drawText(String.format("%d", day), x, y, paint);
            j++;
            if (j == mNumDays) {
                j = 0;
                y += mRowHeight;
            }
        }
    }
    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    /**
     * Calculates the day that the given x position is in, accounting for week
     * number. Returns the day or -1 if the position wasn't in a day.
     *
     * @param x The x position of the touch event
     * @return The day number, or -1 if the position wasn't in a day
     */
    private int getDayFromLocation(float x, float y) {
        int dayStart = mPadding;
        if (x < dayStart || x > mWidth - mPadding) {
            return -1;
        }
        // Selection is (x - start) / (pixels/day) == (x -s) * day / pixels
        int row = (int) (y - mMonthHeaderSize) / mRowHeight;
        int column = (int) ((x - dayStart) * mNumDays / (mWidth - dayStart - mPadding));
        int day = column - findDayOffset() + 1;
        day += row * mNumDays;
        if (day < 1 || day > mNumCells) {
            return -1;
        }
        Log.d("------------------","clicked on "+day);
        return day;
    }

    /**
     * Called when the user clicks on a day. Handles callbacks to the
     * {@link OnDayClickListener} if one is set.
     *
     * @param day The day that was clicked
     */
    private void onDayClick(int day) {
        Log.d("----------", "inside onDayClick");
        if (mOnDayClickListener != null) {
            Log.d("----------", "mOnDayClickListener != null");
            Calendar date = Calendar.getInstance();
            date.set(mYear, mMonth, day);
            Log.d("---------","clicked date : "+ date );
            mOnDayClickListener.onDayClick(this, date);
        }else{
            Log.d("---------- :-(", "mOnDayClickListener == null");
        }
    }


    /**
     * Handles callbacks when the user clicks on a time object.
     */
    public interface OnDayClickListener {
        void onDayClick(MonthView view, Calendar day);
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        mOnDayClickListener = listener;
    }

    void setTextColor(ColorStateList colors) {
        final Resources res = getContext().getResources();
        mNormalTextColor = Color.WHITE;
        mMonthTitlePaint.setColor(mNormalTextColor);
        mMonthDayLabelPaint.setColor(mNormalTextColor);
        mDisabledTextColor = Color.GRAY;
        mDayNumberDisabledPaint.setColor(mDisabledTextColor);
        mSelectedDayColor = Color.GREEN;
        mDayNumberSelectedPaint.setColor(mSelectedDayColor);
        // mDayNumberSelectedPaint.setAlpha(SELECTED_CIRCLE_ALPHA);
    }

    /**
     * Sets all the parameters for displaying this week. Parameters have a default value and
     * will only update if a new value is included, except for focus month, which will always
     * default to no focus month if no value is passed in. The only required parameter is the
     * week start.
     *
     * @param selectedDay the selected day of the month, or -1 for no selection.
     * @param month the month.
     * @param year the year.
     * @param weekStart which day the week should start on. {@link Calendar#SUNDAY} through
     *        {@link Calendar#SATURDAY}.
     * @param enabledDayStart the first enabled day.
     * @param enabledDayEnd the last enabled day.
     */
    void setMonthParams(int selectedDay, int month, int year, int weekStart, int enabledDayStart,
                        int enabledDayEnd) {
        if (mRowHeight < MIN_HEIGHT) {
            mRowHeight = MIN_HEIGHT;
        }
        mSelectedDay = selectedDay;
        if (isValidMonth(month)) {
            mMonth = month;
        }
        mYear = year;
        // Figure out what day today is
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        mHasToday = false;
        mToday = -1;
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);
        if (isValidDayOfWeek(weekStart)) {
            mWeekStart = weekStart;
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }
        if (enabledDayStart > 0 && enabledDayEnd < 32) {
            mEnabledDayStart = enabledDayStart;
        }
        if (enabledDayEnd > 0 && enabledDayEnd < 32 && enabledDayEnd >= enabledDayStart) {
            mEnabledDayEnd = enabledDayEnd;
        }
        mNumCells = getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }
        }
        mNumRows = calculateNumRows();
    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
        requestLayout();
    }

    private static boolean isValidDayOfWeek(int day) {
        return day >= Calendar.SUNDAY && day <= Calendar.SATURDAY;
    }
    private static boolean isValidMonth(int month) {
        return month >= Calendar.JANUARY && month <= Calendar.DECEMBER;
    }
    private static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }
    private boolean sameDay(int day, Time today) {
        return mYear == today.year &&
                mMonth == today.month &&
                day == today.monthDay;
    }
    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows
                + mMonthHeaderSize);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                final int day = getDayFromLocation(event.getX(), event.getY());
                if (day >= 0) {
                    Log.d("yes----------","day grater then 0");
                    onDayClick(day);
                }else{
                    Log.d("Nooo----------","day is 0");
                }
                break;
        }
        return true;
    }
}
