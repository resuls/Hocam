package com.hocam.models;

public class Course
{
    private String name, code;
    private float average;

    public Course(String name, String code, float average)
    {
        this.name = name;
        this.code = code;
        this.average = average;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public float getAverage()
    {
        return average;
    }
}
