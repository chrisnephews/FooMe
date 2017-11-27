package com.example.cknev.foome.utitility.contract;

import android.provider.BaseColumns;

/**
 * Created by cknev on 19-10-2017.
 */

public class UserContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}
    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "User";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATEOFBIRTH = "dateOfBirth";
    }
}
