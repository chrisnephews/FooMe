package com.example.cknev.foome.activity.meal;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cknev.foome.R;
import com.example.cknev.foome.model.Calories;
import com.example.cknev.foome.model.Day;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.utitility.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMealActivity extends AppCompatActivity
{
    EditText _titleText;
    EditText _caloriesText;
    Spinner hdSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        getSupportActionBar().setTitle("Add meal");
        //Initialize the components

        _titleText = (EditText) findViewById(R.id.addTitle);
        _caloriesText = (EditText) findViewById(R.id.addCalories);
        hdSpinner = (Spinner) findViewById(R.id.hdSpinner);

        //Make an ArrayAdapter for the spinner

        ArrayAdapter hdAdapter = ArrayAdapter.createFromResource(this,
                R.array.meal_healthdegree, android.R.layout.simple_spinner_item);

        //Set the spinner adapter accordingly
        hdSpinner.setAdapter(hdAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                saveMeal();
            }
        });
    }

    void saveMeal()
    {
        // Get the current date in numbered day-month-year format
        String curDate = AddMealActivity.getSimpleCurrentDate();
        //Take input from the user
        String title = _titleText.getText().toString();
        String calories = _caloriesText.getText().toString();
        String healthDegree = hdSpinner.getSelectedItem().toString();

        //Some validation
        if ((title != null) && title.isEmpty())
        {
            // Make EditText _titleText display an error message, and display a toast
            // That the title field is empty
            AddMealActivity.setErrorText(_titleText, "Please enter a name");
            showToast("Name field is empty");
        } else if ((calories != null) && calories.isEmpty())
        {
            // Make EditText calories display an error message, and display a toast
            // That the calories field is empty
            AddMealActivity.setErrorText(_caloriesText, "Please enter the amount of calories");
            showToast("Calories are empty");
        } else
        {
            // Create a DBCRUD object, and pass it the context of this activity
            DataSource dataSource = new DataSource(this);
            // Make a meal object based on the input. The correct id will be set in DBCRUD.saveMeal()
            Meal meal = new Meal(-1, title, new Calories(Integer.parseInt(calories), 0,0), curDate, hdToInteger(healthDegree));
            dataSource.saveMeal(meal);
            // Notify the user with a toast that the meal has been added
            showToast("Meal has been added!");

            // Go back to MainActivity
            finish();
        }
    }

    private int hdToInteger(String healthDegree)
    {
        if(healthDegree.equals("Healthy"))
            return 1;
        if(healthDegree.equals("Average"))
            return 2;
        else
            return 3;
    }

    private static String getSimpleCurrentDate() {
        // Formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        // Format.format returns a string
        return format.format(today);
    }

    private static void setErrorText(EditText editText, String message)
    {
        // Get the color white in integer form
        int RGB = Color.argb(255, 255, 0, 0);
        // Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);
        // Object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        // Give the message from the first till the last character a white color.
        // The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        // Make the EditText display the error message
        editText.setError(ssbuilder);
    }
    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}

