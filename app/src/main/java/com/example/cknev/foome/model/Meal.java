package com.example.cknev.foome.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cknev on 18-10-2017.
 *
 * Model for the Meal object.
 */

public class Meal implements Serializable
{
    private int id;
    private String title;
    private Calories calories;
    private String dateAdded;
    private int healthyDegree;

    public Meal(int id, String title, Calories calories, String dateAdded, int healthyDegree)
    {
        this.id = id;
        this.title = title;
        this.calories = calories;
        this.dateAdded = dateAdded;
        this.healthyDegree = healthyDegree;
    }
    public Meal()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Calories getCalories()
    {
        return calories;
    }

    public void setCalories(Calories calories)
    {
        this.calories = calories;
    }

    public String getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public int getHealthyDegree()
    {
        return healthyDegree;
    }

    public void setHealthyDegree(int healthyDegree)
    {
        this.healthyDegree = healthyDegree;
    }

    /*public static List<Meal> generateMeals()
    {
        return Arrays.asList(
                new Meal(1, "Frikadel met friet", new Calories(2, 10, 40), "15/10/2017", 1),
                new Meal(1, "Frikadel met friet", new Calories(2, 10, 40), "15/10/2017", 1),
                new Meal(1, "Frikadel met friet", new Calories(2, 10, 40), "15/10/2017", 1),
                new Meal(1, "Frikadel met friet", new Calories(2, 10, 40), "15/10/2017", 1),
                new Meal(1, "Frikadel met friet", new Calories(2, 10, 40), "15/10/2017", 1));
    }
*/
    @Override
    public String toString()
    {
        return "Meal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", calories=" + calories +
                ", dateAdded='" + dateAdded + '\'' +
                ", healthyDegree=" + healthyDegree +
                '}';
    }
}
