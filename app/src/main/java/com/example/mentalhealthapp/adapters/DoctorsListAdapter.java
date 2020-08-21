package com.example.mentalhealthapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;

import java.util.ArrayList;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.DoctorsViewHolder> {
    private ArrayList<DoctorListItemModel> mDoctorsList;

    public static class DoctorsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView docName;
        public TextView rating;
        public TextView time;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView2);
            docName = itemView.findViewById(R.id.textView3);
            rating = itemView.findViewById(R.id.textView4);
            time = itemView.findViewById(R.id.textView5);
        }
    }

    public DoctorsListAdapter(ArrayList<DoctorListItemModel> doctorsList){
        mDoctorsList = doctorsList;
    }

    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_doctor_item, parent, false);
        DoctorsViewHolder doctorsViewHolder = new DoctorsViewHolder(v);
        return doctorsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position) {
        DoctorListItemModel doctor = mDoctorsList.get(position);
        holder.mImageView.setImageResource(R.drawable.app_logo);

        // So far, uncommenting these will result to a crash

//        holder.docName.setText(doctor.getDocName());
//        holder.rating.setText(doctor.getRating());
//        holder.time.setText(doctor.getTime());
    }

    @Override
    public int getItemCount() {
        return mDoctorsList.size();
    }
}
