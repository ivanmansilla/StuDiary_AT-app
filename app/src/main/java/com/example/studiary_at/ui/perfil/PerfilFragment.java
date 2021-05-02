package com.example.studiary_at.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.studiary_at.LoginActivity;
import com.example.studiary_at.MainActivity;
import com.example.studiary_at.R;
import com.example.studiary_at.ui.settings.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilFragment extends Fragment{
    private PerfilViewModel perfilViewModel;
    private Button signOut_button;
    private FirebaseAuth mAuth;


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
        mAuth = FirebaseAuth.getInstance();
        signOut_button = (Button) root.findViewById(R.id.sign_out_button);
        signOut_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                Intent siguiente = new Intent(PerfilFragment.this.getActivity(), LoginActivity.class);
                startActivity(siguiente);
            }
        });
        return root;
    }
}
