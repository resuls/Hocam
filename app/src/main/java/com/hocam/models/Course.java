package com.hocam.models;

public class Course {
    private String name, code;
    private double average;

    public Course(String name, String code, double average) {
        this.name = name;
        this.code = code;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getAverage() {
        return average;
    }
}
