package com.example.studiary_at;

import android.os.Bundle;

import android.view.View;
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
import com.example.studiary_at.ui.login.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Comunicador{
    private FrameLayout fragmentContainer;
    private EditText input_email;
    private EditText input_password;
    private Button login_button;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login) ;

        fragmentContainer= (FrameLayout) findViewById(R.id.fragment_container_view_tag);
        login_button = (Button) findViewById(R.id.button_login_log2);
        input_email =(EditText) findViewById(R.id.textin_username_log2);
        input_password=(EditText) findViewById(R.id.textin_password_log2);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                LoginViewModel.login(email,password);

                }
            });
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
