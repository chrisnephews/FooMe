package com.example.cknev.foome.model;

import java.io.Serializable;

/**
 * Created by cknev on 18-10-2017.
 *
 * Model for the Calories object.
 */

public class Calories implements Serializable
{
    private int carbs;
    private int proteins;
    private int fats;

    public Calories(int carbs, int proteins, int fats)
    {
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
    }

    public int getCarbs()
    {
        return carbs;
    }

    public void setCarbs(int carbs)
    {
        this.carbs = carbs;
    }

    public int getProteins()
    {
        return proteins;
    }

    public void setProteins(int proteins)
    {
        this.proteins = proteins;
    }

    public int getFats()
    {
        return fats;
    }

    public void setFats(int fats)
    {
        this.fats = fats;
    }

    @Override
    public String toString()
    {
        return "Calories{" +
                "c=" + carbs +
                ", p=" + proteins +
                ", f=" + fats +
                '}';
    }
    public int getTotalAmount()
    {
        return getCarbs() + getProteins() + getFats();
    }
}
