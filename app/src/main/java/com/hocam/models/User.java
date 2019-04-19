package com.hocam.models;

public class User
{
    private String name;
    private String surname;
    private String department;
    private String email;

    public User(String name, String surname, String department, String email)
    {
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
