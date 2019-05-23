package com.hocam.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Instructor implements Serializable {
    private String name;
    private ArrayList<Review> reviews = new ArrayList<>();
    private double rating;

    public Instructor(String name, ArrayList<Review> reviews) {
        this.name = name;
        this.reviews = reviews;
        this.rating = calcRating();
    }

    private double calcRating() {
        double rate = 0;
        for(Review i: this.reviews) {
            rate += i.getRating();
        }
        return rate/this.reviews.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
