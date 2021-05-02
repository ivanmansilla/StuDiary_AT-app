package com.example.studiary_at;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.xml.transform.sax.SAXSource;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nom_et, email_et, dataNaix_et, contrasenya_et, confContrasenya_et;
    private Button register_button;
    private String emailReg, birthReg, passwordReg, confPasReg, nameReg;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        nom_et = (EditText) findViewById(R.id.textin_name_reg);
        email_et = (EditText) findViewById(R.id.textin_email_reg);
        dataNaix_et = (EditText) findViewById(R.id.textin_birthday_reg);
        contrasenya_et = (EditText) findViewById(R.id.textin_password_reg);
        confContrasenya_et = (EditText) findViewById(R.id.textin_confpassword_reg);
        register_button = findViewById(R.id.button_register_reg);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nameReg = nom_et.getText().toString();
                birthReg = dataNaix_et.getText().toString();
                emailReg = email_et.getText().toString();
                passwordReg = contrasenya_et.getText().toString();
                confPasReg = confContrasenya_et.getText().toString();
                if(!passwordReg.equals(confPasReg)){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Contraseñas no identicas!", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    createAccount(emailReg, passwordReg);
                    goToLoginActivity();
                }
            }
        }
        );
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
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
    }
}