package com.example.studiary_at.ui.settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studiary_at.MainActivity;
import com.example.studiary_at.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        
        final MainActivity ma = (MainActivity) getActivity();
        SharedPreferences sp = ma.getSharedPreferences("SP", ma.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final Switch swi = root.findViewById(R.id.switchTema);

        int theme = sp.getInt("Theme", 1);
        if (theme == 1) {
            swi.setChecked(false);
        }else{
            swi.setChecked(true);
        }


        swi.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if (swi.isChecked()){
                    editor.putInt("Theme", 0);
                }
                else{
                    editor.putInt("Theme", 1);
                }
                editor.commit();
                ma.setDayNight();
            }
        });

        return root;
    }}


