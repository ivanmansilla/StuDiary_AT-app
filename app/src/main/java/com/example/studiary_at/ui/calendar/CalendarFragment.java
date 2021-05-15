package com.example.studiary_at.ui.calendar;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studiary_at.LoginActivity;
import com.example.studiary_at.R;
import com.example.studiary_at.ui.notes.NotesActivity;
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
        goToNotesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent notesIntent = new Intent(CalendarFragment.this.getActivity(), NotesActivity.class);
                startActivity(notesIntent);
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                stYear = Integer.toString(year);
                if (month < 9){
                    month = month + 1;//El +1 ja que per algun motiu agafa un mes menys.
                    stMonth = Integer.toString(month);
                    stMonth = ("0"+stMonth);
                }else{
                    month = month + 1;//El +1 ja que per algun motiu agafa un mes menys.
                    stMonth = Integer.toString(month);
                }
                if (dayOfMonth < 9){
                    stDayOfMonth = Integer.toString(dayOfMonth);
                    stDayOfMonth = ("0"+stDayOfMonth);
                }else{
                    stDayOfMonth = Integer.toString(dayOfMonth);
                }
                stDate = (stDayOfMonth+"/"+stMonth+"/"+stYear);
                try {
                    data = new SimpleDateFormat("dd/MM/yyyy").parse(stDate); //Aconseguim la data marcada
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(data);
            }
        });
        return root;
    }



    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
}

 */
}