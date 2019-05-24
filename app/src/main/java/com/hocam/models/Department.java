package com.hocam.models;

public class Department
{
    private String name, abbr;

    public Department(String name, String abbr)
    {
        this.name = name;
        this.abbr = abbr;
    }

    public String getName()
    {
        return name;
    }

    public String getAbbr()
    {
        return abbr;
    }
}
