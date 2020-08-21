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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.DoctorsListAdapter;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;

import java.util.ArrayList;
import java.util.Calendar;

public class BookAnAppointmentFragment extends Fragment {

    TextView date;
    ImageView calendar_img;
    DatePickerDialog.OnDateSetListener listener;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_book_an_appointment, container,false);
        date = (TextView) v.findViewById(R.id.textView2);
        calendar_img = (ImageView) v.findViewById(R.id.imageView3);
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

        ArrayList<DoctorListItemModel> doctorList = new ArrayList<>();
        doctorList.add(new DoctorListItemModel("", "Dr. Quack Quack", "4.0", "3:00 PM"));
        doctorList.add(new DoctorListItemModel("", "Dr. Drake Ramoray", "4.0", "4:00 PM"));
        doctorList.add(new DoctorListItemModel("", "Dr. Johnny Simcard", "4.0", "5:00 PM"));

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new DoctorsListAdapter(doctorList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
