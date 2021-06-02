package com.example.studiary_at.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studiary_at.LoginActivity;
import com.example.studiary_at.R;
import com.example.studiary_at.ui.notes.NotesActivity;
import com.example.studiary_at.ui.notes.NotesActivityViewModel;
import com.example.studiary_at.ui.perfil.PerfilFragment;
import com.example.studiary_at.ui.perfil.PerfilViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendar;
    private Button goToNotesBtn;
    private String stYear, stMonth, stDayOfMonth, stDate;
    private SimpleDateFormat sdf;
    private Date data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        calendar = (CalendarView) root.findViewById(R.id.calendarView);
        goToNotesBtn = (Button) root.findViewById(R.id.add_note_button);
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        stDate = sdf.format(new Date(calendar.getDate()));
        System.out.println(stDate);
        setStDate(stDate);
        goToNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notesIntent = new Intent(CalendarFragment.this.getActivity(), NotesActivity.class);
                notesIntent.putExtra("data", stDate);
                startActivity(notesIntent);
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                stYear = Integer.toString(year);
                if (month < 9) {
                    month = month + 1;//El +1 ja que per algun motiu agafa un mes menys.
                    stMonth = Integer.toString(month);
                    stMonth = ("0" + stMonth);
                } else {
                    month = month + 1;//El +1 ja que per algun motiu agafa un mes menys.
                    stMonth = Integer.toString(month);
                }
                if (dayOfMonth < 9) {
                    stDayOfMonth = Integer.toString(dayOfMonth);
                    stDayOfMonth = ("0" + stDayOfMonth);
                } else {
                    stDayOfMonth = Integer.toString(dayOfMonth);
                }
                stDate = (stDayOfMonth + "-" + stMonth + "-" + stYear);
                try {
                    data = new SimpleDateFormat("dd-MM-yyyy").parse(stDate); //Aconseguim la data marcada
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    public String getStDate() {
        return stDate;
    }

    public void setStDate(String stDate) {
        this.stDate = stDate;
    }


}