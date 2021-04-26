package com.example.studiary_at.data.model;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FireBaseAdapter {/*

    FileInputStream serviceAccount =
            new FileInputStream("C:\Program Files\Android\studiary-at-firebase-adminsdk-ek8dp-d947414867.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://studiaryat-default-rtdb.europe-west1.firebasedatabase.app")
            .build();

    FirebaseApp.initializeApp(options);

/*
    //Sign in in other lenguage
    function signInWithEmailPassword() {
        var email = "test@example.com";
        var password = "hunter2";
        // [START auth_signin_password]
        firebase.auth().signInWithEmailAndPassword(email, password)
                .then((userCredential) => {
                // Signed in
                var user = userCredential.user;
        // ...
    })
    .catch((error) => {
                var errorCode = error.code;
        var errorMessage = error.message;
    });
        // [END auth_signin_password]
    }
------------------------------------------------------------------------------------
    //Sign up in other lenguage
    function signUpWithEmailPassword() {
        var email = "test@example.com";
        var password = "hunter2";
        // [START auth_signup_password]
        firebase.auth().createUserWithEmailAndPassword(email, password)
                .then((userCredential) => {
                // Signed in
                var user = userCredential.user;
        // ...
    })
    .catch((error) => {
                var errorCode = error.code;
        var errorMessage = error.message;
        // ..
    });
        // [END auth_signup_password]
    }*/
}
