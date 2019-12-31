package com.example.wothywalkww.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.wothywalkww.Model.Fbuser;
import com.example.wothywalkww.Model.User;
import com.example.wothywalkww.Presenter.RegisterPresenter;
import com.example.wothywalkww.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

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

    Fbuser fbuser;
    String image;
    String gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter=new RegisterPresenter(this,this);
        firstname =(EditText) findViewById(R.id.firstname);
        lastname =(EditText) findViewById(R.id.lastname);
        gender = (Spinner) findViewById(R.id.gender);

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
        phone = (EditText) findViewById(R.id.phone);


        if(registerPresenter.userupdate){
            registerPresenter.setedittext(registerPresenter.user);
        }

        day.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
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
}
