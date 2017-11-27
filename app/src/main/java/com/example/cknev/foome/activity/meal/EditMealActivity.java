package com.example.cknev.foome.activity.meal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cknev.foome.R;
import com.example.cknev.foome.model.Calories;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.utitility.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMealActivity extends AppCompatActivity
{
    EditText _titleText;
    EditText _caloriesText;
    Spinner hdSpinner;
    Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //Get selected meal
        Intent intent = getIntent();
        meal = (Meal) intent.getSerializableExtra("selectedMeal");

        //Set actionbar title to edit meal
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit meal");
        actionBar.setSubtitle(meal.getTitle());
        //Initialize the components
        setMealView();

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
                editMeal();
            }
        });
    }

    void editMeal()
    {
        // Get the current date in numbered day-month-year format
        String curDate = EditMealActivity.getSimpleCurrentDate();
        //Take input from the user
        String title = _titleText.getText().toString();
        String calories = _caloriesText.getText().toString();
        String healthDegree = hdSpinner.getSelectedItem().toString();

        //Some validation
        if ((title != null) && title.isEmpty())
        {
            // Make EditText _titleText display an error message, and display a toast
            // That the title field is empty
            EditMealActivity.setErrorText(_titleText, "Please enter a name");
            showToast("Name field is empty");
        } else if ((calories != null) && calories.isEmpty())
        {
            // Make EditText calories display an error message, and display a toast
            // That the calories field is empty
            EditMealActivity.setErrorText(_caloriesText, "Please enter the amount of calories");
            showToast("Calories are empty");
        } else
        {
            // Update the game with the new data
            meal.setTitle(title);
            meal.setCalories(new Calories(Integer.parseInt(calories), 0, 0));
            meal.setHealthyDegree(hdToInteger(healthDegree));

            // Create a DataSource object, and pass it the context of this activity
            DataSource datasource = new DataSource(this);
            datasource.editMeal(meal);

            //Notify the user of the success
            showToast("The meal has been edited");

            // Starting the previous Intent
            Intent previousActivity = new Intent(this, EditMealActivity.class);
            // Sending the data to mealDetailsActivity
            previousActivity.putExtra("selectedMeal", meal);
            setResult(1000, previousActivity);
            finish();
        }
    }

    private void setMealView()
    {
        //declare textfields
        _titleText = (EditText) findViewById(R.id.addTitle);
        _caloriesText = (EditText) findViewById(R.id.addCalories);
        hdSpinner = (Spinner) findViewById(R.id.hdSpinner);
        //Set meal data in edit textfields
        _titleText.setText(meal.getTitle());
        _caloriesText.setText(String.valueOf(meal.getCalories().getTotalAmount()));

    }

    private void setSpinnerPosition(ArrayAdapter adapter)
    {
        if (meal.getHealthyDegree() != 0)
        {
            //Gets the position of the correct spinner item by comparing
            //which item of the Spinner matches with the mealStatus
            int spinnerPosition = adapter.getPosition(meal.getHealthyDegree());
            //Display the correct mealStatus in the Spinner based on the found position
            hdSpinner.setSelection(spinnerPosition);
        }
    }

    private int hdToInteger(String healthDegree)
    {
        if (healthDegree.equals("Healthy"))
            return 1;
        if (healthDegree.equals("Average"))
            return 2;
        else
            return 3;
    }

    private static String getSimpleCurrentDate()
    {
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

    private void showToast(String message)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}

