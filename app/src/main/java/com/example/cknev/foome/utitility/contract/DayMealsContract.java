package com.example.cknev.foome.utitility.contract;

import android.provider.BaseColumns;

/**
 * Created by cknev on 19-10-2017.
 */

public class DayMealsContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DayMealsContract() {}
    /* Inner class that defines the table contents */
    public static class DayMealEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "DayMeals";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DAYID= "dayId";
        public static final String COLUMN_NAME_MEALID = "mealId";
    }
}
