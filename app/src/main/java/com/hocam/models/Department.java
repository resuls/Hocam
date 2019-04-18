package com.hocam.models;

public class Department
{
    private String name, abbr;
    //private ArrayList<Course> courses;

    public Department(String name, String abbr)
    {
        this.name = name;
        this.abbr = abbr;
        //this.courses = courses;
    }

    public String getName()
    {
        return name;
    }

    public String getAbbr()
    {
        return abbr;
    }

//    public ArrayList<Course> getCourses() {
//        return courses;
//    }
}
