package com.example.studiary_at.ui.calendar;

import android.widget.CalendarView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    private CalendarView mCalendarView;


    public LiveData<String> getText() {
        return mText;
    }
}