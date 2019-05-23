package com.hocam.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Instructor implements Serializable
{
    private String name;
    private ArrayList<Review> reviews = new ArrayList<>();
    private float rating;

    public Instructor(String name, ArrayList<Review> reviews)
    {
        this.name = name;
        this.reviews = reviews;
        this.rating = calcRating();
    }

    private float calcRating()
    {
        float rate = 0;
        for (Review i : this.reviews)
        {
            rate += i.getRating();
        }
        return rate / this.reviews.size();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<Review> getReviews()
    {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews)
    {
        this.reviews = reviews;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }
}
