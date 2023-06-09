package com.example.studiary_at.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.studiary_at.LoginActivity;
import com.example.studiary_at.MainActivity;
import com.example.studiary_at.R;
import com.example.studiary_at.data.model.FireBaseAdapter;
import com.example.studiary_at.data.model.NotaCard;
import com.example.studiary_at.ui.settings.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilFragment extends Fragment{
    private PerfilViewModel perfilViewModel;
    private Button signOut_button;
    private FirebaseAuth mAuth;
    private TextView mail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }

        });

        mail = (TextView) root.findViewById(R.id.mailperfil);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String emailuser;

        //Tenim en una variable el mail del usuari
        emailuser = user.getEmail();

        mail.setText(emailuser, TextView.BufferType.EDITABLE);

        signOut_button = (Button) root.findViewById(R.id.sign_out_button);
        signOut_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                getActivity().finish();
            }
        });
        return root;
    }
}