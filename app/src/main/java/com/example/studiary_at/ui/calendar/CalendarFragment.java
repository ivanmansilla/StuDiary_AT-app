package com.example.studiary_at.ui.calendar;

import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
/*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
         #FALTA AÑADIR EL FRAGMENT_CALENDAR AL BOTTOM NAVIGATION MENU#    */
}