package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.ConsultationOfferingAdapter;
import com.example.mentalhealthapp.java_objects.ConsultationOfferingModel;

import java.util.ArrayList;
import java.util.List;

public class ConsultationFragment extends Fragment {
    List<ConsultationOfferingModel> listOfServices;

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_consultation, container, false);

        //initializing objects
        listOfServices = new ArrayList<>();
        listView = (ListView) v.findViewById(R.id.listView);

        //adding some values to our list
        listOfServices.add(new ConsultationOfferingModel(R.drawable.ask_question, "Ask a FREE question", "Ask a general mental health query  and get a free answer within 24 hours"));
        listOfServices.add(new ConsultationOfferingModel(R.drawable.book_doc, "Book an appointment", "Get help from any our 1,000+ therapist within 6 hours over chat or video call"));
        listOfServices.add(new ConsultationOfferingModel(R.drawable.thunder_image, "Consult a Therapist now", "Instantly talk to any therapist available now over chat or video call "));

        //creating the adapter
        ConsultationOfferingAdapter adapter = new ConsultationOfferingAdapter(v.getContext(), R.layout.consultation_custom_list, listOfServices);

        //attaching adapter to the listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Ask for free
                        break;
                    case 1:
                        // Book an Appointment
                        break;
                    case 2:
                        // Consult therapist
                        break;
                }
            }
        });
        return v;
    }
}
