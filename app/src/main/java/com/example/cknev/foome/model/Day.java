package com.example.cknev.foome.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cknev on 9-11-2017.
 */

public class Day
{
    private int id;
    private String date;
    private int weight;
    private List<Meal> meals;

    public Day()
    {

    }

    public Day(int id, String date, int weight, List<Meal> meals)
    {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.meals = meals;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public List<Meal> getMeals()
    {
        return meals;
    }

    public void setMeals(List<Meal> meals)
    {
        this.meals = meals;
    }

    public void setDayToToday()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = simpleDateFormat.format(new Date());
        date = today;
    }
}

