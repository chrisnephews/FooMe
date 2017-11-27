package com.example.cknev.foome.utitility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cknev.foome.model.Calories;
import com.example.cknev.foome.model.Day;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.model.User;
import com.example.cknev.foome.utitility.contract.DayContract;
import com.example.cknev.foome.utitility.contract.DayMealsContract;
import com.example.cknev.foome.utitility.contract.MealsContract;
import com.example.cknev.foome.utitility.contract.UserContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cknev on 19-10-2017.
 */

public class DataSource
{
    private final DBHelper dbHelper;
    private SQLiteDatabase database;

    public DataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public void open()
    {
        database = dbHelper.getWritableDatabase();
    } //DB opener

    public void close()
    {
        dbHelper.close();
    }//DB closer

    public void saveUser(User user)
    {
        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, user.getEmail());
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(UserContract.UserEntry.COLUMN_NAME_DATEOFBIRTH, user.getDateOfBirth());
        database.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        close();
    }

    //TODO: Connect to online database in the future for backup purposes
    public User getUser()
    {
        User user = new User();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) //Considering only one user
        {
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME)));
            user.setDateOfBirth(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_DATEOFBIRTH)));
        }
        return user;
    }

    public List<Meal> getMealsFromDay(Day day) //Get all the meals from a specific day
    {
        //Get all the meals from the db
        {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String dayId = String.valueOf(day.getId());
            String sql = "SELECT m." + MealsContract.MealsEntry.COLUMN_NAME_ID + ", " +
                    MealsContract.MealsEntry.COLUMN_NAME_NAME + ", " +
                    MealsContract.MealsEntry.COLUMN_NAME_CALORIES + ", " +
                    MealsContract.MealsEntry.COLUMN_NAME_DATE + ", " +
                    MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE +
                    " FROM " + DayMealsContract.DayMealEntry.TABLE_NAME + " dm " +
                    "INNER JOIN " + MealsContract.MealsEntry.TABLE_NAME + " m ON m." + MealsContract.MealsEntry.COLUMN_NAME_ID + " = " + " dm." + DayMealsContract.DayMealEntry.COLUMN_NAME_MEALID +
                    " WHERE dm." + DayMealsContract.DayMealEntry.COLUMN_NAME_DAYID + " = " + dayId;


            List<Meal> mealList = new ArrayList<>();
            Log.i("sql", sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    Meal meal = new Meal();
                    meal.setId(cursor.getInt(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_ID)));
                    meal.setTitle(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_NAME)));
                    meal.setCalories(new Calories(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_CALORIES))), 0, 0)); //temporary total calories amount
                    meal.setDateAdded(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_DATE)));
                    meal.setHealthyDegree(cursor.getInt(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE)));
                    mealList.add(meal);//add the meals to the list
                } while (cursor.moveToNext());

            }
            cursor.close();
            db.close();
            return mealList;
        }
    }

    public void saveMeal(Meal meal)
    {

        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(MealsContract.MealsEntry.COLUMN_NAME_NAME, meal.getTitle());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_CALORIES, meal.getCalories().getTotalAmount());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE, meal.getHealthyDegree());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_DATE, meal.getDateAdded());
        // Inserting row in Meals
        database.insert(MealsContract.MealsEntry.TABLE_NAME, null, values);
        close(); // Closing database connection
    }

    public Day saveDay(Day day)
    {
        open();
        ContentValues values = new ContentValues();
        values.put(DayContract.DayEntry.COLUMN_NAME_DATE, day.getDate());
        values.put(DayContract.DayEntry.COLUMN_NAME_WEIGHT, day.getWeight());
        database.insert(DayContract.DayEntry.TABLE_NAME, null, values);
        close();
        return findDayWithDate(day.getDate());
    }

    private Day findDayWithDate(String date)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DayContract.DayEntry.TABLE_NAME + " WHERE " + DayContract.DayEntry.COLUMN_NAME_DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(sql, null);
        Day day = new Day();
        if (cursor.moveToFirst()) // Can only be one
        {
            day.setId(cursor.getInt(cursor.getColumnIndex(DayContract.DayEntry.COLUMN_NAME_DATE)));
            day.setDate(cursor.getString(cursor.getColumnIndex(DayContract.DayEntry.COLUMN_NAME_DATE)));
        }
        return day;
    }

    public List<Meal> getMeals() //Get all the meals from the db
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT " + MealsContract.MealsEntry.COLUMN_NAME_ID + ", " +
                MealsContract.MealsEntry.COLUMN_NAME_NAME + ", " +
                MealsContract.MealsEntry.COLUMN_NAME_CALORIES + ", " +
                MealsContract.MealsEntry.COLUMN_NAME_DATE + ", " +
                MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE +
                " FROM " + MealsContract.MealsEntry.TABLE_NAME;
        List<Meal> mealList = new ArrayList<>();
        Log.i("sql", sql);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Meal meal = new Meal();
                meal.setId(cursor.getInt(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_ID)));
                meal.setTitle(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_NAME)));
                meal.setCalories(new Calories(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_CALORIES))), 0, 0)); //temporary total calories amount
                meal.setDateAdded(cursor.getString(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_DATE)));
                meal.setHealthyDegree(cursor.getInt(cursor.getColumnIndex(MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE)));
                mealList.add(meal);//add the meals to the list
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return mealList;
    }


    public void editMeal(Meal meal)
    {
        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(MealsContract.MealsEntry.COLUMN_NAME_NAME, meal.getTitle());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_CALORIES, meal.getCalories().getTotalAmount());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_HEALTHYDEGREE, meal.getHealthyDegree());
        values.put(MealsContract.MealsEntry.COLUMN_NAME_DATE, meal.getDateAdded());
        // Updating the row in the db
        database.update(MealsContract.MealsEntry.TABLE_NAME, values, MealsContract.MealsEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(meal.getId())});
        close(); // Closing database connection
    }

    public void deleteGame(long user_Id)
    {
        open();
        // Open connection to write data
        database.delete(MealsContract.MealsEntry.TABLE_NAME, MealsContract.MealsEntry.COLUMN_NAME_ID + " =?",
                new String[]{Long.toString(user_Id)});
        close(); // Closing database connection
    }
}

