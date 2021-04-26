package com.example.studiary_at;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studiary_at.ui.login.ForgotPasswordFragment;
import com.example.studiary_at.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Comunicador{
    private FrameLayout fragmentContainer;
    private EditText editText;
    private Button button;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login) ;

        fragmentContainer= (FrameLayout) findViewById(R.id.fragment_container_view_tag);
        button = (Button) findViewById(R.id.button_forgotPassw_log2);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void enviar(String envia) {
        ForgotPasswordFragment recibe = (ForgotPasswordFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_password);
        if(recibe != null){
            recibe.recibirTexto(envia);
        }
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //TODO
    }
}
