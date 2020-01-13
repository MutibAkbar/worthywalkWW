package com.example.wothywalkww.Model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    public String FirstName;
    public String LastName;
    public String Gender;
    public float Height;
    public float Weight;
    public int Age;
    public Date Dob;
    public int Knubs;
    public String  ImageUrl;
    public  String Email;
    public String Phone;
    public int totalKnubs;
    public boolean permission;
    public String Token;

    public User(String Email,String firstname, String lastname, String phone, String gender, float height, float weight, int age, Date dob, int knubs,String  imageurl,int totalknubs,boolean permission,String token) {

        this.Email=Email;
        FirstName= firstname;
        LastName= lastname;
        Phone = phone;
        Gender = gender;
        Height = height;
        Weight = weight;
        Age = age;
        Dob = dob;
        Knubs = knubs;
        ImageUrl=imageurl;
        totalKnubs=totalknubs;
        permission=permission;
        Token=token;

    }

    public User(){

    };

}
