package com.whiteelephant.MonthPicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.prem.firstpitch.R;

import java.util.Calendar;


public class MonthPickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {
    private final MonthPickerView _monthPicker;
    private final OnDateSetListener _callBack;


    /**
     * @param context     The context the dialog is to run in.
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     */
    private MonthPickerDialog(Context context,
                              OnDateSetListener callBack,
                              int year,
                              int monthOfYear) {
        this(context, 0, callBack, year, monthOfYear);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param theme       the theme to apply to this dialog
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     */
    private MonthPickerDialog(Context context,
                              int theme,
                              OnDateSetListener callBack,
                              int year,
                              int monthOfYear) {
        super(context, theme);
        _callBack = callBack;
       /* setButton(BUTTON_POSITIVE, "OK", this);
        setButton(BUTTON_NEGATIVE, "CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });*/
        //setIcon(0);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.month_picker_dialog, null);
        setView(view);
        _monthPicker = (MonthPickerView) view.findViewById(R.id.monthPicker);
        _monthPicker.setOnDateListener(new MonthPickerView.OnDateSet() {
            @Override
            public void onDateSet() {
                tryNotifyDateSet();
                MonthPickerDialog.this.dismiss();
            }
        });
        _monthPicker.setOnCancelListener(new MonthPickerView.OnCancel() {
            @Override
            public void onCancel() {
                MonthPickerDialog.this.dismiss();
            }
        });
        _monthPicker.init(year, monthOfYear);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        tryNotifyDateSet();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        _monthPicker.init(year, month);
    }

    /**
     * Gets the {@link DatePicker} contained in this dialog.
     *
     * @return The calendar view.
     */
    public MonthPickerView getDatePicker() {
        return _monthPicker;
    }


    void tryNotifyDateSet() {
        if (_callBack != null) {
            _monthPicker.clearFocus();
            _callBack.onDateSet( _monthPicker.getMonth(), _monthPicker.getYear());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void setMinMonth(int minMonth) {
        _monthPicker.setMinMonth(minMonth);
    }

    private void setMaxMonth(int maxMonth) {
        _monthPicker.setMaxMonth(maxMonth);
    }

    private void setMinYear(int minYear) {
        _monthPicker.setMinYear(minYear);
    }

    private void setMaxYear(int maxYear) {
        _monthPicker.setMaxYear(maxYear);
    }


    private void setActivatedMonth(int activatedMonth) {
        _monthPicker.setActivatedMonth(activatedMonth);
    }

    private void setActivatedYear(int activatedMonth) {
        _monthPicker.setActivatedYear(activatedMonth);
    }

    private void setMonthPickerTitle(String title) {
        _monthPicker.setTitle(title);
    }

    private void showMonthOnly() {
        _monthPicker.showMonthOnly();
    }

    private void showYearOnly() {
        _monthPicker.showYearOnly();
    }

    private void setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
        if (onMonthChangedListener != null) {
            _monthPicker.setOnMonthChangedListener(onMonthChangedListener);
        }
    }

    private void setOnYearChangedListener(OnYearChangedListener onYearChangedListener) {
        if (onYearChangedListener != null) {
            _monthPicker.setOnYearChangedListener(onYearChangedListener);
        }
    }

    public static class Builder {

        private static final String TAG = MonthPickerDialog.Builder.class.getName();
        private Context _context;
        private OnDateSetListener _callBack;
        private int _activatedMonth, _activatedYear;
        private int _minMonth, _maxMonth;
        private int _minYear, _maxYear;
        private boolean monthOnly, yearOnly;
        private String title = null;
        private MonthPickerDialog monthPickerDialog;
        private OnYearChangedListener _onYearChanged;
        private OnMonthChangedListener _onMonthChanged;


        /**
         * Build a Dialog with month and year with given context.
         *
         * @param context  Context: the parent context
         * @param callBack MonthPickerDialog.OnDateSetListener: the listener to call
         *                 when the user sets the date
         * @param year     the initially selected year
         * @param month    the initially selected month (0-11 for compatibility with
         * {@link Calendar}Calender.MONTH or Calendar.JANUARY, Calendar.FEBRUARY etc)
         */

        public Builder(Context context,
                       OnDateSetListener callBack,
                       int year,
                       int month) {
            this._context = context;
            this._callBack = callBack;
            this._activatedMonth = month;
            this._activatedYear = year;

            Calendar calendar = Calendar.getInstance();
            _minMonth = Calendar.JANUARY;
            _maxMonth = Calendar.DECEMBER;
            _minYear = 1900;
            _maxYear = calendar.get(Calendar.YEAR);

        }

        /**
         * Minimum enable month in picker (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param minMonth
         * @return Builder
         */
        public Builder setMinMonth(int minMonth) {
            this._minMonth = minMonth;
            return this;
        }

        /**
         * Maximum enabled month in picker (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param maxMonth
         * @return
         */
        public Builder setMaxMonth(int maxMonth) {
            this._maxMonth = maxMonth;
            return this;
        }


        /**
         * Starting year in the picker.
         *
         * @param minYear
         * @return Builder
         */
        public Builder setMinYear(int minYear) {
            this._minYear = minYear;
            return this;
        }

        /**
         * Ending year in the picker.
         *
         * @param maxYear
         * @return Builder
         */
        public Builder setMaxYear(int maxYear) {
            this._maxYear = maxYear;
            return this;
        }

        /**
         * Initially selected month (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param activatedMonth
         * @return Builder
         */
        public Builder setActivatedMonth(int activatedMonth) {
            this._activatedMonth = activatedMonth;
            return this;
        }

        /**
         * Initially selected year (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param activatedYear
         * @return Builder
         */
        public Builder setActivatedYear(int activatedYear) {
            this._activatedYear = activatedYear;
            return this;
        }


        /**
         * Minimum and Maximum enable month in picker (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param minMonth minimum enabled month.
         * @param maxMonth maximum enabled month.
         * @return Builder
         */
        public Builder setMonthRange(int minMonth, int maxMonth) {
            this._minMonth = minMonth;
            this._maxMonth = maxMonth;
            return this;
        }

        /**
         * Starting and ending year show in picker
         *
         * @param minYear starting year
         * @param maxYear ending year
         * @return
         */
        public Builder setYearRange(int minYear, int maxYear) {
            this._minYear = minYear;
            this._maxYear = maxYear;
            return this;
        }

        /**
         * Set the Minimum, maximum enabled months and starting , ending years.
         *
         * @param minMonth minimum enabled month in picker
         * @param maxMonth maximum enabled month in picker
         * @param minYear  starting year
         * @param maxYear  ending year
         * @return
         */
        public Builder setMonthAndYearRange(int minMonth, int maxMonth, int minYear, int maxYear) {
            this._minMonth = minMonth;
            this._maxMonth = maxMonth;
            this._minYear = minYear;
            this._maxYear = maxYear;
            return this;
        }

        /**
         * User can select month only. Year won't be shown to user once user select the month.
         *
         * @return Builder
         */
        public Builder showMonthOnly() {

            if (yearOnly) {
                Log.e(TAG, "yearOnly also set to true before. Now setting yearOnly to false" +
                        " monthOnly to true");
            }
            this.yearOnly = false;
            this.monthOnly = true;
            return this;
        }

        /**
         * User can select year only. Month won't be shown to user once user select the month.
         *
         * @return Builder
         */
        public Builder showYearOnly() {
            if (monthOnly) {
                Log.e(TAG, "monthOnly also set to true before. Now setting monthOnly to false and" +
                        " yearOnly to true");
            }
            this.monthOnly = false;
            this.yearOnly = true;
            return this;
        }

        /**
         * Set the title to the picker.
         *
         * @param title
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the callback that will be called when user click on any month.
         *
         * @param onMonthChangedListener
         * @return Builder
         */
        public Builder setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
            this._onMonthChanged = onMonthChangedListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the user select any year.
         *
         * @param onYearChangedListener
         * @return Builder
         */
        public Builder setOnYearChangedListener(OnYearChangedListener onYearChangedListener) {
            this._onYearChanged = onYearChangedListener;
            return this;
        }

        public MonthPickerDialog build() {
            monthPickerDialog = new MonthPickerDialog(_context, _callBack, _activatedYear, _activatedMonth);
            if (monthOnly) {
                monthPickerDialog.showMonthOnly();
                _minYear = 0;
                _maxYear = 0;
                _activatedYear = 0;
            } else if (yearOnly) {
                monthPickerDialog.showYearOnly();
                _minMonth = 0;
                _maxMonth = 0;
                _activatedMonth = 0;
            }
            monthPickerDialog.setMinMonth(_minMonth);
            monthPickerDialog.setMaxMonth(_maxMonth);
            monthPickerDialog.setMinYear(_minYear);
            monthPickerDialog.setMaxYear(_maxYear);
            monthPickerDialog.setActivatedMonth(_activatedMonth);
            monthPickerDialog.setActivatedYear(_activatedYear);

            if (_onMonthChanged != null) {
                monthPickerDialog.setOnMonthChangedListener(_onMonthChanged);
            }

            if (_onYearChanged != null) {
                monthPickerDialog.setOnYearChangedListener(_onYearChanged);
            }

            if (title != null) {
                monthPickerDialog.setMonthPickerTitle(title);
            }
            return monthPickerDialog;
        }
    }

    /**
     * The callback used to indicate the user is done selecting month.
     */
    public interface OnDateSetListener {
        /**
         * @param selectedMonth  The month that was set (0-11) for compatibility with {@link Calendar}.
         * @param selectedYear The year that was set.
         *
         */
        void onDateSet(int selectedMonth, int selectedYear);
    }

    /**
     * The callback used to indicate the user click on month
     */
    public interface OnMonthChangedListener {
        /**
         * @param selectedMonth The month that was set (0-11) for compatibility
         *                      with {@link Calendar}.
         */
        void onMonthChanged(int selectedMonth);
    }

    /**
     * The callback used to indicate the user click on year.
     */
    public interface OnYearChangedListener {
        /**
         * Called upon a year change.
         *
         * @param year The year that was set.
         */
        void onYearChanged(int year);
    }

}
