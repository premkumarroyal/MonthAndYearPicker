# MonthAndYearPicker

Month and Year Picker allow user to pick only month and year or only month or only year as required. You will get notified for all action's such as on selection of date, on selection of month and on section of year.


## Listeners
setOnMonthChangedListener(OnMonthChangedListener());
setOnYearChangedListener(OnYearChangedListener());

## Methods

1. setMaxMonth(int maxMonth) :  Maximum month that user can select.
2. setMinMonth(int minMonth) :  Minimum month that user can select.
3. setMonthRange(int minMonth, int maxMonth) : set both max and min sections.
4. setActivatedMonth(activatedMonth) : selected the month when picker opens.

5. setMaxYear(int maxYear) : Maximum year that will be shown in picker.
6. setMinYear(int minYear) : Minimum year that will be shown in picker.
7. setYearRange(int minYear,int maxYear) : set both max and min selections.
8. setActivatedYear(activatedYear) : selected the year when picker opens.

9. setMonthAndYearRange(int minMonth, int maxMonth, int minYear, int maxYear) : set month and year min and max values at once.

10. showMonthOnly() : Only month selection will be shown.
11. showYearOnly() : Only year selection will be shown.

12. setTitle(String title) : set the title for Month Picker Dialog. By default title will be hidden, it will be visible if value set.

13. setOnMonthChangedListener(MonthPickerDialog.OnMonthChangedListener onMonthChange);
14. setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener onYearChange);


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





