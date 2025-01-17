package com.example.mentalhealthapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.PatientListItemModel;
import com.example.mentalhealthapp.ui.VideoActivity;

import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {
    private ArrayList<PatientListItemModel> mPatientsList;
    private Context context;
    public MutableLiveData<String> dateSelected;

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView patientName;
        public TextView time;
        public Button join_room_btn;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.patient_photo);
            patientName = itemView.findViewById(R.id.patient_display_name);
            time = itemView.findViewById(R.id.patient_time);
            join_room_btn = itemView.findViewById(R.id.join_room_btn);
        }
    }

    public PatientListAdapter(Context context, MutableLiveData<String> dateSelected, ArrayList<PatientListItemModel> patientsList){
        mPatientsList = patientsList;
        this.dateSelected = dateSelected;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointment_item, parent, false);
        // Return a new holder instance
        PatientViewHolder patientsViewHolder = new PatientViewHolder(v);
        return patientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        final PatientListItemModel patient = mPatientsList.get(position);
        holder.mImageView.setImageResource(R.drawable.patient_avatar);

        // Set item views based on your views and data model
        holder.patientName.setText(patient.getPatientName());
        holder.time.setText(patient.getDateTime());

        // Implements button listener for the "Join room" button
        holder.join_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("CALL_ID", patient.getVideoRoom());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPatientsList.size();
    }
}
