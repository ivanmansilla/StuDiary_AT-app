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
                                    "CONTRASEÑAS NO IDENTICAS", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    if (isValidEmail(emailReg)) {
                        createAccount(emailReg, passwordReg);
                        goToLoginActivity();
                    } else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "EMAIL NO CORRECTO", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }
            }
        }
        );
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //Mètode per a registrar un nou usuari
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password);
        // [END create_user_with_email]
    }

    private void goToLoginActivity() {
        //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        //startActivity(intent);
        finish();
    }

    private void updateUI(FirebaseUser user) {
    }
}