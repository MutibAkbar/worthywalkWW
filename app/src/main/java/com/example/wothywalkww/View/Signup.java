package com.example.wothywalkww.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wothywalkww.Presenter.SignupPresenter;
import com.example.wothywalkww.R;
import com.example.wothywalkww.Utilities.UserDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;

import static androidx.core.content.ContextCompat.startActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Signup extends AppCompatActivity implements SignupPresenter {

    private EditText emailid;
    private EditText password1;
    private EditText password2;
    private FloatingActionButton reg1_next;
    private String Email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        emailid = (EditText) findViewById(R.id.emailid);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        reg1_next = (FloatingActionButton) findViewById(R.id.reg1_next);

        reg1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }


    @Override
    public void signUp() {
        if (emailid.getText().toString().isEmpty() || password1.getText().toString().isEmpty() || password2.getText().toString().isEmpty()){
            Toast.makeText(Signup.this, "Please enter the credentials!", Toast.LENGTH_SHORT).show();
        }else {
            String id, p1, p2;
            id=emailid.getText().toString().trim();
            p1=password1.getText().toString().trim();
            p2=password2.getText().toString().trim();
            UserDB userdb = new UserDB();
            boolean flag;
            flag=userdb.signUp(id, p1, p2);
            if (flag) {
                Email = id;
                Intent i = new Intent(Signup.this, Register.class);
                i.putExtra("AfterLogin", "false");
                i.putExtra("Email", Email);
                startActivity(i);
                finish();
            }
        }

    }
}
