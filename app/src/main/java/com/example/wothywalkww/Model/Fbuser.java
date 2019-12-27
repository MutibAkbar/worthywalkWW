package com.example.wothywalkww.Model;

import java.io.Serializable;

public class Fbuser implements Serializable {

    public String firstname;
    public String secondname;
    public String email;
    public String imageurl;

    public Fbuser(){}

    public Fbuser(String firstname, String secondname, String email, String imageurl) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.email = email;
        this.imageurl = imageurl;
    }


}
