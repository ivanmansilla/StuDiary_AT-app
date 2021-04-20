package com.example.studiary_at.data.model;

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

    /* File JSON:
    FileInputStream serviceAccount =
            new FileInputStream("C:\Program Files\Android\studiaryat-firebase-adminsdk-fcyyz-53242785dd.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://studiaryat-default-rtdb.europe-west1.firebasedatabase.app")
            .build();

    FirebaseApp.initializeApp(options);*/

    /* FIREBASE-->Sign in
    //In your sign-in activity's onCreate method, get the shared instance of the FirebaseAuth object:
    private FirebaseAuth mAuth;
    // ...
    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();

    //Then, when initializing your Activity, check to see if the user is currently signed in:
    @Override
    public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    updateUI(currentUser);

    //After you receive the custom token from your authentication server, pass it to signInWithCustomToken to sign in the user:
     mAuth.signInWithCustomToken(mCustomToken)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCustomToken:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

        //Sing out a user
        FirebaseAuth.getInstance().signOut();
     */
}
