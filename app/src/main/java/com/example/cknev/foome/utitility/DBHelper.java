package com.example.cknev.foome.utitility;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cknev.foome.utitility.contract.DayContract;
import com.example.cknev.foome.utitility.contract.DayMealsContract;
import com.example.cknev.foome.utitility.contract.MealsContract;
import com.example.cknev.foome.utitility.contract.UserContract;

/**
 * Created by cknev on 18-10-2017.
 */

public class DBHelper extends SQLiteOpenHelper
{
    // Version number to upgrade database version
    // each time if you Add, Edit table, you need to change the
    // version number.
    private static final String DATABASE_NAME = "foome.db";
    private static final int DATABASE_VERSION = 8;
    private final Context context;
    // Creating the table
    private static final String MEALSTABLE_CREATE =
            "CREATE TABLE " + MealsContract.MealsEntry.TABLE_NAME +
                    "(" +
                    MealsContract.MealsEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + MealsContract.MealsEntry.COLUMN_NAME_NAME + " TEXT, "
                    + MealsContract.MealsEntry.COLUMN_NAME_CALORIES + " TEXT, "
                    + MealsContract.MealsEntry.COLUMN_NAME_DATE + " TEXT, "
                    + MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE + " TEXT)";

    private static final String USERTABLE_CREATE =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME +
                    "(" +
                    UserContract.UserEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + UserContract.UserEntry.COLUMN_NAME_NAME + " TEXT, "
                    + UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT, "
                    + UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT, "
                    + UserContract.UserEntry.COLUMN_NAME_DATEOFBIRTH + " TEXT)";

    private static final String DAYTABLE_CREATE =
            "CREATE TABLE " + DayContract.DayEntry.TABLE_NAME +
                    "(" +
                    DayContract.DayEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + DayContract.DayEntry.COLUMN_NAME_DATE + " TEXT, "
                    + DayContract.DayEntry.COLUMN_NAME_WEIGHT + " TEXT) ";

    private static final String DAYMEALSTABLE =
            "CREATE TABLE " + DayMealsContract.DayMealEntry.TABLE_NAME +
                    "(" +
                    DayMealsContract.DayMealEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + DayMealsContract.DayMealEntry.COLUMN_NAME_DAYID + " INTEGER, "
                    + DayMealsContract.DayMealEntry.COLUMN_NAME_MEALID + " INTEGER,"
                    + "FOREIGN KEY(" + DayMealsContract.DayMealEntry.COLUMN_NAME_DAYID + ") REFERENCES " + DayContract.DayEntry.TABLE_NAME + "(" + DayContract.DayEntry.COLUMN_NAME_ID + "), "
                    + "FOREIGN KEY(" + DayMealsContract.DayMealEntry.COLUMN_NAME_MEALID + ") REFERENCES " + MealsContract.MealsEntry.TABLE_NAME + "(" + MealsContract.MealsEntry.COLUMN_NAME_ID + "))";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //All necessary tables are created here
        try
        {
            db.execSQL(USERTABLE_CREATE);
            db.execSQL(MEALSTABLE_CREATE);
            db.execSQL(DAYTABLE_CREATE);
            db.execSQL(DAYMEALSTABLE);
        }
        catch (SQLException e)
        {
            Log.e("e: ", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + MealsContract.MealsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DayContract.DayEntry.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}

