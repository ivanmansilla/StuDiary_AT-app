package com.example.studiary_at;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studiary_at.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    private EditText editText;
    private Button button;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main) ;

        fragmentContainer= (FrameLayout) findViewById(R.id.fragment_container_view_tag);
        editText = (EditText) findViewById(R.id.)
    }
    public void openFragment(String text){
        LoginFragment fragment = LoginFragment.newInstance(text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container_view_tag,fragment,"Blank Fragment");
    }
}
