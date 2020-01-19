package com.example.wothywalkww.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wothywalkww.Model.Fbuser;
import com.example.wothywalkww.Model.User;
import com.example.wothywalkww.Presenter.RegisterPresenter;
import com.example.wothywalkww.R;
import com.example.wothywalkww.Utilities.UserDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wothywalkww.View.Login.MyPREFERENCES;

public class Register extends AppCompatActivity implements TextWatcher, RegisterPresenter.View {

    private RegisterPresenter registerPresenter;
    private EditText firstname;
    private EditText lastname;
    private EditText height;
    private EditText weight;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText phone;
    private Spinner gender;
    private FloatingActionButton go;
    private ProgressBar pbloading;
    private CircleImageView profile_picture;
    StringBuilder dateOfBirth=new StringBuilder();
    SharedPreferences sharedpreferences;
    Fbuser fbuser;
    String image;
    String gen;
    String fname, lname, phn, gend, days, months, years;
    String Email;
    float hei, wei;
    boolean fn,ln,pn,gn,ht,wt,dy,mn,yr;
    boolean update=false;
    User updateuser;
    Gson gson=new Gson();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter=new RegisterPresenter(this,this);
        firstname =(EditText) findViewById(R.id.firstname);
        lastname =(EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        gender = (Spinner) findViewById(R.id.gender);
        profile_picture=(CircleImageView)findViewById(R.id.profile_image);
        pbloading=(ProgressBar) findViewById(R.id.pbLoading);
        height=(EditText) findViewById(R.id.height);
        weight=(EditText) findViewById(R.id.weight);
        day= (EditText) findViewById(R.id.day);
        month=(EditText) findViewById(R.id.month);
        year =(EditText) findViewById(R.id.year);
        go=(FloatingActionButton) findViewById(R.id.go);
        firstname.addTextChangedListener(this);
        lastname.addTextChangedListener(this);
        height.addTextChangedListener(this);
        weight.addTextChangedListener(this);

        context =this;

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Intent intent=getIntent();
        fbuser= (Fbuser) intent.getSerializableExtra("fbuser");
        Email= (String) intent.getSerializableExtra("Email");
        if(registerPresenter.userupdate){
            registerPresenter.setedittext(registerPresenter.user);
        }

        day.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(dateOfBirth.length()==0&day.length()==2)
                {
                    dateOfBirth.append(s);
                    day.clearFocus();
                    month.requestFocus();
                    month.setCursorVisible(true);
                }

            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(dateOfBirth.length()==2)
                {
                    dateOfBirth.deleteCharAt(0);
                    dateOfBirth.deleteCharAt(1);
                }

            }

            public void afterTextChanged(Editable s) {
                if(dateOfBirth.length()==0)
                {
                    day.requestFocus();
                }

            }
        });

        month.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(month.length()==2)
                {
                    dateOfBirth.append(s);
                    month.clearFocus();
                    year.requestFocus();
                    year.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(dateOfBirth.length()==2)
                {
                    dateOfBirth.deleteCharAt(0);
                }

            }

            public void afterTextChanged(Editable s) {
                if(dateOfBirth.length()==0)
                {
                    month.requestFocus();
                }

            }
        });
        year.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(year.length()==4)
                {
                    dateOfBirth.append(s);
                    month.clearFocus();
                    year.requestFocus();
                    year.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(dateOfBirth.length()==2)
                {
                    dateOfBirth.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(dateOfBirth.length()==0)
                {
                    month.requestFocus();
                }

            }
        });

        day.addTextChangedListener(this);
        month.addTextChangedListener(this);
        year.addTextChangedListener(this);


        image="";
        if(fbuser!=null){

            registerPresenter.setfb(fbuser);

        }


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gen=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               updatingValues();
            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean boolphone=false;
        if(firstname.getText().toString().length()==0) {

            firstname.setError("This field can not be empty");
            fn=false;
        }else {
            fn=true;
        }


        boolphone=phone.getText().toString().matches("3[0-9]{9}");

        if(phone.getText().toString().length()<10) phone.setError("Enter 10 digit");
        else if(boolphone) pn=true ;
        else phone.setError("3XXXXXXXXX");

        if(day.getText().toString().length()==0){
            dy=false;
            day.setError("can not be empty");
        }

        else if(Integer.parseInt(day.getText().toString())>31){
            day.setError("Enter valid day");
            dy=false;
        }
        else{
            dy=true;
        }

        if(month.getText().toString().length()==0){
            mn=false;
            month.setError("can not be empty");
        }
        else if(Integer.parseInt(month.getText().toString())>12){
            mn=false;

            month.setError("Enter valid month");
        }else {
            mn=true;

        }

        if(year.getText().toString().length()==0){
            year.setError("can not be empty");
            yr=false;
        }
        else if(Integer.parseInt(year.getText().toString())<1940 || Integer.parseInt(year.getText().toString())>2019){
            year.setError("Not a valid year");
            yr=false;

        }
        else yr=true;


        if(height.getText().toString().length()==0){
            ht=false;
            height.setError("can not be empty");
        }
        else if(Float.parseFloat(height.getText().toString())<3.0 ||Float.parseFloat(height.getText().toString())>14.0) {
            height.setError("enter valid Height");
            ht=false;
        }else  ht=true;

        if(weight.getText().toString().length()==0){
            ht=false;
            weight.setError("can not be empty");
        }
        else if(Float.parseFloat(weight.getText().toString())<20 || Float.parseFloat(weight.getText().toString())>600){
            wt=false;
            weight.setError("Enter valid Weight");
        }else{
            wt=true;
        }


    }

    @Override
    public void updateEditText(User user) {


        firstname.setText(user.FirstName);
        lastname.setText(user.LastName);
        height.setText(String.valueOf(user.Height));
        weight.setText(String.valueOf(user.Weight));
        String phnses=user.Phone.toString().substring(3);

        phone.setText(phnses);
    }

    @Override
    public void updatefbdetails(Fbuser fbuser) {

        firstname.setText(fbuser.firstname);
        lastname.setText(fbuser.secondname);
        Picasso.get().load(fbuser.imageurl).fit().into(profile_picture);
        image=fbuser.imageurl;
    }

    @Override
    public void updatingValues() {
        UserDB userdb = new UserDB();
        if (pn && ht && wt && dy && mn && yr) {
            pbloading.setVisibility(View.VISIBLE);
            fname = firstname.getText().toString();
            fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
            lname = lastname.getText().toString();
            lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);
            phn = phone.getText().toString();
            phn = "+92" + phn;
            hei = Float.parseFloat(height.getText().toString());
            wei = Float.parseFloat(weight.getText().toString());
            days = day.getText().toString();
            months = month.getText().toString();
            years = year.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dob = years + months + days;
            Date d = null;
            try {
                d = sdf.parse(dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentTime = Calendar.getInstance().getTime();
            int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(years);
            User user;
            String token = sharedpreferences.getString("Token", "");

            if (fbuser != null)
                user = new User(fbuser.email,fname, lname, phn, gend, hei, wei, age, d, 500, fbuser.imageurl, 500, false, token);
            else if(update){

                user = new User(updateuser.Email,fname, lname, phn, gend, hei, wei, age, d, updateuser.Knubs, "", updateuser.totalKnubs, false, token);
            }else
                user = new User(Email,fname, lname, phn, gend, hei, wei, age, d, 500, "", 500, false, token);

            String userjson = gson.toJson(user);
            SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
            prefsEditor.putBoolean("Newuser",true);
            prefsEditor.putString("User", userjson);

            if(!update){
                prefsEditor.putFloat("Totaldistance", 0);
                prefsEditor.putFloat("Totalcalorie", 0);
                prefsEditor.putInt("Totalknubs", 0);
                prefsEditor.putInt("Totalsteps", 0);

            }
            prefsEditor.commit();
            if(update){
                userdb.updatedata(user, userdb.returnUser().getUid());

            }else {
                userdb.sendata(user,userdb.returnUser().getUid());

            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Form Validation failed , please provide the correct Information !",Toast.LENGTH_LONG).show();

        }
    }

    public void failure(){
        pbloading.setVisibility(View.GONE);
    }

    public  void success(User user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("User",user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void success1(User user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("User",user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
