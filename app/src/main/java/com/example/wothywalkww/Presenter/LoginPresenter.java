package com.example.wothywalkww.Presenter;

import com.google.firebase.firestore.FirebaseFirestore;

public interface LoginPresenter {

    void checkUser();
    void signInButton();
    void getdoc(String id, final FirebaseFirestore db);
    void sendnewtoken(FirebaseFirestore db);
}
