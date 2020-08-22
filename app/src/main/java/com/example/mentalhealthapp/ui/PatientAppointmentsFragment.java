package com.example.mentalhealthapp.ui;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.DoctorsListAdapter;
import com.example.mentalhealthapp.adapters.PatientListAdapter;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;
import com.example.mentalhealthapp.java_objects.PatientListItemModel;

import java.util.ArrayList;
import java.util.Calendar;

public class PatientAppointmentsFragment extends Fragment {

    TextView date;
    ImageView calendar_img;
    DatePickerDialog.OnDateSetListener listener;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_patients_appointment, container,false);

        date = (TextView) v.findViewById(R.id.patient_list_date);
        calendar_img = (ImageView) v.findViewById(R.id.patient_list_calendar_image);

        // Prepares a calendar picker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setText(year+"/"+(month+1)+"/"+day);
        calendar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            datePickerDialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                date.setText(i+"/"+i1+"/"+i2);
            }
        };

        // Prepares the patient list model
        ArrayList<PatientListItemModel> patientList = new ArrayList<>();
        patientList.add(new PatientListItemModel("", "Alden Richards", "3:00 PM"));
        patientList.add(new PatientListItemModel("", "Liza Soberano", "4:00 PM"));

        recyclerView = v.findViewById(R.id.patientListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new PatientListAdapter(patientList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }

}
