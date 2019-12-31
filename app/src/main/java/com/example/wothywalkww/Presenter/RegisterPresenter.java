package com.example.wothywalkww.Presenter;

import android.content.Context;
import android.view.View;

import com.example.wothywalkww.Model.Fbuser;
import com.example.wothywalkww.Model.User;
import com.example.wothywalkww.Utilities.SessionManagement;
import com.example.wothywalkww.View.Register;

public class RegisterPresenter {

    SessionManagement sessionManagement;
    public User user;
    Context context;
    private View view ;
    public boolean userupdate=false ;

    public RegisterPresenter(View view,Context context){
        this.context=context;
        sessionManagement=new SessionManagement(context);

        user=new User();
        userupdate=checkData();
        this.view= view;
    }






    public void setfb(Fbuser fbuser){

        view.updatefbdetails(fbuser);



    }

    public void setdata(User user){

        sessionManagement.setUser(user);


    }
    public void  setedittext(User user){
        view.updateEditText(user);
    }


    public void updatedata(User user){


        sessionManagement.setUser(user);


    }



    public boolean checkData(){
        user=sessionManagement.getUser();

        if(user!=null) view.updateEditText(user);
        return user!=null;
    }

    public interface View{

        void  updateEditText(User user);
        void  updatefbdetails(Fbuser fbuser);






    }


}

