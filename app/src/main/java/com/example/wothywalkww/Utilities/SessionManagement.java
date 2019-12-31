package com.example.wothywalkww.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.wothywalkww.Model.User;
import com.google.gson.Gson;

public class SessionManagement {

    Context context;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KeyUser = "User" ;

    Gson gson;

    User user;
    SharedPreferences sharedpreferences;
    public  SessionManagement(Context context){
        this.context=context;
        gson= new Gson();
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();


    }


    public User getUser() {

        String userss=sharedpreferences.getString(KeyUser,"a");

        if(!userss.equals("a")){
            user= gson.fromJson(userss,User.class);
        }

        return user;
    }


    public void setUser(User user) {
        String userjson=gson.toJson(user);

        editor.putString(KeyUser,userjson);
        editor .commit();

    }
    public void logoutUser(){

        editor.clear();
        editor.commit();

//        Intent i = new Intent(context,LoginActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(i);
    }
}
