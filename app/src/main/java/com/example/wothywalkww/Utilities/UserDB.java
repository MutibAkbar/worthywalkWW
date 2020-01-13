package com.example.wothywalkww.Utilities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wothywalkww.Model.Fbuser;
import com.example.wothywalkww.Model.User;
import com.example.wothywalkww.View.Login;
import com.example.wothywalkww.View.MainActivity;
import com.example.wothywalkww.View.Register;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import static com.facebook.FacebookSdk.getApplicationContext;
public class UserDB {

    public FirebaseAuth mAuth;
    public boolean flag ;
    public static String TAG = "facebook login";
    FirebaseFirestore db;
    FirebaseUser users;
    String id;

    public UserDB() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        users = mAuth.getCurrentUser();

    }


    public FirebaseFirestore getDb() {
        return db;
    }

    public FirebaseAuth returnAuth(){
        return mAuth;

    }

    public FirebaseUser returnUser(){
        return users;
    }
    public boolean signUp(final String id, String p1, String p2) {
        flag=false;
        if (p1.equals(p2)) {
             mAuth.createUserWithEmailAndPassword(id, p1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        flag = true;
                        Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error,TryAgain!", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        else{
            Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }


    public void forgetPassword(String email) {
        if (email.length() > 3) {

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter correct email address", Toast.LENGTH_LONG).show();

        }

    }

    public boolean getUser() {
        Login login = new Login();
        if (mAuth.getCurrentUser() != null) {
            id = mAuth.getCurrentUser().getUid();
        }
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            login.getdoc(user.getUid(),db);
            return true;
        }else{
            return false;
        }

    }

    public String getID(){
        return id;
    }


    public void validateUser(final String emailid, String pass) {
        final Login login = new Login();
        mAuth.signInWithEmailAndPassword(emailid,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            User user = new User();
            @Override
            public void onSuccess(AuthResult authResult) {
                   id=mAuth.getCurrentUser().getUid();
                   login.getdoc(id,getDb());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "ID or Password Incorrect", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void loaduserprofile(final AccessToken accessToken){

        GraphRequest request=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {

                    String first_name=object.getString("first_name");
                    String last_name=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");
                    String image_url="https://graph.facebook.com/"+id+"/picture?type-normal";
                    Fbuser fBuser=new Fbuser(first_name,last_name,email,image_url);
                    handleFacebookAccessToken(accessToken);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        Bundle parameters=new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final Login login = new Login();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login.getdoc(id=user.getUid(),db);
//                            loaduserprofile(token);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    public void signOut(){
        mAuth.signOut();
    }


    public void sendata(final User user, String uid) {
        final Map<String, Object> docData2 = new HashMap<>();
        final Map<String, Object> docData3 = new HashMap<>();
        final Map<String, Object> docData = new HashMap<>();

        docData.put("Firstname", user.FirstName);
        docData.put("Lastname", user.LastName);
        docData.put("Email", user.Email);
        docData.put("Phone", user.Phone);
        docData.put("Gender", user.Gender);
        docData.put("Height", user.Height);
        docData.put("Weight", user.Weight);
        docData.put("Age", user.Age);
        docData.put("DOB", user.Dob);
        docData.put("Knubs", 500);
        docData.put("Profilepicture",user.ImageUrl);
        docData.put("Totalknubs",user.totalKnubs);
        docData.put("Permission",user.permission);
        docData.put("Token",user.Token);
        docData2.put("Totalcalorie",0.0);
        docData2.put("Totaldistance",0.0);
        docData2.put("Totalsteps",0);
        docData2.put("Totalknubs",500);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Register register = new Register();
        final DocumentReference docRef= db.collection("Users").document(uid);
        final DocumentReference docRef2=db.collection("Monthlywalk").document(uid);
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                transaction.set(docRef,docData );
                transaction.set(docRef2,docData2);
                return null;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                register.failure();
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                 register.success(user);

            }
        });
    }

    public void updatedata(final User user, String uid) {
        final Map<String, Object> docData = new HashMap<>();

        docData.put("Firstname", user.FirstName);
        docData.put("Lastname", user.LastName);
        docData.put("Phone", user.Phone);
        docData.put("Gender", user.Gender);
        docData.put("Email", user.Email);
        docData.put("Height", user.Height);
        docData.put("Weight", user.Weight);
        docData.put("Age", user.Age);
        docData.put("DOB", user.Dob);
        docData.put("Knubs",user.Knubs );
        docData.put("Profilepicture",user.ImageUrl);
        docData.put("Totalknubs",user.totalKnubs);
        docData.put("Permission",user.permission);
        docData.put("Token",user.Token);

        final Register register = new Register();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef= db.collection("Users").document(uid);
        final DocumentReference docRef2=db.collection("Monthlywalk").document(uid);
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                transaction.set(docRef,docData );
                return null;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                register.failure();
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                register.success1(user);
            }
        });

    }



}

