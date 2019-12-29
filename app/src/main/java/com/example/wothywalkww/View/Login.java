package com.example.wothywalkww.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wothywalkww.Model.Fbuser;
import com.example.wothywalkww.Model.User;
import com.example.wothywalkww.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private Button btSignin;
    private TextView forgot;
    private TextView signUp;
    private LoginButton loginButton;
    private ProgressBar pbloading;

    CallbackManager callbackManager ;
    public static String TAG="facebook login";
    Fbuser fBuser;
    private static final String EMAIL = "email";
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MultiDex.install(this);
        callbackManager = CallbackManager.Factory.create();
        emailAddress =(EditText)findViewById(R.id.emailAddress);
        password =(EditText) findViewById(R.id.password);
        btSignin =(Button) findViewById(R.id.btSignIn);
        forgot =(TextView) findViewById(R.id.forgot);
        signUp =(TextView) findViewById(R.id.login_signup);
        loginButton =(LoginButton) findViewById(R.id.login_button);
        loginButton.setPublishPermissions(Arrays.asList(EMAIL,"public_profile"));
        pbloading =(ProgressBar) findViewById(R.id.pbLoading);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ema=emailAddress.getText().toString();
                if(ema.length()>3) {

                }else {
                    Toast.makeText(getApplicationContext(),"Enter correct email address",Toast.LENGTH_LONG).show();
                }

            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                pbloading.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(),"User Canceled",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);

            }
        });

        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = emailAddress.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!emailid.isEmpty() && !pass.isEmpty()){
                    pbloading.setVisibility(View.VISIBLE);

                    //validateUser(emailid, pass);
                }
                else Toast.makeText(getApplicationContext(),"Enter Email /Password ",Toast.LENGTH_LONG).show();

            }
        });



    }


}
