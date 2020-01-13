package com.example.wothywalkww.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.wothywalkww.Utilities.UserDB;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private Button btSignin;
    private TextView forgot;
    private TextView signUp;
    private LoginButton loginButton;
    private ProgressBar pbloading;
    Double distancemon=0.0;
    Double caloriemon=0.0;
    long knubsmon=0;
    long stepsmon=0;
    String token;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    CallbackManager callbackManager ;
    Fbuser fBuser;
    private static final String EMAIL = "email";
    public User user = new User();

    final UserDB userdb = new UserDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MultiDex.install(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        callbackManager = CallbackManager.Factory.create();
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        btSignin = (Button) findViewById(R.id.btSignIn);
        forgot = (TextView) findViewById(R.id.forgot);
        signUp = (TextView) findViewById(R.id.login_signup);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setPublishPermissions(Arrays.asList(EMAIL, "public_profile"));
        pbloading = (ProgressBar) findViewById(R.id.pbLoading);


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ema = emailAddress.getText().toString();
                userdb.forgetPassword(ema);

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
                Toast.makeText(getApplicationContext(), "User Canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        boolean flag = userdb.getUser();
        if (flag) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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

                UserDB userDB = new UserDB();
                String emailid = emailAddress.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!emailid.isEmpty() && !pass.isEmpty()) {
                    pbloading.setVisibility(View.VISIBLE);
                    userDB.validateUser(emailid, pass);
                } else
                    Toast.makeText(getApplicationContext(), "Enter Email /Password ", Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){
                userdb.signOut();
                Toast.makeText(getApplicationContext(),"User logged out",Toast.LENGTH_LONG).show();
            }else
            {
                userdb.loaduserprofile(currentAccessToken);
            }
        }
    };


    public void getdoc(String id, final FirebaseFirestore db) {
        db.collection("Users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getData()!=null) {
                        Log.d("document", document.getData().toString());
                        user = document.toObject(User.class);
                        Gson gson = new Gson();

                        Map<String, Object> usermap = new HashMap<>();
                        Log.d("hrllp", document.toString());

                        String userjson = gson.toJson(user);
                        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                        prefsEditor.putString("User", userjson);
                        prefsEditor.commit();
                        sendnewtoken(db);

                    }else{
                        Log.d("enteredfbuser","checkking");
                        if(fBuser!=null){
                            Intent intent =new Intent(Login.this,Register.class);
                            intent.putExtra("fbuser",fBuser);
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.d("FragNotif", "get failed with ", task.getException());
                }
            }

        });

    }

    public void sendnewtoken(FirebaseFirestore db) {
        token = sharedpreferences.getString("Token","");
        db.collection("Monthlywalk").document(userdb.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                distancemon= snapshot.getDouble("Totaldistance");
                caloriemon= snapshot.getDouble("Totalcalorie");
                knubsmon= snapshot.getLong("Totalknubs");
                stepsmon= snapshot.getLong("Totalsteps");
                SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                prefsEditor.putFloat("Totaldistance",  distancemon.floatValue());
                prefsEditor.putFloat("Totalcalorie", caloriemon.floatValue());
                prefsEditor.putInt("Totalknubs", (int)knubsmon);
                prefsEditor.putInt("Totalsteps", (int) stepsmon);
                prefsEditor.commit();
            }
        });
        final Map<String, Object> doc = new HashMap<>();
        doc.put("Token",token);
        if (token != null) {
            try {
                db.collection("Users").document(userdb.getID()).update(doc).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Void document = task.getResult();
                        }

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        finish();

                    }

                });
            }catch (Exception e){
                Log.d("errortoken",e.getMessage());
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
            finish();

        }

    }


}
