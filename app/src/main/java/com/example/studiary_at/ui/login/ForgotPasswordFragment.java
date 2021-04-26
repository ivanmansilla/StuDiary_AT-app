package com.example.studiary_at.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studiary_at.R;

public class ForgotPasswordFragment extends Fragment {
    private com.example.studiary_at.ui.login.LoginViewModel loginViewModel;
    TextView txtrecibe;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_register, container, false);
        txtrecibe= (TextView) v.findViewById(R.id.textin_name_reg);
        return v;
    }
    public void recibirTexto(String texto){
        txtrecibe.setText(texto);
    }
}
