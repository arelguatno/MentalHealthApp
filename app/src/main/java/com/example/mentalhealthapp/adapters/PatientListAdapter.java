package com.example.mentalhealthapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.PatientListItemModel;

import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {
    private ArrayList<PatientListItemModel> mPatientsList;

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView patientName;
        public TextView time;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.patient_photo);
            patientName = itemView.findViewById(R.id.patient_display_name);
            time = itemView.findViewById(R.id.patient_time);
        }
    }

    public PatientListAdapter(ArrayList<PatientListItemModel> patientsList){
        mPatientsList = patientsList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View v = inflater.inflate(R.layout.patient_appointment_item, parent, false);

        // Return a new holder instance
        PatientViewHolder patientsViewHolder = new PatientViewHolder(v);
        return patientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientListItemModel patient = mPatientsList.get(position);
        holder.mImageView.setImageResource(R.drawable.app_logo);

        // Set item views based on your views and data model
        holder.patientName.setText(patient.getPatientName());
        holder.time.setText(patient.getDateTime());

    }

    @Override
    public int getItemCount() {
        return mPatientsList.size();
    }
}
