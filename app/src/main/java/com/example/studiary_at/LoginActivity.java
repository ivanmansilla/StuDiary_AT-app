package com.example.studiary_at;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studiary_at.ui.login.ForgotPasswordFragment;
import com.example.studiary_at.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.studiary_at.ui.login.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Comunicador{
    private FrameLayout fragmentContainer;
    private EditText input_email;
    private EditText input_password;
    private Button login_button;
    private FirebaseAuth mAuth;
    private static final String TAG = "Login";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login) ;

        fragmentContainer= (FrameLayout) findViewById(R.id.fragment_container_view_tag);
        login_button = (Button) findViewById(R.id.button_login_log2);
        input_email =(EditText) findViewById(R.id.textin_username_log2);
        input_password=(EditText) findViewById(R.id.textin_password_log2);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        login_button.setOnClickListener((View.OnClickListener) this);
        onStart();
        }


    @Override
    public void enviar(String envia) {
        ForgotPasswordFragment recibe = (ForgotPasswordFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_password);
        if(recibe != null){
            recibe.recibirTexto(envia);
        }
    }

    @Override
    public void onClick(View view) {
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        System.out.println(email);
        System.out.println(password);
        LoginViewModel.login(email,password);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    //Mètode per a registrar un nou usuari
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    //Mètode per iniciar sessió
    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void reload() { }

    private void updateUI(FirebaseUser currentUser) {
        //TODO
    }
}
