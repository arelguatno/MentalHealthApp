package com.example.mentalhealthapp.java_objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    public MutableLiveData<String> mDate = new MutableLiveData<>();

    public MutableLiveData<String> getDate(){
        if (mDate == null)
            mDate = new MutableLiveData<String>();
        return mDate;
    }

}
