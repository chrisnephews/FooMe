package com.example.cknev.foome.model;

/**
 * Created by cknev on 18-10-2017.
 *
 * Model for representing the User object
 */

public class User
{
    private int id;
    private String password;
    private String name;
    private String email;
    private String dateOfBirth;

    public User()
    {

    }
    public User(int id, String password, String name, String email, String dateOfBirth)
    {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
