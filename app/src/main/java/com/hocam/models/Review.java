package com.hocam.models;

import java.io.Serializable;

public class Review implements Serializable
{
    private String from, instructor, course, semester, text;
    private int rating, year;
    private boolean anonymous;

    public Review(String from, String instructor, String course, int year, String semester, String text, int rating, boolean anonymous)
    {
        this.from = from;
        this.instructor = instructor;
        this.course = course;
        this.semester = semester;
        this.text = text;
        this.rating = rating;
        this.year = year;
        this.anonymous = anonymous;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getInstructor()
    {
        return instructor;
    }

    public void setInstructor(String instructor)
    {
        this.instructor = instructor;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public boolean isAnonymous()
    {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous)
    {
        this.anonymous = anonymous;
    }
}
