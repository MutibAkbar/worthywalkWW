package com.example.wothywalkww.Model;

import java.util.Date;

public class User {

    public String firstName;
    public String lastName;
    public String gender;
    public float height;
    public float weight;
    public int age;
    public Date dob;
    public int knubs;
    public String  imageUrl;
    public  String email;
    public String password;
    public int totalKnubs;
    public boolean permission;
    public String token;

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public Date getDob() {
        return dob;
    }

    public int getKnubs() {
        return knubs;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalknubs() {
        return totalKnubs;
    }

    public boolean getPermission() {
        return permission;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public void setLastname(String lastName) {
        this.lastName=lastName;
    }


    public void setGendar(String gender) {
        this.gender=gender;
    }

    public void setHeight(float height) {
        this.height=height;
    }

    public void setWeight(float weight) {
        this.weight=weight;
    }

    public void setage(int age) {
        this.age=age;
    }

    public void setDate(Date dob) {
        this.dob=dob;
    }

    public void setKnubs(int knubs) {
       this.knubs=knubs;
    }

    public void setImageurl(String imageUrl) {
        this.imageUrl=imageUrl;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setTotalknubs(int totalKnubs) {
        this.totalKnubs=totalKnubs;
    }

    public void setPermission(boolean permission) {
        this.permission=permission;
    }
}