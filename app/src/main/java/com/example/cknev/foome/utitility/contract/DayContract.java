package com.example.cknev.foome.utitility.contract;

import android.provider.BaseColumns;

/**
 * Created by cknev on 19-10-2017.
 */

public class DayContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DayContract() {}
    /* Inner class that defines the table contents */
    public static class DayEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "Day";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_WEIGHT = "weight";
    }
}
