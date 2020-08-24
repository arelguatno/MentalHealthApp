package com.example.mentalhealthapp.ui;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.DoctorsListAdapter;
import com.example.mentalhealthapp.java_objects.CalendarViewModel;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookAnAppointmentFragment extends Fragment {

    TextView date;
    ImageView calendar_img;
    DatePickerDialog.OnDateSetListener listener;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String dateSelected = "";
    CalendarViewModel calendarViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_book_an_appointment, container,false);

        // Tool Bar
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Book an Appointment");
        }

        date = (TextView) v.findViewById(R.id.textView2);
        calendar_img = (ImageView) v.findViewById(R.id.imageView3);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setText(year+"/"+(month+1)+"/"+day);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dateSelected = sdf.format(d.getTime());
        Log.d("Date Selected", dateSelected);
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().setValue(dateSelected);
        calendarViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("New Value:",s);
            }
        });
        calendar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                date.setText(i+"/"+i1+"/"+i2);
                dateSelected = i+"/"+i1+"/"+i2;
                calendarViewModel.getDate().setValue(dateSelected);
            }
        };
        ArrayList<DoctorListItemModel> doctorList = new ArrayList<>();
        doctorList.add(new DoctorListItemModel("", "Dr. Quacke Quack", "drquackquack@gmail.com","4", "3:00 PM"));
        doctorList.add(new DoctorListItemModel("", "Dr. Drake Ramoray", "drakeramoray@yahoo.com","4.5", "4:00 PM"));
        doctorList.add(new DoctorListItemModel("", "Dr. Johnny Simcard", "johnnysims@philhealth.org","4.8", "5:00 PM"));

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new DoctorsListAdapter(doctorList, calendarViewModel.getDate(), getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }

}
