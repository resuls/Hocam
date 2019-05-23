package com.hocam.models;

import java.io.Serializable;

public class Review implements Serializable {
    private String from, instructor, course, semester, text;
    private int rating, year;
    private boolean anonymous;

    public Review() {
    }

    public Review(String from, String instructor, String course, int year, String semester, String text, int rating, boolean anonymous) {
        this.from = from;
        this.instructor = instructor;
        this.course = course;
        this.semester = semester;
        this.text = text;
        this.rating = rating;
        this.year = year;
        this.anonymous = anonymous;
    }

    public int getRating() {
        return rating;
    }
}
