package com.whiteelephant.monthpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.support.annotation.IntRange;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.prem.firstpitch.R;

import java.util.Calendar;


public class MonthPickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {
    private final MonthPickerView _monthPicker;
    private final OnDateSetListener _callBack;
    private View view;

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


    @Override
    public void show() {

        if (view != null) {
            if (this.getContext().getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                if (getWindow() != null) {
                    lp.copyFrom(getWindow().getAttributes());
                    lp.width = (int) (this.getContext().getResources().getDisplayMetrics().widthPixels * 0.94);
                    lp.height = (int) (this.getContext().getResources().getDisplayMetrics().heightPixels * 0.94);
                    // show the dialog as per super implementation
                    super.show();
                    // now dialog attached to window so apply the size
                    getWindow().setLayout(lp.width, lp.height);
                }

                return;
            }else {
                dismiss();
            }
        }
        super.show();
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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.month_picker_dialog, null);

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

        // to show dialog bigger view in landscape mode we are increasing the
        // height and width of the dialog. If we do that android don't dismiss the dialog after
        // rotation and try to render landscape UI in portrait mode which is not correct.
        // so dismissing the dialog on each time when orientation changes.
        _monthPicker.setOnConfigurationChanged(new OnConfigChangeListener() {
            @Override
            public void onConfigChange() {
                dismiss();
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
            _callBack.onDateSet(_monthPicker.getMonth(), _monthPicker.getYear());
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

    private void setReverseYearOrder(boolean reverseOrder) { _monthPicker.setReverseYearOrder(reverseOrder); }

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
        private int _minMonth = Calendar.JANUARY, _maxMonth = Calendar.DECEMBER;
        private int _minYear, _maxYear;
        private boolean monthOnly, yearOnly;
        private String title = null;
        private MonthPickerDialog monthPickerDialog;
        private OnYearChangedListener _onYearChanged;
        private OnMonthChangedListener _onMonthChanged;
        private boolean _reverseOrder = false;


        /**
         * Build a Dialog with month and year with given context.
         *
         * @param context  Context: the parent context
         * @param callBack MonthPickerDialog.OnDateSetListener: the listener to call
         *                 when the user sets the date
         * @param year     the initially selected year
         * @param month    the initially selected month (0-11 for compatibility with
         *                 {@link Calendar}Calender.MONTH or Calendar.JANUARY, Calendar.FEBRUARY etc)
         */

        public Builder(Context context,
                       OnDateSetListener callBack,
                       int year,
                       @IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER) int month) {

            if (month >= Calendar.JANUARY && month <= Calendar.DECEMBER) {
                this._activatedMonth = month;
            } else {
                throw new IllegalArgumentException("Month range should be between 0 " +
                        "(Calender.JANUARY) to 11 (Calendar.DECEMBER)");
            }


            if (year >= 1) {
                this._activatedYear = year;
            } else {
                throw new IllegalArgumentException("Selected year should be > 1");
            }

            this._context = context;
            this._callBack = callBack;

            if (year > MonthPickerView._minYear) {
                _minYear = MonthPickerView._minYear;
            } else {
                _minYear = year;
                MonthPickerView._minYear = year;
            }

            if (year > MonthPickerView._maxYear) {
                _maxYear = year;
                MonthPickerView._maxYear = year;
            } else {
                _maxYear = MonthPickerView._maxYear;
            }


        }

        /**
         * Minimum enable month in picker (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param minMonth
         * @return Builder
         */
        public Builder setMinMonth(@IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                           int minMonth) {
            if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER) {
                this._minMonth = minMonth;
                return this;
            } else {
                throw new IllegalArgumentException("Month range should be between 0 " +
                        "(Calender.JANUARY) to 11 (Calendar.DECEMBER)");
            }
        }

        /**
         * Maximum enabled month in picker (0-11 for compatibility with Calender.MONTH or
         * Calendar.JANUARY, Calendar.FEBRUARY etc).
         *
         * @param maxMonth
         * @return
         */
        public Builder setMaxMonth(@IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                           int maxMonth) {
            /* if (maxMonth >= Calendar.JANUARY && maxMonth <= Calendar.DECEMBER) {*/
            this._maxMonth = maxMonth;
            return this;
            /*} else {
                throw new IllegalArgumentException("Month range should be between 0 " +
                        "(Calender.JANUARY) to 11 (Calendar.DECEMBER)");
            }*/
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
        public Builder setActivatedMonth(@IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                                 int activatedMonth) {
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
        public Builder setMonthRange(@IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                             int minMonth,
                                     @IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                             int maxMonth) {
            if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER &&
                    maxMonth >= Calendar.JANUARY && maxMonth <= Calendar.DECEMBER) {
                this._minMonth = minMonth;
                this._maxMonth = maxMonth;
                return this;
            } else {
                throw new IllegalArgumentException("Month range should be between 0 " +
                        "(Calender.JANUARY) to 11 (Calendar.DECEMBER)");
            }
        }

        /**
         * Starting and ending year show in picker
         *
         * @param minYear starting year
         * @param maxYear ending year
         * @return
         */
        public Builder setYearRange(int minYear, int maxYear) {
            if (minYear <= maxYear) {
                this._minYear = minYear;
                this._maxYear = maxYear;
                return this;
            } else {
                throw new IllegalArgumentException("Minimum year should be less then Maximum year");
            }

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
        public Builder setMonthAndYearRange(@IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                                    int minMonth,
                                            @IntRange(from = Calendar.JANUARY, to = Calendar.DECEMBER)
                                                    int maxMonth,
                                            int minYear, int maxYear) {
            if (minMonth >= Calendar.JANUARY && minMonth <= Calendar.DECEMBER &&
                    maxMonth >= Calendar.JANUARY && maxMonth <= Calendar.DECEMBER) {
                this._minMonth = minMonth;
                this._maxMonth = maxMonth;

            } else {
                throw new IllegalArgumentException("Month range should be between 0 " +
                        "(Calender.JANUARY) to 11 (Calendar.DECEMBER)");
            }

            if (minYear <= maxYear) {
                this._minYear = minYear;
                this._maxYear = maxYear;
            } else {
                throw new IllegalArgumentException("Minimum year should be less then Maximum year");
            }
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
                Log.e(TAG, "monthOnly also set to true before. Now setting monthOnly to " +
                        "false and yearOnly to true");
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

        /**
         * User can reverse the order of the years to display. Default is false.
         *
         * @return Builder
         */
        public Builder setReverseYearOrder(boolean reverseOrder) {
            this._reverseOrder = reverseOrder;
            return this;
        }

        public MonthPickerDialog build() {

            if (_minMonth > _maxMonth) {
                throw new IllegalArgumentException("Minimum month should always " +
                        "smaller then maximum month.");
            }

            if (_minYear > _maxYear) {
                throw new IllegalArgumentException("Minimum year should always " +
                        "smaller then maximum year.");
            }

            if (_activatedMonth < _minMonth || _activatedMonth > _maxMonth) {
                throw new IllegalArgumentException("Activated month should always " +
                        "in between Minimum and maximum month.");
            }

            if (_activatedYear < _minYear || _activatedYear > _maxYear) {
                throw new IllegalArgumentException("Activated year should always " +
                        "in between Minimum year and maximum year.");
            }


            monthPickerDialog = new MonthPickerDialog(_context, _callBack, _activatedYear,
                    _activatedMonth);
            if (monthOnly) {
                monthPickerDialog.showMonthOnly();
                _minYear = 0;
                _maxYear = 0;
                _activatedYear = 0;
            } else if (yearOnly) {
                monthPickerDialog.showYearOnly();
                monthPickerDialog.setReverseYearOrder(_reverseOrder);
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
                monthPickerDialog.setMonthPickerTitle(title.trim());
            }
            return monthPickerDialog;
        }
    }

    /**
     * The callback used to indicate the user is done selecting month.
     */
    public interface OnDateSetListener {
        /**
         * @param selectedMonth The month that was set (0-11) for compatibility with {@link Calendar}.
         * @param selectedYear  The year that was set.
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

    public interface OnConfigChangeListener{

        void onConfigChange();
    }
}
