package com.example.studiary_at.data.model;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FireBaseAdapter {
    //Connect to firebase when we have a calendar:
    // Write a message to the database
    /*FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    myRef.setValue("Hello, World!");*/

    /* Read from the database
    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            String value = dataSnapshot.getValue(String.class);
            Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });*/

    FileInputStream serviceAccount =
            new FileInputStream("C:\Program Files\Android\studiary-at-firebase-adminsdk-ek8dp-d947414867.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://studiaryat-default-rtdb.europe-west1.firebasedatabase.app")
            .build();

    FirebaseApp.initializeApp(options);

}
