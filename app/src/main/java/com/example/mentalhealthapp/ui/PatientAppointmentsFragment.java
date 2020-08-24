package com.example.mentalhealthapp.ui;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.DoctorsListAdapter;
import com.example.mentalhealthapp.adapters.PatientListAdapter;
import com.example.mentalhealthapp.java_objects.CalendarViewModel;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;
import com.example.mentalhealthapp.java_objects.PatientListItemModel;
import com.example.mentalhealthapp.repository.PatientAppointmentRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PatientAppointmentsFragment extends Fragment {

    TextView date;
    ImageView calendar_img;
    Button video_room_btn;
    DatePickerDialog.OnDateSetListener listener;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    // List model for patients
    ArrayList<PatientListItemModel> patientList;
    String selectedDate;
    CalendarViewModel calendarViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_patients_appointment, container,false);

        // Prepares toolbar
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("View list of Appointments");
        }

        date = (TextView) v.findViewById(R.id.patient_list_date);
        calendar_img = (ImageView) v.findViewById(R.id.patient_list_calendar_image);

        // Determines the selected date (no exact time)
        selectedDate = getFormattedDate(Calendar.getInstance().getTime(), "yyyy/M/d");
        date.setText(selectedDate);

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().setValue(selectedDate);
        calendarViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                fetchData();
                Log.d("New Value:", s);
            }
        });

        // Prepares on click listener for the calendar image view
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // Prepares on date set listener for the Date Picker Dialog
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                // Updates the date textview
                selectedDate = getFormattedDate((i + "/" + i1 + "/" + i2), "yyyy/M/d");
                date.setText(selectedDate);
                calendarViewModel.getDate().setValue(selectedDate);
                // Renews the list of data based on the new query
                //fetchData(selectedDate);
            }
        };

        // Instantiates the recycler view and the patient list
        recyclerView = v.findViewById(R.id.patientListRecyclerView);
        patientList = new ArrayList<>();
        // Begins to fetch the data with a given default date
        fetchData();

        return v;
    }

    private void fetchData(){
        // Refreshes the patient list
        patientList.clear();
        // Gets the data from repository and listens for results
        final PatientAppointmentRepository repository = new PatientAppointmentRepository(selectedDate);
        repository.getPatientAppointmentList(new PatientAppointmentRepository.FetchPatientAppointmentCallback(){

            @Override
            public void onSuccess(PatientListItemModel value) {
                // Do nothing
            }

            @Override
            public void onSuccess(ArrayList<PatientListItemModel> list) {
                patientList = list;
                // Gets the data from repository and listens for results
                for (final PatientListItemModel patient : patientList){
                    repository.getPatientDetails(patient.getPatientEmail(), new PatientAppointmentRepository.FetchPatientDataCallback(){
                        @Override
                        public void onSuccess(HashMap<String, String> data) {
                            patient.setPatientName(data.get("patient_name"));
                            patient.setPhotoURL(data.get("patient_photo"));
                            // Populates the data with a specific display name and photo URL
                            populateData();
                        }

                        @Override
                        public void onFailure() {
                            // Still populates the data (but without a specific display name and photo URL)
                            populateData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                populateData();
            }
        });
    }

    private void populateData() {
        // Deals with the recycler view
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PatientListAdapter(getContext(), calendarViewModel.getDate(), patientList);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }

    private String getFormattedDate(Object date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date instanceof Date) {
            return sdf.format((Date) date);
        }else if (date instanceof String){
            try {
                Date dateTmp = sdf.parse((String) date);
                return sdf.format(dateTmp);
            } catch (ParseException e) {
                e.printStackTrace();
                return "1970/01/01";
            }
        }else{
            return "1970/01/01";
        }
    }

}
