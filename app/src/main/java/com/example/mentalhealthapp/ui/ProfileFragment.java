package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mentalhealthapp.R;

public class ProfileFragment extends Fragment {

    Button editPersonalDetailsBtn;
    Button viewConsultationHistoryBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container,false);

        editPersonalDetailsBtn = (Button) v.findViewById(R.id.edit_personal_details_btn);
        editPersonalDetailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Personal Details clicked", Toast.LENGTH_LONG).show();
            }
        });

        viewConsultationHistoryBtn = (Button) v.findViewById(R.id.consultation_history_btn);
        viewConsultationHistoryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "View Consultation History clicked", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
