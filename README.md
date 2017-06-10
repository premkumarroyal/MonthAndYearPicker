# MonthAndYearPicker

Month and Year Picker allow user to pick only month and year or only month or only year as required. You will get notified for all action's such as on selection of date, on selection of month and on section of year.


## Listeners
    setOnMonthChangedListener(OnMonthChangedListener());
    setOnYearChangedListener(OnYearChangedListener());

## Methods
 Methods | Docs
------------ | -------------
setMaxMonth(int maxMonth) |  Maximum month that user can select.
setMinMonth(int minMonth) |  Minimum month that user can select.
setMonthRange(int minMonth, int maxMonth) | set both max and min sections.
setActivatedMonth(activatedMonth) | selected the month when picker opens.
setMaxYear(int maxYear) | Maximum year that will be shown in picker.
setMinYear(int minYear) | Minimum year that will be shown in picker.
setYearRange(int minYear,int maxYear) | set both max and min selections.
setActivatedYear(activatedYear) | selected the year when picker opens.
setMonthAndYearRange(int minMonth, int maxMonth, int minYear, int maxYear) | set month and year min and max values at once.
showMonthOnly() | Only month selection will be shown.
showYearOnly() | Only year selection will be shown.
setTitle(String title) | set the title for Month Picker Dialog. By default title will be hidden, it will be visible if value set.
setOnMonthChangedListener(MonthPickerDialog.OnMonthChangedListener onMonthChange); | Listener for select month
setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener onYearChange); | Listener for year select year


## Styling

Month and Year picker by default pick the color from theme if you declared colorAccent. If you want to change color's you can override the theme as below.

     <style name="MonthPickerDialogStyle" >
        <item name="monthBgColor">@color/bgColor</item>
        <item name="monthBgSelectedColor">@color/colorAccent</item>
        <item name="monthFontColorSelected">@color/selectionColor</item>
        <item name="monthFontColorNormal">@color/bgColor</item>
        <item name="monthFontColorDisabled">@color/bgColor</item>

        <item name="headerBgColor">@color/colorAccent</item>
        <item name="headerFontColorSelected">#fff</item>
        <item name="headerFontColorNormal">#85FFFFFF</item>
        <item name="headerTitleColor">#fff</item>

        <item name="dialogActionButtonColor">@color/colorAccent</item>
    </style>







