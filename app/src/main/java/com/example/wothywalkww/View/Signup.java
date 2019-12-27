package com.example.wothywalkww.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wothywalkww.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Signup extends AppCompatActivity {

    private EditText emailid;
    private EditText password1;
    private EditText password2;
    private FloatingActionButton reg1_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailid = (EditText) findViewById(R.id.emailid);
        password1 =(EditText) findViewById(R.id.password1);
        password2=(EditText) findViewById(R.id.password2);
        reg1_next=(FloatingActionButton) findViewById(R.id.reg1_next);

        reg1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailid.getText().toString().trim()==null || password1.getText().toString().trim()==null || password2.getText().toString().trim()==null){

                    Toast.makeText(Signup.this, "Error Please enter the credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id,p1,p2;
                    id=emailid.getText().toString().trim();
                    p1=password1.getText().toString().trim();
                    p2=password2.getText().toString().trim();
                    signUp(id,p1,p2);
                }
            }
        });


    }

    public void signUp(final String id, String p1, String p2) {
        if (p1.equals(p2)) {

//         if(){
//            }
//        else {
//            Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
//        }
        }
    }





}
